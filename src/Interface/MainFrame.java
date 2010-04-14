package Interface;

import java.awt.event.ActionEvent;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.io.*;
import URBD1SLib.ftp.*;
import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.SystemTray.*;
import java.awt.Toolkit;
import java.awt.Toolkit.*;
import java.awt.TrayIcon;
import java.awt.TrayIcon.*;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 *
 * @author support
 */
public class MainFrame extends javax.swing.JFrame implements Serializable{

//    Здесь будут писаться заметки, предложения, дальнейшие пожелания для дальнейшей реализации в программе
//    1. Реализовать работу программы по расписанию
//    1.1. Переход на xml-формат хранения настроек программы, расписания и списка баз
//    2. Реализовать работу ФТП через прокси-сервер
//    3. Реализовать работу с несколькими базами.
//    4. Реализавать выполнение сервисных функций 1С. Реализацию сделать через хэш-мап
//    5. Автозапуск(служба или автозагрузка?)
//    6. Сообщение пользователю о удачной или неудачной выгрузке.
//    7. Отправка отчета на сервер о причинах неудачного обмена????

    //объявляем глобальные переменные
    public static Log _log = LogFactory.getLog(MainFrame.class);
    File optionFile;            //файл с настройками (путь)
    File helpFile;              //файл с помощью
    options TmpOptions = new options();
    ftp_work exchange = null;
    run_1s run1s = null;
    JFileChooser jFileChooserPlatformSource;
    JFileChooser jFileChooserBaseSource;
    java.awt.Image image;
    TrayIcon icon = null;
    PopupMenu iconpopup;
    java.util.TimerTask juTT;
    byte key;
    boolean key_stop = false;
    LinkedList<schedulerURBD> schedURBD;
    
        class DrawProgressBar extends Thread implements Serializable
        {
            @Override
            public void run()
            {
                while (thr.isAlive())
                {
                    try
                    {
                        DrawProgressBar.sleep(500);
                        if (jProgressBar1.getValue()<jProgressBar1.getMaximum())
                        {
                            jProgressBar1.setValue(jProgressBar1.getValue()+jProgressBar1.getMaximum()/10);
                        }
                        else jProgressBar1.setValue(jProgressBar1.getMinimum());
                    }
                    catch (InterruptedException err)
                    {
                        if (jProgressBar1.getValue()<jProgressBar1.getMaximum())
                        {
                            jProgressBar1.setValue(jProgressBar1.getValue()+jProgressBar1.getMaximum()/10);
                        }
                        else jProgressBar1.setValue(jProgressBar1.getMinimum());
                    }
                }
                jProgressBar1.setValue(jProgressBar1.getMaximum());
            }
            public Thread thr;
            public DrawProgressBar(Thread tmp)
            {
                this.thr = tmp;
            }
        }

        class RunExchangeInThread implements Runnable, Serializable
        {
            private int keythread;
            public void run()
            {
                switch(this.keythread)
                {
                    case 0:
                        {
                            jButtonRunAll.setEnabled(false);
                            GetFileOnFTP();
                            RunWith1S();
                            PutFileOnFTP();
                            jButtonRunAll.setEnabled(true);
                            break;
                        }
                    case 1:
                        {
                            GetFileOnFTP();
                            break;
                        }
                    case 2:
                        {
                            RunWith1S();
                            break;
                        }
                    case 3:
                        {
                            PutFileOnFTP();
                            break;
                        }
                    case 4:
                        {
                            GetFileOnFTP();
                            RunWith1S();
                            break;
                        }
                    case 5:
                        {
                            RunWith1S();
                            PutFileOnFTP();
                            break;
                        }
                }
            }
            public RunExchangeInThread(int keythread)
            {
                this.keythread = keythread;
            }
        }

    /**
     *
     */
    public MainFrame()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("FileChooser.lookInLabelText", "Папка");
            UIManager.put("FileChooser.filesOfTypeLabelText", "Тип файла");
            UIManager.put("FileChooser.upFolderToolTipText", "На один уровень вверх");
            UIManager.put("FileChooser.fileNameLabelText", "Имя файла");
            UIManager.put("FileChooser.homeFolderToolTipText", "Домашняя папка");
            UIManager.put("FileChooser.newFolderToolTipText", "Новая папка");
            UIManager.put("FileChooser.listViewButtonToolTipText", "Список");
            UIManager.put("FileChooser.detailsViewButtonToolTipText", "Детально");
            UIManager.put("FileChooser.saveButtonText", "Сохранить");
            UIManager.put("FileChooser.openButtonText", "Открыть");
            UIManager.put("FileChooser.cancelButtonText", "Отменить");
            UIManager.put("FileChooser.updateButtonText", "Вверх");
            UIManager.put("FileChooser.helpButtonText", "Помощь");
            UIManager.put("FileChooser.saveButtonToolTipText", "Сохранить");
            UIManager.put("FileChooser.openButtonToolTipText", "Открыть выбранный файл");
            UIManager.put("FileChooser.cancelButtonToolTipText", "Закрыть диалог");
            UIManager.put("FileChooser.listViewButtonAccessibleName", "Список");
        }
        catch (Exception err){}
        
        jFileChooserPlatformSource = new JFileChooser();
        jFileChooserPlatformSource.setDialogTitle("Диалог выбора папки 1С:Предприятие");
        jFileChooserBaseSource = new JFileChooser();
        jFileChooserBaseSource.setDialogTitle("Диалог выбора папки базы");
        initComponents();
        setLocationRelativeTo(null);
        jButtonRunDownload.setEnabled(false);
        jButtonRunUpload.setEnabled(true);
        jButtonRunOutfile.setEnabled(false);
        jButtonRunSynch.setVisible(false);
        checkingOptionFile();
        getDateAndTime();
        schedURBD = new LinkedList<schedulerURBD>();
        MenuItem exitpopup = new MenuItem("Выход");
        MenuItem allExchange = new MenuItem("Полный обмен");
        MenuItem inExchange = new MenuItem("Загрузка");
        MenuItem outExchange = new MenuItem("Выгрузка");
        iconpopup = new PopupMenu("IconPopUP");
        iconpopup.add(allExchange);
        iconpopup.add(inExchange);
        iconpopup.add(outExchange);
        iconpopup.addSeparator();
        iconpopup.add(exitpopup);
        
        exitpopup.addActionListener(new ActionListener()
                                        {
                                            public void actionPerformed(ActionEvent e)
                                            {
                                                MainFrame.this.setExtendedState(MainFrame.NORMAL);
                                                java.awt.SystemTray.getSystemTray().remove(icon);
                                                dispose();
                                            }
                                        });

        allExchange.addActionListener(new ActionListener()
                                          {
                                              public void actionPerformed(ActionEvent e)
                                              {
                                                  jButtonRunAllActionPerformed(e);
                                              }
                                          });

        inExchange.addActionListener(new ActionListener()
                                         {
                                             public void actionPerformed(ActionEvent e)
                                             {
                                                 key = 1;
                                                 Thread thr = (new Thread(new RunExchangeInThread(4)));
                                                 thr.start();
                                                 (new Thread(new DrawProgressBar(thr))).start();
                                             }
                                         });

        outExchange.addActionListener(new ActionListener()
                                          {
                                             public void actionPerformed(ActionEvent e)
                                             {
                                                 key = 2;
                                                 Thread thr = (new Thread(new RunExchangeInThread(5)));
                                                 thr.start();
                                                 (new Thread(new DrawProgressBar(thr))).start();
                                             }
        });

        image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("arrow_refresh.png"));
        setIconImage(image);
        if (SystemTray.isSupported())
        {
            icon = new TrayIcon(image);
            icon.setToolTip(MainFrame.this.getTitle());
            icon.setPopupMenu(iconpopup);
            icon.addActionListener(new ActionListener()
                                       {
                                           public void actionPerformed(ActionEvent e)
                                           {
                                               MainFrame.this.setVisible(true);
                                               MainFrame.this.setExtendedState(MainFrame.NORMAL);
                                               java.awt.SystemTray.getSystemTray().remove(icon);
                                           }
                                       });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenuTextArea = new javax.swing.JPopupMenu();
        jMenuItemTextAreaClear = new javax.swing.JMenuItem();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelMain = new javax.swing.JPanel();
        jButtonRunOutfile = new javax.swing.JButton();
        jButtonRunUpload = new javax.swing.JButton();
        jButtonRunDownload = new javax.swing.JButton();
        jButtonRunInfile = new javax.swing.JButton();
        jButtonRunAll = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaSystemLog = new javax.swing.JTextArea();
        jProgressBar1 = new javax.swing.JProgressBar();
        jPanelSchedule = new javax.swing.JPanel();
        jSpinnerTimer = new javax.swing.JSpinner();
        jComboBoxFrequency = new javax.swing.JComboBox();
        jButtonAddJob = new javax.swing.JButton();
        jButtonRemoveJob = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableListJob = new javax.swing.JTable();
        jPanelOptions = new javax.swing.JPanel();
        jTextFieldBaseSource = new javax.swing.JTextField();
        jButtonCancel = new javax.swing.JButton();
        jButtonApply = new javax.swing.JButton();
        jTextFieldFTPUser = new javax.swing.JTextField();
        jLabelFTPUser = new javax.swing.JLabel();
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
        jLabelInfileOnServer = new javax.swing.JLabel();
        jLabelInfileOnLocalhost = new javax.swing.JLabel();
        jLabelFTPPass = new javax.swing.JLabel();
        jTextFieldOutfileOnLocalhost = new javax.swing.JTextField();
        jLabelOutfileOnLocalhost = new javax.swing.JLabel();
        jTextFieldOutfileOnServer = new javax.swing.JTextField();
        jLabelOutfileOnServer = new javax.swing.JLabel();
        jCheckBoxMinimiz = new javax.swing.JCheckBox();
        jPanelSync = new javax.swing.JPanel();
        jLabelSyncBaseSource = new javax.swing.JLabel();
        jLabelSyncFTPdir = new javax.swing.JLabel();
        jLabelSyncFTP = new javax.swing.JLabel();
        jTextFieldSyncFTP = new javax.swing.JTextField();
        jTextFieldSyncBaseSource = new javax.swing.JTextField();
        jTextFieldSyncFTPdir = new javax.swing.JTextField();
        jLabelSyncFTPUser = new javax.swing.JLabel();
        jTextFieldSyncFTPUser = new javax.swing.JTextField();
        jTextFieldSyncFTPPass = new javax.swing.JPasswordField();
        jLabelSyncFTPPass = new javax.swing.JLabel();
        jButtonRunSynch = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuFileExit = new javax.swing.JMenuItem();
        jMenuQuestion = new javax.swing.JMenu();
        jMenuQuestionHelp = new javax.swing.JMenuItem();
        jMenuQustionAbout = new javax.swing.JMenuItem();

        jMenuItemTextAreaClear.setText("Очистить лог");
        jMenuItemTextAreaClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTextAreaClearActionPerformed(evt);
            }
        });
        jPopupMenuTextArea.add(jMenuItemTextAreaClear);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Клиент URBD1Slib");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowIconified(java.awt.event.WindowEvent evt) {
                formWindowIconified(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jButtonRunOutfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/upload.png"))); // NOI18N
        jButtonRunOutfile.setText("Отправить на сервер");
        jButtonRunOutfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRunOutfileActionPerformed(evt);
            }
        });

        jButtonRunUpload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/database-export.png"))); // NOI18N
        jButtonRunUpload.setText("Выгрузить из базы");
        jButtonRunUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRunUploadActionPerformed(evt);
            }
        });

        jButtonRunDownload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/database-import.png"))); // NOI18N
        jButtonRunDownload.setText("Загрузить в базу");
        jButtonRunDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRunDownloadActionPerformed(evt);
            }
        });

        jButtonRunInfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/download.png"))); // NOI18N
        jButtonRunInfile.setText("Принять с сервера");
        jButtonRunInfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRunInfileActionPerformed(evt);
            }
        });

        jButtonRunAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/arrow_refresh.png"))); // NOI18N
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
        jTextAreaSystemLog.setToolTipText("");
        jTextAreaSystemLog.setComponentPopupMenu(jPopupMenuTextArea);
        jScrollPane2.setViewportView(jTextAreaSystemLog);

        jProgressBar1.setForeground(new java.awt.Color(0, 102, 255));

        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButtonRunOutfile, javax.swing.GroupLayout.PREFERRED_SIZE, 146, Short.MAX_VALUE)
                        .addComponent(jButtonRunUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 146, Short.MAX_VALUE)
                        .addComponent(jButtonRunDownload, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                        .addComponent(jButtonRunAll, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                        .addComponent(jButtonRunInfile, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMainLayout.createSequentialGroup()
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 172, Short.MAX_VALUE)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Главная", new javax.swing.ImageIcon(getClass().getResource("/Interface/home.png")), jPanelMain); // NOI18N

        jSpinnerTimer.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), new java.util.Date(1262293200000L), null, java.util.Calendar.HOUR_OF_DAY));
        jSpinnerTimer.setPreferredSize(new java.awt.Dimension(124, 22));

        jComboBoxFrequency.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Один раз", "Каждый час", "Каждый день", "Каждую неделю", "Каждый месяц", "Каждый год" }));

        jButtonAddJob.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/plus.png"))); // NOI18N
        jButtonAddJob.setPreferredSize(new java.awt.Dimension(42, 23));
        jButtonAddJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddJobActionPerformed(evt);
            }
        });

        jButtonRemoveJob.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/minus.png"))); // NOI18N
        jButtonRemoveJob.setPreferredSize(new java.awt.Dimension(41, 23));
        jButtonRemoveJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveJobActionPerformed(evt);
            }
        });

        jTableListJob.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Время начала", "Периодичность"
            }
        ));
        jTableListJob.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jTableListJob);

        javax.swing.GroupLayout jPanelScheduleLayout = new javax.swing.GroupLayout(jPanelSchedule);
        jPanelSchedule.setLayout(jPanelScheduleLayout);
        jPanelScheduleLayout.setHorizontalGroup(
            jPanelScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelScheduleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jComboBoxFrequency, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSpinnerTimer, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelScheduleLayout.createSequentialGroup()
                        .addComponent(jButtonAddJob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRemoveJob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(185, 185, 185))
        );
        jPanelScheduleLayout.setVerticalGroup(
            jPanelScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelScheduleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                    .addGroup(jPanelScheduleLayout.createSequentialGroup()
                        .addComponent(jSpinnerTimer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxFrequency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonAddJob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonRemoveJob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Расписание", new javax.swing.ImageIcon(getClass().getResource("/Interface/clock.png")), jPanelSchedule); // NOI18N

        jTextFieldBaseSource.setPreferredSize(new java.awt.Dimension(6, 23));

        jButtonCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/cancel.png"))); // NOI18N
        jButtonCancel.setText("Отмена");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jButtonApply.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/accept.png"))); // NOI18N
        jButtonApply.setText("Применить");
        jButtonApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonApplyActionPerformed(evt);
            }
        });

        jLabelFTPUser.setText("FTP-пользователь");

        jTextFieldFTPSource.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldFTPSourceKeyReleased(evt);
            }
        });

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

        jLabelInfileOnServer.setText("Файл загрузки на сервере");

        jLabelInfileOnLocalhost.setText("Файл загрузки локально");

        jLabelFTPPass.setText("FTP-пароль");

        jLabelOutfileOnLocalhost.setText("Файл выгрузки локально");

        jLabelOutfileOnServer.setText("Файл выгрузки на сервере");

        jCheckBoxMinimiz.setText("Запускать минимизированной");

        javax.swing.GroupLayout jPanelOptionsLayout = new javax.swing.GroupLayout(jPanelOptions);
        jPanelOptions.setLayout(jPanelOptionsLayout);
        jPanelOptionsLayout.setHorizontalGroup(
            jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelPlatformSource)
                            .addComponent(jLabelBaseSource)
                            .addComponent(jLabelFTPSource)
                            .addComponent(jLabelFTPUser)
                            .addComponent(jCheckBoxMinimiz, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelFTPPass)
                            .addComponent(jLabelInfileOnServer)
                            .addComponent(jLabelInfileOnLocalhost)
                            .addComponent(jLabelOutfileOnServer)
                            .addComponent(jLabelOutfileOnLocalhost))
                        .addGap(23, 23, 23)
                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jTextFieldOutfileOnServer)
                                .addComponent(jTextFieldInfileOnServer, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextFieldOutfileOnLocalhost)
                                .addComponent(jTextFieldInfileOnLocalhost, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jTextFieldPlatformSource, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldBaseSource, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jTextFieldFTPSource, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jTextFieldFTPPass, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextFieldFTPUser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jButtonSelPlatformSource, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelOptionsLayout.createSequentialGroup()
                                        .addGap(13, 13, 13)
                                        .addComponent(jButtonSelBaseSource, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextFieldBasePass, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jTextFieldBaseUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelBaseUserAndPass)))
                .addGap(180, 180, 180))
            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addComponent(jButtonApply)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 228, Short.MAX_VALUE)
                .addComponent(jButtonCancel)
                .addGap(122, 122, 122))
        );
        jPanelOptionsLayout.setVerticalGroup(
            jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBoxMinimiz)
                .addGap(3, 3, 3)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addComponent(jLabelPlatformSource)
                        .addGap(14, 14, 14)
                        .addComponent(jLabelBaseSource)
                        .addGap(20, 20, 20)
                        .addComponent(jLabelBaseUserAndPass)
                        .addGap(40, 40, 40)
                        .addComponent(jLabelFTPSource)
                        .addGap(17, 17, 17)
                        .addComponent(jLabelFTPUser)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelFTPPass)
                        .addGap(7, 7, 7))
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jTextFieldPlatformSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButtonSelPlatformSource, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonSelBaseSource, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldBaseSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldBaseUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldBasePass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldFTPSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jTextFieldFTPUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jTextFieldFTPPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInfileOnServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelInfileOnServer))
                .addGap(12, 12, 12)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInfileOnLocalhost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelInfileOnLocalhost))
                .addGap(12, 12, 12)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldOutfileOnLocalhost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelOutfileOnLocalhost))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldOutfileOnServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelOutfileOnServer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonApply)
                    .addComponent(jButtonCancel))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Настройки", new javax.swing.ImageIcon(getClass().getResource("/Interface/property.png")), jPanelOptions); // NOI18N

        jPanelSync.setVerifyInputWhenFocusTarget(false);

        jLabelSyncBaseSource.setText("Папка базы локально");

        jLabelSyncFTPdir.setText("Папка на сервере");

        jLabelSyncFTP.setText("FTP-хост");

        jTextFieldSyncFTP.setEnabled(false);

        jTextFieldSyncBaseSource.setEnabled(false);

        jLabelSyncFTPUser.setText("FTP-пользователь");

        jTextFieldSyncFTPPass.setText("jPasswordField1");

        jLabelSyncFTPPass.setText("FTP-пароль");

        jButtonRunSynch.setText("Синхронизация");

        javax.swing.GroupLayout jPanelSyncLayout = new javax.swing.GroupLayout(jPanelSync);
        jPanelSync.setLayout(jPanelSyncLayout);
        jPanelSyncLayout.setHorizontalGroup(
            jPanelSyncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSyncLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanelSyncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelSyncLayout.createSequentialGroup()
                        .addComponent(jButtonRunSynch)
                        .addContainerGap())
                    .addGroup(jPanelSyncLayout.createSequentialGroup()
                        .addGroup(jPanelSyncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelSyncLayout.createSequentialGroup()
                                .addComponent(jLabelSyncBaseSource)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextFieldSyncBaseSource, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelSyncLayout.createSequentialGroup()
                                .addComponent(jLabelSyncFTP)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                                .addComponent(jTextFieldSyncFTP, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSyncLayout.createSequentialGroup()
                                .addGroup(jPanelSyncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelSyncFTPdir)
                                    .addComponent(jLabelSyncFTPUser)
                                    .addComponent(jLabelSyncFTPPass))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                                .addGroup(jPanelSyncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldSyncFTPPass)
                                    .addComponent(jTextFieldSyncFTPUser)
                                    .addComponent(jTextFieldSyncFTPdir, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))))
                        .addContainerGap(347, Short.MAX_VALUE))))
        );
        jPanelSyncLayout.setVerticalGroup(
            jPanelSyncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSyncLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanelSyncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelSyncFTP)
                    .addComponent(jTextFieldSyncFTP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSyncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelSyncBaseSource)
                    .addComponent(jTextFieldSyncBaseSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSyncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSyncFTPdir)
                    .addComponent(jTextFieldSyncFTPdir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSyncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelSyncLayout.createSequentialGroup()
                        .addComponent(jLabelSyncFTPUser)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelSyncFTPPass)
                        .addGap(7, 7, 7))
                    .addGroup(jPanelSyncLayout.createSequentialGroup()
                        .addComponent(jTextFieldSyncFTPUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldSyncFTPPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonRunSynch)
                .addContainerGap(167, Short.MAX_VALUE))
        );

        jButtonRunSynch.getAccessibleContext().setAccessibleParent(jPanelSync);

        jTabbedPane1.addTab("Синхронизация", jPanelSync);

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

        jMenuQuestionHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/question-frame.png"))); // NOI18N
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
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuFileExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuFileExitActionPerformed
        dispose();
    }//GEN-LAST:event_jMenuFileExitActionPerformed

    private void jMenuQuestionHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuQuestionHelpActionPerformed
        //здесь показ хелпа в формате txt или chm
        String userDir = new String();
        userDir = System.getProperty("user.dir") + System.getProperty("file.separator") + "Help" + System.getProperty("file.separator") + "AvtoURIB.chm";
        helpFile = new File(userDir);
        try {
            Desktop.getDesktop().open(helpFile);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuQuestionHelpActionPerformed

    private void jMenuQustionAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuQustionAboutActionPerformed
        new AboutFrame().setVisible(true);
    }//GEN-LAST:event_jMenuQustionAboutActionPerformed

    private void formWindowIconified(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowIconified
        this.setVisible(false);
        try
        {
            java.awt.SystemTray.getSystemTray().add(icon);
        }
        catch (AWTException e1)
        {
            e1.printStackTrace();
        }
    }//GEN-LAST:event_formWindowIconified

    private void jMenuItemTextAreaClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemTextAreaClearActionPerformed
        jTextAreaSystemLog.setText(null);
    }//GEN-LAST:event_jMenuItemTextAreaClearActionPerformed

@SuppressWarnings(value = "static-access")
    private void jButtonSelBaseSourceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelBaseSourceActionPerformed
        // указываем папку с базой
        // открыть диалог выбора файла; если файл выбран - присваиваем его имя в поле
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.MD", "MD");
        jFileChooserBaseSource.setFileFilter(filter);
        int result = jFileChooserBaseSource.showOpenDialog(null);   //объявляем, в след.строке присваиваем
        if (result == jFileChooserBaseSource.APPROVE_OPTION) {
            jTextFieldBaseSource.setText(jFileChooserBaseSource.getSelectedFile().getParent());
            jTextFieldSyncBaseSource.setText(jFileChooserBaseSource.getSelectedFile().getParent());
        }
}//GEN-LAST:event_jButtonSelBaseSourceActionPerformed

@SuppressWarnings(value = "static-access")
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

private void jButtonApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonApplyActionPerformed
    if (jTextFieldPlatformSource.getText().equals("") || jTextFieldBaseSource.getText().equals("") 
            || jTextFieldBaseUser.getText().equals("") || jTextFieldBasePass.getText().equals("")
            || jTextFieldFTPSource.getText().equals("") || jTextFieldFTPUser.getText().equals("")
            || jTextFieldFTPPass.getText().equals("") || jTextFieldInfileOnLocalhost.getText().equals("")
            || jTextFieldInfileOnServer.getText().equals("") || jTextFieldOutfileOnLocalhost.getText().equals("")
            || jTextFieldOutfileOnServer.getText().equals("")) {
                jTextAreaSystemLog.append("\n" + getDateAndTime() + " Заполнены не все поля настроек!");
                javax.swing.JOptionPane.showMessageDialog(null, "Заполнены не все поля настроек!","Ошибка",javax.swing.JOptionPane.WARNING_MESSAGE);
    }
    else {
        Apply();
    }
}//GEN-LAST:event_jButtonApplyActionPerformed

private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
    dispose();
}//GEN-LAST:event_jButtonCancelActionPerformed

private void jButtonRunInfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRunInfileActionPerformed
    jButtonRunInfile.setEnabled(false);
    Thread thr = (new Thread(new RunExchangeInThread(1)));
    thr.start();
    (new Thread(new DrawProgressBar(thr))).start();
    jButtonRunDownload.setEnabled(true);
}//GEN-LAST:event_jButtonRunInfileActionPerformed

private void jButtonRunDownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRunDownloadActionPerformed
    key = 1;
    jButtonRunDownload.setEnabled(false);
    Thread thr = (new Thread(new RunExchangeInThread(2)));
    thr.start();
    (new Thread(new DrawProgressBar(thr))).start();
    jButtonRunInfile.setEnabled(true);
}//GEN-LAST:event_jButtonRunDownloadActionPerformed

private void jButtonRunUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRunUploadActionPerformed
    key = 2;
    jButtonRunUpload.setEnabled(false);
    Thread thr = (new Thread(new RunExchangeInThread(2)));
    thr.start();
    (new Thread(new DrawProgressBar(thr))).start();
    jButtonRunOutfile.setEnabled(true);
}//GEN-LAST:event_jButtonRunUploadActionPerformed

private void jButtonRunOutfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRunOutfileActionPerformed
    jButtonRunOutfile.setEnabled(false);
    Thread thr = (new Thread(new RunExchangeInThread(3)));
    thr.start();
    (new Thread(new DrawProgressBar(thr))).start();
    jButtonRunUpload.setEnabled(true);
}//GEN-LAST:event_jButtonRunOutfileActionPerformed

private void jButtonAddJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddJobActionPerformed
    schedURBD.add(new schedulerURBD(((Date)jSpinnerTimer.getValue()),jComboBoxFrequency.getSelectedIndex()));
    ((DefaultTableModel)jTableListJob.getModel()).setRowCount(jTableListJob.getRowCount()+1);
    jTableListJob.setValueAt(schedURBD.getLast(),jTableListJob.getRowCount()-1,0);
    jTableListJob.setValueAt(jComboBoxFrequency.getSelectedItem(),jTableListJob.getRowCount()-1,1);
    schedURBD.getLast().createSCHED(this);
    schedURBD.getLast().start();
}//GEN-LAST:event_jButtonAddJobActionPerformed

private void jButtonRemoveJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveJobActionPerformed
    int _selectRow = jTableListJob.getSelectedRow();
    if (jTableListJob.getSelectedRow()!=-1)
    {
        if (schedURBD.get(_selectRow).stop()==true)
        {
            ((DefaultTableModel)jTableListJob.getModel()).removeRow(_selectRow);
            jTextAreaSystemLog.append("\n" + getDateAndTime() +" удалено задание "+ schedURBD.get(_selectRow).job.getFullName());
            schedURBD.remove(_selectRow);
        }
        else
        {
            jTextAreaSystemLog.append("\n" + getDateAndTime() +" Не могу удалить задание "+ schedURBD.get(_selectRow).job.getFullName());
        }
    }
    else
    {
        javax.swing.JOptionPane.showMessageDialog(null, "Выберите задание для удаления!!!","!!! В Н И М А Н И Е !!!",javax.swing.JOptionPane.WARNING_MESSAGE);
    }
}//GEN-LAST:event_jButtonRemoveJobActionPerformed

private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
    loadScheduler(evt);
}//GEN-LAST:event_formWindowOpened

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    saveScheduler();
}//GEN-LAST:event_formWindowClosing

private void jTextFieldFTPSourceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldFTPSourceKeyReleased
    jTextFieldSyncFTP.setText(jTextFieldFTPSource.getText());
}//GEN-LAST:event_jTextFieldFTPSourceKeyReleased

private void jButtonRunAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRunAllActionPerformed
    key = 0;
    Thread thr = (new Thread(new RunExchangeInThread(0)));
    thr.start();
    (new Thread(new DrawProgressBar(thr))).start();
}//GEN-LAST:event_jButtonRunAllActionPerformed

    private void Apply() {
        //выгружаем в файл настроек и записываем его
        jTextAreaSystemLog.append("\n" + getDateAndTime() + " Записываю настройки в файл...");
        saveOptions();
    }

    public void GetFileOnFTP()
    {
        byte err;
        exchange = new ftp_work(TmpOptions.get_FTP_SERVER_NAME(),
                                TmpOptions.get_FTP_SERVER_LOGIN(),
                                TmpOptions.get_FTP_SERVER_PASS());
        
        jTextAreaSystemLog.append("\n--------------------------------------------------");
        jTextAreaSystemLog.append("\n" + getDateAndTime() + " Подключение к фтп-серверу...");
        this.getRootPane().updateUI();
        if ((err=exchange.ftp_connect()) == consterr.NOT_ERR)
        {
            jTextAreaSystemLog.append("\n" + getDateAndTime() + " Подключение прошло успешно...");
            jTextAreaSystemLog.append("\n" + getDateAndTime() + " Загружаю файл...");
            if ((err=exchange.get_file(TmpOptions.get_cp_file_on_ftp(),new File(TmpOptions.get_cp_file_on_localhos()))) == consterr.NOT_ERR)
            {
                jTextAreaSystemLog.append("\n" + getDateAndTime() + " Получение файла прошло успешно...");
                jTextAreaSystemLog.append("\n--------------------------------------------------");
                return;
            }
            else
            {
                jTextAreaSystemLog.append("\n" + getDateAndTime() + " "+consterr.PrintErr(err));
                jTextAreaSystemLog.append("\n--------------------------------------------------");
                return;
            }
        }
        else
        {
            jTextAreaSystemLog.append("\n" + getDateAndTime() + " " + consterr.PrintErr(err));
            jTextAreaSystemLog.append("\n--------------------------------------------------");
            return;
        }
    }

    public void PutFileOnFTP()
    {
        byte err;
        exchange = new ftp_work(TmpOptions.get_FTP_SERVER_NAME(),
                                TmpOptions.get_FTP_SERVER_LOGIN(),
                                TmpOptions.get_FTP_SERVER_PASS());

        jTextAreaSystemLog.append("\n--------------------------------------------------");
        jTextAreaSystemLog.append("\n" + getDateAndTime() + " Подключение к фтп-серверу...");
        this.getRootPane().updateUI();
        if ((err=exchange.ftp_connect()) == consterr.NOT_ERR)
        {
            jTextAreaSystemLog.append("\n" + getDateAndTime() + " Подключение прошло успешно...");
            jTextAreaSystemLog.append("\n" + getDateAndTime() + " Отправляю файл...");

            if ((err=exchange.put_file(TmpOptions.get_pc_file_on_ftp(), new File(TmpOptions.get_pc_file_on_localhost()))) == consterr.NOT_ERR)
            {
                jTextAreaSystemLog.append("\n" + getDateAndTime() + " Файл успешно отправлен...");
                jTextAreaSystemLog.append("\n--------------------------------------------------");
                return;
            }
            else
            {
                jTextAreaSystemLog.append("\n" + getDateAndTime() + " "+consterr.PrintErr(err));
                jTextAreaSystemLog.append("\n--------------------------------------------------");
                return;
            }
        }
        else
        {
            jTextAreaSystemLog.append("\n" + getDateAndTime() + " " + consterr.PrintErr(err));
            jTextAreaSystemLog.append("\n--------------------------------------------------");
            return;
        }
    }

    public void RunWith1S()
    {
        int err;
        run1s = new run_1s(TmpOptions.get_PATH_1S(),
                           TmpOptions.get_PATH_BASE(),
                           TmpOptions.get_BASE_LOGIN(),
                           new String(TmpOptions.get_BASE_PASS()));
        List<String> tmplst = null;
        err=run1s.create_prm(key);
        if (err==consterr.NOT_ERR)
        {
            jTextAreaSystemLog.append("\n" + getDateAndTime() + " Запускаем 1С");
            err=run1s.start();
            if (err==consterr.NOT_ERR)
            {
                tmplst = run1s.parsing_log();
                if (tmplst != null)
                {
                    for (int i = 0; i < tmplst.size(); i++)
                    {
                        jTextAreaSystemLog.append("\n" + getDateAndTime() +" "+ tmplst.get(i));
                    }
                }
                else
                {
                    jTextAreaSystemLog.append("\n" + getDateAndTime() +" "+ consterr.PrintErr((byte)err));
                }
            }
            else
            {
                jTextAreaSystemLog.append("\n" + getDateAndTime() +" "+ consterr.PrintErr((byte)err));
            }
        }
        else
        {
            jTextAreaSystemLog.append("\n" + getDateAndTime() +" "+ consterr.PrintErr((byte)err));
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddJob;
    private javax.swing.JButton jButtonApply;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonRemoveJob;
    private javax.swing.JButton jButtonRunAll;
    private javax.swing.JButton jButtonRunDownload;
    private javax.swing.JButton jButtonRunInfile;
    private javax.swing.JButton jButtonRunOutfile;
    private javax.swing.JButton jButtonRunSynch;
    private javax.swing.JButton jButtonRunUpload;
    private javax.swing.JButton jButtonSelBaseSource;
    private javax.swing.JButton jButtonSelPlatformSource;
    private javax.swing.JCheckBox jCheckBoxMinimiz;
    private javax.swing.JComboBox jComboBoxFrequency;
    private javax.swing.JLabel jLabelBaseSource;
    private javax.swing.JLabel jLabelBaseUserAndPass;
    private javax.swing.JLabel jLabelFTPPass;
    private javax.swing.JLabel jLabelFTPSource;
    private javax.swing.JLabel jLabelFTPUser;
    private javax.swing.JLabel jLabelInfileOnLocalhost;
    private javax.swing.JLabel jLabelInfileOnServer;
    private javax.swing.JLabel jLabelOutfileOnLocalhost;
    private javax.swing.JLabel jLabelOutfileOnServer;
    private javax.swing.JLabel jLabelPlatformSource;
    private javax.swing.JLabel jLabelSyncBaseSource;
    private javax.swing.JLabel jLabelSyncFTP;
    private javax.swing.JLabel jLabelSyncFTPPass;
    private javax.swing.JLabel jLabelSyncFTPUser;
    private javax.swing.JLabel jLabelSyncFTPdir;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuFileExit;
    private javax.swing.JMenuItem jMenuItemTextAreaClear;
    private javax.swing.JMenu jMenuQuestion;
    private javax.swing.JMenuItem jMenuQuestionHelp;
    private javax.swing.JMenuItem jMenuQustionAbout;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelOptions;
    private javax.swing.JPanel jPanelSchedule;
    private javax.swing.JPanel jPanelSync;
    private javax.swing.JPopupMenu jPopupMenuTextArea;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinnerTimer;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableListJob;
    public javax.swing.JTextArea jTextAreaSystemLog;
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
    private javax.swing.JTextField jTextFieldSyncBaseSource;
    private javax.swing.JTextField jTextFieldSyncFTP;
    private javax.swing.JPasswordField jTextFieldSyncFTPPass;
    private javax.swing.JTextField jTextFieldSyncFTPUser;
    private javax.swing.JTextField jTextFieldSyncFTPdir;
    // End of variables declaration//GEN-END:variables

    javax.swing.JButton getjButtonRunAll()
    {
        return jButtonRunAll;
    }

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
            jButtonRunAll.setEnabled(false);
            jButtonRunDownload.setEnabled(false);
            jButtonRunInfile.setEnabled(false);
            jButtonRunOutfile.setEnabled(false);
            jButtonRunUpload.setEnabled(false);
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

                jButtonRunAll.setEnabled(false);
                jButtonRunDownload.setEnabled(false);
                jButtonRunInfile.setEnabled(false);
                jButtonRunOutfile.setEnabled(false);
                jButtonRunUpload.setEnabled(false);
            } catch (IOException ex) {
            }
        } else {
            jTextAreaSystemLog.append("\n" + getDateAndTime() + " Файл настроек найден...");
            loadOptions();
            jButtonRunAll.setEnabled(true);
            jButtonRunInfile.setEnabled(true);
            jButtonRunUpload.setEnabled(true);
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
                if (st.countTokens() < 14) {
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
                            jTextFieldSyncBaseSource.setText(tmp);
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
                            jTextFieldSyncFTP.setText(tmp);
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
                            TmpOptions.set_cp_file_on_ftp(tmp);
                            jTextFieldInfileOnServer.setText(tmp);
                            break;
                        }
                        case 9: {
                            tmp = st.nextToken();
                            TmpOptions.set_cp_file_on_localhost(tmp);
                            jTextFieldInfileOnLocalhost.setText(tmp);
                            break;
                        }
                        case 10:
                        {
                            tmp = st.nextToken();
                            TmpOptions.set_pc_file_on_localhost(tmp);
                            jTextFieldOutfileOnLocalhost.setText(tmp);
                            break;
                        }
                        case 11:
                        {
                            tmp = st.nextToken();
                            TmpOptions.set_pc_file_on_ftp(tmp);
                            jTextFieldOutfileOnServer.setText(tmp);
                            break;
                        }
                        case 12:
                        {
                            tmp = st.nextToken();
                            TmpOptions.set_sync_ftp_dir(tmp);
                            jTextFieldSyncFTPdir.setText(tmp);
                            break;
                        }
                        case 13:
                        {
                            tmp = st.nextToken();
                            TmpOptions.set_sync_ftp_user(tmp);
                            jTextFieldSyncFTPUser.setText(tmp);
                            break;
                        }
                        case 14:
                        {
                            tmp = st.nextToken();
                            TmpOptions.set_sync_ftp_pass(tmp.toCharArray());
                            jTextFieldSyncFTPPass.setText(tmp);
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
                    + jTextFieldInfileOnServer.getText() + ";"
                    + jTextFieldInfileOnLocalhost.getText() + ";"
                    + jTextFieldOutfileOnLocalhost.getText() + ";"
                    + jTextFieldOutfileOnServer.getText() + ";"
                    + jTextFieldSyncFTPdir.getText() + ";"
                    + jTextFieldSyncFTPUser.getText() + ";"
                    + new String(jTextFieldSyncFTPPass.getPassword());
//            System.out.println(optionString);
            outoptionfile.write(optionString);
            outoptionfile.close();

            TmpOptions.set_PATH_1S(jTextFieldPlatformSource.getText());
            TmpOptions.set_PATH_BASE(jTextFieldBaseSource.getText());
            TmpOptions.set_BASE_LOGIN(jTextFieldBaseUser.getText());
            TmpOptions.set_BASE_PASS(jTextFieldBasePass.getPassword());
            TmpOptions.set_FTP_SERVER_NAME(jTextFieldFTPSource.getText());
            TmpOptions.set_FTP_SERVER_LOGIN(jTextFieldFTPUser.getText());
            TmpOptions.set_FTP_SERVER_PASS(jTextFieldFTPPass.getPassword());
            TmpOptions.set_cp_file_on_ftp(jTextFieldInfileOnServer.getText());
            TmpOptions.set_cp_file_on_localhost(jTextFieldInfileOnLocalhost.getText());
            TmpOptions.set_pc_file_on_localhost(jTextFieldOutfileOnLocalhost.getText());
            TmpOptions.set_pc_file_on_ftp(jTextFieldOutfileOnServer.getText());
            TmpOptions.set_sync_ftp_dir(jTextFieldSyncFTPdir.getText());
            TmpOptions.set_sync_ftp_user(jTextFieldSyncFTPUser.getText());
            TmpOptions.set_sync_ftp_pass(jTextFieldSyncFTPPass.getPassword());

            jButtonRunAll.setEnabled(true);
            jButtonRunInfile.setEnabled(true);
            jButtonRunUpload.setEnabled(true);

        } catch (IOException e) {
        }
        jTextAreaSystemLog.append("\n" + getDateAndTime() + " Настройки записаны...");
    }

    private void saveScheduler()
    {
        java.util.Properties properties = new java.util.Properties();
        String _str =   System.getProperty("user.dir")+
                        System.getProperty("file.separator")+
                        "AvtoURIB"+
                        System.getProperty("file.separator")+
                        "sched.conf";
        File schedBin = new File(_str);

        try 
        {
            if (!schedBin.getParentFile().exists())
            {    if(schedBin.getParentFile().mkdirs())
                    if (!schedBin.createNewFile())
                    {
//                        System.out.println("не удалось :(");
                        return;
                    }
            }
            else
                if (!schedBin.exists())
                {
                    if (!schedBin.createNewFile())
                    {
                        _log.error("Ошибка при сохранение файла шедулера!!! ошибка создания файла!!!");
                        return;
                    }
                }
                else
                {
                    if(schedBin.delete())
                        if (!schedBin.createNewFile())
                        {
                            _log.error("Ошибка при сохранение файла шедулера!!! ошибка создания файла!!!");
                            return;
                        }
                }
        }
        catch (IOException err)
        {
            _log.error("Ошибка при сохранение файла шедулера!!! ошибка создания файла!!!");
            return;
        }
        try
        {
            InputStream is = new FileInputStream(schedBin);
            properties.load(is);
            properties.setProperty("countjobs",Integer.toString(schedURBD.size()));
            if (jCheckBoxMinimiz.isSelected())
                properties.setProperty("mini","1");
            else
                properties.setProperty("mini","0");
            for (int ind = 0;ind<schedURBD.size();ind++)
            {
                properties.setProperty("jobName"+ind,schedURBD.get(ind).jobName);
                properties.setProperty("triggerName"+ind,schedURBD.get(ind).triggerName);
                properties.setProperty("time"+ind,schedURBD.get(ind).time);
                properties.setProperty("frequency"+ind,Integer.toString(schedURBD.get(ind).Frequency));
                properties.setProperty("date"+ind,Long.toString(schedURBD.get(ind).jobDate.getTime()));
            }
            properties.store(new FileOutputStream(schedBin),"");
            is.close();
        }
        catch (FileNotFoundException err)
        {
            _log.error("Не найден файл для сохранения расписания");
            return;
        }
        catch (IOException err)
        {
            _log.error("Ошибка при сохранении расписания!!!");
            return;
        }
    }

    private void loadScheduler(java.awt.event.WindowEvent evt)
    {
        java.util.Properties properties = new java.util.Properties();
        String _str =   System.getProperty("user.dir")+
                        System.getProperty("file.separator")+
                        "AvtoURIB"+
                        System.getProperty("file.separator")+
                        "sched.conf";
        File schedBin = new File(_str);
        if (schedBin.exists())
        {
            try
            {
                properties.load(new FileInputStream(schedBin));
                int countjobs = Integer.parseInt(properties.getProperty("countjobs"));
                try
                {
                    if(properties.getProperty("mini").equals("1"))
                    {
                        jCheckBoxMinimiz.setSelected(true);
                        formWindowIconified(evt);
                    }
                    else
                        jCheckBoxMinimiz.setSelected(false);
                }
                catch (NullPointerException err){}
                schedURBD = new LinkedList<schedulerURBD>();
                for (int ind = 0;ind<countjobs;ind++)
                {
                    schedURBD.add(new schedulerURBD());
                    schedURBD.getLast().setFrequency(properties.getProperty("frequency"+ind));
                    schedURBD.getLast().setJobDate(properties.getProperty("date"+ind));
                    schedURBD.getLast().setJobName(properties.getProperty("jobName"+ind));
                    schedURBD.getLast().setTriggerName(properties.getProperty("triggerName"+ind));
                    schedURBD.getLast().setTime(properties.getProperty("time"+ind));
                    ((DefaultTableModel)jTableListJob.getModel()).setRowCount(jTableListJob.getRowCount()+1);
                    jTableListJob.setValueAt(schedURBD.getLast(),jTableListJob.getRowCount()-1,0);
                    jTableListJob.setValueAt(jComboBoxFrequency.getItemAt(ind),jTableListJob.getRowCount()-1,1);
                    schedURBD.getLast().createSCHED(this);
                    schedURBD.getLast().start();
                }
            }
            catch (IOException err)
            {
                _log.error("Ошибка при загрузки расписания!!!");
                return;
            }
        }
    }

    /**
     *
     * @return
     */
    public String getDateAndTime() {
        Date date = new Date(System.currentTimeMillis());
        String customerDate = (1900 + date.getYear()) + "-"
                + (1 + date.getMonth()) + "-" + date.getDate()
                + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
//        return customerDate;
          return (new Date()).toString();
    }
}