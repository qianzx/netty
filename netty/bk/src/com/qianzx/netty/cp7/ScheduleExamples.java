package com.qianzx.netty.cp7;

import io.netty.channel.Channel;
import io.netty.channel.EventLoop;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-06-13 13:24:00
 */
public class ScheduleExamples {
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    public static void schedule(){
        ScheduledExecutorService executorService =
                Executors.newScheduledThreadPool(10);
        java.util.concurrent.ScheduledFuture<?> future = executorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("Now it is 60 seconds later");
            }
        }, 60, TimeUnit.SECONDS);
        executorService.shutdown();

    }

    public static void scheduleViaEventLoop(){
        EventLoop eventLoop = CHANNEL_FROM_SOMEWHERE.eventLoop();
        ScheduledFuture<?> future = eventLoop.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("Now it is 60 seconds later");
            }
        }, 60, TimeUnit.SECONDS);
    }

    public static void scheduleFixedViaEventLoop(){
        CHANNEL_FROM_SOMEWHERE.eventLoop().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Run every 60 seconds");
            }
        },60,60,TimeUnit.SECONDS);
    }

    public static void cancelingTaskUsingScheduledFuture(){
        ScheduledFuture<?> future = CHANNEL_FROM_SOMEWHERE.eventLoop().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Run every 60 seconds");
            }
        }, 60, 60, TimeUnit.SECONDS);
        boolean mayInterrupIfRunning = false;
        future.cancel(mayInterrupIfRunning);
    }
}

