package com.bilibili.netty.chat.server;

import com.bilibili.netty.chat.handler.*;
import com.bilibili.netty.chat.protocl.MessageCodecSharable;
import com.bilibili.netty.chat.protocl.ProcotolFrameDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.server
 * @ClassName ChatServer
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/11-21:33
 * @Version 1.0
 */
@Slf4j
public class ChatServer {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        LoginRequestMessageHandler LOGIN_HANDLER = new LoginRequestMessageHandler();
        ChatRequestMessageHandler CHAT_HANDLER = new ChatRequestMessageHandler();
        GroupCreateRequestMessageHandler GROUP_CREATE_HANDLER = new GroupCreateRequestMessageHandler();
        GroupJoinRequestMessageHandler GROUP_JOIN_HANDLER = new GroupJoinRequestMessageHandler();
        GroupMembersRequestMessageHandler GROUP_MEMBERS_HANDLER = new GroupMembersRequestMessageHandler();
        GroupQuitRequestMessageHandler GROUP_QIUT_HANDLER = new GroupQuitRequestMessageHandler();
        GroupChatRequestMessageHandler GROUP_CHAT_HANDLER = new GroupChatRequestMessageHandler();
        QuitHandler QUIT_HANDLER = new QuitHandler();

        try {
            Channel channel = bootstrap.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //用来判断是否读写空闲时间过长，处理假死。超出指定时间会触发相应的事件
                            pipeline.addLast(new IdleStateHandler(10,0,0))
                                //用来处理IdleStateHandler触发的事件需要一个入站出站处理器
                                            .addLast(new ChannelDuplexHandler(){
                                                //用来处理特殊事件
                                                @Override
                                                public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                                    IdleStateEvent event = (IdleStateEvent) evt;
                                                    if(event.state() == IdleState.READER_IDLE){
                                                     log.debug("已经5秒没有读到数据了");
                                                    }
                                                }
                                            });
                            pipeline.addLast(new ProcotolFrameDecoder(1024,17,4,0,0))
                                    .addLast(LOGGING_HANDLER)
                                    .addLast(MESSAGE_CODEC)
                                    .addLast(LOGIN_HANDLER)
                                    .addLast(CHAT_HANDLER)
                                    .addLast(GROUP_CREATE_HANDLER)
                                    .addLast(GROUP_JOIN_HANDLER)
                                    .addLast(GROUP_MEMBERS_HANDLER)
                                    .addLast(GROUP_QIUT_HANDLER)
                                    .addLast(GROUP_CHAT_HANDLER)
                                    .addLast(QUIT_HANDLER);
                        }

                    })
                    .bind(8999).sync().channel();
            channel.closeFuture().sync();

        } catch (InterruptedException e) {
            log.error("server error",e);
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }

    }

}
