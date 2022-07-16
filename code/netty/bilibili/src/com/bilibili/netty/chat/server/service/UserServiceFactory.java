package com.bilibili.netty.chat.server.service;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.server.service
 * @ClassName UserServiceFactory
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/12-22:53
 * @Version 1.0
 */
public class UserServiceFactory {

    public static UserService getUserService(){
        return new UserServiceMemoryImpl();
    }
}
