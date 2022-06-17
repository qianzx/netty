package com.qianzx.netty.cp11;

import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.marshalling.CompatibleMarshallingEncoder;
import io.netty.handler.codec.serialization.CompatibleObjectEncoder;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-06-17 11:00:00
 */
public class FileRegionWriteHandler extends ChannelInboundHandlerAdapter {
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();
    private static final File FILE_FROM_SOMEWHERE = new File("");

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        File file = FILE_FROM_SOMEWHERE;
        Channel channel = CHANNEL_FROM_SOMEWHERE;
        FileInputStream fileInputStream = new FileInputStream(file);
        DefaultFileRegion defaultFileRegion = new DefaultFileRegion(fileInputStream.getChannel(), 0, file.length());
        ChannelFuture channelFuture = channel.writeAndFlush(defaultFileRegion);
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(!future.isSuccess()){
                    Throwable cause = future.cause();
                }
            }
        });
    }
}

