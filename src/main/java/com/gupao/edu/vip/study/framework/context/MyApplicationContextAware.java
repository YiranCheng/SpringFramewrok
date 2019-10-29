package com.gupao.edu.vip.study.framework.context;

/**
 * @author yiran
 * 通过解耦的方式获得IOC容器的顶层设计
 * 后面将通过要给监听器去扫描所有的类，只要实现了此接口，
 * 将自动调用setApplicationContext()方法，从而将IOC容器注册到目标类中
 */
public interface MyApplicationContextAware {
    void setApplicationContext(MyApplicationContext applicationContext);
}
