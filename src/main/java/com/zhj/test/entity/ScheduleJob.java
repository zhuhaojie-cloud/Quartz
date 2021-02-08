/**
 *
 */
package com.zhj.test.entity;

/**
 * 定时任务Entity
 * @author fyj
 * @version 2017-11-01
 */
public class ScheduleJob{

    /**
     * 任务调度参数key
     */
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";
    private static final long serialVersionUID = 1L;
    private String jobId;		// 任务id
    private String jobName;        // 任务名称
    private String beanName;        // spring bean名称
    private String methodName;        // 方法名
    private String params;        // 参数
    private String cronExpression;        // cron表达式
    private Integer status;        // 任务状态

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}