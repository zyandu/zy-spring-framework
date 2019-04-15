package me.zy.spring.framework.beans.factory;

public interface ZYBeanFactory {
    /**
     * 根据beanName从IOC容器中获取实例bean
     * 顶层设计规范
     * @param beanName
     * @return
     */
    Object getBean(String beanName);
}
