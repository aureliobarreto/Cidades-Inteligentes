/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import Model.Lixeira;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aurelio
 */
public class RotaCaminhao extends Thread{
    DataOutputStream dos;
    Socket socket, socketCaminhao;
    ArrayList<Lixeira> lixeiras;
    public RotaCaminhao(Socket s, Socket caminhao, ArrayList<Lixeira> l){
    this.socket = s;
    this.lixeiras = l;
    this.socketCaminhao = caminhao;
    }
    
    @Override
    public void run(){
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            String aux;
        while(true){
            Iterator it1 = lixeiras.iterator();
            while(it1.hasNext()){
                Lixeira temp = (Lixeira) it1.next();
                String mds[] = temp.getIp().split("/");
                InetAddress ip = InetAddress.getByName(mds[1]);
                byte[] buf = new byte[128];
                DatagramSocket s = new DatagramSocket();
                DatagramPacket dp = new DatagramPacket(buf, buf.length,ip,4447);
                try{
                    s.send(dp);
                    
                }catch(IOException e){
                   lixeiras.remove(temp);                   
                }
                
            }
            
            
            aux = "";
            Iterator it = lixeiras.iterator();
            while(it.hasNext()){
                Lixeira l = (Lixeira)it.next();
                aux = aux+l.getIp()+"-"+String.valueOf(l.getCap())+":";
            }
            if(lixeiras.size() > 0 && socketCaminhao.isConnected()){
                if(!socketCaminhao.isClosed()){
                dos.writeUTF(aux);
                }else{
                    socket.close();
                }
            }
           Thread.sleep(3000);
        }
        } catch (IOException ex) {
            Logger.getLogger(RotaCaminhao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(RotaCaminhao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
