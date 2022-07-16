package com.bilibili.netty.chat.server.service;
/**
 * 用户管理接口
 */
public interface UserService {

    /**
     * @description: 登录
     * @author:qianzx
     * @date：2022/7/11-20:59
     * @param: username 用户名
     * @param: password 密码
     * @return: boolean 登录成功返回true，登录失败返回false;
     **/
    boolean login(String username,String password);

}
