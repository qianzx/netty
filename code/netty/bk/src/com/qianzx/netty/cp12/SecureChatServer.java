package com.qianzx.netty.cp12;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLException;
import java.net.InetSocketAddress;
import java.security.cert.CertificateException;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-06-20 14:34:00
 */
public class SecureChatServer extends ChatServer {
    private final SslContext context;

    public SecureChatServer(SslContext context) {
        this.context = context;
    }

    @Override
    public ChannelHandler createInitializer(ChannelGroup channelGroup) {
        return new SecureChatServerInitializer(channelGroup,context);
    }

      public static void main(String[] args) throws Exception {
          SelfSignedCertificate cert = new SelfSignedCertificate();
          SslContextBuilder contextBuilder = SslContextBuilder.forServer(cert.certificate(), cert.privateKey());
          SslContext context = contextBuilder.build();
          final SecureChatServer secureChatServer = new SecureChatServer(context);
          ChannelFuture channelFuture = secureChatServer.start(new InetSocketAddress(9901));
          Runtime.getRuntime().addShutdownHook(new Thread(){
              @Override
              public void run(){
                  secureChatServer.destory();
              }
          });
          channelFuture.channel().closeFuture().syncUninterruptibly();
      }
}

