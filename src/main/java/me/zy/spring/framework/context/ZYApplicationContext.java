package me.zy.spring.framework.context;

import me.zy.spring.framework.beans.factory.ZYBeanFactory;
import me.zy.spring.framework.beans.factory.config.ZYBeanDefinition;
import me.zy.spring.framework.beans.factory.support.ZYBeanDefinitionReader;
import me.zy.spring.framework.beans.factory.support.ZYDefaultListableBeanFactory;

import java.util.List;

/**
 * 底层实现
 */
public class ZYApplicationContext extends ZYDefaultListableBeanFactory implements ZYBeanFactory {
    private String[] configLocations;
    private ZYBeanDefinitionReader reader;

    @Override
    public Object getBean(String beanName) {
        ZYBeanDefinition beanDefinition
        return null;
    }

    @Override
    protected void refresh() {
        //1.定位：定位配置文件
        reader = new ZYBeanDefinitionReader();

        //2.加载：加载配置文件，扫描配置的类，封装到BeanDefinition
        List<ZYBeanDefinition> definitionList = reader.loadBeanDefinitions(configLocations)

        //3.注册：把配置相关信息放到容器里面(伪IOC容器)
        doRegistereBeanDefinition(definitionList);

        //4.初始化非延迟加载的类

    }

    private void doRegistereBeanDefinition(List<ZYBeanDefinition> definitionList){

    }
}
