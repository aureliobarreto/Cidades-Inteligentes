/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import Model.Caminhao;
import Model.Lixeira;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aurelio
 */
public class Multicast extends Thread{
    MulticastSocket socket, socketLixeirasCriticas;
    byte[] buf = new byte[256];
    ArrayList<Caminhao> caminhoes;
    ArrayList<Lixeira> lixeiras;
    Emergencia emergencia;
    public Multicast(ArrayList<Caminhao> c, ArrayList<Lixeira> l){
        this.caminhoes = c;
        this.lixeiras = l;
    }
    @Override
    public void run() {
        
        try {
            emergencia = new Emergencia(caminhoes);
            emergencia.start();
            socket = new MulticastSocket(5333);
            InetAddress group = InetAddress.getByName("230.0.0.1");
            socket.joinGroup(group);
            
             while (true) {                 
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println(received);         
            
            if(received.equals("NecessitoCaminhao") && verifica()){
                String aux = "PossoAjudar:5000";
                buf = aux.getBytes();                
                packet = new DatagramPacket(buf, buf.length,group,5333);
                socket.send(packet);
                
                
            }else if(!received.equals("NecessitoCaminhao")){
                if(!verifica()){
              String[] recebe = received.split(":");
              String lixeirasCriticas = preparaLixeiras();
              buf = lixeirasCriticas.getBytes();
              InetAddress ip = packet.getAddress();             
              packet = new DatagramPacket(buf, buf.length,ip, Integer.valueOf(recebe[1]));
            }
            }
          }
        } catch (IOException ex) {
            Logger.getLogger(Multicast.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Método que verifica a disponibilidade do caminhão
     * @return disponibilidade do caminhão
     */
    public boolean verifica(){
        Iterator it = caminhoes.iterator();
        while(it.hasNext()){
            Caminhao c = (Caminhao) it.next();
            if(c.isEstado()){
                return true;
            }
        }
        return false;
    }
    /**
     * Método que prepara uma unica string com o ip e estado as lixeiras
     * @return 
     */
    public String preparaLixeiras(){
         String aux = "";
            Iterator it = lixeiras.iterator();
            while(it.hasNext()){
                Lixeira l = (Lixeira)it.next();
                aux = aux+l.getIp()+"-"+String.valueOf(l.getCap())+":";
            }
            return aux;
    }
    
}
