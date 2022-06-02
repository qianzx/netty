package com.qianzx.netty.server;

import com.qianzx.netty.server.handler.EchoServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description 为什么客户端用的是SimpleChannelInboundHandler ，而不是?Echo-ServerHandler 中??用的
 * ChannelHandlerAdapter？这和两个因素的相互作用有关：业务逻辑如何理消息以及Netty如何管理资源。
 * 客户端当messageReceived 方法完成时，消息已处理完毕。方法返回时，
 * SimpleChannelInboundHandler 负责释放指向保存该消息的ByteBuf的内存?引用。
 * EchoServerHandler中?仍然需要将传入消息发送给发送者，而write() 操作是异步的，直到channelRead() 返回后
 * 可能仍然没有完成。为此，EchoServerHandler 扩展了ChannelInboundHandlerAdapter，其在这个时间
 * 点上不会释放消息。
 * 消息在EchoServerHandler的channelReadComplete()方法中，当writeAndFlush()方法被调用时被释放
 * @createTime 2022-05-31 17:04:00
 */
public class EchoServer {
    private int port;

    public EchoServer(int port) {
        this.port = port;
    }


    public void start() throws Exception{
        EchoServerHandler serverHandler = new EchoServerHandler();
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        try{
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(nioEventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                //当一个新连接被接收时，一个新的子Channel将会被创建
                //ChannelInitializer将会把一个Handler实例添加到该Channel
                //的ChannelPipeline中。这个Handler将会收到入站消息的通知
                .childHandler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception{
                        ch.pipeline().addLast(serverHandler);
                    }
                });
        ChannelFuture future = serverBootstrap.bind().sync();
        future.channel().closeFuture().sync();
    }finally {
            nioEventLoopGroup.shutdownGracefully().sync();
        }
   }
   public static void main(String[] args) throws Exception {
       EchoServer echoServer = new EchoServer(9999);
       echoServer.start();
   }
}

