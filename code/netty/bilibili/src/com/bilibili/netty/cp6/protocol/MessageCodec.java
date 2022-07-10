package com.bilibili.netty.cp6.protocol;

import com.bilibili.netty.cp6.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.cp6
 * @ClassName MessageCodec
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/10-19:15
 * @Version 1.0
 */
@Slf4j
public class MessageCodec extends ByteToMessageCodec<Message> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf out) throws Exception {
        //魔数
        out.writeBytes("oldbaby".getBytes(StandardCharsets.UTF_8));
        //版本
        out.writeBytes("v1.0".getBytes(StandardCharsets.UTF_8));
        //序列化算法0：jdk;1:json
        out.writeByte(0);
        //指令类型
        out.writeByte(message.getMessageType());
        //请求序号
        out.writeInt(message.getSequenceId());

        //jdk java对象转字节数组
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(message);
        byte[] bytes = bos.toByteArray();
        //正文长度
        out.writeInt(bytes.length);
        //正文内容
        out.writeBytes(bytes);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        String magicNum = byteBuf.readBytes(7).toString(StandardCharsets.UTF_8);
        String version = byteBuf.readBytes(4).toString(StandardCharsets.UTF_8);
        byte serializerType = byteBuf.readByte();
        byte messageType = byteBuf.readByte();
        int sequenceId = byteBuf.readInt();
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
        Message message = (Message) inputStream.readObject();

        log.debug("{},{},{},{},{},{}",magicNum,version,serializerType,messageType,sequenceId,length);
        log.debug("{}",message);
        System.out.println("张三");
        list.add(message);

    }
}
