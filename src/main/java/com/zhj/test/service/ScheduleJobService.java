/**
 *
 */
package com.zhj.test.service;

import com.zhj.test.entity.ScheduleJob;

import java.util.List;

public interface ScheduleJobService{
    List<ScheduleJob> findList();
    void delete(String id);
    void pause(String jobId);
    void resume(String jobId);
    ScheduleJob get(String id);
}