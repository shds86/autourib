package Interface;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.io.*;
import java.util.StringTokenizer;

public class MainFrame extends javax.swing.JFrame {

    //объявляем глобальные переменные
    File optionFile;            //файл с настройками (путь)
    String baseSource = null;   //путь  до базы
    String baseUser = null;     //имя пользователя базы
    String basePass = null;     //пароль пользователя базы
    String ftpSource = null;   //путь до ftp
    String ftpUser = null;     //имя пользователя ftp
    String ftpPass = null;     //пароль пользователя ftp

    public MainFrame() {
        initComponents();
        checkingOptionFile();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialogYesNo = new javax.swing.JDialog();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelMain = new javax.swing.JPanel();
        jButtonRunAll = new javax.swing.JButton();
        jButtonRunInfile = new javax.swing.JButton();
        jButtonRunDownload = new javax.swing.JButton();
        jButtonRunUpload = new javax.swing.JButton();
        jButtonRunOutfile = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaWorkLog = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaSystemLog = new javax.swing.JTextArea();
        jPanelOptions = new javax.swing.JPanel();
        jTextFieldFTPUser = new javax.swing.JTextField();
        jTextFieldFTPSource = new javax.swing.JTextField();
        jLabelFTPUserAndPass = new javax.swing.JLabel();
        jLabelFTPSource = new javax.swing.JLabel();
        jTextFieldBaseUser = new javax.swing.JTextField();
        jTextFieldBaseSource = new javax.swing.JTextField();
        jButtonApply = new javax.swing.JButton();
        jButtonSelectOptionFile = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jTextFieldOptionFile = new javax.swing.JTextField();
        jLabelBaseUserAndPass = new javax.swing.JLabel();
        jLabelBaseSource = new javax.swing.JLabel();
        jLabelOptionFile = new javax.swing.JLabel();
        jTextFieldFTPPass = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jTextFieldBasePass = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuFileExit = new javax.swing.JMenuItem();
        jMenuQuestion = new javax.swing.JMenu();
        jMenuQuestionHelp = new javax.swing.JMenuItem();
        jMenuQustionAbout = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jDialogYesNoLayout = new javax.swing.GroupLayout(jDialogYesNo.getContentPane());
        jDialogYesNo.getContentPane().setLayout(jDialogYesNoLayout);
        jDialogYesNoLayout.setHorizontalGroup(
            jDialogYesNoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialogYesNoLayout.setVerticalGroup(
            jDialogYesNoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Клиент URBD1Slib");

        jButtonRunAll.setText("Запустить обмен");
        jButtonRunAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRunAllActionPerformed(evt);
            }
        });

        jButtonRunInfile.setText("Принять файл");

        jButtonRunDownload.setText("Загрузить файл");

        jButtonRunUpload.setText("Выгрузить файл");

        jButtonRunOutfile.setText("Отправить файл");

        jTextAreaWorkLog.setColumns(1);
        jTextAreaWorkLog.setRows(1);
        jTextAreaWorkLog.setText("Рабочий лог...");
        jScrollPane1.setViewportView(jTextAreaWorkLog);

        jTextAreaSystemLog.setColumns(1);
        jTextAreaSystemLog.setRows(1);
        jTextAreaSystemLog.setText("Системный лог...");
        jScrollPane2.setViewportView(jTextAreaSystemLog);

        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 589, Short.MAX_VALUE)
            .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelMainLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanelMainLayout.createSequentialGroup()
                            .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jButtonRunInfile, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonRunDownload, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonRunUpload, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonRunOutfile, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonRunAll, javax.swing.GroupLayout.Alignment.LEADING))
                            .addGap(50, 50, 50)
                            .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                                .addComponent(jScrollPane1))))
                    .addContainerGap(30, Short.MAX_VALUE)))
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 338, Short.MAX_VALUE)
            .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelMainLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButtonRunAll)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelMainLayout.createSequentialGroup()
                            .addComponent(jButtonRunInfile)
                            .addGap(40, 40, 40)
                            .addComponent(jButtonRunDownload)
                            .addGap(40, 40, 40)
                            .addComponent(jButtonRunUpload)
                            .addGap(40, 40, 40)
                            .addComponent(jButtonRunOutfile))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE))
                    .addContainerGap()))
        );

        jTabbedPane1.addTab("Главная", jPanelMain);

        jLabelFTPUserAndPass.setText("Имя пользователя и пароль");

        jLabelFTPSource.setText("Путь до ftp");

        jButtonApply.setText("Применить");
        jButtonApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonApplyActionPerformed(evt);
            }
        });

        jButtonSelectOptionFile.setText("...");
        jButtonSelectOptionFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectOptionFileActionPerformed(evt);
            }
        });

        jButtonCancel.setText("Отмена");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jLabelBaseUserAndPass.setText("Имя пользователя и пароль");

        jLabelBaseSource.setText("Путь до базы");

        jLabelOptionFile.setText("Файл настроек");

        jButton1.setText("...");

        javax.swing.GroupLayout jPanelOptionsLayout = new javax.swing.GroupLayout(jPanelOptions);
        jPanelOptions.setLayout(jPanelOptionsLayout);
        jPanelOptionsLayout.setHorizontalGroup(
            jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelOptionsLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelOptionsLayout.createSequentialGroup()
                        .addComponent(jLabelFTPUserAndPass)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                                .addComponent(jButtonApply)
                                .addGap(43, 43, 43)
                                .addComponent(jButtonCancel))
                            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                                .addComponent(jTextFieldFTPUser, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldFTPPass, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelOptionsLayout.createSequentialGroup()
                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelBaseUserAndPass)
                            .addComponent(jLabelBaseSource)
                            .addComponent(jLabelFTPSource)
                            .addComponent(jLabelOptionFile))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldOptionFile, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                            .addComponent(jTextFieldFTPSource, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                                .addComponent(jTextFieldBaseUser, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldBasePass, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                            .addComponent(jTextFieldBaseSource, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSelectOptionFile, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
        jPanelOptionsLayout.setVerticalGroup(
            jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldOptionFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelOptionFile)
                    .addComponent(jButtonSelectOptionFile))
                .addGap(27, 27, 27)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelBaseSource)
                    .addComponent(jTextFieldBaseSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelBaseUserAndPass)
                    .addComponent(jTextFieldBaseUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldBasePass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelFTPSource)
                    .addComponent(jTextFieldFTPSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelFTPUserAndPass)
                    .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldFTPUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldFTPPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonApply)
                    .addComponent(jButtonCancel))
                .addContainerGap(56, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Настройки", jPanelOptions);

        jMenuFile.setText("Файл");

        jMenuFileExit.setText("Выход");
        jMenuFileExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuFileExitActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuFileExit);

        jMenuBar1.add(jMenuFile);

        jMenuQuestion.setText("?");

        jMenuQuestionHelp.setText("Помощь");
        jMenuQuestionHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuQuestionHelpActionPerformed(evt);
            }
        });
        jMenuQuestion.add(jMenuQuestionHelp);

        jMenuQustionAbout.setText("О программе");
        jMenuQustionAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuQustionAboutActionPerformed(evt);
            }
        });
        jMenuQuestion.add(jMenuQustionAbout);

        jMenuBar1.add(jMenuQuestion);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuFileExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuFileExitActionPerformed
        dispose();

    }//GEN-LAST:event_jMenuFileExitActionPerformed

    private void jMenuQuestionHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuQuestionHelpActionPerformed
    }//GEN-LAST:event_jMenuQuestionHelpActionPerformed

    private void jMenuQustionAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuQustionAboutActionPerformed
        new AboutFrame().setVisible(true);
    }//GEN-LAST:event_jMenuQustionAboutActionPerformed

    private void jButtonRunAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRunAllActionPerformed
        //
    }//GEN-LAST:event_jButtonRunAllActionPerformed

    private void jButtonApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonApplyActionPerformed
        Apply();
}//GEN-LAST:event_jButtonApplyActionPerformed

    @SuppressWarnings("static-access")
    private void jButtonSelectOptionFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectOptionFileActionPerformed
        // открыть диалог выбора файла; если файл выбран - присваиваем его имя в поле
        JFileChooser jFileChooserOptionFile = new JFileChooser();
        int result = jFileChooserOptionFile.showOpenDialog(null);
        if (result == jFileChooserOptionFile.APPROVE_OPTION) {
            jTextFieldOptionFile.setText(jFileChooserOptionFile.getSelectedFile().getAbsolutePath());
            //далее проверить файл настроек на правильность
            //далее прочитать файл настроек и выставить его содержимое в нужные поля
            //...
        }
}//GEN-LAST:event_jButtonSelectOptionFileActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        dispose();
}//GEN-LAST:event_jButtonCancelActionPerformed

    private void Apply() {
        //проверяем, выбран ли файл
        if (jTextFieldOptionFile.getText().equals("")) {
            System.out.println("Файл не выбран!");
        } else {
            System.out.println(jTextFieldOptionFile.getText());
        }
        //выгружаем в файл настроек и записываем его
    }

    public static void main(String args[]) {
        new MainFrame().setVisible(true);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonApply;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonRunAll;
    private javax.swing.JButton jButtonRunDownload;
    private javax.swing.JButton jButtonRunInfile;
    private javax.swing.JButton jButtonRunOutfile;
    private javax.swing.JButton jButtonRunUpload;
    private javax.swing.JButton jButtonSelectOptionFile;
    private javax.swing.JDialog jDialogYesNo;
    private javax.swing.JLabel jLabelBaseSource;
    private javax.swing.JLabel jLabelBaseUserAndPass;
    private javax.swing.JLabel jLabelFTPSource;
    private javax.swing.JLabel jLabelFTPUserAndPass;
    private javax.swing.JLabel jLabelOptionFile;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuFileExit;
    private javax.swing.JMenu jMenuQuestion;
    private javax.swing.JMenuItem jMenuQuestionHelp;
    private javax.swing.JMenuItem jMenuQustionAbout;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelOptions;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextAreaSystemLog;
    private javax.swing.JTextArea jTextAreaWorkLog;
    private javax.swing.JTextField jTextFieldBasePass;
    private javax.swing.JTextField jTextFieldBaseSource;
    private javax.swing.JTextField jTextFieldBaseUser;
    private javax.swing.JTextField jTextFieldFTPPass;
    private javax.swing.JTextField jTextFieldFTPSource;
    private javax.swing.JTextField jTextFieldFTPUser;
    private javax.swing.JTextField jTextFieldOptionFile;
    // End of variables declaration//GEN-END:variables

    private void checkingOptionFile() {
        //получаем папку пользователя
        String userDir = new String();
        userDir = System.getProperty("user.dir");

        //проверяем, есть ли папка URDBlib
        userDir = userDir + System.getProperty("file.separator") + "AvtoURIB";
        File dir;
        dir = new File(userDir);
        if (!dir.exists()) {
            jTextAreaSystemLog.setText(jTextAreaSystemLog.getText() + "\nКаталог настроек не найден и будет создан...");
            dir.mkdirs();
        } else {
            jTextAreaSystemLog.setText(jTextAreaSystemLog.getText() + "\nКаталог настроек найден...");
        }

        //проверяем, есть ли файл настроек
        userDir = userDir + System.getProperty("file.separator") + "AvtoURIB.ini";
        //File optionFile;
        optionFile = new File(userDir);
        //если нет файла, то создаем
        if (!optionFile.exists()) {
            try {
                jTextAreaSystemLog.setText(jTextAreaSystemLog.getText() + "\nФайл настроек не найден и будет создан...");
                optionFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            jTextAreaSystemLog.setText(jTextAreaSystemLog.getText() + "\nФайл настроек найден...");
            loadOptions();
        }
    }

    private void loadOptions() {
        BufferedReader inoptionfile;
        try {
            inoptionfile = new BufferedReader(new InputStreamReader(new FileInputStream(optionFile)));
            while (inoptionfile.ready()) {
                String stroka = inoptionfile.readLine();
                StringTokenizer st = new StringTokenizer(stroka, ";");
                jTextAreaSystemLog.setText(jTextAreaSystemLog.getText() + "\nЧитаю файл настроек...");
                int tokencounter = 1;
                while (st.hasMoreTokens()) {
                    //System.out.println(st.nextToken());
                    switch (tokencounter) {
                        case 1:
                            jTextFieldBaseSource.setText(st.nextToken());
                            break;
                        case 2:
                            jTextFieldBaseUser.setText(st.nextToken());
                            break;
                        case 3:
                            jTextFieldBasePass.setText(st.nextToken());
                            break;
                        case 4:
                            jTextFieldFTPSource.setText(st.nextToken());
                            break;
                        case 5:
                            jTextFieldFTPUser.setText(st.nextToken());
                            break;
                        case 6:
                            jTextFieldFTPPass.setText(st.nextToken());
                            break;
                    }
                    tokencounter++;
                }
            }
        } catch (IOException e) {
        }

    }
}
