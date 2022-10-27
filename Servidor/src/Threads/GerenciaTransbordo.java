/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import Model.Transbordo;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aurelio
 */
public class GerenciaTransbordo extends Thread {

    DatagramSocket socket;
    ArrayList<Transbordo> transbordos;
    byte[] buf;
    String ip;
    public GerenciaTransbordo(DatagramSocket s, ArrayList<Transbordo> t, String ip) {
        this.socket = s;
        this.transbordos = t;
        buf = new byte[512];
        this.ip = ip;
    }

    @Override
    public void run() {
        String mensagemTransbordo;
        String[] partesDaMensagem;
          try{
              
        while (true) {
            
            Transbordo trans = buscarTransbordo(ip);
            DatagramPacket packet = new DatagramPacket(buf,buf.length);          
            socket.receive(packet);
            mensagemTransbordo = new String(packet.getData(),0, packet.getLength());
            System.out.println(mensagemTransbordo);
            partesDaMensagem = mensagemTransbordo.split(":");
            if (partesDaMensagem[1].equals("1")) {
                if (trans != null) {
                    trans.setCap(3);
                    System.out.println("Transbordo: " + getIpUsuario() + " está cheio");
                } else {
                    System.out.println("Transbordo não encontrado");
                }
            } else {
                if (trans != null) {
                    trans.clear();
                    System.out.println("Transbordo: " + getIpUsuario() + " está disponível");
                } else {
                    System.out.println("Transbordo não encontrado");
                }
            }

        }
          } catch (IOException ex) {
                Logger.getLogger(GerenciaTransbordo.class.getName()).log(Level.SEVERE, null, ex);
            }
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

    /**
     * Método que retorna uma string contendo o ip do cliente
     *
     * @return ip do cliente
     */
    public String getIpUsuario() {
        String ip = socket.getInetAddress().toString();
        return ip;
    }

}
