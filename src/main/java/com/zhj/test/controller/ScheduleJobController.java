/**
 *
 */
package com.zhj.test.controller;

import com.zhj.test.entity.ScheduleJob;
import com.zhj.test.service.impl.ScheduleJobServiceImpl;
import com.zhj.test.util.ScheduleRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 定时任务Controller
 */
@Controller
public class ScheduleJobController{
    @Autowired
    private ScheduleJobServiceImpl scheduleJobServicelmpl;

    @GetMapping("list")
    public String list() {
        List<ScheduleJob> list = scheduleJobServicelmpl.findList();
        return null;
    }

    @GetMapping("get/{jobId}")
    public String get(@PathVariable(name = "jobId") String jobId) {
        ScheduleJob scheduleJob = scheduleJobServicelmpl.get(jobId);
        return null;
    }

    @GetMapping("save")
    public String save() {
        //ValidatorUtils.validateEntity(scheduleJob);
        //scheduleJobServicelmpl.save(scheduleJob);
        return null;
    }

    @GetMapping("delete/{jobId}")
    public String delete(@PathVariable(name = "jobId") String jobId) {
        scheduleJobServicelmpl.delete(jobId);
        return null;
    }

    /**
     * 立即执行任务
     */
    @GetMapping("run/{jobId}")
    public String run(@PathVariable(name = "jobId") String jobId) {
        scheduleJobServicelmpl.run(jobId);
        return null;
    }

    /**
     * 暂停定时任务
     */
    @GetMapping("pause/{jobId}")
    public String pause(@PathVariable(name = "jobId") String jobId) {
        scheduleJobServicelmpl.pause(jobId);
        return null;
    }

    /**
     * 恢复定时任务
     */
    @GetMapping("resume/{jobId}")
    public String resume(@PathVariable(name = "jobId") String jobId) {
        scheduleJobServicelmpl.resume(jobId);
        return null;

    }

    /**
     * 立即执行
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("execute/{jobId}")
    public String execute(@PathVariable(name = "jobId") String jobId) throws NoSuchMethodException, SecurityException, InterruptedException, ExecutionException {
        ScheduleJob scheduleJob = scheduleJobServicelmpl.get(jobId);
        ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(), scheduleJob.getMethodName(), scheduleJob.getParams());
        new Thread(task).start();
        return null;
    }


}