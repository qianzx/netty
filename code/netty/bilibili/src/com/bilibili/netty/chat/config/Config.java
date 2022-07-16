package com.bilibili.netty.chat.config;

import com.bilibili.netty.chat.protocl.Serializer;

import java.io.InputStream;
import java.util.Properties;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.config
 * @ClassName Config
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/16-12:24
 * @Version 1.0
 */
public class Config {
    static Properties properties;
    static {
        try (InputStream resourceAsStream = Config.class.getResourceAsStream("/application.properties")){
            properties = new Properties();
            properties.load(resourceAsStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Serializer.Algorithm getSerializerAlgorithm(){
        String value = properties.getProperty("serializer.algorithm");
        if(value == null){
            return Serializer.Algorithm.JAVA;
        }else {
            return Serializer.Algorithm.valueOf(value);
        }
    }

    public static int getServerPort(){
        String property = properties.getProperty("server.port");
        if(property == null){
            return 8080;
        }else {
            return Integer.parseInt(property);
        }
    }
}
