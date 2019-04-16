package me.zy.spring.framework.beans.factory.support;

import me.zy.spring.framework.beans.factory.config.ZYBeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;

public class ZYBeanDefinitionReader {
    private Properties config = new Properties();
    private List<String> registerClasses;
    private final String SCAN_BASEPACKAGE = "scan.basepackage";

    public ZYBeanDefinitionReader(String... locations){
        //通过url定位找到所对应的文件，转换为文件流
        InputStream is = this.getClass().getClassLoader().
                getResourceAsStream(locations[0].replaceAll("/+","/"));
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
        URL url = this.getClass().getClassLoader().
                getResource("/"+basePackage.replaceAll("\\.","/"));
        File classpath = new File(url.getFile());
        for (File file:classpath.listFiles()){
            if(file.isDirectory()){
                doScanner(basePackage+"."+file.getName());
            }else{
                if(!file.getName().endsWith(".class")){
                    continue;
                }else{
                    String className=(basePackage+"."+file.getName().replace(".class",""));
                    registerClasses.add(className);
                }
            }

        }

    }

    public List<ZYBeanDefinition> loadBeanDefinitions(String... locations){
        for(String className:registerClasses){
            try {
                Class<?> clazz = Class.forName(className);
                //如果是接口，用它的实现类作为ClassName
                if(clazz.isInterface()){

                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    public Properties getConfig() {
        return config;
    }
}
