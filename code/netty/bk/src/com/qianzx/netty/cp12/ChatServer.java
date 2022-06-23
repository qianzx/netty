package com.qianzx.netty.cp12;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.net.InetSocketAddress;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-06-20 11:29:00
 */
public class ChatServer {
    private final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    private final EventLoopGroup group = new NioEventLoopGroup();
    private Channel channel;

    public ChannelFuture start(InetSocketAddress address){
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(createInitializer(channelGroup));
        ChannelFuture channelFuture = bootstrap.bind(address);
        channelFuture.syncUninterruptibly();
        channel = channelFuture.channel();
        return channelFuture;
    }

    public ChannelHandler createInitializer(ChannelGroup channelGroup) {
        return new ChatServerInitializer(channelGroup);
    }

    public void destory(){
        if(channel != null){
            channel.close();
        }
        channelGroup.close();
        group.shutdownGracefully();
    }

      public static void main(String[] args) throws Exception {
          final ChatServer chatServer = new ChatServer();
          ChannelFuture future = chatServer.start(new InetSocketAddress(9902));
          // ���� Java API, ��ν shutdown hook �����Ѿ���ʼ������δ��ʼִ�е��̶߳�����Runtime ע���
          // ���JVMҪֹͣǰ����Щ shutdown hook �㿪ʼִ�С�Ҳ��������ĳ������ǰ��ִ��һЩ��������������û���û�����ĳ���
          //��Щ shutdown hook ����Щ�̶߳�����ˣ����������Ҫд�� run() ����� Java API��
          // �������������̫���ˣ�Ҫ�������������Ȼ���Զ����ݿ���в���������������ȸ���ΰ��ա�
          //��������ر�JVM����ǿ��ɱ�����̵��������Ҫ������������һЩ��Դ���յ����⣬addShutdownHook�ķ���Ҳ������
          Runtime.getRuntime().addShutdownHook(new Thread(){
              @Override
              public void run(){
                  chatServer.destory();
              }
          });
          future.channel().closeFuture().syncUninterruptibly();
      }
}

