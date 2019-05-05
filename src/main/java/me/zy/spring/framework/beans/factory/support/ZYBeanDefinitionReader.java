package me.zy.spring.framework.beans.factory.support;

import me.zy.spring.framework.beans.factory.config.ZYBeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ZYBeanDefinitionReader {
    private Properties config = new Properties();
    private List<String> registerBeanClasses = new ArrayList<>();
    private final String SCAN_BASEPACKAGE = "scan.basepackage";

    public ZYBeanDefinitionReader(String... locations){
        //通过url定位找到所对应的文件，转换为文件流
        InputStream is = this.getClass().getClassLoader().
                getResourceAsStream(locations[0].replace("classpath:",""));
        try {
            config.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        doScanner(config.getProperty(SCAN_BASEPACKAGE));
    }

    //根据配置扫描相应的包路径下的类
    private void doScanner(String basePackage) {
        //URL url = this.getClass().getClassLoader().
        //        getResource("/"+basePackage.replaceAll("\\.","/"));
        URL url = this.getClass().getResource("/"+basePackage.replaceAll("\\.","/"));
        File classpath = new File(url.getFile());
        for (File file:classpath.listFiles()){
            if(file.isDirectory()){
                doScanner(basePackage+"."+file.getName());
            }else{
                if(!file.getName().endsWith(".class")){
                    continue;
                }else{
                    String className=(basePackage+"."+file.getName().replace(".class",""));
                    registerBeanClasses.add(className);
                }
            }

        }

    }

    /**
     * 把配置文件扫描到的所有的配置信息转换为ZYBeanDefinition
     * 方便后续IOC容器使用
     * @return
     */
    public List<ZYBeanDefinition> loadBeanDefinitions(String... configurations) throws ClassNotFoundException {
        List<ZYBeanDefinition> zyBeanDefinitions = new ArrayList<ZYBeanDefinition>();
        for(String beanClassName: registerBeanClasses){
            Class<?> beanClass = Class.forName(beanClassName);
            if(beanClass.isInterface())
                continue;

            //beanName有三种情况:
            //1、默认是类名首字母小写
            //2、自定义名字
            //3、接口注入
            zyBeanDefinitions.add(doCreateBeanDefinition(toFirstCharLowerCase(beanClass.getSimpleName()),
                beanClass.getName()));

            Class<?>[] interfaces = beanClass.getInterfaces();
            for(Class<?> i:interfaces){
                //如果是多个实现类，只能覆盖
                //为什么？因为Spring没那么智能
                //这个时候，可以自定义名字
                zyBeanDefinitions.add(doCreateBeanDefinition(i.getName(),beanClass.getName()));
            }

        }
        return zyBeanDefinitions;
    }

    private ZYBeanDefinition doCreateBeanDefinition(String factoryBeanName,String beanClassName){
        ZYBeanDefinition definition = new ZYBeanDefinition();
        definition.setBeanName(beanClassName);
        definition.setFactoryBeanName(factoryBeanName);
        return definition;
    }

    /**
     * 字符串首字母转为小写字母
     * @param targetString
     * @return
     */
    private String toFirstCharLowerCase(String targetString){
        if(targetString!= null && targetString!=""){
            char[] charArray = targetString.toCharArray();
            //大小写字母ASCII码值相差32
            //大写字母ASCII要小于小写字母
            //对字符串字母进行大小写转换，其实就是对字母的ASCII值进行计算
            charArray[0] +=32;
            return String.valueOf(charArray);
        }else{
            return null;
        }
    }


    public Properties getConfig() {
        return config;
    }

    public static void main(String[] args) {
        ZYBeanDefinition bd = new ZYBeanDefinition();
        System.out.println(bd.getClass().getSimpleName());
        System.out.println(bd.getClass().getName());
    }
}
