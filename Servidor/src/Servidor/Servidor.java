package Servidor;
import Model.Lixeira;
import Model.Caminhao;
import Model.Transbordo;
import Threads.GerenciaCaminhao;
import Threads.GerenciaLixeiras;
import Threads.GerenciaTransbordo;
import Threads.Multicast;
import Threads.RotaCaminhao;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aurelio
 */
public class Servidor extends Thread{
    
     ServerSocket servidorCaminhao, recepcionaCaminhao, rotaCaminhao;
     Socket provisorio, socketCaminhao, socketRota;
     DatagramSocket socketRecepcaoLixeira, socketRecepcaoTransbordo;
     DatagramSocket socketLixeira;     
     DatagramSocket socketTransbordo;
     String recLixeira, recCaminhao, recTransbordo;
     byte[] lix = new byte[512], trans = new byte[512];
     ArrayList lixeiras, caminhoes, transbordos;
     DataInputStream dis ;
     RecebeCaminhao rc;
     RecebeTransbordo rt;
     Multicast multicast;
     /**
      * Construtor onde inicia o servidor instanciando Sockets e arrays que serão essenciais durante a execução
      * @throws IOException  exceção lançada caso há erro com inicialização do socket
      */
     public Servidor() throws IOException{
         System.out.println("Iniciando servidor...");
         lixeiras = new <Lixeira> ArrayList();
         caminhoes = new <Caminhao> ArrayList();
         transbordos = new  <Transbordo>ArrayList();
         recepcionaCaminhao = new ServerSocket(4001);
         servidorCaminhao = new ServerSocket(4445);
         rotaCaminhao = new ServerSocket(5555);
         multicast = new Multicast(caminhoes, lixeiras);
         multicast.start();
         rc = new RecebeCaminhao();
         rt = new RecebeTransbordo();         
         rc.start();
         rt.start();
         new Thread(this).start();         
         System.out.println("Servidor iniciado aguardando clientes");
         
     }
     
     /**
      * Método que executará um loop gerenciando as lixeiras que solicitam comunicação
      */
     @Override
     public void run(){
         try {
             socketRecepcaoLixeira = new DatagramSocket(4000);
             socketLixeira = new DatagramSocket(4444);
         } catch (SocketException ex) {
             Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);     
         }
   
        try{
         while(true){  
            DatagramPacket packetLix = new DatagramPacket(lix, lix.length);                             
            socketRecepcaoLixeira.receive(packetLix);            
            String received = new String(packetLix.getData(), 0, packetLix.getLength());
            
            if(received.equals("Hello")){
              Lixeira temp = new Lixeira( packetLix.getAddress().toString(), socketLixeira);
              lixeiras.add(temp);
              GerenciaLixeiras gerenciaLixeira = new GerenciaLixeiras(temp.getIp(), lixeiras, caminhoes, socketLixeira);             
              gerenciaLixeira.start();              
              System.out.println("Uma lixeira se conectou com o IP: "+packetLix.getAddress().toString());               
                
            }
   
         }
     }catch(IOException e){        
         System.out.println("Erro no servidor!");
     }
         
     }
     
     /**
      * Thread que receberá os caminhões que se conectarem
      */
     public class RecebeCaminhao extends Thread{
         
         @Override
         public void run(){            
             try{
            
             while(true){
                socketCaminhao = servidorCaminhao.accept();
                provisorio = recepcionaCaminhao.accept();
                socketRota = rotaCaminhao.accept();
                dis = new DataInputStream(provisorio.getInputStream());
                 
                recCaminhao = dis.readUTF();
                     
                 
                if(recCaminhao.equals("Hello")){ 
                Caminhao temp = new Caminhao(provisorio, provisorio.getInetAddress().toString());                
                caminhoes.add(temp);                
                GerenciaCaminhao gerenciaCaminhao = new GerenciaCaminhao(socketCaminhao, caminhoes, lixeiras, transbordos);             
                gerenciaCaminhao.start();
                RotaCaminhao rota = new RotaCaminhao(socketRota, socketCaminhao, lixeiras);
                rota.start();
                System.out.println("Um caminhão se conectou com o IP: "+provisorio.getInetAddress().toString());
                }
             }
                }catch(IOException ex){
                 Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
             }
     }
   }
     
      /**
      * Thread que receberá os transbordos que se conectarem
      */
     public class RecebeTransbordo extends Thread{
         @Override
         public void run(){ 
            
             try{
              socketTransbordo = new DatagramSocket(4446);
              socketRecepcaoTransbordo = new DatagramSocket(4002);   
                 
             while(true){
              DatagramPacket packetTrans = new DatagramPacket(trans, trans.length);
              socketRecepcaoTransbordo.receive(packetTrans);
              String received2 = new String(packetTrans.getData(), 0, packetTrans.getLength());
              
            if(received2.equals("Hello")){
                Transbordo temp = new Transbordo(socketTransbordo, packetTrans.getAddress().toString());                     
                transbordos.add(temp);
                GerenciaTransbordo gerenciaTransbordo = new GerenciaTransbordo(socketTransbordo, transbordos, packetTrans.getAddress().toString());             
                gerenciaTransbordo.start();
                System.out.println("Um transbordo se conectou com o IP: "+packetTrans.getAddress().toString());
                
            }
             }
         }   catch (IOException ex) {
                 Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
     }
     
             /*          *********** PROTOCOLO DE COMUNICAÇÃO ************
                                                  
                              "1:90" É uma lixeira e sua capacidade é 90% ou seja, está cheia
                              "1:0" É uma lixeira e está disponível 
                    
                              "2:1:183.333.444" Um caminhão recolheu a lixeira com o ip 183.333.444
                              "2:2" Um caminhão está cheio
                              "2:3" Um caminhão está disponível
                              "2:4" Um caminhão está cheio e solicita descarrego em algum transbordo disponível
                    
                              "3:1" Um trnasbordo está cheio
                              "3:2" Um transbordo está disponível
                    */ 
          
     
     /**
      * Método main
      * @param args 
      */
     public static void main (String args[]){
         try{
             new Servidor();
         }catch(Exception e){
             System.out.println("erro");
         }
     }
}
