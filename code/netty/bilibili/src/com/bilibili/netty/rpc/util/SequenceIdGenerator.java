package com.bilibili.netty.rpc.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.rpc.util
 * @ClassName SequenceIdGenerator
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/17-11:55
 * @Version 1.0
 */
public class SequenceIdGenerator {
    private static final AtomicInteger id = new AtomicInteger();

    public static int next(){
        return id.incrementAndGet();
    }
}
