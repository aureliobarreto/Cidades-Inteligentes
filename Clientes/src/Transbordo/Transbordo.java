package Transbordo;

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
public class Transbordo extends javax.swing.JFrame {
    DatagramSocket socket, socketRetorno;
     InetAddress address;
     private byte[] buf;
    int cap, var;
    TransbordoEscutaEscrita tee;
   // construtor
    public Transbordo() throws SocketException, UnknownHostException, IOException {
        initComponents();
        socket = new DatagramSocket();
        socketRetorno = new DatagramSocket(4448);
        address = InetAddress.getByName("localhost");
        this.cap = 0;
        this.var =0;
        capTransbordo.setText(Integer.toString(cap));
        buf = new byte[512];
        
    }
    
    public void seApresentar() throws IOException{
        String msg = "Hello";
        buf = msg.getBytes();
        DatagramPacket packett = new DatagramPacket(buf, buf.length, address, 4002);
        socket.send(packett); 
      }
    /**
     * Método que solicita a comunicação entre cliente e servidor
     */
    public void iniciaTransbordo() throws IOException{
        tee = new TransbordoEscutaEscrita();
        tee.start();
        seApresentar();
    }
    
    /**
     * Método que incrementa a capacidade do transbordo
     */
    public void incrementa(){
        if(cap < 1){
        cap++;
        capTransbordo.setText(Integer.toString(cap));
        }
    }
    /**
     * Método que devolve a capacidade atual do transbordo
     * @return cap
     */
    public int getCap(){
        return this.cap;
    }
    /**
     * Método que limpa a capacidade do transbordo e atualiza a label da interface
     */
    public void clear(){
        this.cap = 0;
        capTransbordo.setText("0");
        this.var = 0;
    }
    
     public String enviarMsg(String msg) throws IOException {
        buf = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4444);
        socket.send(packet);
        
        String received = new String(packet.getData(), 0, packet.getLength());
        return received;
    }
      
                // ***********THREAD**********
    
    public class TransbordoEscutaEscrita extends Thread{
        //Construtor da thread
        public TransbordoEscutaEscrita(){
            
        }
        
        @Override
        public void run(){
            String recebe = null;
            
        try{
         while(true){
             DatagramPacket packet = new DatagramPacket(buf, buf.length);
             socketRetorno.receive(packet);
                recebe = new String(packet.getData(),0,packet.getLength());      
                
                 if(recebe.equals("Entregou")){
                     incrementa();
                 }
                 if(getCap() == 1 && var<1){
                     enviarMsg("3:1");
                     
                     var++;
                 }
             
             
         }
              } catch (IOException ex) {
                 Logger.getLogger(Transbordo.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
    } // end Thread
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        capTransbordo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        iconTransbordo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Capacidade:");

        capTransbordo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        capTransbordo.setText("jLabel2");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("/1");

        jButton1.setText("Limpar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        iconTransbordo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/transbordoIcon.jpg"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(90, Short.MAX_VALUE)
                .addComponent(iconTransbordo)
                .addGap(77, 77, 77))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(239, 239, 239)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(capTransbordo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(287, 287, 287)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iconTransbordo)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(capTransbordo)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
/**
 * Evento que aciona o esvaziamento do transbordo
 * @param evt evento enviado da interface
 */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(getCap() >= 1){
            try {
                
                enviarMsg("3:2");
                this.clear();
            } catch (IOException ex) {
                Logger.getLogger(Transbordo.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }else if(getCap() < 1){
            JOptionPane.showMessageDialog(null, "O Transbordo não está totalmente cheio", "", JOptionPane.WARNING_MESSAGE);
            
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * Método main
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
            java.util.logging.Logger.getLogger(Transbordo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Transbordo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Transbordo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Transbordo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Transbordo tr;
                try {
                    tr = new Transbordo();
                    tr.setVisible(true);
                    tr.iniciaTransbordo();
                } catch (SocketException ex) {
                    Logger.getLogger(Transbordo.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnknownHostException ex) {
                    Logger.getLogger(Transbordo.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Transbordo.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel capTransbordo;
    private javax.swing.JLabel iconTransbordo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
