package com.gupao.edu.vip.study.framework.beans.support;

import com.gupao.edu.vip.study.framework.beans.config.MyBeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author yiran
 */
public class MyBeanDefinitionReader {

    private List<String> registryBeanClasses = new ArrayList<>();

    private Properties config = new Properties();

    private final String SCAN_PACKAGE = "scanPackage";

    public MyBeanDefinitionReader(String... locations) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(locations[0].replace("classpath:",""));
        try {
            config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        doScanner(config.getProperty(SCAN_PACKAGE));
    }

    private void doScanner(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.","/"));
        if (null == url){return;}
        File classPath = new File(url.getFile());
        for (File file : classPath.listFiles()) {
            if (file.isDirectory()) {
                doScanner(scanPackage + "." +file.getName());
            }else {
                if (file.getName().endsWith(".class")) {
                    String className = scanPackage + "." + file.getName().replace(".class","");
                    registryBeanClasses.add(className);
                }
            }
        }
    }
    public List<MyBeanDefinition> loadBeanDefinitions(){
        List<MyBeanDefinition> result = new ArrayList<>();
        try{
            for (String className : registryBeanClasses) {
                Class<?> beanClass = Class.forName(className);
                if (beanClass.isInterface()) {
                    continue;
                }
                result.add(doCreateBeanDefinition(toLowerFirstCase(beanClass.getSimpleName()),beanClass.getName()));

                Class<?> [] interfaces = beanClass.getInterfaces();
                for (Class<?> i : interfaces) {
                    result.add(doCreateBeanDefinition(i.getName(),beanClass.getName()));
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private MyBeanDefinition doCreateBeanDefinition(String simpleName, String name) {
        MyBeanDefinition beanDefinition = new MyBeanDefinition();
        beanDefinition.setBeanClassName(simpleName);
        beanDefinition.setFactoryBeanName(name);
        return beanDefinition;
    }

    private String toLowerFirstCase(String simpleName){
        char[] chars = simpleName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    public Properties getConfig(){
        return this.config;
    }
}
