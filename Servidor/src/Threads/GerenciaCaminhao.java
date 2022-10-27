/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import Model.Caminhao;
import Model.Lixeira;
import Model.Transbordo;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aurelio
 */
public class GerenciaCaminhao extends Thread {

    Socket socket, socketRetorno;
    ArrayList<Caminhao> caminhoes;
    ArrayList<Lixeira> lixeiras;
    ArrayList<Transbordo> transbordos;
    DataInputStream dis;
    DataOutputStream dos;

    public GerenciaCaminhao(Socket s, ArrayList<Caminhao> c, ArrayList<Lixeira> l, ArrayList<Transbordo> t) throws IOException {
        this.socket = s;
        this.caminhoes = c;
        this.lixeiras = l;
        this.transbordos = t;
       
    }

   

    @Override
    public void run() {
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(GerenciaCaminhao.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] partesDaMensagem;
 
        while (true) {
            if (socket.isConnected()) {
                
                String mensagemCliente = null;
                try {
                    mensagemCliente = dis.readUTF();
                } catch (IOException e) {
                    try {
                        socket.close();
                        System.out.println("O Caminhão: " + getIpUsuario() + " foi desconectado!");
                        removerCaminhao(getIpUsuario());
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(GerenciaCaminhao.class.getName()).log(Level.SEVERE, null, ex);
                    }
                      
                    
                }
                
                partesDaMensagem = mensagemCliente.split(":");
                if (partesDaMensagem[1].equals("1")) {
                    System.out.println("Caminhao: " + getIpUsuario() + " recolheu a lixeira " + partesDaMensagem[2]);
                    Caminhao cam = buscarCaminhao(socket.getInetAddress().toString());
                    try {
                        Lixeira lix = buscarLixeira(partesDaMensagem[2]);
                        if (lix != null) {
                            String msg = "Recolheu";
                            String r = lix.getIp();
                            String s[] = r.split("/");
                            InetAddress ip = InetAddress.getByName(s[1]);
                            DatagramSocket ds = new DatagramSocket();                                                       
                            byte[] temp = msg.getBytes();
                            DatagramPacket packet = new DatagramPacket(temp, temp.length, ip , 4447);
                            ds.send(packet);
                            System.out.println("era pra enviar");
                            cam.addCap();
                            lix.clear();
                                                              
                        } else {
                            System.out.println("lixeira não encontrada");
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(GerenciaCaminhao.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (partesDaMensagem[1].equals("2")) {
                    System.out.println("Caminhão: " + socket.getInetAddress().toString() + " está cheio");
                } else if (partesDaMensagem[1].equals("3")) {
                    Caminhao cam = buscarCaminhao(socket.getInetAddress().toString());
                    cam.clear();
                    System.out.println("Caminhão: " + socket.getInetAddress().toString() + " está disponível");
                } else if (partesDaMensagem[1].equals("4")) {
                    Transbordo trans = getTransbordoDisponivel();
                    if (trans != null) {
                        try {
                            String msg = "Entregou";
                            DatagramSocket ds = new DatagramSocket();
                            int porta = 4448;                            
                            InetAddress ip = InetAddress.getByName(trans.getIp());
                            DatagramPacket dp = new DatagramPacket(msg.getBytes(), msg.getBytes().length, ip, porta);
                            ds.send(dp);                            
                            trans.addCam();

                        } catch (IOException ex) {
                            Logger.getLogger(GerenciaCaminhao.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }else if(partesDaMensagem[1].equals("5")){
                    try {                        
                        caminhaoComDefeito();
                        System.out.println("Caminhão: "+socket.getInetAddress().toString()+" está com falha");
                        Caminhao temp = (Caminhao)buscarCaminhao(getIpUsuario());
                        temp.setEstado(false);
                        
                    } catch (UnknownHostException ex) {
                        Logger.getLogger(GerenciaCaminhao.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(GerenciaCaminhao.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else if(partesDaMensagem[1].equals("6")){
                    System.out.println("Caminhão"+getIpUsuario()+" Consertado");
                    Caminhao temp = (Caminhao)buscarCaminhao(getIpUsuario());
                    temp.setEstado(true);
                }
            }else{
                System.out.println("Desconectou porra");
                        
            }
        }
        
    }

    /**
     * método que busca uma lixeira
     *
     * @param ip ip correspondente a lixeira a ser buscada
     * @return devolve uma lixeira ou nulo caso não encontrado
     */
    public Lixeira buscarLixeira(String ip) {
        Iterator it = lixeiras.iterator();
        while (it.hasNext()) {
            Lixeira lix = (Lixeira) it.next();
            if (ip.equals(lix.getIp())) {
                return lix;
            }
        }
        return null;
    }

    /**
     * método que atribuí um caminhão á uma lixeira
     *
     * @param l lixeira em que será atribuída um caminhão
     * @param c caminhao que será setado na lixeira
     */
    public void atribuirLixeira(Lixeira l, Caminhao c) {
        Lixeira aux = l;
        Caminhao cam = c;
        aux.setCaminhao(cam);

    }

    /**
     * método que busca um caminhão
     *
     * @param ip ip correspondente ao caminhão a ser buscado
     * @return devolve um caminhão ou nulo caso não encontrado
     */
    public Caminhao buscarCaminhao(String ip) {
        Iterator it = caminhoes.iterator();
        Caminhao cam;
        while (it.hasNext()) {
            cam = (Caminhao) it.next();
            if (cam.getIp().equals(ip)) {
                return cam;
            }
        }
        return null;
    }

    /**
     * Método que retorna um caminhão que esteja disponível
     *
     * @return Caminhão cuja capacidade seja inferior a 3
     */
    public Caminhao getCaminhaoDisponivel() {
        Iterator it = caminhoes.iterator();
        while (it.hasNext()) {
            Caminhao cam = (Caminhao) it.next();

            if (cam.getCap() < 3) {
                return cam;
            }

        }
        return null;
    }
    
     public void removerCaminhao(String ip) {
        Caminhao temp = buscarCaminhao(ip);
        caminhoes.remove((Caminhao) temp);
        
    }
    
     public void removerLixeira(String ip) {
        Lixeira temp = buscarLixeira(ip);
        lixeiras.remove((Lixeira) temp);
        if (buscarLixeira(ip) == null) {
            System.out.println("Removeu");
        }
    }

   

    /**
     * Método que retorna uma string contendo o ip do cliente
     *
     * @return ip do cliente
     */
    public String getIpUsuario() {
        String ip = socket.getInetAddress().toString();
        return ip;
    }

    /**
     * Método que retorna um transbordo que esteja disponível
     *
     * @return Transbordo cuja capacidade seja inferior a 3
     */
    public Transbordo getTransbordoDisponivel() {
        Iterator it = transbordos.iterator();
        while (it.hasNext()) {
            Transbordo trans = (Transbordo) it.next();

            if (trans.getCap() < 1) {
                return trans;
            }
        }
        return null;
    }

    /**
     * método que busca um transbordo
     *
     * @param ip ip correspondente ao transbordo a ser buscado
     * @return devolve um transbordo ou nulo caso não encontrado
     */
    public Transbordo buscarTransbordo(String ip) {
        Iterator it = transbordos.iterator();
        while (it.hasNext()) {
            Transbordo trans = (Transbordo) it.next();

            if (trans.getIp().equals(ip)) {
                return trans;
            }
        }
        return null;
    }
    
    public void caminhaoComDefeito() throws SocketException, UnknownHostException, IOException{
        MulticastSocket sockett;
        InetAddress group;
        byte[] buf;
 
        String multicastMessage = "NecessitoCaminhao";
        sockett = new MulticastSocket(5333);
        group = InetAddress.getByName("230.0.0.1");
        sockett.joinGroup(group);
        buf = multicastMessage.getBytes();
        
        DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 5333);
        sockett.send(packet);
        sockett.leaveGroup(group);
        sockett.close();
       
    }

}
