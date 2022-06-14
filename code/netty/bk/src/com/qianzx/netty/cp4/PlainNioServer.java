package com.qianzx.netty.cp4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-06-09 16:16:00
 */
public class PlainNioServer {
    public void server(int port) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(port));
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        final ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes());
        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                try {
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel socketChannel = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel channel = socketChannel.accept();
                        channel.configureBlocking(false);
                        channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, msg.duplicate());
                        System.out.println(
                                "Accepted connection from " + channel);
                    }
                    if (selectionKey.isWritable()) {
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                        while (buffer.hasRemaining()) {
                            if (socketChannel.write(buffer) == 0) {
                                break;
                            }
                        }
                        socketChannel.close();
                    }
                    if (selectionKey.isReadable()) {

                    }
                } catch (Exception e) {
                    selectionKey.cancel();
                    e.printStackTrace();
                }
            }


        }
    }
}

