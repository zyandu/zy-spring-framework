package me.zy.spring.framework.context;

import jdk.internal.jline.internal.Nullable;
import me.zy.spring.framework.beans.ZYBeanWrapper;
import me.zy.spring.framework.beans.factory.ZYBeanFactory;
import me.zy.spring.framework.beans.factory.config.ZYBeanDefinition;
import me.zy.spring.framework.beans.factory.support.ZYBeanDefinitionReader;
import me.zy.spring.framework.beans.factory.support.ZYDefaultListableBeanFactory;

import java.util.List;
import java.util.Map;

/**
 * 底层实现
 */
public class ZYApplicationContext extends ZYDefaultListableBeanFactory implements ZYBeanFactory {
    private String[] configLocations;
    private ZYBeanDefinitionReader reader;

    @Override
    public Object getBean(String beanName) {
        //模拟spring的doCreateBean方法
        //分两步
        //1.初始化bean
        instantiateBean(beanName,new ZYBeanDefinition());
        //2.注入
        populateBean(beanName,new ZYBeanDefinition(),new ZYBeanWrapper());

        //TODO
        return null;

    }

    private void populateBean(String beanName, ZYBeanDefinition beanDefinition, ZYBeanWrapper bw) {
    }

    private void instantiateBean(String beanName,ZYBeanDefinition beanDefinition) {

    }

    @Override
    protected void refresh() {
        //1.定位：定位配置文件
        reader = new ZYBeanDefinitionReader();

        //2.加载：加载配置文件，扫描配置的类，封装到BeanDefinition
        List<ZYBeanDefinition> definitionList = reader.loadBeanDefinitions(configLocations);

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
}
