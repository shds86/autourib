/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package netlib;
import java.io.*;
import java.util.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.io.CopyStreamException;

/**
 *
 * @author Dmitrij S. Shakhtanov
 */
public class FTP
{
    /**
     *
     */
    private String ftpName = null;

    /**
     *
     */
    private String ftpLogin = null;

    /**
     *
     */
    private char ftpPass[] = null;

    /**
     *
     */
    private String fileFromFTP = null;

    /**
     *
     */
    private File fileToLocalhost = null;

    /**
     *
     */
    private String path_parsing_get[] = null;

    /**
     *
     */
    private String path_parsing_put[] = null;
    
    /**
     *
     */
    private FTPClient ftpClient;

    /**
     *
     * @param name
     * @param login
     * @param pass
     */
    public FTP(String name, String login, char pass[]){
        this.ftpName  = name;
        this.ftpLogin = login;
        this.ftpPass  = pass;
    }

    /**
     *
     * @param name
     * @param login
     * @param pass
     * @param fileFromFTP
     * @param fileToLocalhost
     */
    public FTP(String name, String login, char pass[], String fileFromFTP, String fileToLocalhost){
        this.ftpName  = name;
        this.ftpLogin = login;
        this.ftpPass  = new char[pass.length];
        System.arraycopy(pass, 0, this.ftpPass, 0, pass.length);

        this.fileToLocalhost = new File(fileToLocalhost);
        this.fileFromFTP = fileFromFTP;
    }

    /**
     * 
     * @param str
     */
    public void setFileSourceFromFTP(String str){
        this.fileFromFTP = str;
    }

    /**
     *
     * @return
     */
    public byte connectToFTP(){
        if ((this.ftpLogin != null)&(this.ftpName != null )&(this.ftpPass != null))
        {
            ftpClient = new FTPClient();
            try
            {
                ftpClient.connect(ftpName);
                ftpClient.login(ftpLogin, new String(ftpPass));
//                System.out.print(ftpClient.getControlEncoding());
                ftpClient.setControlEncoding("WINDOWS-1251");
            }
            catch (FTPConnectionClosedException err)
            {
                return consterr.ERR_CANT_CONNECT;
            }
            catch (SocketException err)
            {
                return consterr.ERR_SERVER_DONT_ANSWER;
            }
            catch(IOException err)
            {
                return consterr.ERR_INCORRECT_LOGIN_OR_PASS;
            }
            return consterr.NOT_ERR;
        }
        else
        {
            return consterr.ERR_CANT_CONNECT;
        }
    }

    /**
     *
     */
    public void parsing_folder(){
        if (this.fileFromFTP.charAt(0) != '/')
        {
            this.fileFromFTP = "/".concat(this.fileFromFTP);
        }
        String parsing_tmp[];
        int count_symbol = 0;
        StringTokenizer st = new StringTokenizer(this.fileFromFTP,"/\\");
        parsing_tmp = new String [st.countTokens()];
        while (st.hasMoreTokens())
        {
            parsing_tmp[count_symbol] = st.nextToken();
            count_symbol++;
        }
        this.path_parsing_get = new String[parsing_tmp.length];
        System.arraycopy(parsing_tmp, 0, this.path_parsing_get, 0, parsing_tmp.length);
    }

    /**
     *
     * @param file_source_server
     * @return
     */
    public String [] parsing_folder(String file_source_server){
        if (file_source_server.charAt(0) != '/')
        {
            file_source_server = "/".concat(file_source_server);
        }
        String parsing_tmp[];
        int count_symbol = 0;
        StringTokenizer st = new StringTokenizer(file_source_server,"/\\");
        parsing_tmp = new String [st.countTokens()];
        while (st.hasMoreTokens())
        {
            parsing_tmp[count_symbol] = st.nextToken();
            count_symbol++;
        }
        return parsing_tmp;
    }

    public byte getFileFromFTP (String fileFromFTP, File fileToLocalhost) {
        try
        {
            String tmp_parsing_path[] = fileFromFTP.split("/");
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

            if (tmp_parsing_path.length < 1)
            {
                return consterr.ERR_FAILED_TAKE_FILE;
            }

            if (tmp_parsing_path.length > 1)
            {
                for(int index = 0; index < tmp_parsing_path.length-1;index++)
                {
                    ftpClient.changeWorkingDirectory(tmp_parsing_path[index]);
                }
            }
            
            if (!fileToLocalhost.exists())
            {
                if (!fileToLocalhost.getParentFile().exists())
                {
                    if (fileToLocalhost.getParentFile().mkdirs() == true)
                    {
                        if (!fileToLocalhost.createNewFile())
                        {
                            return consterr.ERR_CANT_CREATE_FILE_OR_FOLDER;
                        }
                    }
                    else
                    {
                        return consterr.ERR_CANT_CREATE_FILE_OR_FOLDER;
                    }
                }
                else
                {
                    if (!fileToLocalhost.createNewFile())
                    {
                        return consterr.ERR_CANT_CREATE_FILE_OR_FOLDER;
                    }
                }
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileToLocalhost));
            if (ftpClient.retrieveFile(tmp_parsing_path[tmp_parsing_path.length-1],bos) == true)
            {
                ftpClient.logout();
                ftpClient.disconnect();
                bos.close();
                return consterr.NOT_ERR;
            }
            else
            {
                ftpClient.logout();
                ftpClient.disconnect();
                bos.close();
                return consterr.ERR_FILE_NOT_FOUND;
            }
        }
        catch (FTPConnectionClosedException err)
        {
            return  consterr.ERR_SERVER_CONNECTION_CLOSED;
        }
        catch (CopyStreamException err)
        {
            return consterr.ERR_FAILED_PUT_FILE;
        }
        catch (IOException err)
        {
            return  consterr.ERR_FAILED_TAKE_FILE;
        }
    }

    public byte sendFileToFTP(String fileToFTP, File fileFromLocalhost){
        String tmp_parsing_path[] = parsing_folder(fileToFTP);
        try
        {
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            if (tmp_parsing_path.length < 1)
            {
                return consterr.ERR_CANT_SEND_FILE_ON_FTP;
            }
            if (tmp_parsing_path.length > 1)
            {
                for(int index = 0; index < tmp_parsing_path.length-1;index++)
                {
                    ftpClient.changeWorkingDirectory(tmp_parsing_path[index]);
                }
            }
            BufferedInputStream bis = new BufferedInputStream (new FileInputStream(fileFromLocalhost));
            if (ftpClient.storeFile(tmp_parsing_path[tmp_parsing_path.length-1], bis) == true)
            {
                ftpClient.logout();
                ftpClient.disconnect();
                bis.close();
                return consterr.NOT_ERR;
            }
            else
            {
                ftpClient.logout();
                ftpClient.disconnect();
                bis.close();
                return consterr.ERR_FAILED_PUT_FILE;
            }

        }
        catch (FileNotFoundException err)
        {
            return consterr.ERR_FILE_NOT_FOUND;
        }
        catch (FTPConnectionClosedException err)
        {
            return  consterr.ERR_SERVER_CONNECTION_CLOSED;
        }
        catch (CopyStreamException err)
        {
            return consterr.ERR_FAILED_PUT_FILE;
        }
        catch (IOException err)
        {
            return consterr.ERR_CANT_SEND_FILE_ON_FTP;
        }
    }
    
    public FTPFile [] getDirList(String fileFromFTP){
        try {
            ftpClient.changeWorkingDirectory(fileFromFTP);
            FTPFile [] ftpL = ftpClient.listDirectories();
//            ftpClient.logout();
//            ftpClient.disconnect();
            return ftpL;
        } catch (IOException ex) {
            Logger.getLogger(FTP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public FTPFile [] getFileList(String fileFromFTP){
        try {
            FTPFile [] ftpL = ftpClient.listFiles(fileFromFTP);
            return ftpL;
        } catch (IOException ex) {
            Logger.getLogger(FTP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public void setPMFT() throws IOException{
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
    }
    public byte getFileFromFTP2  (String fileFromFTP, File fileToLocalhost, long time) {
        try
        {
            if (!fileToLocalhost.exists())
            {
                if (!fileToLocalhost.getParentFile().exists())
                {
                    if (fileToLocalhost.getParentFile().mkdirs() == true)
                    {
                        if (!fileToLocalhost.createNewFile())
                        {
                            return consterr.ERR_CANT_CREATE_FILE_OR_FOLDER;
                        }
                    }
                    else
                    {
                        return consterr.ERR_CANT_CREATE_FILE_OR_FOLDER;
                    }
                }
                else
                {
                    if (!fileToLocalhost.createNewFile())
                    {
                        return consterr.ERR_CANT_CREATE_FILE_OR_FOLDER;
                    }
                }
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileToLocalhost));
            if (ftpClient.retrieveFile(fileFromFTP,bos) == true)
            {
                bos.close();
                System.out.println(fileToLocalhost.setLastModified(time));
                return consterr.NOT_ERR;
            }
            else
            {
                bos.close();
                return consterr.ERR_FILE_NOT_FOUND;
            }
        }
        catch (FTPConnectionClosedException err)
        {
            return  consterr.ERR_SERVER_CONNECTION_CLOSED;
        }
        catch (CopyStreamException err)
        {
            return consterr.ERR_FAILED_PUT_FILE;
        }
        catch (IOException err)
        {
            return  consterr.ERR_FAILED_TAKE_FILE;
        }
    }

    public String fileNameConvert(String src, String fromEncoding, String toEncoding){
        try{
            return new String( src.getBytes(fromEncoding),toEncoding);
        }
        catch(Exception e){}
        return "";
    }
  
    public boolean logoff(){
        try{
            this.ftpClient.logout();
            this.ftpClient.disconnect();
        }
        catch(IOException e){
            return false;
        }
        return true;
    }

    public ArrayList<String> listDir(String dir,boolean isF){
        ArrayList<String> list = new ArrayList();

          try {
            FTPFile [] ftpL = ftpClient.listDirectories(dir);
            if(ftpL.length > 0){
                for(int i = 0; i < ftpL.length; i++){
                   list.addAll( listDir(dir+"/"+ftpL[i].getName(),false));
                   if(!isF)list.add(dir);
                }
            }
            else{
                list.add(dir);
            }
          }
          catch(Exception e){
              return null;
          }
        return list;
    }
    
}
