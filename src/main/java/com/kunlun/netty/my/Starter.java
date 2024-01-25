package com.kunlun.netty.my;


import com.kunlun.netty.my.server.DiscardServer;

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
