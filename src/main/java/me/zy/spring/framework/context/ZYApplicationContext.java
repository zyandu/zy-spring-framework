package me.zy.spring.framework.context;

import me.zy.spring.framework.beans.ZYBeanWrapper;
import me.zy.spring.framework.beans.factory.ZYBeanFactory;
import me.zy.spring.framework.annotation.ZYAutowired;
import me.zy.spring.framework.beans.factory.config.ZYBeanDefinition;
import me.zy.spring.framework.beans.factory.config.ZYBeanPostProcessor;
import me.zy.spring.framework.beans.factory.support.ZYBeanDefinitionReader;
import me.zy.spring.framework.beans.factory.support.ZYDefaultListableBeanFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 底层实现
 */
public class ZYApplicationContext extends ZYDefaultListableBeanFactory implements ZYBeanFactory {
    private String[] configLocations;
    private ZYBeanDefinitionReader reader;

    //单例IOC容器缓存
    private Map<String,Object> singletonObjects = new ConcurrentHashMap<>();
    //通用的IOC容器(单例、非单例)
    private Map<String,ZYBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>();

    public ZYApplicationContext(String... configLocations) {
        this.configLocations = configLocations;
        try{
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过类名获取实例bean
     * */
    @Override
    public Object getBean(Class<?> beanClass){
        return this.getBean(beanClass.getName());
    }

    @Override
    public Object getBean(String beanName) {
        ZYBeanDefinition zyBeanDefinition =this.beanDefinitionMap.get(beanName);
        Object instance = null;

        //这个逻辑还不严谨，自己可以去参考Spring源码
        //工厂模式 + 策略模式
        ZYBeanPostProcessor postProcessor = new ZYBeanPostProcessor();
        postProcessor.postProcessBeforeInitialization(instance,beanName);

        //模拟spring的doCreateBean方法
        //分两步
        //1.初始化bean
        instance = instantiateBean(beanName,this.beanDefinitionMap.get(beanName));
        ZYBeanWrapper zyBeanWrapper = new ZYBeanWrapper(instance);

        //2.拿到beanWrapper之后，保存到IOC容器中
        this.factoryBeanInstanceCache.put(beanName,zyBeanWrapper);

        postProcessor.postProcessAfterInitialization(instance,beanName);

        //3.注入
        populateBean(beanName,new ZYBeanDefinition(),zyBeanWrapper);

        return this.factoryBeanInstanceCache.get(beanName).getWrappedInstance();

    }

    private ZYBeanWrapper instantiateBean(String beanName,ZYBeanDefinition beanDefinition) {
        //1.获取类名
        String className = beanDefinition.getBeanName();

        //2.反射创建对象

        //singletonObjects
        //factoryBeanInstanceCache
        Object instance = null;
        try {
            //默认为单例
            if(singletonObjects.containsKey(className)){
                instance = this.singletonObjects.get(className);
            }else{
                instance = Class.forName(className).newInstance();
                this.singletonObjects.put(className,instance);
                this.singletonObjects.put(beanDefinition.getFactoryBeanName(),instance);
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //3.把这个对象封装到BeanWrapper中去
        ZYBeanWrapper beanWrapper = new ZYBeanWrapper(instance);
        return beanWrapper;
    }

    private void populateBean(String beanName, ZYBeanDefinition beanDefinition, ZYBeanWrapper bw) {
        Object instance = bw.getWrappedInstance();

        //加了注解的类，才执行依赖注入
        Class<?> clazz = bw.getWrappedClass();
        //if(!(clazz.isAnnotationPresent(ZYController.class)
        //    || clazz.isAnnotationPresent(ZYService.class))){
        //    return;
        //}

        Field[] fields = clazz.getDeclaredFields();
        for (Field field:fields) {
            if(!field.isAnnotationPresent(ZYAutowired.class)){
                continue;
            }

            ZYAutowired autowired =  field.getAnnotation(ZYAutowired.class);
            String autowiredName = autowired.value().trim();
            if("".equals(autowiredName)){
                autowiredName = field.getType().getName();
            }
            field.setAccessible(true);

            try {
                Object wrappedInstance = this.factoryBeanInstanceCache.get(autowiredName).getWrappedInstance();
                if(null == wrappedInstance){
                    continue;
                }
                field.set(instance,wrappedInstance);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void refresh() {
        //1.定位：定位配置文件
        reader = new ZYBeanDefinitionReader(this.configLocations);

        //2.加载：加载配置文件，扫描配置的类，封装到BeanDefinition
        List<ZYBeanDefinition> definitionList = null;
        try {
            definitionList = reader.loadBeanDefinitions(configLocations);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //3.注册：把配置相关信息放到容器里面(伪IOC容器)
        doRegistereBeanDefinition(definitionList);

        //4.初始化非延迟加载的类
        doAutowired();
    }

    //处理非延迟加载的类
    private void doAutowired() {
        for (Map.Entry<String, ZYBeanDefinition> zyBeanDefinitionEntry : super.beanDefinitionMap.entrySet()) {
            String beanName = zyBeanDefinitionEntry.getKey();
            if(!zyBeanDefinitionEntry.getValue().isLazyInit()){
                getBean(beanName);
            }else {
                continue;
            }
        }

    }

    private void doRegistereBeanDefinition(List<ZYBeanDefinition> definitionList){
        for (int i = 0; i < definitionList.size(); i++) {
            super.beanDefinitionMap.put(definitionList.get(i).getFactoryBeanName(),definitionList.get(i));
        }

    }

    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new  String[this.beanDefinitionMap.size()]);
    }

    public Properties getConfig(){
        return this.reader.getConfig();
    }
}
