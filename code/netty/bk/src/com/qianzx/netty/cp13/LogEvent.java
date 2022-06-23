package com.qianzx.netty.cp13;

import java.net.InetSocketAddress;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-06-20 16:13:00
 */
@SuppressWarnings("ALL")
public class LogEvent {
    public static final byte SEPARATOR = (byte) ':';
    private final InetSocketAddress source;
    //文件名
    private final String logfile;
    //消息内容
    private final String msg;
    //接受LogEvent的时间
    private final long received;
    //传出消息的构造
    public LogEvent(String logfile, String msg) {
        this(null, -1, logfile, msg);
    }

    //传入消息的构造
    public LogEvent(InetSocketAddress source, long received,
            String logfile, String msg) {
        this.source = source;
        this.logfile = logfile;
        this.msg = msg;
        this.received = received;
    }
    public InetSocketAddress getSource() {
        return source;
    }
    public String getLogfile() {
        return logfile;
    }
    public String getMsg() {
        return msg;
    }

    public long getReceivedTimestamp() {
        return received;
    }
}

