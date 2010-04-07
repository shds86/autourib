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
//

    public main() {}
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        Frame.jTextAreaSystemLog.append("\nЗапуск задания по рассписанию в "+ new Date());
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
//            try
//    {
//        SchedulerFactory sf = new StdSchedulerFactory();
//        Log log = LogFactory.getLog(main.class);
//        Scheduler sched = sf.getScheduler();
//        JobDetail job = new JobDetail("job1", "group1", main.class);
//        CronTrigger trigger = new CronTrigger("trigger1", "group1", "job1", "group1", "0 52 14 * * ?");
//        sched.addJob(job, true);
//        Date ft = sched.scheduleJob(trigger);
//        log.info(job.getFullName() + " has been scheduled to run at: " + ft
//                + " and repeat based on expression: "
//                + trigger.getCronExpression());
//        sched.start();
//    }
//    catch (Exception err)
//    {err.printStackTrace();}
    }
}
