package com.ligb.douyu.client;

import com.ligb.douyu.utils.KeepAliveThread;
import com.ligb.douyu.utils.MsgHandler;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Random;

public abstract class DouyuReceiveClient extends DouyuClient {
    public String url = "wss://danmuproxy.douyu.com:" + (8501+new Random().nextInt(6)) +"/";

    public DouyuReceiveClient(String roomid){
        try {
            this.roomid = roomid;
            final DouyuReceiveClient douyuClient = this;
            logger.info("创建发送线程，地址："+url);
            client = new WebSocketClient(new URI(url)) {
                @Override
                public void onOpen(ServerHandshake arg0) {
                    isRun = true;
                    logger.info(getInfo()+"发送线程创建成功");
                    //发送登录请求
                    client.send(MsgHandler.getDanmuLoginData(douyuClient.roomid));
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
                    isLogin = false;
                    isRun = false;
                }

                @Override
                public void onClose(int arg0, String arg1, boolean arg2) {
                    logger.info(getInfo()+"链接已关闭");
                    isLogin = false;
                    isRun = false;
                }

                @Override
                public void onMessage(ByteBuffer bytes) {
                    List<String> msgList = MsgHandler.receiveMsg(bytes.array());
                    for(String msg : msgList){
                        logger.info(getInfo()+"收到消息"+msg);
                        if(msg.indexOf("type@=loginres") > -1 && !isLogin){
                            isLogin = true;
                            client.send(MsgHandler.getJoinGroupData(douyuClient.roomid, "-9999"));
                        }
                        douyuClient.onMessage(msg);
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

    public abstract void onMessage(String msg);

    /**
     //     * 发送心跳包.
     //     */
    public void keepAlive() {
        byte[] data = MsgHandler.getReceiveKeepAliveData();
        try {
            client.send(data);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(getInfo()+"socket closed!!!!");
        }
    }
}
