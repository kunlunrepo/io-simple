package com.kunlun.netty.my.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.HashSet;
import java.util.Set;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-18 15:56
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {


    // 客户端连接触发
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    // 读取数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    }

    // 客户端不活跃时触发
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

    }
}