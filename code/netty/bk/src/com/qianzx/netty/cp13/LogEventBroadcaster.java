package com.qianzx.netty.cp13;

import com.qianzx.netty.cp12.HttpRequestHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-06-20 16:50:00
 */
public class LogEventBroadcaster {
    public static Integer SPORT = 1111;
    public static Integer DPORT = 1112;
    private final EventLoopGroup group;
    private final Bootstrap bootstrap;
    private static final File FILE;

    static {
        URL location = HttpRequestHandler.class.getProtectionDomain().getCodeSource().getLocation();
        try {
            String path = location.toURI() + "index.html";
            path = !path.contains("file:")? path : path.substring(5);
            FILE = new File(path);
        }catch (URISyntaxException e){
            throw new IllegalStateException("Unable to locate index.html",e);
        }
    }

    public LogEventBroadcaster(InetSocketAddress address) {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST,true)
                .handler(new LogEventEncoder(address));
    }

    public void run() throws Exception {
        Channel channel = bootstrap.bind(SPORT).sync().channel();
        long pointer = 0;
        while(true){
            long length = FILE.length();
            if(length < pointer){
                pointer = length;
            }else if(length > pointer){
                RandomAccessFile randomAccessFile = new RandomAccessFile(FILE, "r");
                randomAccessFile.seek(pointer);
                String line;
                while ((line = randomAccessFile.readLine()) != null){
                    LogEvent logEvent = new LogEvent(null, -1, FILE.getAbsolutePath(), line);
                    channel.writeAndFlush(logEvent);
                    System.out.println(logEvent.getMsg());
                }
                pointer = randomAccessFile.getFilePointer();
                randomAccessFile.close();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.interrupted();
                break;
            }
        }
    }
    public void stop() {
        group.shutdownGracefully();
    }
      public static void main(String[] args) throws Exception {
          LogEventBroadcaster broadcaster = new LogEventBroadcaster(
          new InetSocketAddress("255.255.255.255", DPORT));
          try {
              broadcaster.run();
          }
          finally {
              broadcaster.stop();
          }
    }
}

