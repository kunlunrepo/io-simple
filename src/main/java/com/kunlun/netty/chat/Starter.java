package com.kunlun.netty.chat;


import com.kunlun.netty.chat.server.DiscardServer;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-18 15:52
 */
public class Starter {

    public static void main(String[] args) throws Exception {
        new DiscardServer(9001).run();
    }

}
