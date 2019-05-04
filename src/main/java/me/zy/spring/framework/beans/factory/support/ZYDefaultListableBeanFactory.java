package me.zy.spring.framework.beans.factory.support;

import me.zy.spring.framework.beans.factory.ZYBeanFactory;
import me.zy.spring.framework.beans.factory.config.ZYBeanDefinition;
import me.zy.spring.framework.context.support.ZYAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * BeanFactory默认实现
 */
public class ZYDefaultListableBeanFactory extends ZYAbstractApplicationContext implements ZYBeanFactory {
    /** Map of bean definition objects, keyed by bean name */
    //存储注册信息的BeanDefinition
    protected final Map<String, ZYBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, ZYBeanDefinition>();

    @Override
    protected void refresh() {
        super.refresh();
    }

    @Override
    public Object getBean(String beanName) {
        return null;
    }

    @Override
    public Object getBean(Class<?> beanClass) throws Exception {
        return null;
    }
}
