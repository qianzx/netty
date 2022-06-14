package com.qianzx.nio;

import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Random;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-05-25 16:33:00
 */
public class PipeTest {

    private static class Worker extends Thread {
        WritableByteChannel channel;
        private int reps;
        Worker (WritableByteChannel channel, int reps) {
            this.channel = channel;
            this.reps = reps;
        }
        // Thread execution begins here
        @Override
        public void run() {
            ByteBuffer buffer = ByteBuffer.allocate (100);
            try {
                for (int i = 0; i < this.reps; i++) {
                    doSomeWork (buffer);
// channel may not take it all at once
                    while (channel.write (buffer) > 0) {
// empty
                    }
                }
                this.channel.close( );
            } catch (Exception e) {
// easy way out; this is demo code
                e.printStackTrace( );
            }
        }
        private String [] products = {
                "No good deed goes unpunished",
                "To be, or what?",
                "No matter where you go, there you are",
                "Just say \"Yo\"",
                "My karma ran over my dogma"
        };
        private Random rand = new Random( );
        private void doSomeWork (ByteBuffer buffer) {
            int product = rand.nextInt (products.length);
            buffer.clear( );
            buffer.put (products [product].getBytes( ));
            buffer.put ("\r\n".getBytes( ));
            buffer.flip( );
        }
    }

    public static void main (String [] argv) throws Exception {
        // Wrap a channel around stdout
        WritableByteChannel out = Channels.newChannel (System.out);
        // Start worker and get read end of channel
        ReadableByteChannel workerChannel = startWorker (10);
        ByteBuffer buffer = ByteBuffer.allocate (100);
        while (workerChannel.read (buffer) >= 0) {
            buffer.flip( );
            out.write (buffer);
            buffer.clear( );
        } }
     // This method could return a SocketChannel or
     // FileChannel instance just as easily
    private static ReadableByteChannel startWorker (int reps) throws Exception {
        Pipe pipe = Pipe.open( );
        Worker worker = new Worker (pipe.sink( ), reps);
        worker.start( );
        Selector open = Selector.open();
        Pipe.SinkChannel sink = pipe.sink();
        SelectionKey register =sink.register(open, 2);
        sink.register(open,1);
        return (pipe.source( ));
    }
}

