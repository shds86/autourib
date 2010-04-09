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
import org.quartz.SchedulerException;
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
    private int Frequency;
    String jobName;
    String triggerName;
    SchedulerFactory sf;
    Scheduler sched;
    JobDetail job;
    CronTrigger trigger;
    
    public schedulerURBD(){}

    public schedulerURBD(Class _jobClass)
    {
        this.jobClass = _jobClass;
    }

    public schedulerURBD(Date _date, int _Frequency)
    {
        this.jobDate = _date;
        switch (_Frequency)
        {
            case 0:     //Один раз
                {
                    this.time = "0"+                                //сек
                                " "+this.jobDate.getMinutes()+      //минуты
                                " "+this.jobDate.getHours()+        //часы
                                " "+this.jobDate.getDate()+         //число
                                " "+(this.jobDate.getMonth()+1)+    //месяц
                                " "+"?"+                            //день недели
                                " "+(this.jobDate.getYear()+1900);  //год
                    break;
                }
            case 1:     //Каждый час
                {
                    this.time = "0"+                                //сек
                                " "+this.jobDate.getMinutes()+      //минуты
                                " "+"*"+                            //часы
                                " "+"*"+                            //число
                                " "+"*"+                            //месяц
                                " "+"?"+                            //день недели
                                " "+"*";                            //год
                    break;
                }
            case 2:     //Каждый день
                {
                    this.time = "0"+                                //сек
                                " "+this.jobDate.getMinutes()+      //минуты
                                " "+this.jobDate.getHours()+        //часы
                                " "+"*"+                            //число
                                " "+"*"+                            //месяц
                                " "+"?"+                            //день недели
                                " "+"*";                            //год
                    break;
                }
            case 3:     //Каждую неделю
                {
                    this.time = "0"+                                //сек
                                " "+this.jobDate.getMinutes()+      //минуты
                                " "+this.jobDate.getHours()+        //часы
                                " "+"*"+                            //число
                                " "+"*"+                            //месяц
                                " "+(this.jobDate.getDay()+1)+      //день недели
                                " "+"*";                            //год
                    break;
                }
            case 4:     //Каждый месяц
                {
                    this.time = "0"+                                //сек
                                " "+this.jobDate.getMinutes()+      //минуты
                                " "+this.jobDate.getHours()+        //часы
                                " "+this.jobDate.getDate()+         //число
                                " "+"*"+                            //месяц
                                " "+"?"+                            //день недели
                                " "+"*";                            //год
                    break;
                }
            case 5:     //Каждый год
                {
                    this.time = "0"+                                //сек
                                " "+this.jobDate.getMinutes()+      //минуты
                                " "+this.jobDate.getHours()+        //часы
                                " "+this.jobDate.getDate()+         //число
                                " "+(this.jobDate.getMonth()+1)+    //месяц
                                " "+"?"+                            //день недели
                                " "+"*";                            //год
                    break;
                }
        }
        System.out.println(this.time);
        this.Frequency = _Frequency;
    }

    public void createSCHED(String _time)
    {
        jobName = "job"+Long.toString(System.currentTimeMillis());
        triggerName = "triggerFor"+jobName;
        try
        {
            sf = new StdSchedulerFactory();
            sched = sf.getScheduler();
            job = new JobDetail(jobName, "group1", jobClass);
            trigger = new CronTrigger(triggerName, "group1", jobName, "group1", _time);
            sched.addJob(job, true);
            Date ft = sched.scheduleJob(trigger);

        }
        catch (Exception err)
        {err.printStackTrace();}
    }

    public void createSCHED()
    {
        jobName = "job"+Long.toString(System.currentTimeMillis());
        triggerName = "triggerFor"+jobName;
        try
        {
            sf = new StdSchedulerFactory();
            Log log = LogFactory.getLog(schedulerURBD.class);
            sched = sf.getScheduler();
            job = new JobDetail(jobName, "group1", jobClass);
            trigger = new CronTrigger(triggerName, "group1", jobName, "group1", time);
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

    public boolean isStarted()
    {
        try
        {
            return this.sched.isStarted();
        }
        catch(SchedulerException err)
        {
            err.printStackTrace();
            return false;
        }
    }

    public int getFrequency()
    {
        return this.Frequency;
    }

    public boolean start()
    {
        try
        {
            this.sched.start();
            return true;
        }
        catch(SchedulerException err)
        {
            return false;
        }
    }

    public boolean stop()
    {
        try
        {
            if (this.sched.isStarted()==true)
            {
                this.sched.shutdown(true);
                return true;
            }
            return false;
        }
        catch(SchedulerException err)
        {
            return false;
        }
    }
}
