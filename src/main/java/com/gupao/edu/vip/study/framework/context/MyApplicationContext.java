package com.gupao.edu.vip.study.framework.context;

import com.gupao.edu.vip.study.framework.annotation.MyAutowired;
import com.gupao.edu.vip.study.framework.annotation.MyController;
import com.gupao.edu.vip.study.framework.annotation.MyService;
import com.gupao.edu.vip.study.framework.beans.MyBeanWrapper;
import com.gupao.edu.vip.study.framework.beans.config.MyBeanDefinition;
import com.gupao.edu.vip.study.framework.beans.config.MyBeanPostProcessor;
import com.gupao.edu.vip.study.framework.beans.support.MyBeanDefinitionReader;
import com.gupao.edu.vip.study.framework.beans.support.MyDefaultListableBeanFactory;
import com.gupao.edu.vip.study.framework.core.MyBeanFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yiran
 */
public class MyApplicationContext extends MyDefaultListableBeanFactory implements MyBeanFactory {

    private String[] configLocations;

    private MyBeanDefinitionReader reader;

    private Map<String,Object> singleObjects = new ConcurrentHashMap<>();

    private Map<String, MyBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>();

    public MyApplicationContext(String... configLocations) {
        this.configLocations = configLocations;
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void refresh() throws Exception {
        reader = new MyBeanDefinitionReader(this.configLocations);

        List<MyBeanDefinition> beanDefinitions = reader.loadBeanDefinitions();
        
        doRegisterBeanDefinition(beanDefinitions);

        doAutowired();
    }

    private void doAutowired() {
        for (Map.Entry<String,MyBeanDefinition> beanDefinitionEntry : super.beanDefinitionMap.entrySet()){
            String beanName = beanDefinitionEntry.getKey();
            if (!beanDefinitionEntry.getValue().isLazyInit()) {
                try {
                    getBean(beanName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void doRegisterBeanDefinition(List<MyBeanDefinition> beanDefinitions) throws Exception {
        for (MyBeanDefinition beanDefinition : beanDefinitions) {
            if (super.beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())){
                throw new Exception("The " + beanDefinition.getFactoryBeanName() + " is exist");
            }
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(),beanDefinition);
        }
    }

    @Override
    public Object getBean(String beanName) throws Exception {
        MyBeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        Object instance = null;

        MyBeanPostProcessor postProcessor = new MyBeanPostProcessor();

        postProcessor.postProcessBeforeInitialization(instance, beanName);

        instance = instantiateBean(beanName, beanDefinition);

        MyBeanWrapper beanWrapper = new MyBeanWrapper(instance);
        
        factoryBeanInstanceCache.put(beanName, beanWrapper);
        
        postProcessor.postProcessAfterInitialization(instance, beanName);
        
        populateBean(beanName,beanDefinition,beanWrapper);
        return factoryBeanInstanceCache.get(beanName).getWrappedInstance();
    }

    private void populateBean(String beanName, MyBeanDefinition beanDefinition, MyBeanWrapper beanWrapper) {
        Object instance = beanWrapper.getWrappedInstance();
        Class<?> clazz = beanWrapper.getWrappedClass();

        if (!(clazz.isAnnotationPresent(MyController.class)||clazz.isAnnotationPresent(MyService.class))){
            return;
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(MyAutowired.class)){
                continue;
            }
            MyAutowired autowired = field.getAnnotation(MyAutowired.class);
            String autowiredBeanName = autowired.value().trim();
            if ("".equals(autowiredBeanName)) {
                autowiredBeanName = field.getType().getName();
            }

            field.setAccessible(true);
            MyBeanWrapper fieldWrapper = this.factoryBeanInstanceCache.get(autowiredBeanName);
            if (fieldWrapper == null){
                continue;
            }
            try {
                field.set(instance,fieldWrapper.getWrappedInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private Object instantiateBean(String beanName, MyBeanDefinition beanDefinition) {
        String className = beanDefinition.getBeanClassName();
        
        Object instance = null;
        try{
            if (singleObjects.containsKey(className)) {
                instance = this.singleObjects.get(className);
            }else {
                Class<?> clazz = Class.forName(className);
                instance = clazz.newInstance();
                this.singleObjects.put(className,instance);
                this.singleObjects.put(beanName,instance);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
        
    }

    @Override
    public Object getBean(Class<?> clazz) throws Exception {
        return getBean(clazz.getName());
    }

    public String[] getBeanDefinitionNames() {
        return super.beanDefinitionMap.keySet().toArray(new String[beanDefinitionMap.size()]);
    }

    public int getBeanDefinitionCount(){
        return super.beanDefinitionMap.size();
    }

    public Properties getConfig() {
        return this.reader.getConfig();
    }
}
