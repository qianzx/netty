package com.bilibili.netty.chat.handler;

import com.bilibili.netty.chat.message.GroupCreateRequestMessage;
import com.bilibili.netty.chat.message.GroupCreateResponseMessage;
import com.bilibili.netty.chat.server.session.Group;
import com.bilibili.netty.chat.server.session.GroupSession;
import com.bilibili.netty.chat.server.session.GroupSessionFactory;
import io.netty.channel.*;

import java.util.List;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.handler
 * @ClassName GroupCreateRequestMessageHandler
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/13-23:10
 * @Version 1.0
 */
@ChannelHandler.Sharable
public class GroupCreateRequestMessageHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage msg) throws Exception {
        GroupSession groupSession = GroupSessionFactory.getGroupSession();
        Group group = groupSession.createGroup(msg.getGroupName(), msg.getMembers());
        if(group != null){
            ctx.writeAndFlush(new GroupCreateResponseMessage(true, msg.getGroupName() + "创建成功"));
            List<Channel> membersChannel = groupSession.getMembersChannel(msg.getGroupName());
            for(Channel channel:membersChannel){
                channel.writeAndFlush(new GroupCreateResponseMessage(true,  "您已被拉入" + msg.getGroupName() + "群"));
            }
        }else {
            ctx.writeAndFlush(new GroupCreateResponseMessage(false, msg.getGroupName() + "已存在不能重复创建"));

        }
    }
}
