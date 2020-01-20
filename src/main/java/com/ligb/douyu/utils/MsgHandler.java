package com.ligb.douyu.utils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.bson.Document;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MsgHandler {

    private static final int MESSAGE_TYPE_CLIENT = 689;
    private static final int MESSAGE_TYPE_SERVER = 690;
    private static final int BULK = 55;
    private static final String TYPE_BULLET_SCREEN = "chatmsg";
    private static final String TYPE_MRKL = "mrkl";

    private static final String BULLET_SCREEN_NICKNAME = "nn";
    private static final String BULLET_SCREEN_TEXT = "txt";
    private static final String BULLET_SCREEN_LEVEL = "level";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    private static List<Document> docs = new ArrayList<Document>();
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

    private static Logger logger = Logger.getLogger(MsgHandler.class);

    public static byte[] getH5ckreq(String type,String roomid){
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("type", type);
        params.put("rid", roomid);
        params.put("ti", "2501"+sdf.format(new Date()));
        return generateDyMsg(new MsgEncoder().encode(params));
    }

    public static byte[] getH5cs(){
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("type", "h5cs");
        params.put("result", UUID.randomUUID().toString().replace("-", "").toLowerCase());
        return generateDyMsg(new MsgEncoder().encode(params));
    }

    public static byte[] getSendKeepAliveData() {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("type", "keeplive");
        params.put("tick", String.valueOf(System.currentTimeMillis()/1000));
        params.put("vbw", "0");
        params.put("cdn", "");
        params.put("kd", UUID.randomUUID().toString().replace("-", "").toLowerCase());
        return generateDyMsg(new MsgEncoder().encode(params));
    }

    public static byte[] getReceiveKeepAliveData() {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("type", "mrkl");
        return generateDyMsg(new MsgEncoder().encode(params));
    }

    public static byte[] getNormalLoginData(String roomId) {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("type", "loginreq");
        params.put("roomid", roomId);

        return generateDyMsg(new MsgEncoder().encode(params));
    }

    public static byte[] getMsgData(String msg){
        String timestamp = String.valueOf(System.currentTimeMillis());
//        String timestamp = "1579166771580";
        msg = msg.replaceAll("@", "@A").replaceAll("/", "@S");
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("content", msg);
        params.put("col", "0");
        params.put("type", "chatmessage");
//        params.put("dy", "954d4911ce4e50248555a3d500061501");
        params.put("sender", "4892184");
        params.put("ifs", "0");
        params.put("nc", "0");
        params.put("dat", "0");
        params.put("rev", "0");
        params.put("admzq", "0");
        params.put("cst", timestamp);
        params.put("dmt", "3");
//        params.put("dmvv", "019bb0ccd2");

        return generateDyMsg(new MsgEncoder().encode(params));
    }

    public static byte[] getLoginData(String roomId,String cookies) {
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
//        String uuid = "b61c83455a8ff23d6a72b10500071531";
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
//        String timestamp = "1579163742";
        String vk = MD5Util.MD5(timestamp + "r5*^5;}2#${XF[h+;'./.Q'1;,-]f'p[" + uuid);
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("type", "loginreq");
        params.put("password", "");
        params.put("roomid", roomId);
        JSONArray jsonArray = JSONArray.parseArray(cookies);
        for(Object json : jsonArray){
            String name = ((JSONObject) json).getString("name");
            String value = ((JSONObject) json).getString("value");
            if("acf_username".equals(name)){
                params.put("username", value);
            }else if("acf_ltkid".equals(name)){
                params.put("ltkid", value);
            }else if("acf_biz".equals(name)){
                params.put("biz", value);
            }else if("acf_stk".equals(name)){
                params.put("stk", value);
            }else if("acf_ct".equals(name)){
                params.put("ct", value);
            }
        }

        params.put("devid", uuid);
        params.put("rt", timestamp);
        params.put("pt", "2");
        params.put("vk", vk);
        params.put("ver", "20180222");
        params.put("aver", "219032101");
        params.put("dmbt", "mobile safari");
        params.put("dmbv", "11");

        return generateDyMsg(new MsgEncoder().encode(params));
    }

    public static byte[] getDanmuLoginData(String roomId) {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("type", "loginreq");
        params.put("roomid", roomId);
        params.put("dfl", "sn@AA=105@ASss@AA=1");
        params.put("username", "visitor"+String.valueOf((System.currentTimeMillis()/1000000)+69).substring(4)+String.valueOf(new Random().nextInt(10000)));
        params.put("uid", String.valueOf(new Random().nextInt(400000000)+1000000000));
        params.put("ver", "20190610");
        params.put("aver", "218101901");
        params.put("ct", "0");

        return generateDyMsg(new MsgEncoder().encode(params));
    }

    public static byte[] getJoinGroupData(String roomId, String groupId) {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("type", "joingroup");
        params.put("rid", roomId);
        params.put("gid", groupId);

        return generateDyMsg(new MsgEncoder().encode(params));
    }

    /**
     * 接收弹幕等消息.
     */
    public static List<String> receiveMsg(byte[] recvByte) {
        List<String> retList = new ArrayList<String>();
        if (recvByte.length < 12) {
            return retList;
        }
        String dataStr = new String(recvByte, 12, recvByte.length - 12);
        String[] msgs = dataStr.split("\0");
        for (String msg : msgs) {
            if (msg.length() > 7) {
                retList.add(msg);
            }
        }
        return retList;
    }

    /**
     * 按消息类型分发消息.
     */
    public static void recvMsgHandler(String data) {
        String[] params = data.split("/");
        if (params[0] != null && params[0].length() > 7) {
            String type = params[0].substring(6);
            if (!TYPE_MRKL.equals(type)) {
            }
        }
    }

    private static byte[] generateDyMsg(String data) {
        //System.out.println(data);
        ByteArrayOutputStream boutput = new ByteArrayOutputStream();
        DataOutputStream doutput = new DataOutputStream(boutput);

        try {
            boutput.reset();
            doutput.write(FormatTransfer.toLH(data.getBytes().length + 8), 0, 4);        // 4 bytes packet length
            doutput.write(FormatTransfer.toLH(data.getBytes().length + 8), 0, 4);        // 4 bytes packet length
            doutput.write(FormatTransfer.toLH(MESSAGE_TYPE_CLIENT), 0, 2);   // 2 bytes message type
            doutput.writeByte(0);                                               // 1 bytes encrypt
            doutput.writeByte(0);                                               // 1 bytes reserve
            doutput.write(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return boutput.toByteArray();
    }

}
