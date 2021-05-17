package com.common.adapter;

import javafx.scene.chart.Chart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;

/**
 * @author Hope
 * Date： 2021/03/10  15:23
 * 描述：TCP通信类，需要作为spring实例
 */

@Component
public class TCPAdapter implements Adapter<String> {

    private static final Logger log = LoggerFactory.getLogger(TCPAdapter.class);

    //目标服务器IP
    protected String ip;

    //端口
    protected String port;

    //超时时间
    protected String readTimeOut;

    //报文格式
    protected String encoding = "UTF-8";

    @Override
    public String execute(String req) {
        String res = "";
        Socket socket = null;
        DataOutputStream output = null;
        DataInputStream input = null;
        ByteArrayOutputStream sab = new ByteArrayOutputStream();

        try {
            //创建套接字
            socket = new Socket(ip, Integer.valueOf(port));
            //向服务端发送数据
            output = new DataOutputStream(socket.getOutputStream());
            //读取服务端数据
            input = new DataInputStream(socket.getInputStream());
            output.write(requestConvert(req));
            output.flush();

            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = input.read(buf)) > 0) {
                sab.write(buf, 0, len);
            }
            res = new String(sab.toByteArray(), "utf8");
        } catch (Exception e) {
            log.error("socket异常");
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    output = null;
                    log.error("socket finally 异常" + e.getMessage(), e);
                }
            }

            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    input = null;
                    log.error("socket finally 异常" + e.getMessage(), e);
                }
            }

            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    socket = null;
                    log.error("socket finally 异常" + e.getMessage(), e);
                }
            }

        }
        return res;
    }


    public byte[] executeReBytes(String req) {
        byte[] resb = null;
        Socket socket = null;
        DataOutputStream output = null;
        DataInputStream input = null;
        ByteArrayOutputStream sab = new ByteArrayOutputStream();

        try {
            //创建套接字
            socket = new Socket(ip, Integer.valueOf(port));
            //向服务端发送数据
            output = new DataOutputStream(socket.getOutputStream());
            //读取服务端数据
            input = new DataInputStream(socket.getInputStream());
            output.write(requestConvert(req));
            output.flush();

            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = input.read(buf)) > 0) {
                sab.write(buf, 0, len);
            }
            resb = sab.toByteArray();
        } catch (Exception e) {
            log.error("socket异常");
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    output = null;
                    log.error("socket finally 异常" + e.getMessage(), e);
                }
            }

            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    input = null;
                    log.error("socket finally 异常" + e.getMessage(), e);
                }
            }

            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    socket = null;
                    log.error("socket finally 异常" + e.getMessage(), e);
                }
            }

        }
        return resb;
    }


    protected byte[] requestConvert(String req) {

        try {
            return req.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("无效的字符集");
        }

    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(String readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}
