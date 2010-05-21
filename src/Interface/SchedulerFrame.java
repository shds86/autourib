package Interface;

public class SchedulerFrame extends javax.swing.JFrame {

    /** Creates new form SchedulerFrame */
    public SchedulerFrame() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonGroupExchange = new javax.swing.ButtonGroup();
        jButtonОК = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jComboBoxFrequency = new javax.swing.JComboBox();
        jSpinnerTimer = new javax.swing.JSpinner();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jTextFieldSchedulerFormat = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Добавить задание");
        setAlwaysOnTop(true);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        jButtonОК.setText("Добавить");

        jButtonCancel.setText("Отмена");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jComboBoxFrequency.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Один раз", "Каждый час", "Каждый день", "Каждую неделю", "Каждый месяц", "Каждый год", "Другое" }));

        jSpinnerTimer.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), new java.util.Date(), null, java.util.Calendar.HOUR_OF_DAY));
        jSpinnerTimer.setPreferredSize(new java.awt.Dimension(124, 22));

        jCheckBox1.setText("Обмен");

        jCheckBox2.setText("Синхронизация");

        jCheckBox3.setText("jCheckBox3");

        jButtonGroupExchange.add(jRadioButton1);
        jRadioButton1.setText("Полный обмен");

        jButtonGroupExchange.add(jRadioButton2);
        jRadioButton2.setText("Загрузка");

        jButtonGroupExchange.add(jRadioButton3);
        jRadioButton3.setText("Выгрузка");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCheckBox3)
                        .addContainerGap())
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jCheckBox2)
                            .addContainerGap())
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jCheckBox1)
                                            .addGap(69, 69, 69))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jSpinnerTimer, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                                            .addGap(18, 18, 18)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addComponent(jButtonОК)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextFieldSchedulerFormat, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                                            .addComponent(jComboBoxFrequency, javax.swing.GroupLayout.Alignment.TRAILING, 0, 124, Short.MAX_VALUE))
                                        .addGap(108, 108, 108))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(23, 23, 23)
                                        .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap())))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioButton2)
                                    .addComponent(jRadioButton1)
                                    .addComponent(jRadioButton3))
                                .addGap(276, 276, 276))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinnerTimer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxFrequency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox1)
                    .addComponent(jTextFieldSchedulerFormat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton3)
                .addGap(18, 18, 18)
                .addComponent(jCheckBox2)
                .addGap(18, 18, 18)
                .addComponent(jCheckBox3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCancel)
                    .addComponent(jButtonОК))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-325)/2, (screenSize.height-294)/2, 325, 294);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SchedulerFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.ButtonGroup jButtonGroupExchange;
    private javax.swing.JButton jButtonОК;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JComboBox jComboBoxFrequency;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JSpinner jSpinnerTimer;
    private javax.swing.JTextField jTextFieldSchedulerFormat;
    // End of variables declaration//GEN-END:variables

}