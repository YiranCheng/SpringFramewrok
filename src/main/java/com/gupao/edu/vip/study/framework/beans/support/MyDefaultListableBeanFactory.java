package com.gupao.edu.vip.study.framework.beans.support;

import com.gupao.edu.vip.study.framework.beans.config.MyBeanDefinition;
import com.gupao.edu.vip.study.framework.context.support.MyAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yiran
 */
public class MyDefaultListableBeanFactory extends MyAbstractApplicationContext{
    protected final Map<String, MyBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

}
