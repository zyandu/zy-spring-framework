package me.zy.spring.framework.test;

import me.zy.spring.framework.context.ZYApplicationContext;

public class TestIocDI {
    public static void main(String[] args) {
        ZYApplicationContext applicationContext = new ZYApplicationContext("classpath:appliationContext.properties");

        System.out.println(applicationContext.getBean("demo"));
        //System.out.println(applicationContext.getBean("Demo.class"));
        System.out.println(applicationContext);
    }
}
