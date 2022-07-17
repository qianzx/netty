package com.bilibili.netty.rpc.handler;

import com.bilibili.netty.chat.message.RpcRequestMessage;
import com.bilibili.netty.chat.message.RpcResponseMessage;
import com.bilibili.netty.rpc.server.HelloService;
import com.bilibili.netty.rpc.server.ServiceFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.rpc.handler
 * @ClassName RpcRequestMessageHandler
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/16-21:34
 * @Version 1.0
 */
public class RpcRequestMessageHandler extends SimpleChannelInboundHandler<RpcRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequestMessage msg) throws Exception {
        RpcResponseMessage rpcResponseMessage = new RpcResponseMessage();

        try {
            rpcResponseMessage.setSequenceId(msg.getSequenceId());
            String interfaceName = msg.getInterfaceName();
            HelloService service = (HelloService) ServiceFactory.getService(Class.forName(interfaceName));
            Method method = service.getClass().getMethod(msg.getMethodName(),String.class);
            Object invoke = method.invoke(service, msg.getParameterValue());
            System.out.println(invoke);
            rpcResponseMessage.setReturnValue(invoke);
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getCause().getMessage();
            rpcResponseMessage.setReturnValue("远程调用出错" + message);
        }
        ctx.writeAndFlush(rpcResponseMessage);
    }
}
