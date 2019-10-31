package com.gupao.edu.vip.study.demo.controller;

import com.gupao.edu.vip.study.demo.service.UserService;
import com.gupao.edu.vip.study.framework.annotation.MyAutowired;
import com.gupao.edu.vip.study.framework.annotation.MyController;
import com.gupao.edu.vip.study.framework.annotation.MyRequestMapping;

/**
 * @author yiran
 */
@MyController
public class UserController {
    @MyAutowired
    private UserService userService;

    @MyRequestMapping("123")
    public int getCount() {
        return userService.countUser();
    }
}
