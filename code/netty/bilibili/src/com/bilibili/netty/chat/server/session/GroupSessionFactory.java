package com.bilibili.netty.chat.server.session;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.server.session
 * @ClassName GroupSessionFactory
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/13-23:20
 * @Version 1.0
 */
public class GroupSessionFactory {
    public static GroupSession getGroupSession(){
        return new GroupSessionMemberImpl();
    }
}
