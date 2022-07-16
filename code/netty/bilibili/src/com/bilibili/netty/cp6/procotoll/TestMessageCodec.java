package com.bilibili.netty.cp6.procotoll;

import com.bilibili.netty.chat.message.LoginRequestMessage;
import com.bilibili.netty.chat.protocl.MessageCodecSharable;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.cp6.protocol
 * @ClassName TestMessageCodec
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/10-19:51
 * @Version 1.0
 */
public class TestMessageCodec {
    public static void main(String[] args) throws Exception {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024,17,4,0,0),
                new LoggingHandler(LogLevel.DEBUG)
                ,new MessageCodecSharable()
        );
       LoginRequestMessage message = new LoginRequestMessage("zhangsan","12345");
         embeddedChannel.writeOutbound(message);
       /* ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        MessageCodec  m = new MessageCodec();
        m.encode(null,message,buffer);
        ByteBuf slice = buffer.slice(0, 100);
        ByteBuf slice1 = buffer.slice(100, buffer.readableBytes() - 100);

        buffer.retain();
        //writeInbound会调用buffer.release()
        embeddedChannel.writeInbound(slice);
        embeddedChannel.writeInbound(slice1);*/


    }
}
