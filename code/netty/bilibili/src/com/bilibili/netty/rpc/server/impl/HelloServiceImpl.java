package com.bilibili.netty.rpc.server.impl;

import com.bilibili.netty.rpc.server.HelloService;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.rpc.server.impl
 * @ClassName HelloServiceImpl
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/17-1:02
 * @Version 1.0
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String msg) {
        return "你好" + msg;
    }
}
