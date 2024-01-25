package com.kunlun.netty.upload;

import lombok.Data;

/**
 * description : 文件对象
 *
 * @author kunlunrepo
 * date :  2024-01-25 15:24
 */
@Data
public class FileDto {

    // 文件名
    private String fileName;

    // 命令类型 1.创建文件 2.写入文件
    private Integer command;

    // 文件字节数组
    private byte[] bytes;

}
