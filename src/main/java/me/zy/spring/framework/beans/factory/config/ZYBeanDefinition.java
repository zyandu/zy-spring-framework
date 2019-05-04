package me.zy.spring.framework.beans.factory.config;
import lombok.Data;

@Data
public class ZYBeanDefinition {
    private String beanName;
    private boolean lazyInit = false;
    private String factoryBeanName;
    private boolean isSingleton = true;//默认是单例
}
