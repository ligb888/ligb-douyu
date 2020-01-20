package com.ligb.douyu.utils;

import com.ligb.douyu.client.DouyuClient;
import org.apache.log4j.Logger;

public class KeepAliveThread extends Thread {

    private Logger logger = Logger.getLogger(KeepAliveThread.class);

    public DouyuClient client;

    public KeepAliveThread(DouyuClient client){
        this.client = client;
    }

    public void run() {
        //最多检查5次是否登录
        for(int i = 0; i < 5; i++){
            if(client.isRun && client.isLogin){
                break;
            }
            try {
                Thread.sleep(5*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //登录成功保持心跳
        while (client.isRun && client.isLogin) {
            try {
                client.keepAlive();
                Thread.sleep(30*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        client.client.close();
    }
}
