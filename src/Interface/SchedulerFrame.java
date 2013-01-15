package Interface;

import java.util.Date;

public class SchedulerFrame extends javax.swing.JFrame {

    schedulerURBD sched=null;
    MainFrame mF;
    /** Creates new form SchedulerFrame */
    public SchedulerFrame() {
        initComponents();
        jCheckBox3.setVisible(false);
        jRadioButtonFull.setVisible(false);
        jRadioButtonDownload.setVisible(false);
        jRadioButtonUpload.setVisible(false);
    }

    public SchedulerFrame(schedulerURBD _sched,MainFrame mf) {
        initComponents();
        jCheckBox3.setVisible(false);
        jRadioButtonFull.setVisible(false);
        jRadioButtonDownload.setVisible(false);
        jRadioButtonUpload.setVisible(false);
        mF = mf;
        sched=_sched;
        if ((sched!=null)&(sched.pause()))
        {
            if(sched.jobDate != null)
                this.jSpinnerTimer.setValue(sched.jobDate);
            if(sched.getFrequency() != 0)
                this.jComboBoxFrequency.setSelectedIndex(sched.getFrequency());
            if(!"".equals(sched.time))
                this.jTextFieldSchedulerFormat.setText(sched.time); 
        }
        jCheckBoxExchange.setSelected(((Boolean)sched.job.getJobDataMap().get("exchange")));
        jCheckBoxSync.setSelected(((Boolean)sched.job.getJobDataMap().get("sync")));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonGroupExchange = new javax.swing.ButtonGroup();
        jButtonОК = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jComboBoxFrequency = new javax.swing.JComboBox();
        jSpinnerTimer = new javax.swing.JSpinner();
        jCheckBoxExchange = new javax.swing.JCheckBox();
        jCheckBoxSync = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jRadioButtonFull = new javax.swing.JRadioButton();
        jRadioButtonDownload = new javax.swing.JRadioButton();
        jRadioButtonUpload = new javax.swing.JRadioButton();
        jTextFieldSchedulerFormat = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Добавить задание");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonОК.setText("Добавить");
        jButtonОК.setActionCommand("Окай");
        jButtonОК.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonОКActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonОК, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 252, -1, -1));

        jButtonCancel.setText("Отмена");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(194, 252, 83, -1));

        jComboBoxFrequency.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Один раз", "Каждый час", "Каждый день", "Каждую неделю", "Каждый месяц", "Каждый год" }));
        jComboBoxFrequency.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxFrequencyActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBoxFrequency, new org.netbeans.lib.awtextra.AbsoluteConstraints(171, 32, 124, -1));

        jSpinnerTimer.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), new java.util.Date(1348421512701L), null, java.util.Calendar.HOUR));
        jSpinnerTimer.setPreferredSize(new java.awt.Dimension(124, 22));
        jSpinnerTimer.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerTimerStateChanged(evt);
            }
        });
        getContentPane().add(jSpinnerTimer, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 31, 143, -1));

        jCheckBoxExchange.setSelected(true);
        jCheckBoxExchange.setText("Обмен");
        getContentPane().add(jCheckBoxExchange, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 126, -1, -1));

        jCheckBoxSync.setText("Синхронизация");
        getContentPane().add(jCheckBoxSync, new org.netbeans.lib.awtextra.AbsoluteConstraints(183, 126, -1, -1));

        jCheckBox3.setText("jCheckBox3");
        jCheckBox3.setEnabled(false);
        getContentPane().add(jCheckBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(183, 149, -1, -1));

        jButtonGroupExchange.add(jRadioButtonFull);
        jRadioButtonFull.setText("Полный обмен");
        getContentPane().add(jRadioButtonFull, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 149, -1, -1));

        jButtonGroupExchange.add(jRadioButtonDownload);
        jRadioButtonDownload.setText("Загрузка");
        getContentPane().add(jRadioButtonDownload, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 172, -1, -1));

        jButtonGroupExchange.add(jRadioButtonUpload);
        jRadioButtonUpload.setText("Выгрузка");
        getContentPane().add(jRadioButtonUpload, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 195, -1, -1));

        jTextFieldSchedulerFormat.setEditable(false);
        jTextFieldSchedulerFormat.setEnabled(false);
        getContentPane().add(jTextFieldSchedulerFormat, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 79, 285, -1));

        jLabel1.setText("Время");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, -1));

        jLabel2.setText("Периодичность");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(171, 11, -1, -1));

        jLabel3.setText("Ручной вид задания расписания");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 59, -1, -1));

        jLabel4.setText("Список задач");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 105, -1, -1));

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-325)/2, (screenSize.height-317)/2, 325, 317);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonОКActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonОКActionPerformed
        sched.setJobDate(((Date) jSpinnerTimer.getValue()));
        sched.setFrequency(this.jComboBoxFrequency.getSelectedIndex());
        sched.setTime();
        this.jTextFieldSchedulerFormat.setText(sched.time);
        mF.setSchedul(sched,jCheckBoxExchange.isSelected(),jCheckBoxSync.isSelected());
        mF.tableJobEnable(true);
        dispose();
    }//GEN-LAST:event_jButtonОКActionPerformed

    private void jComboBoxFrequencyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxFrequencyActionPerformed
        this.jTextFieldSchedulerFormat.setText(sched.switchFrequency(this.jComboBoxFrequency.getSelectedIndex()));
    }//GEN-LAST:event_jComboBoxFrequencyActionPerformed

    private void jSpinnerTimerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerTimerStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jSpinnerTimerStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.ButtonGroup jButtonGroupExchange;
    private javax.swing.JButton jButtonОК;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBoxExchange;
    private javax.swing.JCheckBox jCheckBoxSync;
    private javax.swing.JComboBox jComboBoxFrequency;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JRadioButton jRadioButtonDownload;
    private javax.swing.JRadioButton jRadioButtonFull;
    private javax.swing.JRadioButton jRadioButtonUpload;
    private javax.swing.JSpinner jSpinnerTimer;
    private javax.swing.JTextField jTextFieldSchedulerFormat;
    // End of variables declaration//GEN-END:variables

}