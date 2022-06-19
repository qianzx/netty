package com.qianzx.chat;

import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
* 目标：服务端群聊系统实现
 * */
public class Server {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private final int PORT = 8888;

    public Server(){
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.listen();
    }

    private void listen() {
        try {
            while (selector.select() > 0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    Channel channel = selectionKey.channel();

                    if (channel instanceof ServerSocketChannel && selectionKey.isAcceptable()){
                        ServerSocketChannel acceptChannel = (ServerSocketChannel) channel;
                        SocketChannel socketChannel = acceptChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector,SelectionKey.OP_READ);
                    }
                    if (selectionKey.isReadable()){
                        readClientData(selectionKey);
                    }
                    iterator.remove();
                }
            }
        }catch (Exception e){

        }
    }

    private void readClientData(SelectionKey selectionKey) {
        SocketChannel socketChannel = null;
        try {
            socketChannel = (SocketChannel) selectionKey.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = socketChannel.read(buffer);
            while (count > 0){
                buffer.flip();
                String msg = new String(buffer.array(),0,buffer.remaining());
                System.out.println("接收到得客户端消息：" + msg);
                sendMsgToAllClient(msg,socketChannel);
            }
        }catch (Exception e){
            try {
                System.out.println(socketChannel.getRemoteAddress() + "离线");
                selectionKey.cancel();
                socketChannel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void sendMsgToAllClient(String msg, SocketChannel socketChannel) throws IOException {
        System.out.println("服务端开始转发消息：当前处理得线程" + Thread.currentThread());
        for(SelectionKey selectionKey:selector.keys()){
            Channel channel = selectionKey.channel();
            if(channel instanceof SocketChannel && channel != socketChannel){
                SocketChannel writeChannel = (SocketChannel) channel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes(CharsetUtil.UTF_8));
                writeChannel.write(buffer);
            }
        }
    }
}
