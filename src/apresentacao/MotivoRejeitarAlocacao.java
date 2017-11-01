package apresentacao;

import java.awt.Toolkit;
import javax.swing.JFrame;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Thiago
 */
public class MotivoRejeitarAlocacao extends javax.swing.JDialog {

    private JFrame previousFrame;
    
    /**
     * Creates new form MotivoRejeitarAlocacao
     */
    public MotivoRejeitarAlocacao() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(Recursos.LOGO_DO_MACKENZIE)));
    }
    
    public MotivoRejeitarAlocacao(JFrame previous) {
        initComponents();
        previousFrame = previous;
        previousFrame.setEnabled(false);
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(Recursos.LOGO_DO_MACKENZIE)));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txta_Motivo = new javax.swing.JTextArea();
        btn_Confirmar = new javax.swing.JToggleButton();
        btn_Cancelar = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Motivo da Rejeição");
        setAlwaysOnTop(true);
        setName("frm_MotivoRejeitarAlocacao"); // NOI18N
        setResizable(false);

        jLabel1.setText("Motivo da Rejeição:");

        txta_Motivo.setColumns(20);
        txta_Motivo.setRows(5);
        txta_Motivo.setToolTipText("Descreva o motivo de não poder suprir a ausência.");
        jScrollPane1.setViewportView(txta_Motivo);

        btn_Confirmar.setHideActionText(true);
        btn_Confirmar.setLabel("Confirmar");
        btn_Confirmar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ConfirmarMouseClicked(evt);
            }
        });

        btn_Cancelar.setLabel("Cancelar");
        btn_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_Cancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_Confirmar))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Confirmar)
                    .addComponent(btn_Cancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CancelarActionPerformed
        try {
            this.dispose();
            previousFrame.setEnabled(true);
            previousFrame.setVisible(true);
        } catch (Exception e) {
            System.out.println("Erro ao retornar para janela anterior!");
        }   
    }//GEN-LAST:event_btn_CancelarActionPerformed

    private void btn_ConfirmarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ConfirmarMouseClicked
        System.out.println("OI");
    }//GEN-LAST:event_btn_ConfirmarMouseClicked

    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btn_Cancelar;
    private javax.swing.JToggleButton btn_Confirmar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txta_Motivo;
    // End of variables declaration//GEN-END:variables
}
