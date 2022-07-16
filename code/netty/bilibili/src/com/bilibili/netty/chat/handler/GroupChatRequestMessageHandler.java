package com.bilibili.netty.chat.handler;

import com.bilibili.netty.chat.message.GroupChatRequestMessage;
import com.bilibili.netty.chat.message.GroupChatResponseMessage;
import com.bilibili.netty.chat.server.session.GroupSessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.handler
 * @ClassName GroupChatRequestHandler
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/13-23:09
 * @Version 1.0
 */
@ChannelHandler.Sharable

public class GroupChatRequestMessageHandler extends SimpleChannelInboundHandler<GroupChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatRequestMessage msg) throws Exception {
        List<Channel> membersChannel = GroupSessionFactory.getGroupSession().getMembersChannel(msg.getGroupName());
        membersChannel.forEach(channel -> {
            channel.writeAndFlush(new GroupChatResponseMessage(msg.getFrom(),msg.getContent()));
        });
    }
}
