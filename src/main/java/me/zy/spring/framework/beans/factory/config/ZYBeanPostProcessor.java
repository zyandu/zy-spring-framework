package me.zy.spring.framework.beans.factory.config;

import jdk.internal.jline.internal.Nullable;

/**
 * Spring中此类是接口
 */
public class ZYBeanPostProcessor {//为在Bean的初始化前提供回调入口
    //为在Bean的初始化前提供回调入口
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }


    //为在Bean的初始化之后提供回调入口
    @Nullable
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }

}
