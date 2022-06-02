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
 * @Description Ϊʲô�ͻ����õ���SimpleChannelInboundHandler ��������?Echo-ServerHandler ��??�õ�
 * ChannelHandlerAdapter������������ص��໥�����йأ�ҵ���߼��������Ϣ�Լ�Netty��ι�����Դ��
 * �ͻ��˵�messageReceived �������ʱ����Ϣ�Ѵ�����ϡ���������ʱ��
 * SimpleChannelInboundHandler �����ͷ�ָ�򱣴����Ϣ��ByteBuf���ڴ�?���á�
 * EchoServerHandler��?��Ȼ��Ҫ��������Ϣ���͸������ߣ���write() �������첽�ģ�ֱ��channelRead() ���غ�
 * ������Ȼû����ɡ�Ϊ�ˣ�EchoServerHandler ��չ��ChannelInboundHandlerAdapter���������ʱ��
 * ���ϲ����ͷ���Ϣ��
 * ��Ϣ��EchoServerHandler��channelReadComplete()�����У���writeAndFlush()����������ʱ���ͷ�
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
                //��һ�������ӱ�����ʱ��һ���µ���Channel���ᱻ����
                //ChannelInitializer�����һ��Handlerʵ����ӵ���Channel
                //��ChannelPipeline�С����Handler�����յ���վ��Ϣ��֪ͨ
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

