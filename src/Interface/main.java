/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Interface;

import java.util.Date;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author support
 */
public class main implements Job{
    public static Log _log = LogFactory.getLog(main.class);
    static MainFrame Frame;

    public main() {}
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        Frame.jTextAreaSystemLog.append("\n-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
        Frame.jTextAreaSystemLog.append("\n"+ new Date()+" Запуск задания по расписанию");
        Frame.getjButtonRunAll().doClick();
        //System.out.println(context.getJobRunTime());
        String jobName = context.getJobDetail().getFullName();
        _log.info("main says: " + jobName + " executing at " + new Date());
    }
    /**
     *
     * @param args
     */
    public static void main(String args[]) {
        Frame = new MainFrame();
        Frame.setVisible(true);
    }
}
