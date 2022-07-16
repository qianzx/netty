package com.bilibili.netty.chat.server.session;

import io.netty.channel.Channel;

import java.util.List;
import java.util.Set;

/**
 * 聊团组会话管理接口
 **/
public interface GroupSession {
    /**
     * @description: 创建聊天组
     * @author:qianzx
     * @date：2022/7/11-21:28
     * @param: groupName
     * @param: members
     * @return: com.bilibili.netty.demo.server.session.Group
     **/
    Group createGroup(String groupName, Set<String> members);

    /**
     * @description:聊团组加入成员
     * @author:qianzx
     * @date：2022/7/11-21:28
     * @param: groupName
     * @param: member
     * @return: com.bilibili.netty.demo.server.session.Group
     **/
    Group joinMember(String groupName,String member);

    /**
     * @description:聊团组删除成员
     * @author:qianzx
     * @date：2022/7/11-21:28
     * @param: groupName
     * @param: member
     * @return: com.bilibili.netty.demo.server.session.Group
     **/
    Group removeMember(String groupName,String member);

    /**
     * @description:删除聊天组
     * @author:qianzx
     * @date：2022/7/11-21:29
     * @param: groupName
     * @return: com.bilibili.netty.demo.server.session.Group
     **/
    Group removeGroup(String groupName);

    /**
     * @description:获取聊团组所有成员
     * @author:qianzx
     * @date：2022/7/11-21:29
     * @param: groupName
     * @return: java.util.Set<java.lang.String>
     **/
    Set<String> getMember(String groupName);

    /**
     * @description:获取组成员的Channel集合，只有在线的Channel才会返回
     * @author:qianzx
     * @date：2022/7/11-21:30
     * @param: groupName
     * @return: java.util.List<io.netty.channel.Channel>
     **/
    List<Channel> getMembersChannel(String groupName);
}
