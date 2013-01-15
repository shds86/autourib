/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.util.Date;
import java.util.logging.Level;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Dmitry.Shahtanov
 */
public class SyncJobClass  implements Job
{
    public static Log _log = LogFactory.getLog(SyncJobClass.class);
    static MainFrame mF;

    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        mF = (MainFrame)dataMap.get("Frame");
        mF.jTextAreaSystemLog.append("\n-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
        mF.jTextAreaSystemLog.append("\n"+ new Date()+" Запуск задания по расписанию");
        MainFrame.logger.log(Level.INFO, "{0} Запуск задания по расписанию",new Date());
        mF.RunSync();
        String jobName = context.getJobDetail().getFullName();
        _log.info("JobClass says: " + jobName + " executing at " + new Date());
    }
}
