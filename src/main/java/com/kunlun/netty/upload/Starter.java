package com.kunlun.netty.upload;

import com.kunlun.netty.upload.server.UploadFileServer;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2024-01-25 15:14
 */
public class Starter {
    public static void main(String[] args) throws Exception {
        new UploadFileServer(9001).run();
    }
}
