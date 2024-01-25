package com.kunlun.netty.upload.codec;

import com.kunlun.netty.upload.FileDto;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * description : 上传文件的解码器
 *
 * @author kunlunrepo
 * date :  2024-01-25 15:19
 */
public class UploadFileDecodecer extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 字符缓存对象小于8字节，不处理
        if (in.readableBytes() < 8) {
            return;
        }

        // 命令类型
        int command = in.readInt();

        // 文件长度
        long fileNameLen = in.readInt();
        System.out.println("文件长度：" + fileNameLen);

        // 如果字节缓存对象的长度小于文件长度，不处理
        if (in.readableBytes() < fileNameLen) {
            // 重置字节缓存对象
            in.resetReaderIndex();
            return;
        }

        // 创建文件
        FileDto fileDto = new FileDto();
        byte[] data = new byte[(int) fileNameLen];
        in.readBytes(data);
        String fileName = new String(data);
        fileDto.setFileName(fileName);
        fileDto.setCommand(command);

        // 如果命令是写入文件
        if (command == 2) {
            int dataLen = in.readInt();
            if (in.readableBytes() < dataLen) {
                in.resetReaderIndex();
                return;
            }
            byte[] fileData = new byte[dataLen];
            in.readBytes(fileData);
            fileDto.setBytes(fileData);
        }

        // 标记当前的读索引
        // 通过这种方式，你可以临时读取或跳过 ByteBuf 中的部分数据，并在后续操作中回到初始读取位置，这对于实现如协议解析、查找特定分隔符后的数据块等场景非常有用
        in.markReaderIndex();

        // 发送文件
        out.add(fileDto);
    }
}
