package com.gupao.edu.vip.study.framework.beans.config;

/**
 * @author yiran
 */
public class MyBeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean,String beanNae){
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean,String beanName) {
        return bean;
    }
}
