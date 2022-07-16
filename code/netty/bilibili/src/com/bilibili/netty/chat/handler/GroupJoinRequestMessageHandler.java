package com.bilibili.netty.chat.handler;

import com.bilibili.netty.chat.message.GroupJoinRequestMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.handler
 * @ClassName GroupJoinRequestMessageHandler
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/13-23:11
 * @Version 1.0
 */
@ChannelHandler.Sharable

public class GroupJoinRequestMessageHandler extends SimpleChannelInboundHandler<GroupJoinRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupJoinRequestMessage msg) throws Exception {

    }
}
