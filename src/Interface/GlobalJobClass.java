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
 * @author support
 */
public class GlobalJobClass implements Job
{
    public static Log _log = LogFactory.getLog(GlobalJobClass.class);
    static MainFrame mF;

    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        mF = (MainFrame)dataMap.get("Frame");
        mF.jTextAreaSystemLog.append("\n-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
        mF.jTextAreaSystemLog.append("\n"+ new Date()+" Запуск задания по расписанию");
        MainFrame.logger.log(Level.INFO, "{0} Запуск задания по расписанию",new Date());
        if( (Boolean)dataMap.get("exchange") )
            mF.RunAll();
        if( (Boolean)dataMap.get("sync") )
            mF.RunSync();
        String jobName = context.getJobDetail().getFullName();
        _log.info("JobClass says: " + jobName + " executing at " + new Date());
    }
}
