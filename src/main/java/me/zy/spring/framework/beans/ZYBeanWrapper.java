package me.zy.spring.framework.beans;

public class ZYBeanWrapper {
    private Object wrapperInstance;
    private Class<?> wrapperClass;

    public ZYBeanWrapper(Object wrapperInstance) {
        this.wrapperInstance = wrapperInstance;
    }

    /**
     * Return the bean instance wrapped by this object.
     */
    public Object getWrappedInstance(){
        return this.wrapperInstance;
    }

    /**
     * 返回代理后的Class，可能是$Proxy0
     */
    public Class<?> getWrappedClass(){
        return this.wrapperInstance.getClass();
    }

}
