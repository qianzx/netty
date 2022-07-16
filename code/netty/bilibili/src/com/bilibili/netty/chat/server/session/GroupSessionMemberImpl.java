package com.bilibili.netty.chat.server.session;

import io.netty.channel.Channel;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.server.session
 * @ClassName GroupSessionMemberImpl
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/11-21:31
 * @Version 1.0
 */
public class GroupSessionMemberImpl implements GroupSession{
    private static ConcurrentHashMap<String,Group> GROUPS = new ConcurrentHashMap();
    @Override
    public Group createGroup(String groupName, Set<String> members) {
        Group group = GROUPS.get(groupName);
        if(group != null){
            return null;
        }else {
            Group group1 = new Group(groupName, members);
            GROUPS.put(groupName,group1);
           return group1;
        }
    }

    @Override
    public Group joinMember(String groupName, String member) {
        Group group = GROUPS.get(groupName);
        if(group == null){

        }else {
            group.getMembers().add(member);
        }
        return group;
    }

    @Override
    public Group removeMember(String groupName, String member) {
        Group group = GROUPS.get(groupName);
        if(group == null){

        }else {
            group.getMembers().remove(member);
        }
        return group;
    }

    @Override
    public Group removeGroup(String groupName) {
        Group group = GROUPS.remove(groupName);
        return group;
    }

    @Override
    public Set<String> getMember(String groupName) {
        return GROUPS.get(groupName).getMembers();
    }

    @Override
    public List<Channel> getMembersChannel(String groupName) {
        Set<String> members = GROUPS.get(groupName).getMembers();
        Session session = SessionFactory.getSession();
        List<Channel> channelList = members.stream().map(str -> session.getChannel(str))
                .filter(Objects::nonNull).collect(Collectors.toList());

        return channelList;
    }
}
