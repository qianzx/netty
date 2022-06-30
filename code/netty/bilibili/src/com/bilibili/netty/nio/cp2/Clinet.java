package com.bilibili.netty.nio.cp2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.nio.cp2
 * @ClassName Clinet
 * @Description TODO
 * @Author qianzx
 * @Date 2022/6/22-22:30
 * @Version 1.0
 */
public class Clinet {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8080));
        ByteBuffer source = ByteBuffer.allocate(32);
        source.put("Hello,world\nI`m zhangsan\nHo".getBytes(StandardCharsets.UTF_8));
        source.flip();
        int write = socketChannel.write(source);
        System.out.println(write);

        System.in.read();

    }
}
