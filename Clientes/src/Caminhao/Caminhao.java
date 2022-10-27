/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Caminhao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Aurelio
 */
public class Caminhao extends javax.swing.JFrame {

    EscutaEscrita esc;
    private int cap, var;
    boolean estado;
    Socket cliente, seApresenta, rota;
    DataOutputStream dos;
    DataInputStream dis, dis1;
    DefaultTableModel dtm;

    //CONSTRUTOR
    public Caminhao() throws IOException {
        initComponents();
        this.cap = 0;
        this.var = 0;
        capCaminhao.setText(Integer.toString(cap));
        dtm = (DefaultTableModel) tabelaRota.getModel();

    }

    public void iniciaCaminhao() throws IOException {
        try {
            cliente = new Socket("localhost", 4445);
            seApresenta = new Socket("localhost", 4001);
            rota = new Socket("localhost", 5555);
            dos = new DataOutputStream(seApresenta.getOutputStream());
            dos.writeUTF("Hello");
            dos.flush();
            esc = new EscutaEscrita(rota);
            esc.start();
        } catch (IOException e) {
            System.out.println("erro!");
        }

    }
    //************* MÉTODOS BÁSICOS ************

    /**
     * @return retorna a capacidade atual do caminhão
     */
    public int getCap() {
        return cap;
    }

    /**
     * Método que incrementa a capacidade do caminhão
     */
    public void addCap() {
        this.cap++;
    }

    /**
     * Método que limpa o caminhão atribuindo zero a sua variável
     */
    public void clear() {
        this.cap = 0;
        this.var = 0;
        capCaminhao.setText("0");
    }

    //************THREADS ***************
    public class EscutaEscrita extends Thread {

        Socket cliente;
        DataInputStream dis;
        DataOutputStream dos;
        String resposta, partesMensagem[];

        public EscutaEscrita(Socket s) throws IOException {
            cliente = s;
            dis = new DataInputStream(cliente.getInputStream());
            dos = new DataOutputStream(cliente.getOutputStream());

        }

        @Override
        @SuppressWarnings("empty-statement")
        public void run() {

            try {
                resposta = null;
                int i = 0;
                while (true) {

                    resposta = dis.readUTF();
                    
                    if (!resposta.equals("ok") && !resposta.equals("semTransbordo") && resposta != null) {
                        
                        partesMensagem = resposta.split(":");
                        for(int d = 0; d<dtm.getRowCount(); d++){
                            //dtm.setValueAt(null, d, 0);
                            //dtm.setValueAt(null, d, 1);
                        }
                        for (String a : partesMensagem) {
                            String[] pack;
                            pack = a.split("-");
                           // for (int j = 0; j < dtm.getRowCount(); j++) {
                                for (int z = 0; z < dtm.getRowCount(); z++){                                
                                    if (dtm.getValueAt(z, 0) != null) {                                        
                                        if (dtm.getValueAt(z, 0).equals(pack[0])){
                                            if (pack[1].equals("90")) {
                                                dtm.setValueAt("Cheia", z, 1);
                                            } else {
                                                dtm.setValueAt("Vazia", z, 1);
                                            }
                                        }

                                    } else {
                                        boolean temp = false;
                                        for(int ah = 0; ah < dtm.getRowCount(); ah++){                                            
                                           if(dtm.getValueAt(ah,0) != null){
                                             if(dtm.getValueAt(ah,0).equals(pack[0])){
                                               temp = true;  
                                             }
                                           }
                                        }
                                        if(!temp){                                            
                                         dtm.setValueAt(pack[0], z, 0);
                                        if (pack[1].equals("90")) {
                                            dtm.setValueAt("Cheia", z, 1);
                                        } else {
                                            dtm.setValueAt("Vazia", z, 1);
                                         }   
                                       }
                                        
                                    }                                    
                                }

                            //}
                        }

                        i++;
                        if (i >= 3) {
                            i = 0;
                        }

                    }
                    if (getCap() >= 3 && var < 1) {
                        dos.writeUTF("2:2");
                        dos.flush();
                        var++;

                    }

                }
            } catch (IOException ex) {
                System.out.println("Erro!");
            }
        }
    } // END THREAD

    public class gerenciaRota extends Thread {

        public gerenciaRota() {

        }

        @Override
        public void run() {

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator16 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        capCaminhao = new javax.swing.JLabel();
        btnEsvaziar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaRota = new javax.swing.JTable();
        camIcon2 = new javax.swing.JLabel();
        CheckBox = new javax.swing.JCheckBox();
        btnColeta = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Rota");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Capacidade:");

        capCaminhao.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        capCaminhao.setText("cap here");

        btnEsvaziar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnEsvaziar.setText("Esvaziar");
        btnEsvaziar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEsvaziarActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("/3");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/caminhaoIcon.jpg"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Yu Gothic Medium", 1, 14)); // NOI18N
        jLabel6.setText("Coleta Inteligente");

        tabelaRota.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "IP", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tabelaRota);

        CheckBox.setText("Quebrado");
        CheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckBoxActionPerformed(evt);
            }
        });

        btnColeta.setText("Coletar");
        btnColeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnColetaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnEsvaziar)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(capCaminhao, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addGap(109, 109, 109)
                        .addComponent(CheckBox))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 15, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(camIcon2, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(142, 142, 142))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(32, 32, 32))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(53, 53, 53))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnColeta)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(capCaminhao)
                            .addComponent(jLabel4))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(242, 242, 242)
                                .addComponent(camIcon2, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 212, Short.MAX_VALUE)
                                        .addComponent(btnColeta))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnEsvaziar)
                                        .addGap(28, 28, 28)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(26, 26, 26))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel6))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(CheckBox)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addGap(39, 39, 39)))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEsvaziarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEsvaziarActionPerformed

        if (getCap() == 3) {
            try {

                dos = new DataOutputStream(cliente.getOutputStream());
                dos.writeUTF("2:4");
                dos.flush();
                clear();

                try {
                    dos.writeUTF("2:3");
                    dos.flush();
                } catch (IOException e) {
                    System.out.println("erro!");
                }

            } catch (IOException ex) {
                Logger.getLogger(Caminhao.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (getCap() < 3) {
            JOptionPane.showMessageDialog(null, "Caminhão não está totalmente cheio", "", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnEsvaziarActionPerformed

    private void CheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckBoxActionPerformed
        if (CheckBox.isSelected()) {
            this.estado = false;
            try {
                dos = new DataOutputStream(cliente.getOutputStream());
                dos.writeUTF("2:5");
                dos.flush();
            } catch (IOException ex) {
                Logger.getLogger(Caminhao.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                dos.writeUTF("2:6");
                dos.flush();
            } catch (IOException ex) {
                Logger.getLogger(Caminhao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_CheckBoxActionPerformed

    private void btnColetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnColetaActionPerformed
        if (cap < 3) {
            for (int i = 0; i < tabelaRota.getRowCount(); i++) {
                
                    if (dtm.getValueAt(i, 1) != null && dtm.getValueAt(i, 1).toString().equals("Cheia")) {
                        dtm.setValueAt("Vazia", i, 1);
                        try {
                            dos = new DataOutputStream(cliente.getOutputStream());
                            dos.writeUTF("2:1:" + (String)dtm.getValueAt(i, 0));
                            dos.flush();
                            addCap();
                            capCaminhao.setText(Integer.toString(getCap()));
                            break;
                        } catch (IOException ex) {
                            Logger.getLogger(Caminhao.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                
            }
        } else {
            JOptionPane.showMessageDialog(null, "Caminhão cheio", "", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnColetaActionPerformed

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
            java.util.logging.Logger.getLogger(Caminhao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Caminhao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Caminhao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Caminhao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Caminhao c = new Caminhao();
                    c.setVisible(true);
                    c.iniciaCaminhao();

                } catch (IOException ex) {
                    Logger.getLogger(Caminhao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox CheckBox;
    private javax.swing.JButton btnColeta;
    private javax.swing.JButton btnEsvaziar;
    private javax.swing.JLabel camIcon2;
    private javax.swing.JLabel capCaminhao;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JTable tabelaRota;
    // End of variables declaration//GEN-END:variables

}
