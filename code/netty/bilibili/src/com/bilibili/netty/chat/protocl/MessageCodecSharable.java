package com.bilibili.netty.chat.protocl;

import com.bilibili.netty.chat.config.Config;
import com.bilibili.netty.chat.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.message
 * @ClassName MessageCodec
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/11-21:41
 * @Version 1.0
 */
@Slf4j
@ChannelHandler.Sharable
/**
    这两个泛型上吃过大亏
 **/
public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer();
        //魔数
        buffer.writeBytes("oldbaby".getBytes(StandardCharsets.UTF_8));
        //版本
        buffer.writeBytes("v1.0".getBytes(StandardCharsets.UTF_8));
        //序列化算法0：jdk;1:json
        //ordinal()获取枚举对象的顺序
        buffer.writeByte(Config.getSerializerAlgorithm().ordinal());
        //指令类型
        buffer.writeByte(msg.getMessageType());
        //请求序号
        buffer.writeInt(msg.getSequenceId());

        //jdk java对象转字节数组
        /*ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(msg);
        byte[] bytes = bos.toByteArray();*/
        byte[] bytes = Config.getSerializerAlgorithm().serialize(msg);
        //正文长度
        buffer.writeInt(bytes.length);
        //正文内容
        buffer.writeBytes(bytes);
        out.add(buffer);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        String magicNum = msg.readBytes(7).toString(StandardCharsets.UTF_8);
        String version = msg.readBytes(4).toString(StandardCharsets.UTF_8);
        byte serializerType = msg.readByte();
        byte messageType = msg.readByte();
        int sequenceId = msg.readInt();
        int length = msg.readInt();
        byte[] bytes = new byte[length];
        msg.readBytes(bytes);
        /*ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
        Message message = (Message) inputStream.readObject();*/
        //values()获取所有枚举实列数组
        Serializer.Algorithm algorithm = Serializer.Algorithm.values()[serializerType];
        Class<?> messageClass = Message.getMessageClass(messageType);
        Object message = algorithm.deserialize(messageClass, bytes);

        log.debug("{},{},{},{},{},{}",magicNum,version,serializerType,messageType,sequenceId,length);
        log.debug("{}",message);
        out.add(message);
    }
}
