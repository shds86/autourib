/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package netlib;

import java.io.Serializable;

/**
 * Класс хранит в себе основные настройки: хост имя фтп-сервера, имя пользователя и пароль к фтп. Путь до 1С: предприятие 7.7
 * пути до файлов на фтп-сервере и на локальном компьютере.
 * @author Dmitrij S. Shakhtanov
 */
public class options implements Serializable
{
    /**
     *
     */
    private String ftpServerHost = null;
    /**
     *
     */
    private String ftpServerLogin = null;
    /**
     *
     */
    private char[] ftpServerPass = null;
    /**
     *
     */
    private String pathTo1S = null;
    /**
     *
     */
    private String pathToBase = null;
    /**
     *
     */
    private String baseLogin = null;
    private char[] basePass = null;
    /**
     *
     */
    private String pathToPRMFile = null;
    /**
     *
     */
    private String cpFileFromFTP = null;
    /**
     *
     */
    private String cpFileToLocalhost = null;
    /**
     *
     */
    private String pcFileFromFTP = null;
    /**
     *
     */
    private String pcFileToLocalhost = null;

    private String ftpSyncHost = null;
    
    private String ftpSyncHostUser = null;
    
    private char[] ftpSyncHostPass = null;

    private String ftpSyncHostDir = null;

    /**
     *
     */
    public options(){};

    /**
     *
     * @return
     */
    public String getFTPHost(){return this.ftpServerHost;}

    /**
     *
     * @return
     */
    public String getFTPServerLogin(){return this.ftpServerLogin;}

    /**
     *
     * @return
     */
    public char[] getFTPServerPass(){return this.ftpServerPass;}

    /**
     *
     * @return
     */
    public String getPathTo1S(){return this.pathTo1S;}

    /**
     *
     * @return
     */
    public String getPathToBase(){return this.pathToBase;}

    /**
     *
     * @return
     */
    public String getBaseUser(){return this.baseLogin;}

    /**
     *
     * @return
     */
    public char[] getBasePass(){return this.basePass;}

    /**
     *
     * @return
     */
    public String getPathToPRMFile(){return this.pathToPRMFile;}

    /**
     *
     * @return
     */
    public String getCPFileFromFTP(){return this.cpFileFromFTP;}

    /**
     *
     * @return
     */
    public String getCPFileToLocalhost(){return this.cpFileToLocalhost;}

    /**
     *
     * @return
     */
    public String getPCFileFromFTP(){return this.pcFileFromFTP;}

    /**
     *
     * @return
     */
    public String getPCFileToLocalhost(){return this.pcFileToLocalhost;}

    /**
     *
     * @param _name
     */
    public void setFTPHost(String _name){this.ftpServerHost = _name;}

    /**
     *
     * @param _login
     */
    public void setFTPServerLogin(String _login){this.ftpServerLogin = _login;}

    /**
     *
     * @param _pass
     */
    public void setFTPServerPass(char[] _pass){this.ftpServerPass = _pass;}

    /**
     *
     * @param _1s
     */
    public void setPathTo1S(String _1s){this.pathTo1S = _1s;}

    /**
     *
     * @param _base
     */
    public void setPathToBase(String _base){this.pathToBase = _base;}

    /**
     *
     * @param _base_login
     */
    public void setBaseUser(String _base_login){this.baseLogin = _base_login;}

    /**
     *
     * @param _base_pass
     */
    public void setBasePass(char[] _base_pass){this.basePass = _base_pass;}

    /**
     *
     * @param _prm
     */
    public void setPathToPRMFile(String _prm){this.pathToPRMFile = _prm;}

    /**
     *
     * @param _cp_file_on_ftp
     */
    public void setCPFileFromFTP(String _cp_file_on_ftp){this.cpFileFromFTP = _cp_file_on_ftp;}

    /**
     *
     * @param _cp_file_on_localhost
     */
    public void setCPFileToLocalhost(String _cp_file_on_localhost){this.cpFileToLocalhost = _cp_file_on_localhost;}

    /**
     *
     * @param _pc_file_on_ftp
     */
    public void setPCFileFromFTP(String _pc_file_on_ftp){this.pcFileFromFTP = _pc_file_on_ftp;}

    /**
     *
     * @param _pc_file_on_localhost
     */
    public void setPCFileToLocalhost(String _pc_file_on_localhost){this.pcFileToLocalhost = _pc_file_on_localhost;}

    public void setSyncFTPHost(String _sync_ftp_host){this.ftpSyncHost = _sync_ftp_host;}

    public void setSyncFTPHostUser(String _sync_ftp_user){this.ftpSyncHostUser = _sync_ftp_user;}

    public void setSyncFTPHostPass(char[] _sync_ftp_pass){this.ftpSyncHostPass = _sync_ftp_pass;}

    public void setSyncFTPHostDir(String _sync_ftp_dir){this.ftpSyncHostDir = _sync_ftp_dir;}

    public String getSyncFTPHost(){return this.ftpSyncHost;}

    public String getSyncFTPHostUser(){return this.ftpSyncHostUser;}

    public char[] getSyncFTPHostPass(){return this.ftpSyncHostPass;}

    public String getSyncFTPHostDir(){return this.ftpSyncHostDir;}
}
