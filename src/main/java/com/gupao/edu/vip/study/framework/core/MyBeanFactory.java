package com.gupao.edu.vip.study.framework.core;

/**
 * @author yiran
 */
public interface MyBeanFactory {
    /**
     * 根据beanName从Ioc容器中获取一个实例bean
     * @param beanName 全类名
     * @return 实例
     * @throws Exception 异常
     */
    Object getBean(String beanName) throws Exception;

    Object getBean(Class<?> clazz) throws Exception;
}
