/**
 *
 */
package com.zhj.test.service.impl;

import com.zhj.test.entity.ScheduleJob;
import com.zhj.test.mapper.SchedulerMapper;
import com.zhj.test.service.ScheduleJobService;
import com.zhj.test.util.Constant;
import com.zhj.test.util.ScheduleUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {
    @Autowired
    @Qualifier("schedulerFactoryBean")
    private Scheduler scheduler;

    @Autowired
    private SchedulerMapper schedulerMapper;

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        List<ScheduleJob> scheduleJobList = schedulerMapper.findList();
        for (ScheduleJob scheduleJob : scheduleJobList) {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
            //如果不存在，则创建
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }

    @Override
    public ScheduleJob get(String id) {
        return schedulerMapper.get(id);
    }

    @Override
    public List<ScheduleJob> findList() {
        return schedulerMapper.findList();
    }

    /*@Transactional(readOnly = false)
    public void save(ScheduleJob scheduleJob) {
        if (scheduleJob.getIsNewRecord()) {
            scheduleJob.setStatus(ScheduleStatus.PAUSE.getValue());
            scheduleJob.setId(UUID.randomUUID().toString().replace("-", ""));
            ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            scheduleJob.setIsNewRecord(true);
        } else {
            ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
        }
        super.save(scheduleJob);
    }*/

    @Override
    public void delete(String id) {
        schedulerMapper.delete(id);
        ScheduleUtils.deleteScheduleJob(scheduler, id);
    }


    public void run(String jobId) {
        //for(Long jobId : jobIds){
        ScheduleUtils.run(scheduler, get(jobId));
        //}
    }

    @Override
    public void pause(String jobId) {
        ScheduleJob scheduleJob = schedulerMapper.get(jobId);
        if (scheduleJob != null) {
            ScheduleUtils.pauseJob(scheduler, jobId);
            scheduleJob.setStatus(Constant.ScheduleStatus.PAUSE.getValue());
            schedulerMapper.update(scheduleJob);
        }
    }

    @Override
    public void resume(String jobId) {
        ScheduleJob scheduleJob = schedulerMapper.get(jobId);
        if (scheduleJob != null) {
            ScheduleUtils.resumeJob(scheduler, jobId);
            scheduleJob.setStatus(Constant.ScheduleStatus.NORMAL.getValue());
            schedulerMapper.update(scheduleJob);
        }
    }

}