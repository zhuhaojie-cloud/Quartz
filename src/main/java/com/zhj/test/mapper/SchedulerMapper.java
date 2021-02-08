package com.zhj.test.mapper;

import com.zhj.test.entity.ScheduleJob;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SchedulerMapper {

    List<ScheduleJob> findList();

    ScheduleJob get(@Param(value = "id")String id);

    void delete(@Param(value = "id")String id);

    void update(@Param(value = "scheduleJob")ScheduleJob scheduleJob);
}