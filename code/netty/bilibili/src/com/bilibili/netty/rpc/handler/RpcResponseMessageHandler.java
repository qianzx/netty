package com.bilibili.netty.rpc.handler;

import com.bilibili.netty.chat.message.RpcResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Promise;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.rpc.handler
 * @ClassName RpcResponseMessageHandler
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/16-21:39
 * @Version 1.0
 */
public class RpcResponseMessageHandler extends SimpleChannelInboundHandler<RpcResponseMessage> {

    public static final Map<Integer, Promise<Object>> PROMISES = new ConcurrentHashMap();
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponseMessage msg) throws Exception {
        int sequenceId = msg.getSequenceId();
        Promise<Object> promise = PROMISES.remove(sequenceId);
        if(promise != null){
            Object returnValue = msg.getReturnValue();
            Exception exceptionValue = msg.getExceptionValue();
            if(exceptionValue == null){
                promise.setSuccess(returnValue);
            }else {
                promise.setFailure(exceptionValue);
            }
        }
    }
}
