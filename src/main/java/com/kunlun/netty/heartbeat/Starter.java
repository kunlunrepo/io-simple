package com.kunlun.netty.heartbeat;

import com.kunlun.netty.heartbeat.server.DiscardServer;

/**
 * description : 心跳机制
 *
 * @author kunlunrepo
 * date :  2024-01-25 14:31
 */
public class Starter {


    public static void main(String[] args) throws Exception {
        new DiscardServer(9001).run();
    }
}
