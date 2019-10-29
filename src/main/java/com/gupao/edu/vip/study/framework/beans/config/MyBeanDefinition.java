package com.gupao.edu.vip.study.framework.beans.config;

import lombok.Data;

/**
 * @author yiran
 */
@Data
public class MyBeanDefinition {
    private String beanClassName;
    private boolean lazyInit;
    private String factoryBeanName;
}
