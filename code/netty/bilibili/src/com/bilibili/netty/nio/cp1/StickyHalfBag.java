package com.bilibili.netty.nio.cp1;


import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
/**
* 黏包 半包
* */
public class StickyHalfBag {
    public static void main(String[] args) {
        ByteBuffer source = ByteBuffer.allocate(32);
        source.put("Hello,world\nI`m zhangsan\nHo".getBytes(StandardCharsets.UTF_8));
        split(source);
        source.put("w are you?\n".getBytes(StandardCharsets.UTF_8));
        split(source);
    }

    public static void split(ByteBuffer source) {
        source.flip();
        for(int i = 0;i < source.limit();i++){
            if(source.get(i) == '\n'){
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                for(int j = 0; j < length; j++){
                    target.put(source.get());
                }
                System.out.println(CharsetUtil.UTF_8.decode(target));
            }
        }
        source.compact();
    }
}
