package com.bilibili.netty.chat.handler;

import com.bilibili.netty.chat.message.ChatRequestMessage;
import com.bilibili.netty.chat.message.ChatResponseMessage;
import com.bilibili.netty.chat.server.session.Session;
import com.bilibili.netty.chat.server.session.SessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.handler
 * @ClassName ChatRequestMessageHandler
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/13-21:51
 * @Version 1.0
 */
@ChannelHandler.Sharable
public class ChatRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage msg) throws Exception {
        String to = msg.getTo();
        Session session = SessionFactory.getSession();
        Channel channel = session.getChannel(to);
        if(channel != null){
            channel.writeAndFlush(new ChatResponseMessage(msg.getFrom(), msg.getContent()));
        }else {
            ctx.channel().writeAndFlush(new ChatResponseMessage(false,"对方用户不在线或者不存在"));
        }
    }
}
