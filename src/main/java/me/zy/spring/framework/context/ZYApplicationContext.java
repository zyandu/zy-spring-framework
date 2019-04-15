package me.zy.spring.framework.context;

import me.zy.spring.framework.beans.factory.ZYBeanFactory;
import me.zy.spring.framework.beans.factory.support.ZYDefaultListableBeanFactory;

/**
 * 底层实现
 */
public class ZYApplicationContext extends ZYDefaultListableBeanFactory implements ZYBeanFactory {
    @Override
    public Object getBean(String beanName) {
        return null;
    }

    @Override
    protected void refresh() {
        super.refresh();
    }
}
