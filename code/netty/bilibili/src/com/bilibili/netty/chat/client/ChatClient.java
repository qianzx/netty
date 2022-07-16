package com.bilibili.netty.chat.client;

import com.bilibili.netty.chat.message.*;
import com.bilibili.netty.chat.protocl.MessageCodecSharable;
import com.bilibili.netty.chat.protocl.ProcotolFrameDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.client
 * @ClassName ChatClient
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/11-22:00
 * @Version 1.0
 */
@Slf4j
public class ChatClient {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        AtomicBoolean LOGIN = new AtomicBoolean(false);
        CountDownLatch WAIT_FOR_LOGIN = new CountDownLatch(1);
        try {
            ChannelFuture channelFuture = bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();



                            pipeline.addLast(new ProcotolFrameDecoder(1024,17,4,0,0))
                                    .addLast("loginHandler",LOGGING_HANDLER)
                                    .addLast("messageCodec",MESSAGE_CODEC);
                            //客户端心跳检测
                            pipeline.addLast(new IdleStateHandler(0,8,0))
                                    .addLast(new ChannelDuplexHandler(){
                                        @Override
                                        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                            IdleStateEvent event = (IdleStateEvent) evt;
                                            if(event.state()== IdleState.WRITER_IDLE){
                                                //log.info("8秒没发数据进行心跳检测");
                                                ctx.writeAndFlush(new PingMessage());
                                            }
                                        }
                                    })

                            //发送登录请求
                                    .addLast(new ChannelInboundHandlerAdapter(){
                                         @Override
                                         public void channelActive(ChannelHandlerContext ctx){
                                          new Thread(()->{
                                              Scanner scanner = new Scanner(System.in);
                                              System.out.println("请输入账号");
                                              String username = scanner.nextLine();
                                              System.out.println("请输入密码");
                                              String password = scanner.nextLine();
                                              LoginRequestMessage loginRequestMessage = new LoginRequestMessage(username, password);
                                              ctx.writeAndFlush(loginRequestMessage);
                                              try {
                                                  WAIT_FOR_LOGIN.await();
                                              } catch (InterruptedException e) {
                                                  throw new RuntimeException(e);
                                              }
                                              if(!LOGIN.get()){
                                                  ctx.channel().close();
                                                  return;
                                              }
                                              while (true){
                                                  System.out.println("=========================");
                                                  System.out.println("send [username] [content]");
                                                  System.out.println("gsend [group name] [content]");
                                                  System.out.println("gcreate [group name] [m1,m2,m3...]");
                                                  System.out.println("gmembers [group name]");
                                                  System.out.println("gjoin [group name]");
                                                  System.out.println("gquit [group name]");
                                                  System.out.println("quit");
                                                  System.out.println("=========================");
                                                  String command = scanner.nextLine();
                                                  String[] comandArr = command.split(" ");
                                                  switch (comandArr[0]){
                                                      case "send":
                                                          ctx.writeAndFlush(new ChatRequestMessage(username,comandArr[1],comandArr[2]));
                                                          break;
                                                      case "gsend":
                                                          ctx.writeAndFlush(new GroupChatRequestMessage(username,comandArr[1],comandArr[2]));
                                                          break;
                                                      case "gcreate":
                                                          HashSet<String> members = new HashSet<>(Arrays.asList(comandArr[2].split(",")));
                                                          ctx.writeAndFlush(new GroupCreateRequestMessage(comandArr[1],members));
                                                          break;
                                                      case "gmembers":
                                                          ctx.writeAndFlush(new GroupMembersRequestMessage(comandArr[1]));
                                                          break;
                                                      case "gjoin":
                                                          ctx.writeAndFlush(new GroupJoinRequestMessage(username,comandArr[1]));
                                                          break;
                                                      case "gquit":
                                                          ctx.writeAndFlush(new GroupQuitRequestMessage(username,comandArr[1]));
                                                          break;
                                                      case "quit":
                                                          ctx.channel().close();
                                                          break;
                                                      default:
                                                          break;
                                                  }

                                              }
                                          }).start();
                                       }
                                        @Override
                                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                            if(msg instanceof LoginResponseMessage){
                                                LoginResponseMessage responseMessage = (LoginResponseMessage) msg;
                                                if(responseMessage.isSuccess()){
                                                    LOGIN.compareAndSet(false,true);
                                                }
                                                WAIT_FOR_LOGIN.countDown();
                                            }
                                        }

                                    });
                        }
                    }).connect("localhost", 8999).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("client error",e);
        } finally {
            group.shutdownGracefully();
        }
    }
}
