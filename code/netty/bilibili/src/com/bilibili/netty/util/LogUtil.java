package com.bilibili.netty.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
import static io.netty.util.internal.StringUtil.NEWLINE;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.util
 * @ClassName LogUtil
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/5-21:57
 * @Version 1.0
 */
public class LogUtil {
    public static void log(ByteBuf byteBuf){
        int length = byteBuf.readableBytes();
        int row = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
        StringBuilder sb = new StringBuilder(row * 80 * 2)
                .append("read index:").append(byteBuf.readerIndex())
                .append("write index:").append(byteBuf.writerIndex())
                .append("capacity:").append(byteBuf.capacity())
                .append(NEWLINE);
        ByteBufUtil.appendPrettyHexDump(sb,byteBuf);
        System.out.println(sb);
    }
}
