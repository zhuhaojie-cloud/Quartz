package com.zhj.test.task;

import org.springframework.stereotype.Component;


@Component("Task1")
public class Task1 {

    public void test1(){
        System.out.println("test1 开始启动");

        System.out.println("test1 完成");
    }
}
