package com.bilibili.netty.cp5;

import com.qianzx.netty.cp13.LogEventDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;

import java.net.SocketAddress;
import java.nio.charset.Charset;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.cp5
 * @ClassName TestRedis
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/7-22:32
 * @Version 1.0
 */
public class TestRedis {
    private static final byte[] LINE = {13,10};

    public static void main(String[] args) {
        NioEventLoopGroup work = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(work)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    ByteBuf buffer = ctx.alloc().buffer();
                                    buffer.writeBytes("*3".getBytes(CharsetUtil.UTF_8));
                                    buffer.writeBytes(LINE);
                                    buffer.writeBytes("$3".getBytes(CharsetUtil.UTF_8));
                                    buffer.writeBytes(LINE);
                                    buffer.writeBytes("set".getBytes(CharsetUtil.UTF_8));
                                    buffer.writeBytes(LINE);
                                    buffer.writeBytes("$4".getBytes(CharsetUtil.UTF_8));
                                    buffer.writeBytes(LINE);
                                    buffer.writeBytes("name".getBytes(CharsetUtil.UTF_8));
                                    buffer.writeBytes(LINE);
                                    buffer.writeBytes("$8".getBytes(CharsetUtil.UTF_8));
                                    buffer.writeBytes(LINE);
                                    buffer.writeBytes("zhangsan".getBytes(CharsetUtil.UTF_8));
                                    buffer.writeBytes(LINE);

                                    ctx.writeAndFlush(buffer);
                                }

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    ByteBuf byteBuf = (ByteBuf) msg;
                                    System.out.println(byteBuf.toString(CharsetUtil.UTF_8));
                                    ctx.fireChannelRead(msg);
                                }


                            });

                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect("192.168.200.130", 6379).sync();
            System.out.println(333);

            //阻塞获取通道关闭时将通知的ChannelFuture,直到channel.close()被调用
            ChannelFuture channelFuture1 = channelFuture.channel().closeFuture().sync();
         /*   channelFuture.channel().closeFuture().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    System.out.println(111);
                }
            });*/
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
           work.shutdownGracefully();
        }
    }
}
