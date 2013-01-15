package Interface;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author support
 */
public class schedulerURBD
{
    public static Log _log = LogFactory.getLog(schedulerURBD.class);
    private Class jobClass = GlobalJobClass.class;
    Date jobDate;
    String time;
    int Frequency;
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
        this.jobName = "job"+_date.getTime();
        this.triggerName = "triggerFor"+this.jobName;
        this.jobDate = _date;
        this.Frequency = _Frequency;
        this.time = switchFrequency(this.Frequency);
    }

    public boolean createSCHED(String _time)
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
            //Date ft = sched.scheduleJob(trigger);
            return true;
        }
        catch (Exception err)
        {err.printStackTrace();}
        return true;
    }
    
    public boolean createSCHED(MainFrame Frame, Class _jobClass, boolean f){
        try
        {
            this.jobClass = _jobClass;
            sf = new StdSchedulerFactory();
            Log log = LogFactory.getLog(schedulerURBD.class);
            sched = sf.getScheduler();
            job = new JobDetail(jobName, "group1", jobClass);
            job.getJobDataMap().put("Frame", Frame);
            job.getJobDataMap().put("exchange", true);
            job.getJobDataMap().put("sync", false);
            trigger = new CronTrigger(triggerName, "group1", jobName, "group1", time);
            sched.addJob(job, true);
            Date ft = sched.scheduleJob(trigger);
            log.info(job.getFullName() + " has been scheduled to run at: " + ft
                + " and repeat based on expression: "
                + trigger.getCronExpression());
            return true;
        }
        catch (Exception err)
        {
            err.printStackTrace();
        }
        return false;
    }

    public boolean createSCHED(MainFrame Frame, Class _jobClass)
    {
        try
        {
            this.jobClass = _jobClass;
            sf = new StdSchedulerFactory();
            Log log = LogFactory.getLog(schedulerURBD.class);
            sched = sf.getScheduler();
            job = new JobDetail(jobName, "group1", jobClass);
            job.getJobDataMap().put("Frame", Frame);
            job.getJobDataMap().put("exchange", true);
            job.getJobDataMap().put("sync", false);
            trigger = new CronTrigger(triggerName, "group1", jobName, "group1", time);
            sched.addJob(job, true);
            Date ft = sched.scheduleJob(trigger);
            Frame.jTextAreaSystemLog.append("\n--------------------------------------------------");
            Frame.jTextAreaSystemLog.append("\n"+ new Date()+" Задание успешно создано. " +
                                            "\nВремя запуска задания "+ this.jobDate+
                                            "\n"+getFrequency(this.Frequency));
            MainFrame.logger.log(Level.INFO, "{0} Задание успешно создано. Время запуска задания {1} {2}",
                                        new Object[]{new Date(),this.jobDate,getFrequency(this.Frequency)});

            log.info(job.getFullName() + " has been scheduled to run at: " + ft
                + " and repeat based on expression: "
                + trigger.getCronExpression());
            return true;
        }
        catch (Exception err)
        {
            err.printStackTrace();
        }
        return false;
    }
    
    public boolean createSCHED(MainFrame Frame, Class _jobClass,boolean exchange,boolean sync)
    {
        try
        {
            this.jobClass = _jobClass;
            sf = new StdSchedulerFactory();
            Log log = LogFactory.getLog(schedulerURBD.class);
            sched = sf.getScheduler();
            job = new JobDetail(jobName, "group1", jobClass);
            job.getJobDataMap().put("Frame", Frame);
            job.getJobDataMap().put("exchange", exchange);
            job.getJobDataMap().put("sync", sync);
            trigger = new CronTrigger(triggerName, "group1", jobName, "group1", time);
            sched.addJob(job, true);
            Date ft = sched.scheduleJob(trigger);
            Frame.jTextAreaSystemLog.append("\n--------------------------------------------------");
            Frame.jTextAreaSystemLog.append("\n"+ new Date()+" Задание успешно создано. " +
                                            "\nВремя запуска задания "+ this.jobDate+
                                            "\n"+getFrequency(this.Frequency));
            MainFrame.logger.log(Level.INFO, "{0} Задание успешно создано. Время запуска задания {1} {2}",
                                        new Object[]{new Date(),this.jobDate,getFrequency(this.Frequency)});

            log.info(job.getFullName() + " has been scheduled to run at: " + ft
                + " and repeat based on expression: "
                + trigger.getCronExpression());
            return true;
        }
        catch (Exception err)
        {
            err.printStackTrace();
        }
        return false;
    }

    public boolean reInitSCHED(MainFrame Frame, Class _jobClass,boolean exchange,boolean sync)
    {
        try
        {
            this.jobClass = _jobClass;
            sf = new StdSchedulerFactory();
            Log log = LogFactory.getLog(schedulerURBD.class);
            sched = sf.getScheduler();
            sched.unscheduleJob(triggerName, "group1");
            JobDetail j = sched.getJobDetail(jobName, "group1");
            job = new JobDetail(jobName, "group1", jobClass);
            job.getJobDataMap().put("Frame", Frame);
            job.getJobDataMap().put("exchange", exchange);
            job.getJobDataMap().put("sync", sync);
            trigger = new CronTrigger(triggerName, "group1", jobName, "group1", time);
            sched.addJob(job, true);
            Date ft = sched.scheduleJob(trigger);
            Frame.jTextAreaSystemLog.append("\n--------------------------------------------------");
            Frame.jTextAreaSystemLog.append("\n"+ new Date()+" Задание успешно создано. " +
                                            "\nВремя запуска задания "+ this.jobDate+
                                            "\n"+getFrequency(this.Frequency));
            MainFrame.logger.log(Level.INFO, "{0} Задание успешно создано. Время запуска задания {1} {2}",
                                        new Object[]{new Date(),this.jobDate,getFrequency(this.Frequency)});

            log.info(job.getFullName() + " has been scheduled to run at: " + ft
                + " and repeat based on expression: "
                + trigger.getCronExpression());
            return true;
        }
        catch (Exception err)
        {
            err.printStackTrace();
        }
        return false;
    }
    
    public String getFrequency(int _Frequency)
    {
        switch (Frequency)
        {
            case 0:{return "Задание выполнится один раз";}
            case 1:{return "Задание будет выполняться каждый час";}
            case 2:{return "Задание будет выполняться каждый день";}
            case 3:{return "Задание будет выполняться каждую неделю";}
            case 4:{return "Задание будет выполняться каждый месяц";}
            case 5:{return "Задание будет выполняться каждый год";}
            default:{return "Задание запускаться не будет";}
        }
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

    public boolean pause()
    {
        try
        {
            if(this.sched != null)
                this.sched.standby();
            return true;
        }
        catch(SchedulerException err)
        {
            return false;
        }
    }

    public boolean start()
    {
        try
        {
            this.sched.start();
//            this.sched.triggerJob(jobName,triggerName);
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
            if (this.sched.isStarted())
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

    public void setTime(String _time)
    {
        this.time = _time;
    }

    public void setJobName(String _jobName)
    {
        this.jobName = _jobName;
    }

    public void setTriggerName(String _triggerName)
    {
        this.triggerName = _triggerName;
    }

    public void setJobDate(String _jobDate)
    {
        this.jobDate = new Date(Long.parseLong(_jobDate));
    }
    
    public void setJobDate(Date _jobDate)
    {
        this.jobDate = _jobDate;
    }
    
    public void setFrequency(int _Frequency)
    {
        this.Frequency = _Frequency;
    }
    
    public String switchFrequency(int _Frequency)
    {
        switch (_Frequency)
        {
            case 0:     //Один раз
                {
                    return "0"+                                //сек
                                " "+this.jobDate.getMinutes()+      //минуты
                                " "+this.jobDate.getHours()+        //часы
                                " "+this.jobDate.getDate()+         //число
                                " "+(this.jobDate.getMonth()+1)+    //месяц
                                " "+"?"+                            //день недели
                                " "+(this.jobDate.getYear()+1900);  //год
                }
            case 1:     //Каждый час
                {
                    return "0"+                                //сек
                                " "+this.jobDate.getMinutes()+      //минуты
                                " "+"*"+                            //часы
                                " "+"*"+                            //число
                                " "+"*"+                            //месяц
                                " "+"?"+                            //день недели
                                " "+"*";                            //год
                }
            case 2:     //Каждый день
                {
                    return "0"+                                //сек
                                " "+this.jobDate.getMinutes()+      //минуты
                                " "+this.jobDate.getHours()+        //часы
                                " "+"*"+                            //число
                                " "+"*"+                            //месяц
                                " "+"?"+                            //день недели
                                " "+"*";                            //год
                }
            case 3:     //Каждую неделю
                {
                    return "0"+                                //сек
                                " "+this.jobDate.getMinutes()+      //минуты
                                " "+this.jobDate.getHours()+        //часы
                                " "+"?"+                            //число
                                " "+"*"+                            //месяц
                                " "+(this.jobDate.getDay()+1)+      //день недели
                                " "+"*";                            //год
                }
            case 4:     //Каждый месяц
                {
                    return "0"+                                //сек
                                " "+this.jobDate.getMinutes()+      //минуты
                                " "+this.jobDate.getHours()+        //часы
                                " "+this.jobDate.getDate()+         //число
                                " "+"*"+                            //месяц
                                " "+"?"+                            //день недели
                                " "+"*";                            //год
                }
            case 5:     //Каждый год
                {
                    return "0"+                                //сек
                                " "+this.jobDate.getMinutes()+      //минуты
                                " "+this.jobDate.getHours()+        //часы
                                " "+this.jobDate.getDate()+         //число
                                " "+(this.jobDate.getMonth()+1)+    //месяц
                                " "+"?"+                            //день недели
                                " "+"*";                            //год
                }
        }
        return  "0"+ //сек
                " "+this.jobDate.getMinutes()+      //минуты
                " "+this.jobDate.getHours()+        //часы
                " "+this.jobDate.getDate()+         //число
                " "+(this.jobDate.getMonth()+1)+    //месяц
                " "+"?"+                            //день недели
                " "+(this.jobDate.getYear()+1900);  //год
    }
    
    public void changeJob()
    {
        
    
    }
    public Class getJobClass(){
        return  this.jobClass;
    }

    void setJobClass(String property) {
        try{
            this.jobClass = Class.forName(property);
        }
        catch(ClassNotFoundException err){
            this.jobClass = GlobalJobClass.class;
        }
    }

    void setTime() {
        time = switchFrequency(this.Frequency);
    }
    
    void destroy(){
        try {
            sched.unscheduleJob(triggerName, "group1");
        } catch (SchedulerException ex) {
            Logger.getLogger(schedulerURBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
