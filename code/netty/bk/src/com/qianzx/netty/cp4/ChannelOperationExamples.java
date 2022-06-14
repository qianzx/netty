package com.qianzx.netty.cp4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-06-09 17:44:00
 */
@SuppressWarnings("ALL")
public class ChannelOperationExamples {
    private final static Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    public static void writingToChannel() {
        Channel channel = CHANNEL_FROM_SOMEWHERE;
        ByteBuf buf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8);
        ChannelFuture channelFuture = channel.write(buf);
        channel.flush();
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){
                    System.out.println("Write Success");
                }else {
                    System.out.println("Write error");
                    channelFuture.cause().printStackTrace();
                }
            }
        });
    }

    //Netty的Channel实现是线程安全的
    public static void writingToChannelFromManyThreads(){
        final Channel channel = CHANNEL_FROM_SOMEWHERE;
        final ByteBuf byteBuf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8);
        Runnable writer = new Runnable() {
            @Override
            public void run() {
                channel.write(byteBuf);
            }
        };

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(writer);
        executorService.execute(writer);
    }
}

