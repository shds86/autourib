/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Interface;

import java.io.Serializable;
import java.util.Date;
import javax.swing.JButton;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author support
 */
public class JobClass implements Job,Serializable
{

    public static Log _log = LogFactory.getLog(JobClass.class);
    static JButton JBFrame;
//    static MainFrame Frame;

    public JobClass() {}
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        JBFrame = (JButton)dataMap.get("Frame");
//        Frame = (MainFrame)dataMap.get("Frame");
//        Frame.jTextAreaSystemLog.append("\n-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
//        Frame.jTextAreaSystemLog.append("\n"+ new Date()+" Запуск задания по расписанию");
//        Frame.getjButtonRunAll().doClick();
        JBFrame.doClick();
        String jobName = context.getJobDetail().getFullName();
        _log.info("JobClass says: " + jobName + " executing at " + new Date());
    }
}
