package com.ligb.douyu.client;

import org.apache.log4j.Logger;
import org.java_websocket.client.WebSocketClient;

public class DouyuClient{
    public Logger logger = Logger.getLogger(DouyuReceiveClient.class);

    public boolean isRun = false;
    public boolean isLogin = false;
    public String url;
    public String roomid;
    public String cookies;
    public int timeout = 300*1000;
    public WebSocketClient client;

    public String getInfo(){
        return "";
    }

    public void keepAlive() {
    }
}
