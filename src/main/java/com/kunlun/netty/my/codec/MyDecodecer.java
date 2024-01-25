package com.kunlun.netty.my.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-18 17:50
 */
public class MyDecodecer extends ByteToMessageDecoder {

    // 解码器
    // 数据长度 +数据
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list) throws Exception {

        // 可读大小
        if (in.readableBytes() < 4) {
            return;
        }
        // 数据长度
        int len = in.readInt();
        // 读取数据
        byte[] data = new byte[len];
        in.readBytes(data); // data中有数据

        System.out.println(new String(data));
    }

}
