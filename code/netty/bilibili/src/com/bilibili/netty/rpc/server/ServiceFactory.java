package com.bilibili.netty.rpc.server;

import com.bilibili.netty.rpc.config.Config;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.rpc.server
 * @ClassName ServiceFactory
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/16-23:07
 * @Version 1.0
 */
public class ServiceFactory {
    static Properties properties;
    static Map<Class<?>,Object> map = new ConcurrentHashMap<>();

    static {
        try(InputStream resourceAsStream = Config.class.getResourceAsStream("/application.properties")) {
            properties = new Properties();
            properties.load(resourceAsStream);
            Set<String> names = properties.stringPropertyNames();
            for(String name:names){
                if(name.endsWith("Service")){
                    Class<?> interfaceClass = Class.forName(name);
                    Class<?> instanceClass = Class.forName(properties.getProperty(name));
                    map.put(interfaceClass,instanceClass.newInstance());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T getService(Class<T> interfaceClass){
        return (T) map.get(interfaceClass);
    }
}
