package com.bilibili.netty.cp5;

import com.qianzx.netty.cp12.HttpRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.cp5
 * @ClassName HttpTest
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/10-10:45
 * @Version 1.0
 */
public class HttpTest {
    public static void main(String[] args) throws InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup work = new NioEventLoopGroup();
        NioEventLoopGroup boss = new NioEventLoopGroup();

        try {
            serverBootstrap.group(boss,work)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                            ch.pipeline().addLast(new HttpServerCodec());
                            //只处理指定类型的DefaultHttpRequest消息
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<DefaultHttpRequest>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, DefaultHttpRequest msg) throws Exception {
                                    DefaultFullHttpResponse response = new DefaultFullHttpResponse(msg.protocolVersion(), HttpResponseStatus.OK);
                                    byte[] bytes = "<h1>Hello world</h1>".getBytes();
                                    response.content().writeBytes(bytes);
                                    //指定内容长度否则游览器会认为数据未传输完一直转圈等待
                                    response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH,bytes.length);
                                    ctx.writeAndFlush(response);

                                }
                            });
                          /*  ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    if(msg instanceof DefaultHttpRequest){

                                    }else if(msg instanceof LastHttpContent){

                                    }else {

                                    }
                                }
                            });*/
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(8888).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            work.shutdownGracefully();
            boss.shutdownGracefully();
        }


    }
}
