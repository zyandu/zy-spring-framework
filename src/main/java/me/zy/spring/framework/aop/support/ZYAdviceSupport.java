package me.zy.spring.framework.aop.support;

public class ZYAdviceSupport {
    private Class<?> targetClass;

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
    }
}
