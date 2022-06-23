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
          // 根据 Java API, 所谓 shutdown hook 就是已经初始化但尚未开始执行的线程对象。在Runtime 注册后，
          // 如果JVM要停止前，这些 shutdown hook 便开始执行。也就是在你的程序结束前，执行一些清理工作，尤其是没有用户界面的程序。
          //这些 shutdown hook 都是些线程对象，因此，你的清理工作要写在 run() 里。根据 Java API，
          // 你的清理工作不能太重了，要尽快结束。但仍然可以对数据库进行操作。问题是这个度该如何把握。
          //对于意外关闭JVM或者强制杀死进程的情况，想要清理垃圾或者一些资源回收的问题，addShutdownHook的方法也不管用
          Runtime.getRuntime().addShutdownHook(new Thread(){
              @Override
              public void run(){
                  chatServer.destory();
              }
          });
          future.channel().closeFuture().syncUninterruptibly();
      }
}

