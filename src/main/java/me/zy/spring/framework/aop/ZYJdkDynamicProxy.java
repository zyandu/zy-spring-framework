package me.zy.spring.framework.aop;

import me.zy.spring.framework.aop.support.ZYAdviceSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ZYJdkDynamicProxy implements ZYAopProxy, InvocationHandler {
    private ZYAdviceSupport zyAdviceSupport;

    public ZYJdkDynamicProxy(ZYAdviceSupport zyAdviceSupport) {
        this.zyAdviceSupport = zyAdviceSupport;
    }

    @Override
    public Object getProxy() {
        return this.getProxy(this.zyAdviceSupport.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader,this.zyAdviceSupport.getTargetClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
