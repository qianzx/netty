package com.bilibili.netty.rpc.server;

import com.bilibili.netty.chat.message.RpcRequestMessage;
import com.bilibili.netty.chat.protocl.MessageCodecSharable;
import com.bilibili.netty.chat.protocl.ProcotolFrameDecoder;
import com.bilibili.netty.rpc.handler.RpcRequestMessageHandler;
import com.bilibili.netty.rpc.handler.RpcResponseMessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.rpc.server
 * @ClassName RpcServer
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/16-21:26
 * @Version 1.0
 */
@Slf4j
public class RpcServer {
    public static void main(String[] args) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        NioEventLoopGroup boss = new NioEventLoopGroup();
        LoggingHandler LOGGINGHANDLER = new LoggingHandler();
        MessageCodecSharable MESSAGECODECSHARABLE = new MessageCodecSharable();
        RpcRequestMessageHandler RPCR_EQUEST_MESSAGE_HANDLER = new RpcRequestMessageHandler();
        try {
            bootstrap.group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ProcotolFrameDecoder())
                                    .addLast(LOGGINGHANDLER)
                                    .addLast(MESSAGECODECSHARABLE)
                                    .addLast(RPCR_EQUEST_MESSAGE_HANDLER);
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(8080).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("server error{}",e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }
}
