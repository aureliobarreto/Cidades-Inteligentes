/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import Model.Caminhao;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aurelio
 */
public class Emergencia extends Thread{
    Socket socketCaminhao;
    DatagramSocket socket;
    ArrayList<Caminhao> caminhoes;
    DataOutputStream dos;
    public Emergencia(ArrayList<Caminhao> c) throws SocketException{
     this.caminhoes = c;
     
    }
    
    @Override
    public void run(){
       
        try {
         
         socket = new DatagramSocket(5000);
         String received;
         String[] lixeiras;
         byte[] buf = new byte[512];
         DatagramPacket pack = new DatagramPacket(buf,buf.length);  
         socket.receive(pack);          
         received = new String(pack.getData(), 0, pack.getLength());
         lixeiras = received.split(":");
         System.out.println("Lixeiras Críticas");
         for(String a: lixeiras){
           System.out.println(a);
         }                  
        } catch (IOException ex) {
            Logger.getLogger(Emergencia.class.getName()).log(Level.SEVERE, null, ex);
        }
  }  
}
