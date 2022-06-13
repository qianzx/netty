package com.qianzx.netty.cp8;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-06-13 14:44:00
 */
public class BootstrapClientWithOptionsAndAttrs {
    public void bootstrap(){
        final AttributeKey<String> id = AttributeKey.newInstance("ID");
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println("Received data");
                    }

                    @Override
                    public void channelRegistered(ChannelHandlerContext ctx)
                            throws Exception {
                        String idValue = ctx.channel().attr(id).get();
                        System.out.println(idValue);

                        // do something with the idValue
                    }
                });
        bootstrap.option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000);
        bootstrap.attr(id,"kjasdfhkasdfsdklajfhl");
        ChannelFuture future = bootstrap.connect("www.baidu.com", 9090);
        future.syncUninterruptibly();
    }
      public static void main(String[] args){
          BootstrapClientWithOptionsAndAttrs client = new BootstrapClientWithOptionsAndAttrs();
          client.bootstrap();
      }
}

