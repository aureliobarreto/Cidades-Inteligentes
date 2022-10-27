/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;


import Model.Lixeira;
import Model.Caminhao;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Aurelio
 */
public class GerenciaLixeiras extends Thread{
    String ip;
    ArrayList<Lixeira> lixeiras;
    ArrayList<Caminhao> caminhoes;
    DatagramSocket ds;
    byte[] buf = new byte[256];
    
    public GerenciaLixeiras(String ip, ArrayList<Lixeira> l, ArrayList<Caminhao> cam, DatagramSocket ds){
        this.ip = ip;
        this.lixeiras = l;
        this.caminhoes = cam;
        this.ds = ds;
    }
    
    @Override
    public void run(){
        String mensagemLixeira;
        String[] partesDaMensagem;
        
        try{
          DatagramSocket socket = ds;  
        while(true){
            if(true){
            DatagramPacket dp = new DatagramPacket(buf, buf.length);            
            socket.receive(dp);            
            mensagemLixeira = new String(dp.getData(),0,dp.getLength());
            if(!mensagemLixeira.equals("Recolheu")){
            partesDaMensagem = mensagemLixeira.split(":");            
            Lixeira lix = buscarLixeira(ip);                            
                
                            if(partesDaMensagem[1].equals("90")){                               
                                lix.setCap(90);
                                System.out.println("Lixeira: "+ip+" está cheia");                                
                                                    
                               
                            }else{
                                if(lix != null){
                                    lix.clear();
                                System.out.println("Lixeira: "+ip+" está disponível");
                                }else{
                                    System.out.println("Lixeira não encontrada");
                                }
                            }
            }else{
                System.out.println("vai entrar aqui dnv");
              String aux = "Recolheu";
              buf = aux.getBytes();
              DatagramPacket pack = new DatagramPacket(buf, buf.length, dp.getAddress(), dp.getPort());
              ds.send(pack);
              
            }
        }else{
                ds.close();
                removerLixeira(ip);
                System.out.println("Lixeira: "+ip+" desconectada");
                break;
            }
        }
        }catch(IOException ex){
            
        }
    }
          /**
         * método que busca uma lixeira
         * @param ip ip correspondente a lixeira a ser buscada
         * @return devolve uma lixeira ou nulo caso não encontrado
         */
       public Lixeira buscarLixeira(String ip){
            Iterator it = lixeiras.iterator();
            while(it.hasNext()){
                Lixeira lix = (Lixeira) it.next();               
                if(ip.equals(lix.getIp())){
                    return lix;
                }
            }
            return null;
        }
       
         /**
         * método que atribuí um caminhão á uma lixeira
         * @param l lixeira em que será atribuída um caminhão
         * @param c caminhao que será setado na lixeira
         */
        public void atribuirLixeira(Lixeira l, Caminhao c){
            Lixeira aux = l;
            Caminhao cam = c;           
            aux.setCaminhao(cam);
            
        }
        
          /**
         * método que busca um caminhão
         * @param ip ip correspondente ao caminhão a ser buscado
         * @return devolve um caminhão ou nulo caso não encontrado
         */
        public Caminhao buscarCaminhao(String ip){
            Iterator it = caminhoes.iterator();
            Caminhao cam;
             while(it.hasNext()){
                 cam = (Caminhao) it.next();
                 if(cam.getIp().equals(ip)){
                     return cam;
                 }
             }
             return null;
        }
        /**
         * Método que retorna um caminhão que esteja disponível
         * @return Caminhão cuja capacidade seja inferior a 3
         */
        public Caminhao getCaminhaoDisponivel(){
            Iterator it = caminhoes.iterator();
            while(it.hasNext()){
                Caminhao cam = (Caminhao) it.next();
               
                    if(cam.getCap() <3){
                    return cam;
                    }
                
            }
          return null;  
        }
        
        public void removerLixeira(String ip){
            Lixeira temp = buscarLixeira(ip);
            lixeiras.remove((Lixeira)temp);
            if(buscarLixeira(ip) == null){
                System.out.println("Removeu");
            }
        }
}
