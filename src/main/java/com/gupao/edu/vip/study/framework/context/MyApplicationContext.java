package com.gupao.edu.vip.study.framework.context;

import com.gupao.edu.vip.study.framework.beans.MyBeanWrapper;
import com.gupao.edu.vip.study.framework.beans.config.MyBeanDefinition;
import com.gupao.edu.vip.study.framework.beans.support.MyBeanDefinitionReader;
import com.gupao.edu.vip.study.framework.beans.support.MyDefaultListableBeanFactory;
import com.gupao.edu.vip.study.framework.core.MyBeanFactory;

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
        return null;
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
