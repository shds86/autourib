package Interface;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import netlib.consterr;
import netlib.FTP;
import netlib.options;
import netlib.run_1s;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.mail.*;

/**
 *
 * @author support
 */
public class MainFrame extends javax.swing.JFrame {

//    Здесь будут писаться заметки, предложения, дальнейшие пожелания для дальнейшей реализации в программе
//    1. Реализавать выполнение сервисных функций 1С. Реализацию сделать через хэш-мап
//    2. Переход на xml-формат хранения настроек программы, расписания и списка баз
//    3. Реализовать работу ФТП через прокси-сервер
//    4. Реализовать работу с несколькими базами.
//    5. Автозапуск(служба или автозагрузка?)
//    6. Сообщение пользователю о удачной или неудачной выгрузке.
//    7. Отправка отчета на сервер о причинах неудачного обмена????
    //объявляем глобальные переменные
    public static Log _log = LogFactory.getLog(MainFrame.class);
    static final Logger logger = Logger.getLogger("testlogger");
    FileHandler fh;
    File optionFile;            //файл с настройками (путь)
    File helpFile;              //файл с помощью
    options TmpOptions = new options();
    FTP exchange = null;
    FTP syncFTP = null;
    run_1s run1s = null;
    JFileChooser jFileChooserPlatformSource;
    JFileChooser jFileChooserBaseSource;
    java.awt.Image image;
    TrayIcon icon = null;
    PopupMenu iconpopup;
    MenuItem exitpopup = new MenuItem("Выход");
    MenuItem allExchange = new MenuItem("Полный обмен");
    MenuItem inExchange = new MenuItem("Загрузка");
    MenuItem outExchange = new MenuItem("Выгрузка");
    MenuItem syncFTPPopUp = new MenuItem("Sync FTP");
    byte key;
    String log;
    Level _level;

    class DrawProgressBar extends Thread {

        byte count_image = 13;
        int ind = 0;

        @Override
        public void run() {
            while (thr.isAlive()) {
                try {
                    DrawProgressBar.sleep(200);
                    if (jProgressBar1.getValue() < jProgressBar1.getMaximum()) {
                        jProgressBar1.setValue(jProgressBar1.getValue() + jProgressBar1.getMaximum() / 10);
                        if (SystemTray.isSupported()) {
                            icon.setImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/image/busy-icon" + ind + ".png")));
                        }
                        if (ind > count_image) {
                            ind = 0;
                        } else {
                            ind++;
                        }
                    } else {
                        jProgressBar1.setValue(jProgressBar1.getMinimum());
                    }
                } catch (InterruptedException err) {
                    if (jProgressBar1.getValue() < jProgressBar1.getMaximum()) {
                        jProgressBar1.setValue(jProgressBar1.getValue() + jProgressBar1.getMaximum() / 10);
                        if (SystemTray.isSupported()) {
                            icon.setImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/image/busy-icon" + ind + ".png")));
                        }
                        if (ind > count_image) {
                            ind = 0;
                        } else {
                            ind++;
                        }
                        jProgressBar1.setValue(jProgressBar1.getValue() + jProgressBar1.getMaximum() / 10);
                    } else {
                        jProgressBar1.setValue(jProgressBar1.getMinimum());
                    }
                }
            }
            jProgressBar1.setValue(jProgressBar1.getMaximum());
            if (SystemTray.isSupported()) {
                icon.setImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/image/arrow_refresh.png")));
            }
        }
        public Thread thr;

        public DrawProgressBar(Thread tmp) {
            this.thr = tmp;
            ind = 0;
        }
    }

    class RunExchangeInThread implements Runnable {

        private int keythread;

        public void run() {
            switch (this.keythread) {
                case 0: {
                    setEnabledButtonsOff();
                    GetFileFromFTP();
                    RunWith1S();
                    PutFileToFTP();
                    setEnabledButtonsOn();
                    break;
                }
                case 1: {
                    setEnabledButtonsOff();
                    GetFileFromFTP();
                    setEnabledButtonsOn();
                    break;
                }
                case 2: {
                    setEnabledButtonsOff();
                    RunWith1S();
                    setEnabledButtonsOn();
                    break;
                }
                case 3: {
                    setEnabledButtonsOff();
                    PutFileToFTP();
                    setEnabledButtonsOn();
                    break;
                }
                case 4: {
                    setEnabledButtonsOff();
                    GetFileFromFTP();
                    RunWith1S();
                    setEnabledButtonsOn();
                    break;
                }
                case 5: {
                    setEnabledButtonsOff();
                    RunWith1S();
                    PutFileToFTP();
                    setEnabledButtonsOn();
                    break;
                }
                case 6: {
                    setEnabledButtonsOff();
                    RunSync();
                    setEnabledButtonsOn();
                    break;
                }
            }
        }

        private void setEnabledButtonsOff() {
            log = "";
            jButtonRunSync.setEnabled(false);
            syncFTPPopUp.setEnabled(false);
            jButtonRunAll.setEnabled(false);
            jButtonRunDownload.setEnabled(false);
            jButtonRunInfile.setEnabled(false);
            jButtonRunOutfile.setEnabled(false);
            jButtonRunUpload.setEnabled(false);
            allExchange.setEnabled(false);
            inExchange.setEnabled(false);
            outExchange.setEnabled(false);
        }

        private void setEnabledButtonsOn() {
            allExchange.setEnabled(true);
            inExchange.setEnabled(true);
            outExchange.setEnabled(true);
            jButtonRunAll.setEnabled(true);
            jButtonRunDownload.setEnabled(true);
            jButtonRunInfile.setEnabled(true);
            jButtonRunOutfile.setEnabled(true);
            jButtonRunUpload.setEnabled(true);
            jButtonRunSync.setEnabled(true);
            syncFTPPopUp.setEnabled(true);
            logger.log(_level, log);
        }

        public RunExchangeInThread(int keythread) {
            this.keythread = keythread;
        }
    }

    /**
     *
     */
    public MainFrame() {
        try {
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
        } catch (Exception err) {
        }
        try {
            fh = new FileHandler(System.getProperty("user.dir")
                    + System.getProperty("file.separator")
                    + "AvtoURIB"
                    + System.getProperty("file.separator")
                    + "log.txt");
        } catch (IOException err) {
        }
        logger.addHandler(fh);
        logger.setLevel(Level.ALL);

        jFileChooserPlatformSource = new JFileChooser();
        jFileChooserPlatformSource.setDialogTitle("Диалог выбора папки 1С:Предприятие");
        jFileChooserBaseSource = new JFileChooser();
        jFileChooserBaseSource.setDialogTitle("Диалог выбора папки базы");
        initComponents();
        setLocationRelativeTo(null);
        checkingOptionFile();
        jButtonAddJob.setVisible(false);
        jSpinnerTimer.setVisible(false);
        jComboBoxFrequency.setVisible(false);
        
        iconpopup = new PopupMenu("IconPopUP");
        iconpopup.add(allExchange);
        iconpopup.add(inExchange);
        iconpopup.add(outExchange);
        iconpopup.addSeparator();
        iconpopup.add(syncFTPPopUp);
        iconpopup.addSeparator();
        iconpopup.add(exitpopup);

        exitpopup.addActionListener(new ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e) {
//                                                MainFrame.this.setExtendedState(MainFrame.NORMAL);
                saveScheduler();
                java.awt.SystemTray.getSystemTray().remove(icon);
                System.exit(0);
            }
        });

        allExchange.addActionListener(new ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e) {
                RunAll();
            }
        });

        inExchange.addActionListener(new ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e) {
                RunIn();
            }
        });

        outExchange.addActionListener(new ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e) {
                RunOut();
            }
        });
        
        syncFTPPopUp.addActionListener(new ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e) {
                key = 6;
                Thread thr = (new Thread(new RunExchangeInThread(6)));
                thr.start();
                (new Thread(new DrawProgressBar(thr))).start();
            }
        });

        image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/image/arrow_refresh.png"));
        setIconImage(image);
        if (SystemTray.isSupported()) {
            icon = new TrayIcon(image);
            icon.setToolTip(MainFrame.this.getTitle());
            icon.setPopupMenu(iconpopup);
            icon.addActionListener(new ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e) {
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
        jMenuItemTextAreaSave = new javax.swing.JMenuItem();
        jMenuItemTextAreaClear = new javax.swing.JMenuItem();
        jPopupMenuListJob = new javax.swing.JPopupMenu();
        jMenuItemChange = new javax.swing.JMenuItem();
        jMenuItemDelete = new javax.swing.JMenuItem();
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
        jButtonRunSync = new javax.swing.JButton();
        jPanelSchedule = new javax.swing.JPanel();
        jSpinnerTimer = new javax.swing.JSpinner();
        jComboBoxFrequency = new javax.swing.JComboBox();
        jButtonAddJob = new javax.swing.JButton();
        jButtonRemoveJob = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableListJob = new javax.swing.JTable();
        jButtonAdd = new javax.swing.JButton();
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
        jCheckBoxExpert = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jLabelSyncBaseSource = new javax.swing.JLabel();
        jLabelSyncFTPPass = new javax.swing.JLabel();
        jTextFieldSyncLocalhostDestination = new javax.swing.JTextField();
        jTextFieldSyncFTPUser = new javax.swing.JTextField();
        jTextFieldSyncFTPSource = new javax.swing.JTextField();
        jLabelSyncFTPUser = new javax.swing.JLabel();
        jLabelSyncFTP = new javax.swing.JLabel();
        jLabelSyncFTPdir = new javax.swing.JLabel();
        jTextFieldSyncFTPPass = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldSyncFTPHost = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuFileExit = new javax.swing.JMenuItem();
        jMenuQuestion = new javax.swing.JMenu();
        jMenuQuestionHelp = new javax.swing.JMenuItem();
        jMenuQustionAbout = new javax.swing.JMenuItem();

        jMenuItemTextAreaSave.setText("Сохранить лог в файл");
        jMenuItemTextAreaSave.setEnabled(false);
        jMenuItemTextAreaSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTextAreaSaveActionPerformed(evt);
            }
        });
        jPopupMenuTextArea.add(jMenuItemTextAreaSave);

        jMenuItemTextAreaClear.setText("Очистить лог");
        jMenuItemTextAreaClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTextAreaClearActionPerformed(evt);
            }
        });
        jPopupMenuTextArea.add(jMenuItemTextAreaClear);

        jPopupMenuListJob.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                jPopupMenuListJobPopupMenuWillBecomeVisible(evt);
            }
        });

        jMenuItemChange.setText("Изменить");
        jMenuItemChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemChangeActionPerformed(evt);
            }
        });
        jPopupMenuListJob.add(jMenuItemChange);

        jMenuItemDelete.setText("Удалить");
        jMenuItemDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDeleteActionPerformed(evt);
            }
        });
        jPopupMenuListJob.add(jMenuItemDelete);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle(ver());
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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

        jPanelMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonRunOutfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/upload.png"))); // NOI18N
        jButtonRunOutfile.setText("Отправить на сервер");
        jButtonRunOutfile.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonRunOutfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRunOutfileActionPerformed(evt);
            }
        });
        jPanelMain.add(jButtonRunOutfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 172, 164, -1));

        jButtonRunUpload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/database-export.png"))); // NOI18N
        jButtonRunUpload.setText("Выгрузить из базы");
        jButtonRunUpload.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonRunUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRunUploadActionPerformed(evt);
            }
        });
        jPanelMain.add(jButtonRunUpload, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 136, 164, -1));

        jButtonRunDownload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/database-import.png"))); // NOI18N
        jButtonRunDownload.setText("Загрузить в базу");
        jButtonRunDownload.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonRunDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRunDownloadActionPerformed(evt);
            }
        });
        jPanelMain.add(jButtonRunDownload, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 164, -1));

        jButtonRunInfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/download.png"))); // NOI18N
        jButtonRunInfile.setText("Принять с сервера");
        jButtonRunInfile.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonRunInfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRunInfileActionPerformed(evt);
            }
        });
        jPanelMain.add(jButtonRunInfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 64, 164, -1));

        jButtonRunAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/arrow_refresh.png"))); // NOI18N
        jButtonRunAll.setText("Полный обмен");
        jButtonRunAll.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonRunAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRunAllActionPerformed(evt);
            }
        });
        jPanelMain.add(jButtonRunAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 28, 164, -1));

        jTextAreaSystemLog.setColumns(1);
        jTextAreaSystemLog.setEditable(false);
        jTextAreaSystemLog.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jTextAreaSystemLog.setRows(1);
        jTextAreaSystemLog.setToolTipText("");
        jTextAreaSystemLog.setComponentPopupMenu(jPopupMenuTextArea);
        jScrollPane2.setViewportView(jTextAreaSystemLog);

        jPanelMain.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(184, 11, 461, 392));

        jProgressBar1.setForeground(new java.awt.Color(0, 102, 255));
        jPanelMain.add(jProgressBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 389, 164, -1));

        jButtonRunSync.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/sync.png"))); // NOI18N
        jButtonRunSync.setText("Синхронизация");
        jButtonRunSync.setMaximumSize(new java.awt.Dimension(161, 25));
        jButtonRunSync.setMinimumSize(new java.awt.Dimension(161, 25));
        jButtonRunSync.setPreferredSize(new java.awt.Dimension(161, 25));
        jButtonRunSync.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRunSyncActionPerformed(evt);
            }
        });
        jPanelMain.add(jButtonRunSync, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 164, -1));

        jTabbedPane1.addTab("Главная", new javax.swing.ImageIcon(getClass().getResource("/image/home.png")), jPanelMain); // NOI18N

        jSpinnerTimer.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), new java.util.Date(1348424649400L), null, java.util.Calendar.HOUR_OF_DAY));
        jSpinnerTimer.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jSpinnerTimer.setEnabled(false);
        jSpinnerTimer.setMaximumSize(new java.awt.Dimension(200, 50));
        jSpinnerTimer.setPreferredSize(new java.awt.Dimension(124, 22));

        jComboBoxFrequency.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Один раз", "Каждый час", "Каждый день", "Каждую неделю", "Каждый месяц", "Каждый год" }));
        jComboBoxFrequency.setEnabled(false);

        jButtonAddJob.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/plus.png"))); // NOI18N
        jButtonAddJob.setEnabled(false);
        jButtonAddJob.setPreferredSize(new java.awt.Dimension(42, 23));
        jButtonAddJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddJobActionPerformed(evt);
            }
        });

        jButtonRemoveJob.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/minus.png"))); // NOI18N
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
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableListJob.setComponentPopupMenu(jPopupMenuListJob);
        jTableListJob.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jTableListJob);

        jButtonAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/plus.png"))); // NOI18N
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelScheduleLayout = new javax.swing.GroupLayout(jPanelSchedule);
        jPanelSchedule.setLayout(jPanelScheduleLayout);
        jPanelScheduleLayout.setHorizontalGroup(
            jPanelScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelScheduleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelScheduleLayout.createSequentialGroup()
                        .addGroup(jPanelScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButtonAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonAddJob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRemoveJob))
                    .addGroup(jPanelScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jComboBoxFrequency, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSpinnerTimer, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(199, Short.MAX_VALUE))
        );
        jPanelScheduleLayout.setVerticalGroup(
            jPanelScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelScheduleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                    .addGroup(jPanelScheduleLayout.createSequentialGroup()
                        .addGroup(jPanelScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonRemoveJob)
                            .addComponent(jButtonAdd))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinnerTimer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxFrequency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonAddJob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Расписание", new javax.swing.ImageIcon(getClass().getResource("/image/clock.png")), jPanelSchedule); // NOI18N

        jTextFieldBaseSource.setPreferredSize(new java.awt.Dimension(6, 23));

        jButtonCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/cancel.png"))); // NOI18N
        jButtonCancel.setText("Отмена");
        jButtonCancel.setMaximumSize(new java.awt.Dimension(109, 25));
        jButtonCancel.setMinimumSize(new java.awt.Dimension(109, 25));
        jButtonCancel.setPreferredSize(new java.awt.Dimension(109, 25));
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jButtonApply.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/accept.png"))); // NOI18N
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

        jTextFieldPlatformSource.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextFieldPlatformSource.setPreferredSize(new java.awt.Dimension(6, 23));

        jLabelBaseSource.setText("Путь до базы");

        jLabelPlatformSource.setText("Путь до папки 1С");

        jButtonSelPlatformSource.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/folder-horizontal-open.png"))); // NOI18N
        jButtonSelPlatformSource.setPreferredSize(new java.awt.Dimension(51, 30));
        jButtonSelPlatformSource.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelPlatformSourceActionPerformed(evt);
            }
        });

        jButtonSelBaseSource.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/folder-horizontal-open.png"))); // NOI18N
        jButtonSelBaseSource.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelBaseSourceActionPerformed(evt);
            }
        });

        jLabelInfileOnServer.setText("Файл загрузки на сервере");

        jLabelInfileOnLocalhost.setText("Файл загрузки локально");

        jLabelFTPPass.setText("FTP-пароль");

        jLabelOutfileOnLocalhost.setText("Файл выгрузки локально");

        jLabelOutfileOnServer.setText("Файл выгрузки на сервере");

        jCheckBoxMinimiz.setText("Запускать минимизированной");
        jCheckBoxMinimiz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMinimizActionPerformed(evt);
            }
        });

        jCheckBoxExpert.setText("Эксперт-режим");
        jCheckBoxExpert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxExpertActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabelSyncBaseSource.setText("Папка локально");

        jLabelSyncFTPPass.setText("FTP-пароль");

        jTextFieldSyncLocalhostDestination.setEnabled(false);

        jTextFieldSyncFTPSource.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jLabelSyncFTPUser.setText("FTP-пользователь");

        jLabelSyncFTP.setText("FTP-хост");

        jLabelSyncFTPdir.setText("Папка на сервере");

        jLabel1.setText("Синхронизация");

        jTextFieldSyncFTPHost.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSyncFTP)
                    .addComponent(jLabelSyncFTPdir)
                    .addComponent(jTextFieldSyncFTPHost, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelSyncFTPUser)
                    .addComponent(jTextFieldSyncFTPUser, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelSyncFTPPass)
                    .addComponent(jTextFieldSyncFTPPass, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelSyncBaseSource)
                    .addComponent(jTextFieldSyncLocalhostDestination, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldSyncFTPSource, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelSyncFTP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldSyncFTPHost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelSyncFTPUser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldSyncFTPUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelSyncFTPPass)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldSyncFTPPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelSyncBaseSource)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldSyncLocalhostDestination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelSyncFTPdir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldSyncFTPSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelOptionsLayout = new javax.swing.GroupLayout(jPanelOptions);
        jPanelOptions.setLayout(jPanelOptionsLayout);
        jPanelOptionsLayout.setHorizontalGroup(
            jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addComponent(jButtonApply)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(375, 375, 375))
            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelPlatformSource)
                            .addComponent(jLabelBaseSource)
                            .addComponent(jLabelFTPSource)
                            .addComponent(jLabelFTPUser)
                            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                                .addComponent(jCheckBoxMinimiz, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCheckBoxExpert))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanelOptionsLayout.setVerticalGroup(
            jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBoxMinimiz)
                            .addComponent(jCheckBoxExpert))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonApply)
                            .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Настройки", new javax.swing.ImageIcon(getClass().getResource("/image/property.png")), jPanelOptions); // NOI18N

        jMenuFile.setText("Файл");

        jMenuFileExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        jMenuFileExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/door_in.png"))); // NOI18N
        jMenuFileExit.setText("Выход");
        jMenuFileExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuFileExitActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuFileExit);

        jMenuBar1.add(jMenuFile);

        jMenuQuestion.setText("?");

        jMenuQuestionHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/question-frame.png"))); // NOI18N
        jMenuQuestionHelp.setText("Помощь");
        jMenuQuestionHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuQuestionHelpActionPerformed(evt);
            }
        });
        jMenuQuestion.add(jMenuQuestionHelp);

        jMenuQustionAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/information-frame.png"))); // NOI18N
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
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-679)/2, (screenSize.height-503)/2, 679, 503);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuFileExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuFileExitActionPerformed
        saveScheduler();
//        this.setVisible(false);
        System.exit(0);
    }//GEN-LAST:event_jMenuFileExitActionPerformed

    private void jMenuQuestionHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuQuestionHelpActionPerformed
        //здесь показ хелпа в формате txt или chm
        String userDir = System.getProperty("user.dir")
//                + System.getProperty("file.separator")
//                + "Help"
                + System.getProperty("file.separator")
                + "AvtoURIB.html";
        helpFile = new File(userDir);
        try {
            Desktop.getDesktop().open(helpFile);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuQuestionHelpActionPerformed

    private void jMenuQustionAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuQustionAboutActionPerformed
        (new AboutFrame()).setVisible(true);
    }//GEN-LAST:event_jMenuQustionAboutActionPerformed

    @SuppressWarnings("CallToThreadDumpStack")
    private void formWindowIconified(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowIconified
        this.setVisible(false);
        try {
            java.awt.SystemTray.getSystemTray().add(icon);
        } catch (AWTException e1) {
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
            jTextFieldSyncLocalhostDestination.setText(jFileChooserBaseSource.getSelectedFile().getParent());
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
                logger.log(Level.WARNING, "Заполнены не все поля настроек!");
                javax.swing.JOptionPane.showMessageDialog(null, "Заполнены не все поля настроек!", "Ошибка", javax.swing.JOptionPane.WARNING_MESSAGE);
            } else {
                Apply();
            }
	}//GEN-LAST:event_jButtonApplyActionPerformed

	private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
            dispose();
	}//GEN-LAST:event_jButtonCancelActionPerformed

	private void jButtonRunInfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRunInfileActionPerformed
            log = "";
            if (!jCheckBoxExpert.isSelected()) {
                RunIn();
            } else {
                RunInfile();
            }
            jTextAreaSystemLog.append(log);
	}//GEN-LAST:event_jButtonRunInfileActionPerformed

	private void jButtonRunDownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRunDownloadActionPerformed
            log = "";
            if (!jCheckBoxExpert.isSelected()) {
                RunOut();
            } else {
                RunDownload();
            }
            jTextAreaSystemLog.append(log);
	}//GEN-LAST:event_jButtonRunDownloadActionPerformed

	private void jButtonRunUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRunUploadActionPerformed
            log = "";
            RunUpload();
            jTextAreaSystemLog.append(log);
	}//GEN-LAST:event_jButtonRunUploadActionPerformed

	private void jButtonRunOutfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRunOutfileActionPerformed
            log = "";
            RunOutfile();
            jTextAreaSystemLog.append(log);
	}//GEN-LAST:event_jButtonRunOutfileActionPerformed

	private void jButtonAddJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddJobActionPerformed
            schedulerURBD _sched = new schedulerURBD(((Date) jSpinnerTimer.getValue()), jComboBoxFrequency.getSelectedIndex());
            if (_sched.createSCHED(this, GlobalJobClass.class)) {
                if (_sched.start()) {
                    ((DefaultTableModel) jTableListJob.getModel()).setRowCount(jTableListJob.getRowCount() + 1);
                    jTableListJob.setValueAt(_sched, jTableListJob.getRowCount() - 1, 0);
                    jTableListJob.setValueAt(jComboBoxFrequency.getSelectedItem(), jTableListJob.getRowCount() - 1, 1);
                    saveScheduler();
                }
            }
            else{
                javax.swing.JOptionPane.showMessageDialog(null, "Проверте параметры создаваемого задания!!!", "!!! В Н И М А Н И Е !!!", javax.swing.JOptionPane.WARNING_MESSAGE);
            }
            _sched = null;
	}//GEN-LAST:event_jButtonAddJobActionPerformed

	private void jButtonRemoveJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveJobActionPerformed
            RemoveJob();
	}//GEN-LAST:event_jButtonRemoveJobActionPerformed

	private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
            loadScheduler(evt);
	}//GEN-LAST:event_formWindowOpened

	private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
            formWindowIconified(evt);
	}//GEN-LAST:event_formWindowClosing

	private void jTextFieldFTPSourceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldFTPSourceKeyReleased
            jTextFieldSyncFTPHost.setText(jTextFieldFTPSource.getText());
	}//GEN-LAST:event_jTextFieldFTPSourceKeyReleased

	private void jButtonRunAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRunAllActionPerformed
            RunAll();
	}//GEN-LAST:event_jButtonRunAllActionPerformed

	private void jCheckBoxMinimizActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMinimizActionPerformed
            saveScheduler();
	}//GEN-LAST:event_jCheckBoxMinimizActionPerformed

	private void jCheckBoxExpertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxExpertActionPerformed
            CheckBoxExpertRun();
            saveScheduler();
	}//GEN-LAST:event_jCheckBoxExpertActionPerformed

	private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
//            schedulerURBD _sched = new schedulerURBD(new Date(((Date) jSpinnerTimer.getValue()).getTime()+600000), jComboBoxFrequency.getSelectedIndex());
            schedulerURBD _sched = new schedulerURBD(new Date(System.currentTimeMillis()), 1);
            if (_sched.createSCHED(this,GlobalJobClass.class,false)) {
                tableJobEnable(false);
                (new SchedulerFrame(_sched, this)).setVisible(true);
            }
	}//GEN-LAST:event_jButtonAddActionPerformed

        public schedulerURBD getSchedFromTable(int row){
            return (schedulerURBD)jTableListJob.getValueAt(row, 0);
        }
        
	private void jMenuItemTextAreaSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemTextAreaSaveActionPerformed
	}//GEN-LAST:event_jMenuItemTextAreaSaveActionPerformed

	private void jPopupMenuListJobPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jPopupMenuListJobPopupMenuWillBecomeVisible
            if (jTableListJob.getSelectedRow() != -1) {
                jMenuItemChange.setEnabled(true);
                jMenuItemDelete.setEnabled(true);
            } else {
                jMenuItemChange.setEnabled(false);
                jMenuItemDelete.setEnabled(false);
            }
	}//GEN-LAST:event_jPopupMenuListJobPopupMenuWillBecomeVisible

	private void jMenuItemChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemChangeActionPerformed
            int _selectRow = jTableListJob.getSelectedRow();
            if (_selectRow != -1) {
                (new SchedulerFrame(getSchedFromTable(_selectRow), this)).setVisible(true);
            }
	}//GEN-LAST:event_jMenuItemChangeActionPerformed

    private void jMenuItemDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemDeleteActionPerformed
        RemoveJob();
    }//GEN-LAST:event_jMenuItemDeleteActionPerformed

    private void jButtonRunSyncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRunSyncActionPerformed
        key = 6;
        Thread thr = (new Thread(new RunExchangeInThread(6)));
        thr.start();
        (new Thread(new DrawProgressBar(thr))).start();
    }//GEN-LAST:event_jButtonRunSyncActionPerformed

    public void CheckBoxExpertRun() {
        if (!jCheckBoxExpert.isSelected()) {
            jButtonRunInfile.setText("Загрузка");
            jButtonRunDownload.setText("Выгрузка");
            jButtonRunDownload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/upload.png")));
            jButtonRunOutfile.setVisible(false);
            jButtonRunOutfile.setEnabled(false);
            jButtonRunUpload.setVisible(false);
            jButtonRunUpload.setEnabled(false);
        } else {
            jButtonRunInfile.setText("Принять с сервера");
            jButtonRunDownload.setText("Загрузить в базу");
            jButtonRunDownload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/database-import.png")));
            jButtonRunOutfile.setVisible(true);
            jButtonRunOutfile.setEnabled(true);
            jButtonRunUpload.setVisible(true);
            jButtonRunUpload.setEnabled(true);
        }
    }

    public void RunAll() {
        key = 0;
        Thread thr = (new Thread(new RunExchangeInThread(0)));
        thr.start();
        (new Thread(new DrawProgressBar(thr))).start();
    }

    public void RunInfile() {
        Thread thr = (new Thread(new RunExchangeInThread(1)));
        thr.start();
        (new Thread(new DrawProgressBar(thr))).start();
    }

    public void RunDownload() {
        key = 1;
        Thread thr = (new Thread(new RunExchangeInThread(2)));
        thr.start();
        (new Thread(new DrawProgressBar(thr))).start();
    }

    public void RunUpload() {
        key = 2;
        Thread thr = (new Thread(new RunExchangeInThread(2)));
        thr.start();
        (new Thread(new DrawProgressBar(thr))).start();
    }

    public void RunOutfile() {
        Thread thr = (new Thread(new RunExchangeInThread(3)));
        thr.start();
        (new Thread(new DrawProgressBar(thr))).start();
    }

    public void RunIn() {
        key = 1;
        Thread thr = (new Thread(new RunExchangeInThread(4)));
        thr.start();
        (new Thread(new DrawProgressBar(thr))).start();
    }

    public void RunOut() {
        key = 2;
        Thread thr = (new Thread(new RunExchangeInThread(5)));
        thr.start();
        (new Thread(new DrawProgressBar(thr))).start();
    }

    private void Apply() {
        //выгружаем в файл настроек и записываем его
        jTextAreaSystemLog.append("\n" + getDateAndTime() + " Записываю настройки в файл...");
        logger.log(Level.INFO, "{0} Записываю настройки в файл...", getDateAndTime());
        saveOptions();
    }

    public void GetFileFromFTP() {
        byte err;
        exchange = new FTP(TmpOptions.getFTPHost(),
                TmpOptions.getFTPServerLogin(),
                TmpOptions.getFTPServerPass());

        log = "--------------------------------------------------\n" + getDateAndTime() + " Подключение к фтп-серверу...\n";
        jTextAreaSystemLog.append("\n--------------------------------------------------\n" + getDateAndTime() + " Подключение к фтп-серверу...");
        _level = Level.INFO;
        this.getRootPane().updateUI();
        if ((err = exchange.connectToFTP()) == consterr.NOT_ERR) {
            jTextAreaSystemLog.append("\n" + getDateAndTime() + " Подключение прошло успешно...\n" + getDateAndTime() + " Загружаю файл...");
            log += getDateAndTime() + " Подключение прошло успешно...\n" + getDateAndTime() + " Загружаю файл...\n";
            if ((err = exchange.getFileFromFTP(TmpOptions.getCPFileFromFTP(), new File(TmpOptions.getCPFileToLocalhost()))) == consterr.NOT_ERR) {
                jTextAreaSystemLog.append("\n" + getDateAndTime() + " Получение файла прошло успешно..." + "\n--------------------------------------------------");
                log += getDateAndTime() + " Получение файла прошло успешно...\n";
            } else {
                jTextAreaSystemLog.append("\n" + getDateAndTime() + " " + consterr.PrintErr(err) + "\n--------------------------------------------------");
                _level = Level.SEVERE;
                log += getDateAndTime() + " " + consterr.PrintErr(err) + "\n--------------------------------------------------\n";
            }
        } else {
            jTextAreaSystemLog.append("\n" + getDateAndTime() + " " + consterr.PrintErr(err));
            jTextAreaSystemLog.append("\n--------------------------------------------------");
            _level = Level.SEVERE;
            log += getDateAndTime() + " " + consterr.PrintErr(err) + "\n--------------------------------------------------\n";
        }
    }

    public void PutFileToFTP() {
        byte err;
        exchange = new FTP(TmpOptions.getFTPHost(),
                TmpOptions.getFTPServerLogin(),
                TmpOptions.getFTPServerPass());

        jTextAreaSystemLog.append("\n--------------------------------------------------");
        jTextAreaSystemLog.append("\n" + getDateAndTime() + " Подключение к фтп-серверу...");
        _level = Level.INFO;
        log += "--------------------------------------------------\n" + getDateAndTime() + " Подключение к фтп-серверу...\n";
        this.getRootPane().updateUI();
        if ((err = exchange.connectToFTP()) == consterr.NOT_ERR) {
            jTextAreaSystemLog.append("\n" + getDateAndTime() + " Подключение прошло успешно...");
            jTextAreaSystemLog.append("\n" + getDateAndTime() + " Отправляю файл...");
            log += getDateAndTime() + " Подключение прошло успешно...\n" + getDateAndTime() + " Отправляю файл...\n";

            if ((err = exchange.sendFileToFTP(TmpOptions.getPCFileFromFTP(), new File(TmpOptions.getPCFileToLocalhost()))) == consterr.NOT_ERR) {
                jTextAreaSystemLog.append("\n" + getDateAndTime() + " Файл успешно отправлен...");
                jTextAreaSystemLog.append("\n--------------------------------------------------");
                log += getDateAndTime() + " Файл успешно отправлен...\n--------------------------------------------------\n";
            } else {
                jTextAreaSystemLog.append("\n" + getDateAndTime() + " " + consterr.PrintErr(err));
                jTextAreaSystemLog.append("\n--------------------------------------------------");
                log += getDateAndTime() + " " + consterr.PrintErr(err) + "\n--------------------------------------------------\n";
                _level = Level.SEVERE;
            }
        } else {
            jTextAreaSystemLog.append("\n" + getDateAndTime() + " " + consterr.PrintErr(err));
            jTextAreaSystemLog.append("\n--------------------------------------------------");
            log += getDateAndTime() + " " + consterr.PrintErr(err) + "\n--------------------------------------------------\n";
            _level = Level.SEVERE;
        }
    }

    public void RunWith1S() {
        int err;
        run1s = new run_1s(TmpOptions.getPathTo1S(),
                TmpOptions.getPathToBase(),
                TmpOptions.getBaseUser(),
                new String(TmpOptions.getBasePass()));
        List<String> tmplst;
        err = run1s.create_prm(key);
        if (err == consterr.NOT_ERR) {
            jTextAreaSystemLog.append("\n--------------------------------------------------");
            jTextAreaSystemLog.append("\n" + getDateAndTime() + " Запускаем 1С");
            _level = Level.INFO;
            log += "--------------------------------------------------\n" + getDateAndTime() + " Запускаем 1С\n";
            err = run1s.start();
            if (err == consterr.NOT_ERR) {
                tmplst = run1s.parsing_log();
                if (tmplst != null) {
                    for (int i = 0; i < tmplst.size(); i++) {
                        jTextAreaSystemLog.append("\n" + getDateAndTime() + " " + tmplst.get(i));
                        log += getDateAndTime() + " " + tmplst.get(i) + "\n";
                    }
                } else {
                    jTextAreaSystemLog.append("\n" + getDateAndTime() + " " + consterr.PrintErr((byte) err));
                    log += getDateAndTime() + " " + consterr.PrintErr((byte) err) + "\n--------------------------------------------------\n";
                    _level = Level.SEVERE;
                }
            } else {
                jTextAreaSystemLog.append("\n" + getDateAndTime() + " " + consterr.PrintErr((byte) err));
                log += getDateAndTime() + " " + consterr.PrintErr((byte) err) + "\n--------------------------------------------------\n";
                _level = Level.SEVERE;
            }
        } else {
            jTextAreaSystemLog.append("\n" + getDateAndTime() + " " + consterr.PrintErr((byte) err));
            log += getDateAndTime() + " " + consterr.PrintErr((byte) err) + "\n--------------------------------------------------\n";
            _level = Level.SEVERE;
        }
    }

    public void RemoveJob() {
        int _selectRow = jTableListJob.getSelectedRow();
        if (_selectRow != -1) {
            schedulerURBD schedFromTable = getSchedFromTable(_selectRow);
            if (schedFromTable.stop()) {
                ((DefaultTableModel) jTableListJob.getModel()).removeRow(_selectRow);
                jTextAreaSystemLog.append("\n" + getDateAndTime() + " удалено задание " + schedFromTable.job.getFullName());
                logger.log(Level.INFO, "{0} удалено задание {1}", new Object[]{getDateAndTime(), schedFromTable.job.getFullName()});
                saveScheduler();
            } else {
                jTextAreaSystemLog.append("\n" + getDateAndTime() + " Не могу удалить задание " + schedFromTable.job.getFullName());
                logger.log(Level.WARNING, "{0} Не могу удалить задание {1}", new Object[]{getDateAndTime(), schedFromTable.job.getFullName()});
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Выберите задание для удаления!!!", "!!! В Н И М А Н И Е !!!", javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }

    public void RunSync() {
//        if( (new Date()).getTime() > new Date("Nov 01 2012").getTime()){
//            jTextAreaSystemLog.append("\n--------------------------------------------------\n" + getDateAndTime() + " Срок оплаты прошел(((");
//            return;
//        }
        byte err;
        FTPFile fl[], fl2[];
        syncFTP = new FTP(TmpOptions.getSyncFTPHost(),
                TmpOptions.getSyncFTPHostUser(),
                TmpOptions.getSyncFTPHostPass());
        log = "--------------------------------------------------\n" + getDateAndTime() + " Подключение к фтп-серверу для синхронизации...\n";
        jTextAreaSystemLog.append("\n--------------------------------------------------\n" + getDateAndTime() + " Подключение к фтп-серверу...");
        _level = Level.INFO;
        String pathToSave = TmpOptions.getPathToBase()+"\\temp\\";
        try{
            if ((err = syncFTP.connectToFTP()) == consterr.NOT_ERR) {
                syncFTP.setPMFT();
                jTextAreaSystemLog.append("\n" + getDateAndTime() + " Подключение прошло успешно...\n" + getDateAndTime() + " Загружаю список файлов...");
                ArrayList<String> a = syncFTP.listDir(TmpOptions.getSyncFTPHostDir(),true);
                fl = syncFTP.getDirList(TmpOptions.getSyncFTPHostDir());
                for(int i=0;i < a.size(); i++){
                    fl2 = syncFTP.getFileList("/"+a.get(i));
                    for(int j=0;j < fl2.length; j++){
                        if(fl2[j].isDirectory()) continue;
                        try{
                            if ( consterr.NOT_ERR == syncFTP.getFileFromFTP2("/"+a.get(i) + "/" + syncFTP.fileNameConvert(fl2[j].getName(),"CP1251","ISO-8859-1"),
                                    new File(pathToSave+a.get(i).split(TmpOptions.getSyncFTPHostDir()+"/")[1].replace('/', '\\')+"\\"+ fl2[j].getName()),
                                    fl2[j].getTimestamp().getTimeInMillis())){
                                jTextAreaSystemLog.append("\n" + getDateAndTime() + " Файл "+pathToSave+a.get(i).split(TmpOptions.getSyncFTPHostDir()+"/")[1].replace('/', '\\')+"\\"+ fl2[j].getName()+" загружен.");
                            }
                            else throw new Exception();
                        }
                        catch(Exception e){
                            jTextAreaSystemLog.append("\n" + getDateAndTime() + " Файл "+TmpOptions.getPathToBase()+"\\"+a.get(i).split(TmpOptions.getSyncFTPHostDir()+"/")[1].replace('/', '\\')+"\\"+ fl2[j].getName()+" не загружен!");
                        }
                    }
                }
            }
        }
        catch(Exception e){
            jTextAreaSystemLog.append("\n" + getDateAndTime() + " Все накрылось медным тазом!!!");
        }
        if(syncFTP.logoff()){
            try{
                File afile =new File(pathToSave);
                jTextAreaSystemLog.append("\n\n" + getDateAndTime() + " Копирую загруженные файлы.");
                copyFolder(afile,new File(TmpOptions.getPathToBase()));
            }
            catch(Exception e){
                jTextAreaSystemLog.append("\n" + getDateAndTime() + " Тут нужно было чета сделать, но мне так было лень...");
            }
           
            log = "\n"+getDateAndTime() + " Синхронизация завершена.\n";
            jTextAreaSystemLog.append("\n"+ getDateAndTime() + " Синхронизация завершена.\n");    
//            
//            try{
//             
//                SimpleEmail email = new SimpleEmail();
//                email.setHostName("smtp.gmail.com");
//                email.setSmtpPort(465);
//                email.setSSL(true);
//                email.setAuthenticator(new DefaultAuthenticator("sudstrike@gmail.com", "pdtplfyenjtgexteibt"));
//                email.setTLS(true);
//                email.setFrom("sudstrike@gmail.com");
//                email.setSubject("TestMail");
//                email.setMsg("This is a test mail ... :-)");
//                email.addTo("sudstrike@mail.ru");
//                email.send();
// 
//            
//                System.out.println(")))");
//            }
//            catch(Exception e){
//                System.out.println("((((");
//            }
        }
    }

    public void copyFolder(File src, File dest){
 
    	if(src.isDirectory()){
 
    		//if directory not exists, create it
    		if(!dest.exists()){
    		   dest.mkdir();
    		}
 
    		//list all the directory contents
    		String files[] = src.list();
 
    		for (String file : files) {
    		   //construct the src and dest file structure
    		   File srcFile = new File(src, file);
    		   File destFile = new File(dest, file);
    		   //recursive copy
    		   copyFolder(srcFile,destFile);
    		}
 
    	}else{
    		//if file, then copy it
    		//Use bytes stream to support all file types
                if(src.length() > 0){
                    try{
                        InputStream in = new FileInputStream(src);
                        OutputStream out = new FileOutputStream(dest); 

                        byte[] buffer = new byte[1024];

                        int length;
                        //copy the file content in bytes 
                        while ((length = in.read(buffer)) > 0){
                           out.write(buffer, 0, length);
                        }

                        in.close();
                        out.close();
                        jTextAreaSystemLog.append("\n" + getDateAndTime() + " Файл "+dest+" обновлен.");
                    }
                    catch(Exception e){
                        jTextAreaSystemLog.append("\n" + getDateAndTime() + " Файл "+dest+" не обновлен.");
                    }

                }
                else{
                    jTextAreaSystemLog.append("\n" + getDateAndTime() + " Файл "+dest+" был получен с нулевой длиной. Файл не обновлен.");
                }
    	}
    }
    
    public void RunJob() {
        RunAll();
        RunSync();        
    }

    public void setSchedul(schedulerURBD _tmp,boolean exchange, boolean sync) {
        if (_tmp != null) {
            if (_tmp.reInitSCHED(this, _tmp.getJobClass(),exchange,sync)) {
                if (_tmp.start()) {
                    int _selectedRow =  jTableListJob.getSelectedRow();
                    if(_selectedRow == -1 ){
                        ((DefaultTableModel) jTableListJob.getModel()).setRowCount(jTableListJob.getRowCount()+1);
                        _selectedRow = jTableListJob.getRowCount();
                        jTableListJob.setValueAt(_tmp,_selectedRow-1, 0);
                        jTableListJob.setValueAt(jComboBoxFrequency.getItemAt(_tmp.getFrequency()),_selectedRow-1, 1);
                    }
                    else
                        jTableListJob.setValueAt(jComboBoxFrequency.getItemAt(_tmp.getFrequency()),_selectedRow, 1);
                    saveScheduler();
                }
            }
            _tmp = null;
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonAddJob;
    private javax.swing.JButton jButtonApply;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonRemoveJob;
    public javax.swing.JButton jButtonRunAll;
    private javax.swing.JButton jButtonRunDownload;
    private javax.swing.JButton jButtonRunInfile;
    private javax.swing.JButton jButtonRunOutfile;
    private javax.swing.JButton jButtonRunSync;
    private javax.swing.JButton jButtonRunUpload;
    private javax.swing.JButton jButtonSelBaseSource;
    private javax.swing.JButton jButtonSelPlatformSource;
    private javax.swing.JCheckBox jCheckBoxExpert;
    private javax.swing.JCheckBox jCheckBoxMinimiz;
    private javax.swing.JComboBox jComboBoxFrequency;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JMenuItem jMenuItemChange;
    private javax.swing.JMenuItem jMenuItemDelete;
    private javax.swing.JMenuItem jMenuItemTextAreaClear;
    private javax.swing.JMenuItem jMenuItemTextAreaSave;
    private javax.swing.JMenu jMenuQuestion;
    private javax.swing.JMenuItem jMenuQuestionHelp;
    private javax.swing.JMenuItem jMenuQustionAbout;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelOptions;
    private javax.swing.JPanel jPanelSchedule;
    private javax.swing.JPopupMenu jPopupMenuListJob;
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
    private javax.swing.JTextField jTextFieldSyncFTPHost;
    private javax.swing.JPasswordField jTextFieldSyncFTPPass;
    private javax.swing.JTextField jTextFieldSyncFTPSource;
    private javax.swing.JTextField jTextFieldSyncFTPUser;
    private javax.swing.JTextField jTextFieldSyncLocalhostDestination;
    // End of variables declaration//GEN-END:variables

    private void checkingOptionFile() {
        //очищаем информационные поля
        jTextAreaSystemLog.setText("");

        //получаем папку пользователя
        String userDir = System.getProperty("user.dir");

        //проверяем, есть ли папка AvtoURIB
        userDir = userDir + System.getProperty("file.separator") + "AvtoURIB";
        File dir;
        dir = new File(userDir);
        if (!dir.exists()) {
            jTextAreaSystemLog.append(getDateAndTime() + " Каталог настроек не найден и будет создан...");
            logger.log(Level.WARNING, "{0} Каталог настроек не найден и будет создан...", getDateAndTime());
            dir.mkdirs();
            jButtonRunAll.setEnabled(false);
            jButtonRunDownload.setEnabled(false);
            jButtonRunInfile.setEnabled(false);
            jButtonRunOutfile.setEnabled(false);
            jButtonRunUpload.setEnabled(false);
        } else {
            jTextAreaSystemLog.append(getDateAndTime() + " Каталог настроек найден...");
            logger.log(Level.INFO, "{0} Каталог настроек найден...", getDateAndTime());
        }

        //проверяем, есть ли файл настроек
        userDir = userDir + System.getProperty("file.separator") + "AvtoURIB.ini";
        optionFile = new File(userDir);
        //если нет файла, то создаем
        if (!optionFile.exists()) {
            try {
                jTextAreaSystemLog.append("\n" + getDateAndTime() + " Файл настроек не найден и будет создан...");
                logger.log(Level.WARNING, "{0} Файл настроек не найден и будет создан...", getDateAndTime());
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
            logger.log(Level.INFO, "{0} Файл настроек найден...", getDateAndTime());
            loadOptions();
            jButtonRunAll.setEnabled(true);
            jButtonRunInfile.setEnabled(true);
            jButtonRunUpload.setEnabled(true);
        }
    }

    private void loadOptions() {
        java.util.Properties properties = new java.util.Properties();
        try {
            
            InputStream is = new FileInputStream(optionFile);
            properties.load(is);
            is.close();
            is = null;
            TmpOptions.setPathTo1S(properties.getProperty("PlatformSource"));
            jTextFieldPlatformSource.setText(TmpOptions.getPathTo1S());

            TmpOptions.setPathToBase( properties.getProperty("BaseSource"));
            jTextFieldBaseSource.setText(TmpOptions.getPathToBase());
            jTextFieldSyncLocalhostDestination.setText(TmpOptions.getPathToBase());

            TmpOptions.setBaseUser(properties.getProperty("BaseUser"));
            jTextFieldBaseUser.setText(TmpOptions.getBaseUser());
            
            String s = new String(biz.source_code.base64Coder.Base64Coder.decode(properties.getProperty("BasePass")));
            TmpOptions.setBasePass( properties.getProperty("BasePass") == null ?
                                    "".toCharArray() :
                                    s.toCharArray());
            jTextFieldBasePass.setText(s);
            
            TmpOptions.setFTPHost(properties.getProperty("FTPSource"));
            jTextFieldFTPSource.setText(TmpOptions.getFTPHost());
            
            TmpOptions.setFTPServerLogin(properties.getProperty("FTPUser"));
            jTextFieldFTPUser.setText(TmpOptions.getFTPServerLogin());
            
            s = new String(biz.source_code.base64Coder.Base64Coder.decode(properties.getProperty("FTPPass")));
            TmpOptions.setFTPServerPass(properties.getProperty("FTPPass") == null ?
                                        "".toCharArray() : 
                                       s.toCharArray());
            jTextFieldFTPPass.setText(new String(TmpOptions.getFTPServerPass()));
            
            TmpOptions.setCPFileFromFTP(properties.getProperty("InfileOnServer"));
            jTextFieldInfileOnServer.setText(TmpOptions.getCPFileFromFTP());
                        
            TmpOptions.setCPFileToLocalhost(properties.getProperty("InfileOnLocalhost"));
            jTextFieldInfileOnLocalhost.setText(TmpOptions.getCPFileToLocalhost());

            TmpOptions.setPCFileToLocalhost(properties.getProperty("OutfileOnLocalhost"));
            jTextFieldOutfileOnLocalhost.setText(TmpOptions.getPCFileToLocalhost());

            TmpOptions.setPCFileFromFTP(properties.getProperty("OutfileOnServer"));
            jTextFieldOutfileOnServer.setText(TmpOptions.getPCFileFromFTP());
            
            TmpOptions.setSyncFTPHostDir(properties.getProperty("SyncFTPSource"));
            jTextFieldSyncFTPSource.setText(TmpOptions.getSyncFTPHostDir());
            
            TmpOptions.setSyncFTPHostUser(properties.getProperty("SyncFTPUser"));
            jTextFieldSyncFTPUser.setText(TmpOptions.getSyncFTPHostUser());
            
            s = new String(biz.source_code.base64Coder.Base64Coder.decode(properties.getProperty("SyncFTPPass")));
            TmpOptions.setSyncFTPHostPass(properties.getProperty("SyncFTPPass") == null ?
                                            "".toCharArray() : 
                                            s.toCharArray());
            jTextFieldSyncFTPPass.setText(new String(TmpOptions.getSyncFTPHostPass()));
            
            TmpOptions.setSyncFTPHost(properties.getProperty("SyncFTPHost"));
            jTextFieldSyncFTPHost.setText(TmpOptions.getSyncFTPHost());
            
            jTextAreaSystemLog.append("\n" + getDateAndTime() + " Файл настроек прочитан...");
            logger.log(Level.INFO, "{0} Файл настроек прочитан...", getDateAndTime());
        } catch (IOException e) {
        }
    }

    private void saveOptions() {
        //записываем настройки в файл
        java.util.Properties properties = new java.util.Properties();
        try {
            properties.setProperty("PlatformSource", jTextFieldPlatformSource.getText());
            properties.setProperty("BaseSource", jTextFieldBaseSource.getText());
            jTextFieldSyncLocalhostDestination.setText(jTextFieldBaseSource.getText());
            properties.setProperty("BaseUser", jTextFieldBaseUser.getText());
            
            String s = biz.source_code.base64Coder.Base64Coder.encodeLines(new String(jTextFieldBasePass.getPassword()).getBytes());
            properties.setProperty("BasePass", s.replace("\n", "").replace("\r", ""));
            
            properties.setProperty("FTPSource", jTextFieldFTPSource.getText());
            properties.setProperty("FTPUser", jTextFieldFTPUser.getText());
            
            s = biz.source_code.base64Coder.Base64Coder.encodeLines(new String(jTextFieldFTPPass.getPassword()).getBytes());
            properties.setProperty("FTPPass", s.replace("\n", "").replace("\r", ""));
            
            properties.setProperty("InfileOnServer", jTextFieldInfileOnServer.getText());
            properties.setProperty("InfileOnLocalhost", jTextFieldInfileOnLocalhost.getText());
            properties.setProperty("OutfileOnLocalhost", jTextFieldOutfileOnLocalhost.getText());
            properties.setProperty("OutfileOnServer", jTextFieldOutfileOnServer.getText());
            
            properties.setProperty("SyncFTPSource", jTextFieldSyncFTPSource.getText());
            properties.setProperty("SyncFTPUser", jTextFieldSyncFTPUser.getText());
            
            s = biz.source_code.base64Coder.Base64Coder.encodeLines(new String(jTextFieldSyncFTPPass.getPassword()).getBytes());
            properties.setProperty("SyncFTPPass", s.replace("\n", "").replace("\r", ""));
            properties.setProperty("SyncFTPHost", jTextFieldSyncFTPHost.getText());
            OutputStream os = new FileOutputStream(optionFile);
            properties.store(os, "");
            os.close();
            
            TmpOptions.setPathTo1S(jTextFieldPlatformSource.getText());
            TmpOptions.setPathToBase(jTextFieldBaseSource.getText());
            TmpOptions.setBaseUser(jTextFieldBaseUser.getText());
            TmpOptions.setBasePass(jTextFieldBasePass.getPassword());
            TmpOptions.setFTPHost(jTextFieldFTPSource.getText());
            TmpOptions.setFTPServerLogin(jTextFieldFTPUser.getText());
            TmpOptions.setFTPServerPass(jTextFieldFTPPass.getPassword());
            TmpOptions.setCPFileFromFTP(jTextFieldInfileOnServer.getText());
            TmpOptions.setCPFileToLocalhost(jTextFieldInfileOnLocalhost.getText());
            TmpOptions.setPCFileToLocalhost(jTextFieldOutfileOnLocalhost.getText());
            TmpOptions.setPCFileFromFTP(jTextFieldOutfileOnServer.getText());
            TmpOptions.setSyncFTPHostDir(jTextFieldSyncFTPSource.getText());
            TmpOptions.setSyncFTPHostUser(jTextFieldSyncFTPUser.getText());
            TmpOptions.setSyncFTPHostPass(jTextFieldSyncFTPPass.getPassword());
            TmpOptions.setSyncFTPHost(jTextFieldSyncFTPHost.getText());

            jButtonRunAll.setEnabled(true);
            jButtonRunInfile.setEnabled(true);
            jButtonRunUpload.setEnabled(true);

        } catch (IOException e) {
        }
        jTextAreaSystemLog.append("\n" + getDateAndTime() + " Настройки записаны...");
        logger.log(Level.INFO, "{0} Настройки записаны...", getDateAndTime());
    }

    private void saveScheduler() {
        java.util.Properties properties = new java.util.Properties();
        String _str = System.getProperty("user.dir")
                + System.getProperty("file.separator")
                + "AvtoURIB"
                + System.getProperty("file.separator")
                + "sched.conf";
        File schedBin = new File(_str);

        try {
            if (!schedBin.getParentFile().exists()) {
                if (schedBin.getParentFile().mkdirs()) {
                    if (!schedBin.createNewFile()) {
                        return;
                    }
                }
            } else if (!schedBin.exists()) {
                if (!schedBin.createNewFile()) {
                    _log.error("Ошибка при сохранение расписания!!!");
                    return;
                }
            } else {
                if (schedBin.delete()) {
                    if (!schedBin.createNewFile()) {
                        _log.error("Ошибка при сохранение файла расписания!!!");
                        return;
                    }
                }
            }
        } catch (IOException err) {
            _log.error("Ошибка при сохранение расписания!!!");
            return;
        }
        try {
            properties.setProperty("countjobs", Integer.toString(jTableListJob.getRowCount()));
            if (jCheckBoxMinimiz.isSelected()) {
                properties.setProperty("mini", "1");
            } else {
                properties.setProperty("mini", "0");
            }
            if (jCheckBoxExpert.isSelected()) {
                properties.setProperty("expert", "1");
            } else {
                properties.setProperty("expert", "0");
            }

            for (int ind = 0; ind <jTableListJob.getRowCount(); ind++) {
                properties.setProperty("jobName" + ind, getSchedFromTable(ind).jobName);
                properties.setProperty("triggerName" + ind, getSchedFromTable(ind).triggerName);
                properties.setProperty("time" + ind, getSchedFromTable(ind).time);
                properties.setProperty("frequency" + ind, Integer.toString(getSchedFromTable(ind).Frequency));
                properties.setProperty("date" + ind, Long.toString(getSchedFromTable(ind).jobDate.getTime()));
                properties.setProperty("className" + ind, getSchedFromTable(ind).getJobClass().getName());
                properties.setProperty("jobExchange" + ind, getSchedFromTable(ind).job.getJobDataMap().get("exchange").toString());
                properties.setProperty("jobSync" + ind, getSchedFromTable(ind).job.getJobDataMap().get("sync").toString());
            }
            OutputStream os = new FileOutputStream(schedBin);
            properties.store(os, "");
            os.close();
        } catch (FileNotFoundException err) {
            _log.error("Не найден файл для сохранения расписания");
        } catch (IOException err) {
            _log.error("Ошибка при сохранении расписания!!!");
        }
    }

    private void loadScheduler(java.awt.event.WindowEvent evt) {
        java.util.Properties properties = new java.util.Properties();
        String _str = System.getProperty("user.dir")
                + System.getProperty("file.separator")
                + "AvtoURIB"
                + System.getProperty("file.separator")
                + "sched.conf";
        File schedBin = new File(_str);
        if (schedBin.exists()) {
            try {
                InputStream is = new FileInputStream(schedBin);
                properties.load(is);
                is.close();
                int countjobs = Integer.parseInt(properties.getProperty("countjobs"));
                try {
                    if ((properties.getProperty("mini") != null) & (properties.getProperty("mini").equals("1"))) {
                        jCheckBoxMinimiz.setSelected(true);
                        formWindowIconified(evt);
                    } else {
                        jCheckBoxMinimiz.setSelected(false);
                    }
                    if (properties.getProperty("expert") != null) {
                        if (properties.getProperty("expert").equals("0")) {
                            jCheckBoxExpert.setSelected(false);
                            CheckBoxExpertRun();
                        }
                        if (properties.getProperty("expert").equals("1")) {
                            jCheckBoxExpert.setSelected(true);
                            CheckBoxExpertRun();
                        }
                    }
                } catch (NullPointerException err) {
                }
                for (int ind = 0; ind < countjobs; ind++) {
                    if (!((Long.parseLong(properties.getProperty("date" + ind)) < System.currentTimeMillis())
                            && (Integer.parseInt(properties.getProperty("frequency" + ind)) == 0))) {
                        schedulerURBD _sched = new schedulerURBD();
                        _sched.setFrequency(Integer.parseInt(properties.getProperty("frequency" + ind)));
                        _sched.setJobDate(properties.getProperty("date" + ind));
                        _sched.setJobName(properties.getProperty("jobName" + ind));
                        _sched.setTriggerName(properties.getProperty("triggerName" + ind));
                        _sched.setTime(properties.getProperty("time" + ind));
                        ((DefaultTableModel) jTableListJob.getModel()).setRowCount(jTableListJob.getRowCount() + 1);
                        jTableListJob.setValueAt(_sched, jTableListJob.getRowCount() - 1, 0);
                        jTableListJob.setValueAt(jComboBoxFrequency.getItemAt(_sched.getFrequency()), jTableListJob.getRowCount() - 1, 1);
                        try {
                            if (_sched.createSCHED(this, Class.forName(properties.getProperty("className" + ind)),properties.getProperty("jobExchange" + ind).equals("true"),properties.getProperty("jobSync" + ind).equals("true"))) {
                                    if (_sched.start()) {
                                    }
                            }
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        _sched = null;
                    }
                }
            } catch (IOException err) {
                _log.error("Ошибка при загрузки расписания!!!");
            }
        }
    }

    public static String ver() {
        java.util.Properties properties = new java.util.Properties();
        String _str = System.getProperty("user.dir")
                + System.getProperty("file.separator")
                + "AvtoURIB"
                + System.getProperty("file.separator")
                + "version.properties";
        File versionBin = new File(_str);
        if (versionBin.exists()) {
            try {
                properties.load(new FileInputStream(versionBin));
                return "Клиент AvtoURIB v."
                        + properties.getProperty("VERSION") + "."
//                        + properties.getProperty("RUN") + "."
                        + properties.getProperty("BUILD");
            } catch (IOException err) {
                return "Клиент AvtoURIB";
            }
        } else {
            return "Клиент AvtoURIB";
        }
    }

    public String getDateAndTime() {
        return (new Date()).toString();
    }

    public void tableJobEnable(boolean enable){
        this.jTableListJob.setEnabled(enable);
    }
}
