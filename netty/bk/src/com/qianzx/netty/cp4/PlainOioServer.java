package com.qianzx.netty.cp4;

import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-06-09 16:09:00
 */
public class PlainOioServer {
    public void serve(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        try {
            while (true){
                final Socket socket = serverSocket.accept();
                System.out.println(
                        "Accepted connection from " + socket);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try ( OutputStream outputStream = socket.getOutputStream();){
                            outputStream.write("Hi!\r\n".getBytes(
                                    CharsetUtil.UTF_8));
                            outputStream.flush();
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

