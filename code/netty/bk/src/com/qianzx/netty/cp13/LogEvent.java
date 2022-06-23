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
    //�ļ���
    private final String logfile;
    //��Ϣ����
    private final String msg;
    //����LogEvent��ʱ��
    private final long received;
    //������Ϣ�Ĺ���
    public LogEvent(String logfile, String msg) {
        this(null, -1, logfile, msg);
    }

    //������Ϣ�Ĺ���
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

