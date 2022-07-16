package com.bilibili.netty.chat.server.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.server.service
 * @ClassName UserServiceMemoryImpl
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/11-21:03
 * @Version 1.0
 */
public class UserServiceMemoryImpl implements UserService{

    private Map<String,String> allUserMap = new ConcurrentHashMap<>();


    {
        allUserMap.put("zhangsan","123");
        allUserMap.put("lisi","123");
        allUserMap.put("wangwu","123");
        allUserMap.put("zhaoliu","123");
        allUserMap.put("qianqi","123");
        allUserMap.put("gouba","123");
    }
    @Override
    public boolean login(String username, String password) {
        String pass = allUserMap.get(username);
        if(pass == null){
            return false;
        }
        return  pass.equals(password);
    }
}
