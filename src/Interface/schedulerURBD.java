/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Interface;

import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.Scheduler;
/**
 *
 * @author support
 */
public class schedulerURBD
{
    public static Log _log = LogFactory.getLog(schedulerURBD.class);
    private Class jobClass = main.class;
    private Date jobDate;
    private String time;
    private String Frequency;
    SchedulerFactory sf;
    Scheduler sched;
    JobDetail job;
    CronTrigger trigger;
    
    public schedulerURBD(){}
    public schedulerURBD(Class _jobClass)
    {
        this.jobClass = _jobClass;
    }
    public schedulerURBD(Date _date, String _Frequency)
    {
        this.jobDate = _date;
        this.time = "0 "+this.jobDate.getMinutes()+" "+this.jobDate.getHours()+" *"+" *"+" ?";
        System.out.println(this.time);
        this.Frequency = _Frequency;
    }

    public void createSCHED(String _time)
    {
        try
        {
            sf = new StdSchedulerFactory();
            sched = sf.getScheduler();
            job = new JobDetail("job1", "group1", jobClass);
            trigger = new CronTrigger("trigger1", "group1", "job1", "group1", _time);
            sched.addJob(job, true);
            Date ft = sched.scheduleJob(trigger);
            sched.start();
        }
        catch (Exception err)
        {err.printStackTrace();}
    }
    public void createSCHED()
    {
        try
        {
            sf = new StdSchedulerFactory();
            Log log = LogFactory.getLog(schedulerURBD.class);
            sched = sf.getScheduler();
            job = new JobDetail("job1", "group1", jobClass);
            trigger = new CronTrigger("trigger1", "group1", "job1", "group1", time);
            sched.addJob(job, true);
            Date ft = sched.scheduleJob(trigger);
            log.info(job.getFullName() + " has been scheduled to run at: " + ft
                + " and repeat based on expression: "
                + trigger.getCronExpression());
            sched.start();
        }
        catch (Exception err)
        {err.printStackTrace();}
    }

    @Override
    public String toString()
    {
        return this.jobDate.toString();
    }

    public String getFrequency()
    {
        return this.Frequency;
    }
}
