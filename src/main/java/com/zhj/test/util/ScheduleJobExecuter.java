package com.zhj.test.util;

import com.alibaba.fastjson.JSONObject;
import com.zhj.test.entity.ScheduleJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * 定时任务
 */
public class ScheduleJobExecuter extends QuartzJobBean {
    private ExecutorService service = Executors.newSingleThreadExecutor();

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        String jsonJob = context.getMergedJobDataMap().getString(ScheduleJob.JOB_PARAM_KEY);
        ScheduleJob scheduleJob = JSONObject.parseObject(jsonJob, ScheduleJob.class);
        //获取spring bean
        //任务开始时间
        long startTime = System.currentTimeMillis();
        try {
            //执行任务
            System.out.println("任务准备执行，任务ID：" + scheduleJob.getJobId());
            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(),
                    scheduleJob.getMethodName(), scheduleJob.getParams());
            Future<?> future = service.submit(task);
            //执行任务总耗时暂不计算，多个触发器触发一个任务（job），在其中一个任务没有执行完的情况下，其他任务无法执行
            //future.get();
            //任务执行总时长
            //long times = System.currentTimeMillis() - startTime;
            //任务状态    0：成功    1：失败
            //logger.debug("任务执行完毕，任务ID：" + scheduleJob.getJobId() + "  总共耗时：" + times / 1000 + "秒");
        } catch (Exception e) {
            System.out.println("任务执行失败，任务ID：" + scheduleJob.getJobId());
            //任务执行总时长
            //long times = System.currentTimeMillis() - startTime;
            //log.setTimes((int)times);
            //任务状态    0：成功    1：失败
            //log.setStatus(1);
            //log.setError(StringUtils.substring(e.toString(), 0, 2000));
        } finally {
            //scheduleJobLogService.save(log);
        }
    }
}
