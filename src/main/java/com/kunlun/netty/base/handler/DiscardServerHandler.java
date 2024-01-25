package com.kunlun.netty.base.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-18 15:56
 */
public class DiscardServerHandler  extends ChannelInboundHandlerAdapter {

    // 客户端连接触发
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有客户端连接了");
    }

    // 读取数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf out = (ByteBuf) msg;
        String data = out.toString(CharsetUtil.UTF_8);
        System.out.println(data);
        short length = (short) data
                .getBytes().length;
        ByteBuf byteBuf = Unpooled.directBuffer(8);
        byteBuf.writeShort(length);
        byteBuf.writeBytes(data.getBytes());
        ctx.writeAndFlush(byteBuf);
    }
}