package me.zy.spring.framework.aop;

public interface ZYAopProxy {

    Object getProxy();


    Object getProxy(ClassLoader classLoader);
}
