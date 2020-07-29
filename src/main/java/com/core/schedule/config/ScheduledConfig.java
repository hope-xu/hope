package com.core.schedule.config;

import com.core.schedule.domain.BatchTimerDef;
import com.core.schedule.service.BatchTimerDefService;
import com.utils.SpringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * 定时任务并行执行  定时任务执行时间方法等参数  都数据库配置化
 *
 * @author DELL
 */
@Lazy(value = false)
@Component("ScheduledJobConfig")
public class ScheduledConfig implements SchedulingConfigurer {

    private static Logger logger = LoggerFactory.getLogger(ScheduledConfig.class);

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private BatchTimerDefService batchTimerDefService;

    private static int poolSize = 0;

    @Bean(destroyMethod = "shutdown")
    public Executor setTaskExecutors() {

        return new ThreadPoolExecutor(10,20,
                1L,TimeUnit.SECONDS,
                //存放提交但未执行的任务队列
                new LinkedBlockingQueue<>(15),
                //创建线程的工厂类
                Executors.defaultThreadFactory(),
                //等待队列满后的拒绝策略
                new ThreadPoolExecutor.AbortPolicy());
//        return Executors.newScheduledThreadPool(Integer.valueOf(poolSize)); //处理线程数
    }


    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        //获取状态为Y的定时任务
        List<BatchTimerDef> tasks = batchTimerDefService.getTaskListByStatus("Y");
        poolSize = tasks != null ? tasks.size() : poolSize;
        taskRegistrar.setScheduler(setTaskExecutors());
        //从数据库里取得所有要执行的定时任务  有效的定时任务
        logger.info("定时任务启动,预计启动任务数量=" + tasks.size() + "; time=" + sdf.format(new Date()));

        //执行定时任务
        int count = 0;
        if (tasks != null && tasks.size() > 0) {
            for (int i = 0; i < tasks.size(); i++) {
                try {
                    logger.info("ClassName:" + tasks.get(i).getClassName());
                    taskRegistrar.addTriggerTask(getRunnable(tasks.get(i)), getTrigger(tasks.get(i)));
                    count++;
                } catch (Exception e) {
                    logger.error("定时任务启动错误:" + tasks.get(i).getClassName() + ";" + tasks.get(i).getMethod() + ";" + e.getMessage());
                }
            }
        }
        logger.info("定时任务实际启动数量=" + count + "; time=" + sdf.format(new Date()));
    }


    private Runnable getRunnable(BatchTimerDef task) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    //SpringUtil 此方法需要加方法  task.getClassName()
                    IScheduled obj = SpringUtil.getBean(task.getClassName(), IScheduled.class);
                    obj.execute();
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        };
    }

    private Trigger getTrigger(BatchTimerDef task) {
        return new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                //将Cron 0/1 * * * * ? 输入取得下一次执行的时间
                CronTrigger trigger = new CronTrigger(task.getCron());
                Date nextExec = trigger.nextExecutionTime(triggerContext);
                return nextExec;
            }
        };

    }

    //检查定时任务列表参数
    private List<BatchTimerDef> checkDataList(List<BatchTimerDef> list) {
        String errMsg = "";
        for (int i = 0; i < list.size(); i++) {
            if (!"success".equalsIgnoreCase(checkOneData(list.get(i)))) {
                errMsg += list.get(i).getTimerName() + ";";
                list.remove(list.get(i));
                i--;
            }
        }
        if (!StringUtils.isBlank(errMsg)) {
            errMsg = "未启动的任务:" + errMsg;
            logger.error(errMsg);
        }
        return list;
    }

    //对定时任务信息参数核对
    private String checkOneData(BatchTimerDef task) {
        String result = "success";
        try {
            IScheduled obj = SpringUtil.getBean(task.getClassName(), IScheduled.class);
            String cron = task.getCron();
            if (StringUtils.isBlank(cron)) {
                result = "定时任务启动错误，无cron:" + task.getTimerName();
                logger.error(result);
            }
        } catch (Exception e) {
            result = "定时任务启动错误，找不到方法,方法必须是public:" + task.getClassName() + ";" + task.getMethod() + ";" + e.getMessage();
            logger.error(e.getMessage());
        }
        return result;
    }
}
