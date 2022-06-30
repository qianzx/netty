package com.bilibili.netty.nio.cp3;

import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.nio.cp3
 * @ClassName MultiThreadServer
 * @Description TODO
 * @Author qianzx
 * @Date 2022/6/28-21:01
 * @Version 1.0
 */
public class MultiThreadServer {
    public static void main(String[] args) throws IOException {

        Thread.currentThread().setName("BOSS");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8080));
        ssc.configureBlocking(false);
        Selector bossSelector = Selector.open();
        ssc.register(bossSelector, SelectionKey.OP_ACCEPT);
        Worker worker = new Worker("work-0");
        //Runtime.getRuntime().availableProcessors()获取CPU的个数
        //如果工作在docker容器下，因为容器不是物理隔离会拿到宿主机的cpu个数，
        //而不是容器申请时的个数。
        //这个问题JDK10才修复，使用jvm参数UseContainerSupport配置，默认开启
        Worker[] workers = new Worker[Runtime.getRuntime().availableProcessors()];
        for(int i = 0 ; i< workers.length;i++){
            workers[i] = new Worker("work-" + i);
        }

        AtomicInteger count = new AtomicInteger();
        while (true){
            bossSelector.select();
            Iterator<SelectionKey> iterator = bossSelector.selectedKeys().iterator();
            if(iterator.hasNext()){
                SelectionKey sk = iterator.next();
                iterator.remove();
                if(sk.isAcceptable()){
                    ServerSocketChannel channel = (ServerSocketChannel) sk.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    //如果selector所在线程阻塞，register()会等待
                    workers[count.getAndIncrement() % workers.length].register(sc);
                }
            }

        }

    }

    static class Worker implements Runnable{
        private Thread thread;
        private Selector selector;
        private String name;
        //队列用于线程之间传递数据
        //private ConcurrentLinkedDeque<SocketChannel> linkedDeque = new ConcurrentLinkedDeque<>();
        private ConcurrentLinkedDeque<Runnable> linkedDeque = new ConcurrentLinkedDeque<>();
        private volatile boolean isRegister = false;

        public Worker(String name) {
            this.name = name;
        }
        public void register(SocketChannel sc) throws IOException {
            if(!isRegister){
                selector = Selector.open();
                thread = new Thread(this,name);
                isRegister = true;
                thread.start();
            }
            //linkedDeque.add(sc);

            linkedDeque.add(()-> {
                try {
                    sc.register(selector,SelectionKey.OP_READ);
                } catch (ClosedChannelException e) {
                    throw new RuntimeException(e);
                }
            });
            selector.wakeup();
        }

        @Override
        public void run() {
            while (true){
                try {
                    System.out.println("...............");
                    Runnable poll = linkedDeque.poll();
                    if(poll != null){
                        poll.run();
                    }
                   /* SocketChannel poll = linkedDeque.poll();
                    if(poll != null){
                        poll.register(selector,SelectionKey.OP_READ);
                    }*/
                    selector.select();
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while(iterator.hasNext()){
                        SelectionKey selectionKey = iterator.next();
                        iterator.remove();
                        if(selectionKey.isReadable()){
                            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            System.out.println("read........" + socketChannel.getRemoteAddress());
                            socketChannel.read(buffer);
                            buffer.flip();
                            System.out.println(CharsetUtil.UTF_8.decode(buffer).toString());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
