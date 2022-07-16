package com.bilibili.netty.chat.server.session;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.server.session
 * @ClassName SessionFactory
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/13-21:41
 * @Version 1.0
 */
public class SessionFactory {
    public static Session getSession(){
        return new SessionMemoryImpl();
    }
}
