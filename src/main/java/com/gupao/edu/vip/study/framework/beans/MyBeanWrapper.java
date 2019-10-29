package com.gupao.edu.vip.study.framework.beans;

/**
 * @author yiran
 */
public class MyBeanWrapper {

    private Object wrappedInstance;
    private Class<?> wrappedClass;

    public MyBeanWrapper(Object instance) {
        wrappedInstance = instance;
    }

    public Object getWrappedInstance() {
        return wrappedInstance;
    }

    public Class<?> getWrappedClass() {
        return wrappedInstance.getClass();
    }
}
