package com.bilibili.netty.chat.message;

import lombok.Data;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.chat.message
 * @ClassName RpcRequestMessage
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/16-21:18
 * @Version 1.0
 */
@Data
public class RpcRequestMessage extends Message{
    /**
     * 调用接口的全限定名，服务端根据它找到实现类
     **/
    private String interfaceName;
    //调用接口中的方法名称
    private String methodName;
    //方法返回类型
    private Class<?> returnType;
    //方法参数类型数组
    private Class[] parameterType;
    //方法参数值数组
    private Object[] parameterValue;
    @Override
    public int getMessageType() {
        return Message.RpcRequestMessage;
    }

    public RpcRequestMessage(int sequenceId,String interfaceName, String methodName, Class<?> returnType, Class[] parameterType, Object[] parameterValue) {
        super.setSequenceId(sequenceId);
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.returnType = returnType;
        this.parameterType = parameterType;
        this.parameterValue = parameterValue;
    }
}
