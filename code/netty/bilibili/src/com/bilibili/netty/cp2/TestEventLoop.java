package com.bilibili.netty.cp2;

import io.netty.channel.DefaultEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.NettyRuntime;

import java.util.concurrent.TimeUnit;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.cp2
 * @ClassName TestEventLoop
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/3-13:10
 * @Version 1.0
 */
public class TestEventLoop {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup(4);
        //DefaultEventLoop eventExecutors1 = new DefaultEventLoop();
        /*System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());*/

        group.next().execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                System.out.println("ok");
            }
        });
        group.next().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                System.out.println("ok2");
            }
        },0,1, TimeUnit.SECONDS);
        System.out.println(Thread.currentThread().getName());


    }
}
