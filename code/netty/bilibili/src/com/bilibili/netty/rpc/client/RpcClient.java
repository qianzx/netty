package com.bilibili.netty.rpc.client;

import com.bilibili.netty.chat.message.RpcRequestMessage;
import com.bilibili.netty.chat.protocl.MessageCodecSharable;
import com.bilibili.netty.chat.protocl.ProcotolFrameDecoder;
import com.bilibili.netty.rpc.handler.RpcResponseMessageHandler;
import com.bilibili.netty.rpc.server.HelloService;
import com.bilibili.netty.rpc.util.SequenceIdGenerator;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.Map;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.rpc.client
 * @ClassName RpcClient
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/16-21:35
 * @Version 1.0
 */
@Slf4j
public class RpcClient {
    private static volatile Channel channel;

    private static final Object LOCK = new Object();

    public static Channel getChannel(){
        if (channel != null){
            return channel;
        }
        synchronized (LOCK){
            if(channel != null){
                return channel;
            }
            initChanel();
            return channel;
        }
    }


    public static void main(String[] args) {

        HelloService service = getService(HelloService.class);
        service.sayHello("张三");

    }

    public static <T> T getService(Class<T> tClass){
        Object o = Proxy.newProxyInstance(tClass.getClassLoader(), new Class[]{tClass},
                (Object proxy, Method method, Object[] args)->{
                    int next = SequenceIdGenerator.next();
                    RpcRequestMessage rpcRequestMessage = new RpcRequestMessage(next,
                            tClass.getName(),
                            method.getName(),
                            method.getReturnType(),
                            method.getParameterTypes(),
                            args);
                    Channel channel = getChannel();
                    channel.writeAndFlush(rpcRequestMessage);
                    Map<Integer, Promise<Object>> promises = RpcResponseMessageHandler.PROMISES;
                    DefaultPromise promise = new DefaultPromise(channel.eventLoop());
                    promises.put(next,promise);
                    promise.await();
                    if(promise.isSuccess()){
                        return promise.getNow();
                    }else {
                        throw new RuntimeException(promise.cause());
                    }

                });
        return (T) o;
    }

    private static void initChanel() {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        LoggingHandler LOGGINGHANDLER = new LoggingHandler();
        MessageCodecSharable MESSAGECODECSHARABLE = new MessageCodecSharable();
        RpcResponseMessageHandler RPC_HANDLER = new RpcResponseMessageHandler();
        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ProcotolFrameDecoder())
                                    .addLast(LOGGINGHANDLER)
                                    .addLast(MESSAGECODECSHARABLE)
                                    .addLast(RPC_HANDLER);
                        }
                    });
            ChannelFuture future = bootstrap.connect(new InetSocketAddress("localhost", 8080)).sync();
            channel = future.channel();
        /*    channel.closeFuture().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {

                }
            })*/
            channel.closeFuture().addListener(closeFuture-> group.shutdownGracefully());
        } catch (Exception e) {
            log.error("client error{}",e);
        }
    }
}
