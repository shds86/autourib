package Interface;

import java.util.*;
import javax.swing.*;
import java.io.*;
import URBD1SLib.ftp.*;
import java.awt.Color;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;


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
        jTextFieldFileOnServer = new javax.swing.JTextField();
        jTextFieldFileOnLocalhost = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuFileExit = new javax.swing.JMenuItem();
        jMenuQuestion = new javax.swing.JMenu();
        jMenuQuestionHelp = new javax.swing.JMenuItem();
        jMenuQustionAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Клиент URBD1Slib");

        jButtonRunOutfile.setText("Отправить файл");

        jButtonRunUpload.setText("Выгрузить файл");

        jButtonRunDownload.setText("Загрузить файл");

        jButtonRunInfile.setText("Принять файл");

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
                    .addComponent(jButtonRunOutfile, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addComponent(jButtonRunUpload, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addComponent(jButtonRunDownload, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addComponent(jButtonRunAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonRunInfile, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addComponent(jButtonRunSynch, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                .addContainerGap())
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(jButtonRunSynch)
                .addGap(38, 38, 38))
        );

        jTabbedPane1.addTab("Главная", jPanelMain);

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

        jLabelFTPUserAndPass.setText("Имя пользователя и пароль");

        jLabelFTPSource.setText("Путь до ftp");

        jLabelBaseUserAndPass.setText("Имя пользователя и пароль");

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

        jTextFieldFileOnServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFileOnServerActionPerformed(evt);
            }
        });

        jLabel1.setText("Путь до файла на фтп");

        jLabel2.setText("Путь до файла локально");

        javax.swing.GroupLayout jPanelOptionsLayout = new javax.swing.GroupLayout(jPanelOptions);
        jPanelOptions.setLayout(jPanelOptionsLayout);
        jPanelOptionsLayout.setHorizontalGroup(
            jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelFTPSource)
                            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelOptionsLayout.createSequentialGroup()
                                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabelPlatformSource)
                                            .addComponent(jLabelBaseSource)
                                            .addComponent(jLabelBaseUserAndPass))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextFieldBaseSource, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                                            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                                                .addComponent(jTextFieldBaseUser, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jTextFieldBasePass, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                                            .addComponent(jTextFieldPlatformSource, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                                            .addComponent(jTextFieldFTPSource, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelOptionsLayout.createSequentialGroup()
                                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabelFTPUserAndPass)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel2))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                                                .addComponent(jTextFieldFTPUser, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(10, 10, 10)
                                                .addComponent(jTextFieldFTPPass, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                                            .addComponent(jTextFieldFileOnServer, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                                            .addComponent(jTextFieldFileOnLocalhost, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))))
                                .addGap(18, 18, 18)
                                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButtonSelBaseSource, 0, 0, Short.MAX_VALUE)
                                    .addComponent(jButtonSelPlatformSource, javax.swing.GroupLayout.PREFERRED_SIZE, 31, Short.MAX_VALUE))
                                .addGap(65, 65, 65)))
                        .addGap(11, 11, 11))
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addComponent(jButtonApply)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonCancel)
                        .addContainerGap(334, Short.MAX_VALUE))))
        );
        jPanelOptionsLayout.setVerticalGroup(
            jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPlatformSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPlatformSource)
                    .addComponent(jButtonSelPlatformSource))
                .addGap(18, 18, 18)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelBaseSource)
                    .addComponent(jButtonSelBaseSource)
                    .addComponent(jTextFieldBaseSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldBaseUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldBasePass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelBaseUserAndPass))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelFTPSource)
                    .addComponent(jTextFieldFTPSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldFTPUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldFTPPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelFTPUserAndPass))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldFileOnServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldFileOnLocalhost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(39, 39, 39)
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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
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
        //ДИМЫЧ! надо разбить код этого метода на 4 метода, и запускать их отсюда последовательно,
        //т.к. должна быть возможность запускать эти методы отдельно друг от друга (infile,download и.т.д)
        byte err;
        exchange = new ftp_work(TmpOptions.get_FTP_SERVER_NAME(),
                                TmpOptions.get_FTP_SERVER_LOGIN(),
                                TmpOptions.get_FTP_SERVER_PASS(),
                                TmpOptions.get_cp_file(),
                                TmpOptions.get_pc_file());

        exchange.parsing_folder();
        jTextAreaSystemLog.append("\n" + getDateAndTime() + " Подключение к фтп-серверу...");
        if ((err=exchange.ftp_connect()) == consterr.NOT_ERR)
        {
            jTextAreaSystemLog.append("\n" + getDateAndTime() + " Подключение прошло успешно...");
            
            jTextAreaSystemLog.append("\n" + getDateAndTime() + " Загружаю файл...");
            if ((err=exchange.get_file()) == consterr.NOT_ERR)
            {
                jTextAreaSystemLog.append("\n" + getDateAndTime() + " Получение файла прошло успешно...");
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
        }
    }//GEN-LAST:event_jButtonSelPlatformSourceActionPerformed

    private void jTextFieldFileOnServerActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jTextFieldFileOnServerActionPerformed
    {//GEN-HEADEREND:event_jTextFieldFileOnServerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFileOnServerActionPerformed

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

    private void Apply() {
        //выгружаем в файл настроек и записываем его
        jTextAreaSystemLog.append("\n" + getDateAndTime() + " Записываю настройки в файл...");
        saveOptions();
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
    private javax.swing.JTextField jTextFieldFileOnLocalhost;
    private javax.swing.JTextField jTextFieldFileOnServer;
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
            dir.mkdirs();
        } else {
            jTextAreaSystemLog.append(getDateAndTime() + " Каталог настроек найден...");
        }

        //проверяем, есть ли файл настроек
        userDir = userDir + System.getProperty("file.separator") + "AvtoURIB.ini";
        optionFile = new File(userDir);
        //если нет файла, то создаем
        if (!optionFile.exists()) {
            try {
                jTextAreaSystemLog.append("\n" + getDateAndTime() + " Файл настроек не найден и будет создан...");
                optionFile.createNewFile();
            } catch (IOException ex) {
            }
        } else {
            jTextAreaSystemLog.append("\n" + getDateAndTime() + " Файл настроек найден...");
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
                    break;
                }
                jTextAreaSystemLog.append("\n" + getDateAndTime() + " Читаю файл настроек...");
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
                            TmpOptions.set_cp_file(tmp);
                            jTextFieldFileOnServer.setText(tmp);
                            break;
                        }
                        case 9: {
                            tmp = st.nextToken();
                            TmpOptions.set_pc_file(tmp);
                            jTextFieldFileOnLocalhost.setText(tmp);
                            break;
                        }
                    }
                    tokencounter++;
                }
                jTextAreaSystemLog.append("\n" + getDateAndTime() + " Файл настроек прочитан...");
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
                    + jTextFieldFileOnServer.getText() + ";"
                    + jTextFieldFileOnLocalhost.getText();
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
            TmpOptions.set_cp_file(jTextFieldFileOnServer.getText());
            TmpOptions.set_pc_file(jTextFieldFileOnLocalhost.getText());

        } catch (IOException e) {
        }
        jTextAreaSystemLog.append("\n" + getDateAndTime() + " Настройки записаны...");
    }

    public String getDateAndTime() {
        Date date = new Date(System.currentTimeMillis());
        String customerDate = (1900 + date.getYear()) + "-"
                + (1 + date.getMonth()) + "-" + date.getDate()
                + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        return customerDate;
    }
}

