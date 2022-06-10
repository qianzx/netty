package com.qianzx.netty.cp5;

import io.netty.buffer.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Random;

import static com.qianzx.netty.cp5.DummyChannelHandlerContext.DUMMY_INSTANCE;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-06-10 10:30:00
 */
public class BytebufExample {
    private final static Random random = new Random();
    private static final ByteBuf BYTE_BUF_FROM_SOMEWHERE = Unpooled.buffer(1024);
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    private static final ChannelHandlerContext CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE = DUMMY_INSTANCE;

    private static void handleArray(byte[] array, int offset, int len) {}

    public static void heapBuffer(){
        ByteBuf heapBuf = BYTE_BUF_FROM_SOMEWHERE;
        if(heapBuf.hasArray()){
            byte[] array = heapBuf.array();
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            int length = heapBuf.readableBytes();
            handleArray(array,offset,length);
        }
    }

    public static void directBuffer() {
        ByteBuf directBuf = BYTE_BUF_FROM_SOMEWHERE;
        if(!directBuf.hasArray()){
            int length = directBuf.readableBytes();
            byte[] arr = new byte[length];
            directBuf.getBytes(directBuf.readerIndex(),arr);
            handleArray(arr,0,length);
        }

    }

    public static void byteBufferComposite(ByteBuffer header,ByteBuffer body){
        ByteBuffer[] messages = new ByteBuffer[]{header,body};
        ByteBuffer message2 = ByteBuffer.allocate(header.capacity() + body.capacity());
        message2.put(header);
        message2.put(body);
        message2.flip();
    }


    public static void byteBufComposite(){
        CompositeByteBuf messageBuf = Unpooled.compositeBuffer();
        ByteBuf header = BYTE_BUF_FROM_SOMEWHERE;
        ByteBuf body = BYTE_BUF_FROM_SOMEWHERE;
        messageBuf.addComponent(header);
        messageBuf.addComponent(body);
        messageBuf.removeComponent(0);
        for(ByteBuf buf : messageBuf){
            System.out.println(buf.toString());
        }
    }

    public static void byteBuffCompositeArray(){
        CompositeByteBuf comBuf = Unpooled.compositeBuffer();
        int length = comBuf.readableBytes();
        byte[] array = new byte[length];
        comBuf.getBytes(comBuf.readerIndex(),array);
        handleArray(array,0,length);
    }

    public static void byteBufRelativeAccess(){
        ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE;
        for(int i = 0 ; i < buffer.readableBytes();i++){
            byte aByte = buffer.getByte(i);
            System.out.println((char)aByte);
        }
    }

    public static void readAllData(){
        ByteBuf byteBuf = BYTE_BUF_FROM_SOMEWHERE;
        while (byteBuf.isReadable()){
            System.out.println(byteBuf.readByte());
        }
    }

    public static void write(){
        ByteBuf byteBuf = BYTE_BUF_FROM_SOMEWHERE;
        while (byteBuf.writableBytes() >= 4){
            byteBuf.writeInt(random.nextInt());
        }
    }

    /**
    sing ByteProcessor to find \r
   */
    public static void byteProcessor(){
        ByteBuf byteBuf = BYTE_BUF_FROM_SOMEWHERE;
        //ÀàËÆÓÚStringµÄindexof()
        //int i1 = byteBuf.indexOf(0, byteBuf.readableBytes(), (byte) 'a');
        int i = byteBuf.forEachByte(ByteProcessor.FIND_CR);
    }

    public static void byteBufProcessor(){
        ByteBuf byteBuf = BYTE_BUF_FROM_SOMEWHERE;
        byteBuf.forEachByteDesc(ByteBufProcessor.FIND_CR);
    }

    public static void byteBufSlice(){
        ByteBuf byteBuf = Unpooled.copiedBuffer("Netty in Action", CharsetUtil.UTF_8);
        ByteBuf slice = byteBuf.slice(0, 15);
        System.out.println(byteBuf.readableBytes());
        System.out.println(slice.toString(CharsetUtil.UTF_8));
        slice.setByte(0,'J');
        System.out.println(byteBuf.toString(CharsetUtil.UTF_8));
    }

    public static void byteBufCopy(){
        Charset utf8 = CharsetUtil.UTF_8;
        ByteBuf byteBuf = Unpooled.copiedBuffer("Netty in Action", utf8);
        ByteBuf copy = byteBuf.copy(0,byteBuf.readableBytes());
        System.out.println(copy.toString(utf8));
        copy.setByte(0,'W');
        System.out.println(byteBuf.toString(utf8));
        System.out.println(copy.toString(utf8));
    }

    public static void byteBufSetGet(){
        Charset utf8 = CharsetUtil.UTF_8;
        ByteBuf byteBuf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        System.out.println((char)byteBuf.getByte(0));
        int readerIndex = byteBuf.readerIndex();
        int writerIndex = byteBuf.writerIndex();
        System.out.println(readerIndex + " " + writerIndex);
        byteBuf.setByte(0,'B');
        int readerIndexA = byteBuf.readerIndex();
        int writerIndexA = byteBuf.writerIndex();
        System.out.println(readerIndexA + " " + writerIndexA);

    }

    public static void byteBufWriteRead(){
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        int readerIndexB = buf.readerIndex();
        int writerIndexB = buf.writerIndex();
        System.out.println(readerIndexB + " " + writerIndexB);

        System.out.println((char)buf.readByte());
        int readerIndex = buf.readerIndex();
        int writerIndex = buf.writerIndex();
        System.out.println(readerIndex + " " + writerIndex);
        buf.writeByte((byte)'?');
        int readerIndexA = buf.readerIndex();
        int writerIndexA = buf.writerIndex();
        System.out.println(readerIndexA + " " + writerIndexA);

    }
      public static void main(String[] args){
          ByteBufAllocator alloc1 = CHANNEL_FROM_SOMEWHERE.alloc();
          ByteBufAllocator alloc = CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE.alloc();
          byteBufWriteRead();
          }
}

