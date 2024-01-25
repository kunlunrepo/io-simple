package com.kunlun.netty.heartbeat.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * description : 心跳处理器
 *
 * @author kunlunrepo
 * date :  2024-01-25 14:56
 */
public class HeartbeatHandler extends ChannelInboundHandlerAdapter {

    // 心跳次数
    int readTimeOut = 0;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("心跳事件触发：" + evt);
        // 心跳事件
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;

            /*
                READER_IDLE：表示在指定时间内没有从Channel读取到任何数据。
                WRITER_IDLE：表示在指定时间内没有向Channel写入任何数据。
                ALL_IDLE：表示在指定时间内既没有从Channel读取也没有向Channel写入任何数据
             */
            if (event.state() == IdleState.READER_IDLE) {
                // 处理读空闲状态，例如关闭连接、发送心跳包等
                readTimeOut++;
            }

            System.out.println("readTimeOut = " + readTimeOut);

            if (readTimeOut >= 3) {
                System.out.println("超时超过3次，客户端断开连接");
                ctx.close();
            }
        }
    }
}
