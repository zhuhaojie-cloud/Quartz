package com.zhj.test.task;

import org.springframework.stereotype.Component;


@Component("Task2")
public class Task2 {

    public void test2(){
        System.out.println("test2 开始启动");

        System.out.println("test2 完成");
    }
}
