package com.kunlun.netty.upload.server;

import com.kunlun.netty.heartbeat.handler.HeartbeatHandler;
import com.kunlun.netty.upload.codec.UploadFileDecodecer;
import com.kunlun.netty.upload.handler.UploadFileHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2024-01-25 15:14
 */
public class UploadFileServer {

    private int port;

    public UploadFileServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        // 1. 创建bossGroup和workerGroup
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 2. 创建服务器
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            // 添加上传文件解码器
                            channel.pipeline().addLast(new UploadFileDecodecer());
                            // 添加上传文件处理器
                            channel.pipeline().addLast(new UploadFileHandler());
                        }
                    })
                    // 当请求连接数超过此值时，操作系统可能会开始拒绝新的连接请求。
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 设置每个由服务器接受的新连接（子 Channel）的 TCP 选项 SO_KEEPALIVE 为 true，这将启用 TCP 保持活动功能，有助于检测死掉的连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // 3. 启动服务器
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("上传文件服务启动");

            // 等待关闭
            future.channel().closeFuture().sync();
        } finally {
            // 关闭线程池
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
