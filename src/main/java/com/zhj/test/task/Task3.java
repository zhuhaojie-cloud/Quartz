package com.zhj.test.task;

import org.springframework.stereotype.Component;


@Component("Task3")
public class Task3 {
    public void test3(String params) {
        System.out.println("我是带参数的test3方法，正在被执行，参数为：" + params);
    }
}
