package com.bilibili.netty.nio.cp2;

import com.bilibili.netty.nio.cp1.StickyHalfBag;
import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;


/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.nio.cp2
 * @ClassName Server
 * @Description TODO
 * @Author qianzx
 * @Date 2022/6/22-22:30
 * @Version 1.0
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8080));
        serverSocketChannel.configureBlocking(false);
        //List<SocketChannel> channelList = new ArrayList<>();
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true){
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                if(selectionKey.isAcceptable()){
                    ServerSocketChannel serverSocketChannelSlect = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = serverSocketChannelSlect.accept();
                    socketChannel.configureBlocking(false);
                    ByteBuffer buffer = ByteBuffer.allocate(5);
                    //每个socketChannel都需要自己ByteBuffer
                    socketChannel.register(selector,SelectionKey.OP_READ,buffer);
                }
                if(selectionKey.isReadable()){
                    try {
                        SocketChannel socketChannelSelect = (SocketChannel) selectionKey.channel();
                        ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                        int read = socketChannelSelect.read(buffer);
                        //处理客户端正常断开
                        if(read == -1){
                            selectionKey.cancel();
                        }else {
                            //System.out.println(CharsetUtil.UTF_8.decode(buffer));
                            split(buffer);
                            //判断是否需要扩容
                            if(buffer.position() == buffer.limit()){
                                ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
                                buffer.flip();
                                newBuffer.put(buffer);
                                selectionKey.attach(newBuffer);
                            }
                        }
                        //buffer.clear();
                    } catch (IOException e) {
                        //处理客户端异常端口
                        selectionKey.cancel();
                        e.printStackTrace();
                    }
                }
                iterator.remove();
            }
        }
    }

    private static void split(ByteBuffer source) {
        source.flip();
        for(int i = 0;i < source.limit();i++){
            if(source.get(i) == '\n'){
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                for(int j = 0; j < length; j++){
                    target.put(source.get());
                }
                target.flip();
                System.out.print(CharsetUtil.UTF_8.decode(target));
            }
        }
        source.compact();
        //source.clear();
    }
}
