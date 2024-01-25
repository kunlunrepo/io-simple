package com.kunlun.netty.upload.handler;

import com.kunlun.netty.upload.FileDto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * description : 上传文件的处理器
 *
 * @author kunlunrepo
 * date :  2024-01-25 15:38
 */
public class UploadFileHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("上传文件处理器");
        if (msg instanceof FileDto) {
            FileDto fileDto = (FileDto) msg;
            if (fileDto.getCommand() == 1) {
                // 创建文件
                File file = new File("K://"+fileDto.getFileName());
                if (!file.exists()) {
                    file.createNewFile();
                }
            } else if (fileDto.getCommand() == 2) {
                // 写入文件
                save2File("K://"+fileDto.getFileName(), fileDto.getBytes());
            }
        }
    }

    // 写入文件
    private static boolean save2File(String fileName, byte[] msg) {

        // 创建输出流
        OutputStream outputStream = null;

        try {
            // 创建文件
            File file = new File(fileName);
            // 读取父文件
            File parentFile = file.getParentFile();
            // 父文件不存在 并且 目录不存在
            if ((!parentFile.exists()) && (!parentFile.mkdirs())) {
                return false;
            }
            outputStream = new FileOutputStream(file, true);
            outputStream.write(msg);
            outputStream.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
