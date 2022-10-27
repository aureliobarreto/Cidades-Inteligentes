package Lixeira;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Aurelio
 */
public class Lixeira extends javax.swing.JFrame {
    //               ***** DECLARAÇÃO DE VARIÁVEIS/OBJETOS  ********
    
    //EscritaThread escrita = new EscritaThread();
     DatagramSocket socket, socketRetorno;
     InetAddress address;
     private byte[] buf;
    
   
    
    
     public Lixeira() throws SocketException, UnknownHostException, IOException{
        initComponents();       
        iniciaCliente();
        
        //EscritaThread et = new EscritaThread(cliente);
       // et.start();
    }
     /**
      * Método que envia soliticação para o servidor e a mensagem de identificação
     * @throws java.net.SocketException
     * @throws java.net.UnknownHostException
      */
      public void iniciaCliente() throws SocketException, UnknownHostException, IOException{       
          
          socket = new DatagramSocket();
          socketRetorno = new DatagramSocket(4447);
          address = InetAddress.getByName("localhost");
          seApresentar("Hello");
          
      }
      
      public void seApresentar(String msg) throws IOException{
         buf = msg.getBytes();
        DatagramPacket packett = new DatagramPacket(buf, buf.length, address, 4000);
        socket.send(packett); 
      }
      
      public DatagramPacket enviarMsg(String msg) throws IOException {
        buf = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4444);
        socket.send(packet);        
        return packet;
    }
 
    /**
     * Método que limpa a lixeira e avisa ao servidor que está disponível
     */
    public void clear(){      
        
       try{           
          enviarMsg("1:0");          
          labelAtualiza.setText("Disponível");
          btnEncher.setVisible(true);
       }catch(IOException e){
           JOptionPane.showMessageDialog(null, "Erro ao enviar a mensagem", "", JOptionPane.WARNING_MESSAGE);
       }
    }
    /**
     * Método que enche a lixeira e avisa ao servidor o seu estado
     */
    public void enche(){  
        String resposta = null;
      try{                   
                    
          labelAtualiza.setText("Cheia");    
          btnEncher.hide();
          DatagramPacket packett = new DatagramPacket(buf, buf.length);
          packett = enviarMsg("1:90");
          socketRetorno.receive(packett);
          resposta = new String(packett.getData(), 0, packett.getLength());
          System.out.println(resposta);
        if(resposta.equals("Reco")){
            clear();
        }else if(resposta.equals("semCaminhão")){
            JOptionPane.showMessageDialog(null, "Não existem caminhões disponíveis no momento", "", JOptionPane.WARNING_MESSAGE);
            labelAtualiza.setText("Disponível"); 
        }
      }catch(IOException e){
          JOptionPane.showMessageDialog(null, "Erro ao enviar a mensagem", "", JOptionPane.WARNING_MESSAGE);
      }
       
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu3 = new javax.swing.JMenu();
        jLabel1 = new javax.swing.JLabel();
        btnEncher = new javax.swing.JButton();
        labelEstado = new javax.swing.JLabel();
        lixeiraIcon = new javax.swing.JLabel();
        labelAtualiza = new javax.swing.JLabel();

        jMenu3.setText("jMenu3");

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnEncher.setText("Encher");
        btnEncher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEncherActionPerformed(evt);
            }
        });

        labelEstado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelEstado.setText("Estado da lixeira: ");

        lixeiraIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lixeiraIcon.jpg"))); // NOI18N

        labelAtualiza.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelEstado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelAtualiza, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lixeiraIcon)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(180, 180, 180)
                        .addComponent(btnEncher)))
                .addContainerGap(94, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lixeiraIcon)
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelEstado)
                    .addComponent(labelAtualiza, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEncher)
                .addGap(26, 26, 26))
        );

        setSize(new java.awt.Dimension(461, 345));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
                   
    
                        // ******************** EVENTOS  **********************   
    
    /**
     * Envento que aciona o método enche
     * @param evt evento recebido da interface
     */
    private void btnEncherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEncherActionPerformed
        enche();
    }//GEN-LAST:event_btnEncherActionPerformed
    
                                // VOID MAIN 
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Lixeira.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Lixeira.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Lixeira.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Lixeira.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {                 
                    new Lixeira().setVisible(true);
                } catch (SocketException | UnknownHostException ex) {
                    Logger.getLogger(Lixeira.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Lixeira.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        
    } // end main

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEncher;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JLabel labelAtualiza;
    private javax.swing.JLabel labelEstado;
    private javax.swing.JLabel lixeiraIcon;
    // End of variables declaration//GEN-END:variables

   
}
