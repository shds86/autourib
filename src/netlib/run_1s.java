package netlib;

import java.io.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Класс для запуска 1с с заданными параметрами и в заданных режимах.
 * @author Dmitrij S. Shakhtanov
 */
public class run_1s
{
    private String login = null;
    private String pass = null;

    /**
     *
     */
    public File pathToBase = null;

    /**
     *
     */
    public File pathTo1S = null;

    /**
     *
     */
    public File prmFile = null;

    /**
     *
     */
    public File readLog = null;

    /**
     *
     * @param str_path_1s
     * @param str_path_base
     * @param login
     * @param pass
     */
    public run_1s(String str_path_1s, String str_path_base, String login, String pass)
    {
        this.pathTo1S = new File(str_path_1s);
        this.pathToBase = new File(str_path_base);
        this.login = login;
        this.pass = pass;
    }

    /**
     *
     * @param key 
     * @return
     */
    public byte create_prm(byte key)
    {
        String str_title[] = {"[General]","[AutoExchange]","[CheckAndRepair]","[UnloadData]","[SaveData]"};
        String str_key[][] = {{"Output = ","Quit = 1","CheckAndRepair = ","UnloadDate = ","SaveDate = ","AutoExchange = 1"},
                              {"SharedMode = 1","ReceiveFrom = ","ReadFrom = ","WriteTo = ","SendTo = "},
                              {"Repair = ","PhysicalIntegrity = ","Reindex = ","LogicalIntegrity = ","RecalcSecondaries = ",
                               "RecalcTotals = ","Pack = ","SkipUnresolved = ","CreateForUnresolved = ","Reconstruct = "},
                              {"UnloadToFile = ","IncludeUserDef = ","Password = "},
                              {"SaveToFile = ","FileList = "}};
        PrintWriter pw;
        try
        {
            Date dat = new Date();
            String str = System.getProperty("user.dir")+System.getProperty("file.separator")+"AvtoURIB"+
                                System.getProperty("file.separator")+"TMP"+System.getProperty("file.separator")+dat.getTime()+".prm";
            this.prmFile = new File(str);
            if (!this.prmFile.exists())
            {
                if(this.prmFile.getParentFile().mkdirs())
                    if (!this.prmFile.createNewFile())
                        return consterr.ERR_CANT_CREATE_PRM_FILE;
            }
            else
                return consterr.ERR_CANT_CREATE_PRM_FILE;
            pw = new PrintWriter (new OutputStreamWriter (new FileOutputStream (this.prmFile)));
            readLog = new File(System.getProperty("user.dir")+System.getProperty("file.separator")+"AvtoURIB"+
                                System.getProperty("file.separator")+"TMP"+System.getProperty("file.separator")+dat.getTime()+".log");


            pw.println("[General]");
            pw.println("Output = "+readLog.getCanonicalFile());
            pw.println("Quit = 1");
            pw.println("AutoExchange = 1");

            pw.println("[AutoExchange]");
            pw.println("SharedMode = 1");
            switch (key)
            {
                case 0:
                {
                    pw.println("ReadFrom = *");
                    pw.println("WriteTo = *");                    
                    break;
                }
                case 1:
                {
                    pw.println("ReadFrom = *");
                    break;
                }
                case 2:
                {
                    pw.println("WriteTo = *");
                    break;
                }
            }
            pw.close();
            return consterr.NOT_ERR;
        }
        catch (Exception err)
        {
            return consterr.ERR_CANT_CREATE_PRM_FILE;
        }
    }

    /**
     *
     * @return
     */
    public List<String> parsing_log()
    {
        BufferedReader tmp;
        List<String> lst;
        if (this.readLog.exists())
        {
            try
            {
                String Data, EncodedData;
                StringTokenizer str_token;
                lst = new LinkedList<String>();
                tmp = new BufferedReader (new InputStreamReader(new FileInputStream(this.readLog)));
                while (tmp.ready())
                {
                    Data = tmp.readLine();
                    str_token = new StringTokenizer(Data,";");
                    if (Data != null)
                    {
                        while (str_token.hasMoreTokens())
                        {
                            EncodedData = str_token.nextToken();
                            if (EncodedData.equals("DistUplErr"))
                            {
                                str_token.nextToken();
                                lst.add(str_token.nextToken());
                            }                                
//                            if (EncodedData.equals("DistUplFail"))
//                            {
//                                lst.add("Данные из указанного файла переноса\n данных уже загружались в текущую информационную базу.");
//                            }
                            if  (EncodedData.equals("DistUplSuc"))
                            {
                                lst.add("Загрузка прошла успешно");
                            }
                            if (EncodedData.equals("DistDnldFail"))
                            {
                                lst.add("Выгрузка не прошла.");
                            }
                            if  (EncodedData.equals("DistDnldSuc"))
                            {
                                lst.add("Выгрузка прошла успешно");
                            }
                        }
                    }
                }
                tmp.close();
            }
            catch(IOException err)
            {
                return null;
            }
            catch(IllegalStateException err)
            {
                return null;
            }
            this.readLog.delete();
            return lst;
        }
        return null;
    }

    /**
     *
     * @return
     */
    public int start()
    {
        int _result1S;
        try
        {
            String cmd[] =  {this.pathTo1S.getPath(),"config",
                             "/D",this.pathToBase.getAbsolutePath(),
                             "/N",login,
                             "/P",pass,
                             "/@",this.prmFile.getCanonicalPath()};
            Process proc = Runtime.getRuntime().exec(cmd);
            _result1S = proc.waitFor();
            this.prmFile.getAbsoluteFile().delete();
            this.prmFile.getParentFile().delete();
            return _result1S;
        }
        catch (SecurityException err)
        {
            return consterr.ERR_EXCHANGE;
        }
        catch (IOException err)
        {
            return consterr.ERR_EXCHANGE;
        }
        catch (NullPointerException err)
        {
            return consterr.ERR_EXCHANGE;
        }
        catch (IllegalArgumentException  err)
        {
            return consterr.ERR_EXCHANGE;
        }
        catch (InterruptedException err)
        {
            return consterr.ERR_EXCHANGE;
        }
    }
}