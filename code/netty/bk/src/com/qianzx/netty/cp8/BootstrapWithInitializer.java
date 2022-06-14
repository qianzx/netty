package com.qianzx.netty.cp8;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-06-13 16:42:00
 */
public class BootstrapWithInitializer {
    public void bootstrap() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup groupP = new NioEventLoopGroup();
        NioEventLoopGroup groupC = new NioEventLoopGroup();
        bootstrap.group(groupP,groupC)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializerImpl());
        ChannelFuture future = bootstrap.bind(9999);
        future.sync();
    }

    final class ChannelInitializerImpl extends ChannelInitializer<Channel> {
        @Override
        protected void initChannel(Channel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(new HttpClientCodec());
            pipeline.addLast(new HttpObjectAggregator(Integer.MAX_VALUE));

        }
    }
}

