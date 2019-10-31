package com.gupao.edu.vip.study.demo;

import com.gupao.edu.vip.study.demo.controller.UserController;
import com.gupao.edu.vip.study.framework.context.MyApplicationContext;

/**
 * @author yiran
 */
public class Test {
    public static void main(String[] args) {
        MyApplicationContext applicationContext = new MyApplicationContext("classpath:application.properties");
        try {
            Object o = applicationContext.getBean(UserController.class);
            System.out.println(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
