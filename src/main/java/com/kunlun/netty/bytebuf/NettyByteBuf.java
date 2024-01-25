package com.kunlun.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * description : 测试ByteBuf
 *
 * @author kunlunrepo
 * date :  2024-01-25 14:17
 */
public class NettyByteBuf {

    public static void main(String[] args) {

        // ByteBuf是一个用于处理网络数据的灵活、零拷贝的字节缓冲区

        // 创建byteBuf对象 10个字节数组
        ByteBuf byteBuf = Unpooled.buffer(10);
        System.out.println("byteBuf=" + byteBuf);

        // 写入数据
        for (int i = 0; i < 8; i++) {
            byteBuf.writeByte(i);
        }
        System.out.println("byteBuf=" + byteBuf);
        // ridx(读索引): 0, widx(写索引): 8, cap(容量): 10

        // 读取数据
        for (int i = 0; i < 5; i++) {
            System.out.println("遍历byteBuf=" + byteBuf.getByte(i));
        }
        System.out.println("byteBuf=" + byteBuf);

        // 读取数据 ridx发生变化
        for (int i = 0; i < 5; i++) {
            System.out.println("遍历byteBuf=" + byteBuf.readByte());
        }
        System.out.println("byteBuf=" + byteBuf);
    }
}
