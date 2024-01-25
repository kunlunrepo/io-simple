package com.kunlun.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-18 15:23
 */
public class NioServer {

    // 保存客户端连接
    static List<SocketChannel> channelList = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        // 定义socket
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(9001));
        serverSocket.configureBlocking(false); // 配置为非阻塞
        System.out.println("服务端启动成功");

        while (true) {
            // 定义接收通道
            SocketChannel channel = serverSocket.accept();
            if (channel != null) {
                System.out.println("客户端连接成功");
                channel.configureBlocking(false); // 非阻塞
                channelList.add(channel);
            }
            // 遍历连接 读取数据
            Iterator<SocketChannel> iterator = channelList.iterator();
            while (iterator.hasNext()) {
                SocketChannel sc = iterator.next();
                ByteBuffer byteBuffer = ByteBuffer.allocate(128);

                int len = sc.read(byteBuffer);
                // 如果有数据，把数据打印出来
                if (len > 0) {
                    System.out.println(Thread.currentThread().getName() + " 接收到消息：" + new String(byteBuffer.array()));
                } else if (len == -1) {
                    iterator.remove();
                    System.out.println("客户端断开连接");
                }
            }

        }

    }
}
