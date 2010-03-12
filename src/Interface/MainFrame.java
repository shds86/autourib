package Interface;

import java.util.*;
import javax.swing.*;
import java.io.*;
import URBD1SLib.ftp.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class MainFrame extends javax.swing.JFrame {

    //объявляем глобальные переменные
    File optionFile;            //файл с настройками (путь)
    String baseSource = null;   //путь  до базы
    String baseUser = null;     //имя пользователя базы
    String basePass = null;     //пароль пользователя базы
    String ftpSource = null;   //путь до ftp
    String ftpUser = null;     //имя пользователя ftp
    String ftpPass = null;     //пароль пользователя ftp
    options TmpOptions = new options();
    ftp_work exchange = null;
    run_1s run1s = null;
    JFileChooser jFileChooserPlatformSource = new JFileChooser();
    JFileChooser jFileChooserBaseSource = new JFileChooser();

    public MainFrame()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception err){}
        initComponents();
        setLocationRelativeTo(null);
        jButtonRunDownload.setEnabled(false);
        jButtonRunUpload.setEnabled(false);
        jButtonRunOutfile.setEnabled(false);
        checkingOptionFile();
        getDateAndTime();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelMain = new javax.swing.JPanel();
        jButtonRunOutfile = new javax.swing.JButton();
        jButtonRunUpload = new javax.swing.JButton();
        jButtonRunDownload = new javax.swing.JButton();
        jButtonRunInfile = new javax.swing.JButton();
        jButtonRunAll = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaSystemLog = new javax.swing.JTextArea();
        jButtonRunSynch = new javax.swing.JButton();
        jPanelOptions = new javax.swing.JPanel();
        jTextFieldBaseSource = new javax.swing.JTextField();
        jButtonCancel = new javax.swing.JButton();
        jButtonApply = new javax.swing.JButton();
        jTextFieldFTPUser = new javax.swing.JTextField();
        jLabelFTPUserAndPass = new javax.swing.JLabel();
        jTextFieldFTPSource = new javax.swing.JTextField();
        jLabelFTPSource = new javax.swing.JLabel();
        jTextFieldBaseUser = new javax.swing.JTextField();
        jLabelBaseUserAndPass = new javax.swing.JLabel();
        jTextFieldPlatformSource = new javax.swing.JTextField();
        jLabelBaseSource = new javax.swing.JLabel();
        jLabelPlatformSource = new javax.swing.JLabel();
        jButtonSelPlatformSource = new javax.swing.JButton();
        jButtonSelBaseSource = new javax.swing.JButton();
        jTextFieldBasePass = new javax.swing.JPasswordField();
        jTextFieldFTPPass = new javax.swing.JPasswordField();
        jTextFieldInfileOnServer = new javax.swing.JTextField();
        jTextFieldInfileOnLocalhost = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldOutfileOnLocalhost = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldOutfileOnServer = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuFileExit = new javax.swing.JMenuItem();
        jMenuQuestion = new javax.swing.JMenu();
        jMenuQuestionHelp = new javax.swing.JMenuItem();
        jMenuQustionAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Клиент URBD1Slib");
        setResizable(false);

        jButtonRunOutfile.setText("Отправить на сервер");
        jButtonRunOutfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRunOutfileActionPerformed(evt);
            }
        });

        jButtonRunUpload.setText("Выгрузить из базы");
        jButtonRunUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRunUploadActionPerformed(evt);
            }
        });

        jButtonRunDownload.setText("Загрузить в базу");
        jButtonRunDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRunDownloadActionPerformed(evt);
            }
        });

        jButtonRunInfile.setText("Принять с сервера");
        jButtonRunInfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRunInfileActionPerformed(evt);
            }
        });

        jButtonRunAll.setText("Запустить обмен");
        jButtonRunAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRunAllActionPerformed(evt);
            }
        });

        jTextAreaSystemLog.setColumns(1);
        jTextAreaSystemLog.setEditable(false);
        jTextAreaSystemLog.setFont(new java.awt.Font("Monospaced", 0, 12));
        jTextAreaSystemLog.setRows(1);
        jTextAreaSystemLog.setText("Системный лог...");
        jScrollPane2.setViewportView(jTextAreaSystemLog);

        jButtonRunSynch.setText("Синхронизация");

        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonRunOutfile, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonRunUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonRunDownload, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonRunAll, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonRunInfile, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonRunSynch, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jButtonRunAll)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonRunInfile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonRunDownload)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonRunUpload)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonRunOutfile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 135, Short.MAX_VALUE)
                .addComponent(jButtonRunSynch)
                .addGap(38, 38, 38))
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Главная", jPanelMain);

        jTextFieldBaseSource.setPreferredSize(new java.awt.Dimension(6, 23));

        jButtonCancel.setText("Отмена");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jButtonApply.setText("Применить");
        jButtonApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonApplyActionPerformed(evt);
            }
        });

        jLabelFTPUserAndPass.setText("FTP-пользователь");

        jLabelFTPSource.setText("FTP-хост");

        jLabelBaseUserAndPass.setText("Имя пользователя и пароль");

        jTextFieldPlatformSource.setPreferredSize(new java.awt.Dimension(6, 23));

        jLabelBaseSource.setText("Путь до базы");

        jLabelPlatformSource.setText("Путь до папки 1С");

        jButtonSelPlatformSource.setText("...");
        jButtonSelPlatformSource.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelPlatformSourceActionPerformed(evt);
            }
        });

        jButtonSelBaseSource.setText("...");
        jButtonSelBaseSource.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelBaseSourceActionPerformed(evt);
            }
        });

        jTextFieldBasePass.setText("jPasswordField1");

        jTextFieldFTPPass.setText("jPasswordField1");

        jLabel1.setText("Файл загрузки на сервере");

        jLabel2.setText("Файл загрузки локально");

        jLabel3.setText("FTP-пароль");

        jLabel4.setText("Файл выгрузки локально");

        jLabel5.setText("Файл выгрузки на сервере");

        javax.swing.GroupLayout jPanelOptionsLayout = new javax.swing.GroupLayout(jPanelOptions);
        jPanelOptions.setLayout(jPanelOptionsLayout);
        jPanelOptionsLayout.setHorizontalGroup(
            jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelPlatformSource)
                                    .addComponent(jLabelBaseSource))
                                .addGap(55, 55, 55)
                                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextFieldPlatformSource, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldBaseSource, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelFTPSource)
                                    .addComponent(jLabelFTPUserAndPass))
                                .addGap(52, 52, 52)
                                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldFTPSource, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jTextFieldFTPPass, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextFieldFTPUser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)))))
                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jButtonSelPlatformSource, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelOptionsLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jButtonSelBaseSource, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldInfileOnServer, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3)
                            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(10, 10, 10)
                                .addComponent(jTextFieldOutfileOnServer, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextFieldOutfileOnLocalhost)
                                    .addComponent(jTextFieldInfileOnLocalhost, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)))))
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButtonApply)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonCancel))
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelBaseUserAndPass)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldBasePass, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldBaseUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE))))
                .addGap(187, 187, 187))
        );
        jPanelOptionsLayout.setVerticalGroup(
            jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabelPlatformSource))
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jTextFieldPlatformSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonSelPlatformSource, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelBaseSource)
                    .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonSelBaseSource, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldBaseSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelBaseUserAndPass)
                    .addComponent(jTextFieldBaseUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldBasePass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelFTPSource)
                    .addComponent(jTextFieldFTPSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelFTPUserAndPass)
                    .addComponent(jTextFieldFTPUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel3))
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jTextFieldFTPPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldInfileOnServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel2))
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jTextFieldInfileOnLocalhost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldOutfileOnLocalhost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldOutfileOnServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonApply)
                    .addComponent(jButtonCancel))
                .addContainerGap())
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
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 628, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
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
        //ДИМЫЧ! надо разбить код этого метода на 4 метода, и запускать их отсюда последовательно,
        //т.к. должна быть возможность запускать эти методы отдельно друг от друга (infile,download и.т.д)
        this.getRootPane().updateUI();
        GetFileOnFTP();
        RunWith1S();
    }//GEN-LAST:event_jButtonRunAllActionPerformed

    private void jButtonApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonApplyActionPerformed
        Apply();
}//GEN-LAST:event_jButtonApplyActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        dispose();
}//GEN-LAST:event_jButtonCancelActionPerformed

    @SuppressWarnings("static-access")
    private void jButtonSelPlatformSourceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelPlatformSourceActionPerformed
        // указываем папку с запускалкой 1С
        // открыть диалог выбора файла; если файл выбран - присваиваем его имя в поле
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.exe", "exe");
        jFileChooserPlatformSource.setFileFilter(filter);
        int result = jFileChooserPlatformSource.showOpenDialog(null);   //объявляем, в след.строке присваиваем
        if (result == jFileChooserPlatformSource.APPROVE_OPTION) {
            jTextFieldPlatformSource.setText(jFileChooserPlatformSource.getSelectedFile().getAbsolutePath());
            jTextAreaSystemLog.repaint();
        }
    }//GEN-LAST:event_jButtonSelPlatformSourceActionPerformed

    @SuppressWarnings("static-access")
    private void jButtonSelBaseSourceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelBaseSourceActionPerformed
        // указываем папку с базой
        // открыть диалог выбора файла; если файл выбран - присваиваем его имя в поле
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.MD", "MD");
        jFileChooserBaseSource.setFileFilter(filter);
        int result = jFileChooserBaseSource.showOpenDialog(null);   //объявляем, в след.строке присваиваем
        if (result == jFileChooserBaseSource.APPROVE_OPTION) {
            jTextFieldBaseSource.setText(jFileChooserBaseSource.getSelectedFile().getParent());
        }
    }//GEN-LAST:event_jButtonSelBaseSourceActionPerformed

    private void jButtonRunInfileActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonRunInfileActionPerformed
    {//GEN-HEADEREND:event_jButtonRunInfileActionPerformed
        jButtonRunInfile.setEnabled(false);

        jButtonRunDownload.setEnabled(true);
    }//GEN-LAST:event_jButtonRunInfileActionPerformed

    private void jButtonRunDownloadActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonRunDownloadActionPerformed
    {//GEN-HEADEREND:event_jButtonRunDownloadActionPerformed
        jButtonRunDownload.setEnabled(false);

        jButtonRunUpload.setEnabled(true);
    }//GEN-LAST:event_jButtonRunDownloadActionPerformed

    private void jButtonRunOutfileActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonRunOutfileActionPerformed
    {//GEN-HEADEREND:event_jButtonRunOutfileActionPerformed
        jButtonRunOutfile.setEnabled(false);

        jButtonRunInfile.setEnabled(true);
    }//GEN-LAST:event_jButtonRunOutfileActionPerformed

    private void jButtonRunUploadActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonRunUploadActionPerformed
    {//GEN-HEADEREND:event_jButtonRunUploadActionPerformed
        jButtonRunUpload.setEnabled(false);

        jButtonRunOutfile.setEnabled(true);
    }//GEN-LAST:event_jButtonRunUploadActionPerformed

    private void Apply() {
        //выгружаем в файл настроек и записываем его
        jTextAreaSystemLog.append("\n" + getDateAndTime() + " Записываю настройки в файл...");
        jTextAreaSystemLog.repaint();
        saveOptions();
    }

    private void GetFileOnFTP()
    {
        byte err;
        exchange = new ftp_work(TmpOptions.get_FTP_SERVER_NAME(),
                                TmpOptions.get_FTP_SERVER_LOGIN(),
                                TmpOptions.get_FTP_SERVER_PASS(),
                                TmpOptions.get_cp_ftp_file(),
                                TmpOptions.get_pc_local_file());

        exchange.parsing_folder();
        jTextAreaSystemLog.append("\n" + getDateAndTime() + " Подключение к фтп-серверу...");
        jTextAreaSystemLog.repaint();
        this.getRootPane().updateUI();
        if ((err=exchange.ftp_connect()) == consterr.NOT_ERR)
        {
            jTextAreaSystemLog.append("\n" + getDateAndTime() + " Подключение прошло успешно...");
            jTextAreaSystemLog.append("\n" + getDateAndTime() + " Загружаю файл...");
            if ((err=exchange.get_file()) == consterr.NOT_ERR)
            {
                jTextAreaSystemLog.append("\n" + getDateAndTime() + " Получение файла прошло успешно...");
                return;
            }
            else
            {
                jTextAreaSystemLog.append("\n" + getDateAndTime() + " "+consterr.PrintErr(err));
                return;
            }
        }
        else
        {
            jTextAreaSystemLog.append("\n" + getDateAndTime() + " " + consterr.PrintErr(err));
            return;
        }
    }

    private void RunWith1S()
    {
        int err;
        run1s = new run_1s(TmpOptions.get_PATH_1S(),
                           TmpOptions.get_PATH_BASE(),
                           TmpOptions.get_BASE_LOGIN(),
                           new String(TmpOptions.get_BASE_PASS()));
        List<String> tmplst = null;
        err=run1s.create_prm();
        if (err==consterr.NOT_ERR)
        {
            jTextAreaSystemLog.append("\n" + getDateAndTime() + " Запускаем 1С");
            jTextAreaSystemLog.repaint();
            err=run1s.start();
            if (err==consterr.NOT_ERR)
            {
                tmplst = run1s.parsing_log();
                if (tmplst != null)
                {
                    for (int i = 0; i < tmplst.size(); i++)
                    {
                        jTextAreaSystemLog.append("\n" + getDateAndTime() +" "+ tmplst.get(i));
                        jTextAreaSystemLog.repaint();
                    }
                }
                else
                {
                    jTextAreaSystemLog.append("\n" + getDateAndTime() +" "+ consterr.PrintErr((byte)err));
                    jTextAreaSystemLog.repaint();
                }
            }
            else
            {
                jTextAreaSystemLog.append("\n" + getDateAndTime() +" "+ consterr.PrintErr((byte)err));
                jTextAreaSystemLog.repaint();
            }
        }
        else
        {
            jTextAreaSystemLog.append("\n" + getDateAndTime() +" "+ consterr.PrintErr((byte)err));
            jTextAreaSystemLog.repaint();
        }
    }

    public static void main(String args[]) {
        new MainFrame().setVisible(true);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonApply;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonRunAll;
    private javax.swing.JButton jButtonRunDownload;
    private javax.swing.JButton jButtonRunInfile;
    private javax.swing.JButton jButtonRunOutfile;
    private javax.swing.JButton jButtonRunSynch;
    private javax.swing.JButton jButtonRunUpload;
    private javax.swing.JButton jButtonSelBaseSource;
    private javax.swing.JButton jButtonSelPlatformSource;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelBaseSource;
    private javax.swing.JLabel jLabelBaseUserAndPass;
    private javax.swing.JLabel jLabelFTPSource;
    private javax.swing.JLabel jLabelFTPUserAndPass;
    private javax.swing.JLabel jLabelPlatformSource;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuFileExit;
    private javax.swing.JMenu jMenuQuestion;
    private javax.swing.JMenuItem jMenuQuestionHelp;
    private javax.swing.JMenuItem jMenuQustionAbout;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelOptions;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextAreaSystemLog;
    private javax.swing.JPasswordField jTextFieldBasePass;
    private javax.swing.JTextField jTextFieldBaseSource;
    private javax.swing.JTextField jTextFieldBaseUser;
    private javax.swing.JPasswordField jTextFieldFTPPass;
    private javax.swing.JTextField jTextFieldFTPSource;
    private javax.swing.JTextField jTextFieldFTPUser;
    private javax.swing.JTextField jTextFieldInfileOnLocalhost;
    private javax.swing.JTextField jTextFieldInfileOnServer;
    private javax.swing.JTextField jTextFieldOutfileOnLocalhost;
    private javax.swing.JTextField jTextFieldOutfileOnServer;
    private javax.swing.JTextField jTextFieldPlatformSource;
    // End of variables declaration//GEN-END:variables

    private void checkingOptionFile() {
        //очищаем информационные поля
        jTextAreaSystemLog.setText("");

        //получаем папку пользователя
        String userDir = new String();
        userDir = System.getProperty("user.dir");

        //проверяем, есть ли папка AvtoURIB
        userDir = userDir + System.getProperty("file.separator") + "AvtoURIB";
        File dir;
        dir = new File(userDir);
        if (!dir.exists()) {
            jTextAreaSystemLog.append(getDateAndTime() + " Каталог настроек не найден и будет создан...");
            jTextAreaSystemLog.repaint();
            dir.mkdirs();
        } else {
            jTextAreaSystemLog.append(getDateAndTime() + " Каталог настроек найден...");
            jTextAreaSystemLog.repaint();
        }

        //проверяем, есть ли файл настроек
        userDir = userDir + System.getProperty("file.separator") + "AvtoURIB.ini";
        optionFile = new File(userDir);
        //если нет файла, то создаем
        if (!optionFile.exists()) {
            try {
                jTextAreaSystemLog.append("\n" + getDateAndTime() + " Файл настроек не найден и будет создан...");
                jTextAreaSystemLog.repaint();
                optionFile.createNewFile();
            } catch (IOException ex) {
            }
        } else {
            jTextAreaSystemLog.append("\n" + getDateAndTime() + " Файл настроек найден...");
            jTextAreaSystemLog.repaint();
            loadOptions();
        }
    }

    private void loadOptions() {
        BufferedReader inoptionfile;
        String tmp;
        try {
            inoptionfile = new BufferedReader(new InputStreamReader(new FileInputStream(optionFile)));
            while (inoptionfile.ready()) {
                String stroka = inoptionfile.readLine();
                StringTokenizer st = new StringTokenizer(stroka, ";");
                if (st.countTokens() < 7) {
                    jTextAreaSystemLog.append("\n" + getDateAndTime() + " Неверный файл настроек!..");
                    jTextAreaSystemLog.repaint();
                    break;
                }
                jTextAreaSystemLog.append("\n" + getDateAndTime() + " Читаю файл настроек...");
                jTextAreaSystemLog.repaint();
                int tokencounter = 1;
                while (st.hasMoreTokens()) {
                    switch (tokencounter) {
                        case 1: {
                            tmp = st.nextToken();
                            TmpOptions.set_PATH_1S(tmp);
                            jTextFieldPlatformSource.setText(tmp);
                            break;
                        }

                        case 2: {
                            tmp = st.nextToken();
                            TmpOptions.set_PATH_BASE(tmp);
                            jTextFieldBaseSource.setText(tmp);
                            break;
                        }
                        case 3: {
                            tmp = st.nextToken();
                            TmpOptions.set_BASE_LOGIN(tmp);
                            jTextFieldBaseUser.setText(tmp);
                            break;
                        }
                        case 4: {
                            tmp = st.nextToken();
                            TmpOptions.set_BASE_PASS(tmp.toCharArray());
                            jTextFieldBasePass.setText(tmp);
                            break;
                        }
                        case 5: {
                            tmp = st.nextToken();
                            TmpOptions.set_FTP_SERVER_NAME(tmp);
                            jTextFieldFTPSource.setText(tmp);
                            break;
                        }
                        case 6: {
                            tmp = st.nextToken();
                            TmpOptions.set_FTP_SERVER_LOGIN(tmp);
                            jTextFieldFTPUser.setText(tmp);
                            break;
                        }
                        case 7: {
                            tmp = st.nextToken();
                            TmpOptions.set_FTP_SERVER_PASS(tmp.toCharArray());
                            jTextFieldFTPPass.setText(tmp);
                            break;
                        }
                        case 8: {
                            tmp = st.nextToken();
                            TmpOptions.set_cp_ftp_file(tmp);
                            jTextFieldInfileOnServer.setText(tmp);
                            break;
                        }
                        case 9: {
                            tmp = st.nextToken();
                            TmpOptions.set_pc_local_file(tmp);
                            jTextFieldInfileOnLocalhost.setText(tmp);
                            break;
                        }
                        case 10: {
                            tmp = st.nextToken();
                            //TmpOptions.set_pc_local_file(tmp);
                            jTextFieldInfileOnServer.setText(tmp);
                            break;
                        }
                        case 11: {
                            tmp = st.nextToken();
                            //TmpOptions.set_pc_local_file(tmp);
                            jTextFieldOutfileOnLocalhost.setText(tmp);
                            break;
                        }
                    }
                    tokencounter++;
                }
                jTextAreaSystemLog.append("\n" + getDateAndTime() + " Файл настроек прочитан...");
                jTextAreaSystemLog.repaint();
            }
        } catch (IOException e) {
        }
    }

    private void saveOptions() {
        //записываем настройки в файл
        BufferedWriter outoptionfile;
        try {
            outoptionfile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(optionFile)));
            //формируем строку для записи
            String optionString = jTextFieldPlatformSource.getText() + ";"
                    + jTextFieldBaseSource.getText() + ";"
                    + jTextFieldBaseUser.getText() + ";"
                    + new String(jTextFieldBasePass.getPassword()) + ";"
                    + jTextFieldFTPSource.getText() + ";"
                    + jTextFieldFTPUser.getText() + ";"
                    + new String(jTextFieldFTPPass.getPassword()) + ";"
                    + jTextFieldInfileOnServer.getText() + ";"
                    + jTextFieldInfileOnLocalhost.getText() + ";"
                    + jTextFieldOutfileOnServer.getText() + ";"
                    + jTextFieldOutfileOnLocalhost.getText();
            System.out.println(optionString);
            outoptionfile.write(optionString);
            outoptionfile.close();

            TmpOptions.set_PATH_1S(jTextFieldPlatformSource.getText());
            TmpOptions.set_PATH_BASE(jTextFieldBaseSource.getText());
            TmpOptions.set_BASE_LOGIN(jTextFieldBaseUser.getText());
            TmpOptions.set_BASE_PASS(jTextFieldBasePass.getPassword());
            TmpOptions.set_FTP_SERVER_NAME(jTextFieldFTPSource.getText());
            TmpOptions.set_FTP_SERVER_LOGIN(jTextFieldFTPUser.getText());
            TmpOptions.set_FTP_SERVER_PASS(jTextFieldFTPPass.getPassword());
            TmpOptions.set_cp_ftp_file(jTextFieldFileOnServer.getText());
            TmpOptions.set_pc_local_file(jTextFieldFileOnLocalhost.getText());


        } catch (IOException e) {
        }
        jTextAreaSystemLog.append("\n" + getDateAndTime() + " Настройки записаны...");
        jTextAreaSystemLog.repaint();
    }

    public String getDateAndTime() {
        Date date = new Date(System.currentTimeMillis());
        String customerDate = (1900 + date.getYear()) + "-"
                + (1 + date.getMonth()) + "-" + date.getDate()
                + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        return customerDate;
    }
}

