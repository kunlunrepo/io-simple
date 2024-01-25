package com.kunlun.netty.my.server;

import com.kunlun.netty.my.codec.MyDecodecer;
import com.kunlun.netty.my.handler.DiscardServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-18 15:53
 */
public class DiscardServer {

    private int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        // 启动线程池、工作线程池
        EventLoopGroup bossGroup = new NioEventLoopGroup(1); //线程池
        EventLoopGroup workerGroup = new NioEventLoopGroup();//

        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            Charset gbk = Charset.forName("GBK");
                            // 解决粘包方法
                            // 第一种：固定长度 弊端是数据的长度可能超过定义的数
//                            ch.pipeline().addLast(new FixedLengthFrameDecoder(8)); // 中文占用2个字节
                            // 第二种：固定字符 弊端是字符中不再允许出现该字符
//                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer("_".getBytes())));
                            // 第三种：私有协议
                            ch.pipeline().addLast(new MyDecodecer());
                            // 处理器
                            ch.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            // 绑定端口、接收连接
            System.out.println("服务端启动成功");
            ChannelFuture f = b.bind(port).sync(); // (7)

            // 关闭服务
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
