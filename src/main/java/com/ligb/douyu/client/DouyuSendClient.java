package com.ligb.douyu.client;

import com.ligb.douyu.utils.KeepAliveThread;
import com.ligb.douyu.utils.MsgHandler;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Random;

public abstract class DouyuSendClient extends DouyuClient {
    public String url = "wss://wsproxy.douyu.com:" + (6671+new Random().nextInt(5)) +"/";

    public DouyuSendClient(String roomid,String cookies){
        try {
            this.roomid = roomid;
            this.cookies = cookies;
            final DouyuSendClient douyuClient = this;
            logger.info("创建发送线程，地址："+url);
            client = new WebSocketClient(new URI(url)) {
                @Override
                public void onOpen(ServerHandshake arg0) {
                    logger.info(getInfo()+"发送线程创建成功");
                    isRun = true;
                    //发送登录请求
                    client.send(MsgHandler.getLoginData(douyuClient.roomid, douyuClient.cookies));
                    //启用心跳检测线程
                    new KeepAliveThread(douyuClient).start();
                }

                @Override
                public void onMessage(String arg0) {
                    logger.info(getInfo()+"收到消息1"+arg0);
                }

                @Override
                public void onError(Exception arg0) {
                    arg0.printStackTrace();
                    logger.info(getInfo()+"发生错误已关闭");
                    isRun = false;
                }

                @Override
                public void onClose(int arg0, String arg1, boolean arg2) {
                    logger.info(getInfo()+"链接已关闭");
                    isRun = false;
                }

                @Override
                public void onMessage(ByteBuffer bytes) {
                    try {
                        String msg = new String(bytes.array(),"utf-8");
                        logger.info(getInfo()+"收到消息"+msg);
                        if(msg.indexOf("type@=loginres") > -1 && !isLogin){
                            isLogin = true;
                            client.send(MsgHandler.getH5ckreq("h5ckreq",douyuClient.roomid));
                            douyuClient.onLogin();
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void send(byte[] data) {
                    logger.info(getInfo()+"发送请求"+new String(data));
                    super.send(data);
                }
            };
            client.setConnectionLostTimeout(timeout);
            client.connect();
        } catch (Exception e) {
            logger.error("创建异常",e);
        }
    }

    public abstract void onLogin();

    public boolean sendMsg(String msg){
        try{
            client.send(MsgHandler.getMsgData(msg));
        }catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     //     * 发送心跳包.
     //     */
    public void keepAlive() {
        byte[] data = MsgHandler.getSendKeepAliveData();
        try {
            client.send(data);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(getInfo()+"socket closed!!!!");
        }
    }
}
