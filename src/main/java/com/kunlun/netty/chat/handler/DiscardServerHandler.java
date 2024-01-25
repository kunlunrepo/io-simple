package com.kunlun.netty.chat.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-18 15:56
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    static Set<Channel> channelList = new HashSet<>(); // 现有客户端集合

    // 客户端连接触发
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 通知其他客户端 我上线了
        for (Channel channel : channelList) {
            channel.writeAndFlush("[客户端]" + ctx.channel().remoteAddress() + "上线了");
        }
        // 将自己加入到现有客户端集合
        channelList.add(ctx.channel());
    }

    // 读取数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message = (String) msg;
        // 分发给聊天室内的所有客户端
        /*for (Channel channel : channelList) {
            if (channel == ctx.channel()) {
                channel.writeAndFlush("[自己]：" + message);
            } else {
                channel.writeAndFlush("[客户端]" +channel.remoteAddress()+ "：" + message);
            }
        }*/

        System.out.println("客户端消息：" + message);
    }

    // 客户端不活跃时触发
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        channelList.remove(ctx.channel());
        // 通知其他客户端 我下线了
        for (Channel channel : channelList) {
            channel.writeAndFlush("[客户端]" + ctx.channel().remoteAddress() + "下线了");
        }
    }
}