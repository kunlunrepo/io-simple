package com.kunlun.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-18 14:35
 */
public class SocketServer {

    public static void main(String[] args) throws Exception {
        // 定义一个socket 为处理客户端请求
        ServerSocket serverSocket = new ServerSocket(9001);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");


        // 循环等待连接
        while (true) {
            System.out.println("【初始化】等待连接...");

            LocalDateTime dateTime = LocalDateTime.now();
            String nowDateTime = dateTime.format(formatter);

            // 阻塞方法
            Socket accept = serverSocket.accept();
            System.out.println("【" + nowDateTime + "】 有客户端连接了...");

            // 单线程处理内容 (服务端在处理第一个客户端的所有事件之前，无法为其他客户提供服务)
//            handler(accept);

            // 多线程处理内容 (会产生大量空闲线程，浪费服务器资源)
            new Thread(() -> {
                try {
                    handler(accept);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();

        }

    }

    private static void handler(Socket accept) throws IOException {
        byte[] bytes = new byte[1024];
        System.out.println("准备好了...");

        // 接收客户端的数据，阻塞方法，没有数据可读时就阻塞
        int read = accept.getInputStream().read(bytes);
        System.out.println("读完毕...");

        if (read != -1) {
            System.out.println("接收到客户端的数据："+ new String(bytes, 0 ,read));
        }
    }

}
