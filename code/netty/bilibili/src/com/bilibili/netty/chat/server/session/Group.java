package com.bilibili.netty.chat.server.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Set;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.server.session
 * @ClassName Group
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/11-21:25
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@ToString(callSuper = true)
public class Group {
    private String groupName;

    private Set<String> members;
}
