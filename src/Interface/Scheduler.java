/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Scheduler.java
 *
 * Created on 06.04.2010, 14:14:06
 */

package Interface;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author support
 */
public class Scheduler extends javax.swing.JFrame {
    private LinkedList<schedulerURBD> listSched;
    /** Creates new form Scheduler */
    public Scheduler(LinkedList<schedulerURBD> _listSched) {
        initComponents();
        setLocationRelativeTo(null);
        this.listSched = _listSched;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonCloseScheludFrame = new javax.swing.JButton();
        jComboBoxFrequency = new javax.swing.JComboBox();
        jSpinnerTimer = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableListJob = new javax.swing.JTable();
        jButtonAddJob = new javax.swing.JButton();
        jButtonRemoveJob = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jButtonCloseScheludFrame.setText("Закрыть");
        jButtonCloseScheludFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseScheludFrameActionPerformed(evt);
            }
        });

        jComboBoxFrequency.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Один раз", "Каждый час", "Каждый день", "Каждую неделю", "Каждый месяц", "Каждый год" }));

        jSpinnerTimer.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), new java.util.Date(1262293200000L), null, java.util.Calendar.HOUR_OF_DAY));
        jSpinnerTimer.setPreferredSize(new java.awt.Dimension(124, 22));

        jTableListJob.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Время начала", "Периодичность"
            }
        ));
        jTableListJob.setEnabled(false);
        jTableListJob.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jTableListJob);

        jButtonAddJob.setText("+");
        jButtonAddJob.setPreferredSize(new java.awt.Dimension(42, 23));
        jButtonAddJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddJobActionPerformed(evt);
            }
        });

        jButtonRemoveJob.setText("-");
        jButtonRemoveJob.setPreferredSize(new java.awt.Dimension(41, 23));
        jButtonRemoveJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveJobActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jComboBoxFrequency, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSpinnerTimer, javax.swing.GroupLayout.PREFERRED_SIZE, 100, Short.MAX_VALUE))
                    .addComponent(jButtonCloseScheludFrame)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonAddJob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRemoveJob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(37, 37, 37))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSpinnerTimer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxFrequency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonAddJob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonRemoveJob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
                        .addComponent(jButtonCloseScheludFrame))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCloseScheludFrameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseScheludFrameActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonCloseScheludFrameActionPerformed

    private void jButtonAddJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddJobActionPerformed
        System.out.println(((Date)jSpinnerTimer.getValue()));
        this.listSched.add(new schedulerURBD(((Date)jSpinnerTimer.getValue()),(String)(jComboBoxFrequency.getSelectedItem())));
        this.listSched.getLast().createSCHED();
        ((DefaultTableModel)jTableListJob.getModel()).setRowCount(jTableListJob.getRowCount()+1);
        //jTableListJob.addRowSelectionInterval(0, 1);
        jTableListJob.setValueAt(this.listSched.getLast(),jTableListJob.getRowCount()-1,0);
        jTableListJob.setValueAt(this.listSched.getLast().getFrequency(),jTableListJob.getRowCount()-1,1);
    }//GEN-LAST:event_jButtonAddJobActionPerformed

    private void jButtonRemoveJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveJobActionPerformed

    }//GEN-LAST:event_jButtonRemoveJobActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddJob;
    private javax.swing.JButton jButtonCloseScheludFrame;
    private javax.swing.JButton jButtonRemoveJob;
    private javax.swing.JComboBox jComboBoxFrequency;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinnerTimer;
    private javax.swing.JTable jTableListJob;
    // End of variables declaration//GEN-END:variables

}
