import com.ligb.douyu.client.DouyuReceiveClient;
import com.ligb.douyu.client.DouyuSendClient;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        DouyuReceiveClient receiveClient = new DouyuReceiveClient("7517996"){
            public void onMessage(String msg) {
                //接收消息
            }
        };
        DouyuSendClient sendClient = new DouyuSendClient("7517996","[{\"domain\": \".douyu.com\", \"httpOnly\": false, \"name\": \"Hm_lpvt_e99aee90ec1b2106afe7ec3b199020a7\", \"path\": \"/\", \"secure\": false, \"value\": \"1577872451\"}, {\"domain\": \".www.douyu.com\", \"httpOnly\": false, \"name\": \"acf_biz\", \"path\": \"/\", \"secure\": true, \"value\": \"1\"}, {\"domain\": \".www.douyu.com\", \"httpOnly\": false, \"name\": \"acf_own_room\", \"path\": \"/\", \"secure\": true, \"value\": \"0\"}, {\"domain\": \".www.douyu.com\", \"httpOnly\": false, \"name\": \"Hm_lvt_e99aee90ec1b2106afe7ec3b199020a7\", \"path\": \"/\", \"secure\": true, \"value\": \"1577619853\"}, {\"domain\": \".www.douyu.com\", \"httpOnly\": false, \"name\": \"acf_avatar\", \"path\": \"/\", \"secure\": true, \"value\": \"https%3A%2F%2Fapic.douyucdn.cn%2Fupload%2Favatar%2Fdefault%2F03_\"}, {\"domain\": \".www.douyu.com\", \"httpOnly\": false, \"name\": \"dy_auth\", \"path\": \"/\", \"secure\": true, \"value\": \"8a0cfGOgswIBkVP2Qqxfbf9vD9LTgHLQeJhbtqEErmJ4vZS8%2BOoqWbA2wJBx4Vw78tzSIQI%2FaWKf5TN0dsY7ZkgIGUJIKz2Hj1AnzU0AvLf1tl6khzlM5Dg\"}, {\"domain\": \".www.douyu.com\", \"httpOnly\": false, \"name\": \"dy_did\", \"path\": \"/\", \"secure\": true, \"value\": \"a3a6f0ba377041ff7b2d79bd00081501\"}, {\"domain\": \".www.douyu.com\", \"httpOnly\": false, \"name\": \"PHPSESSID\", \"path\": \"/\", \"secure\": true, \"value\": \"tvkqvrq0p01008iinemmhdvml1\"}, {\"domain\": \".www.douyu.com\", \"httpOnly\": false, \"name\": \"acf_username\", \"path\": \"/\", \"secure\": true, \"value\": \"276261951\"}, {\"domain\": \".www.douyu.com\", \"httpOnly\": false, \"name\": \"Hm_lpvt_e99aee90ec1b2106afe7ec3b199020a7\", \"path\": \"/\", \"secure\": true, \"value\": \"1577809295\"}, {\"domain\": \".www.douyu.com\", \"httpOnly\": false, \"name\": \"acf_uid\", \"path\": \"/\", \"secure\": true, \"value\": \"276261951\"}, {\"domain\": \".www.douyu.com\", \"httpOnly\": false, \"name\": \"acf_ltkid\", \"path\": \"/\", \"secure\": true, \"value\": \"14760205\"}, {\"domain\": \".www.douyu.com\", \"httpOnly\": false, \"name\": \"wan_auth37wan\", \"path\": \"/\", \"secure\": true, \"value\": \"9a764d28797fiUtPPDhRa6xtvs3vmQavUwkhwU2vVVhPBox8MsN2fRrqrLPfkANUevcyDWXC6KHqJxzOmVaCNgupTUMMo93wkIAMgvk4m0wH02rRL8U\"}, {\"domain\": \".www.douyu.com\", \"httpOnly\": false, \"name\": \"acf_stk\", \"path\": \"/\", \"secure\": true, \"value\": \"0f6664309e479f19\"}, {\"domain\": \".www.douyu.com\", \"httpOnly\": false, \"name\": \"acf_ct\", \"path\": \"/\", \"secure\": true, \"value\": \"0\"}, {\"domain\": \".douyu.com\", \"expiry\": 1609408451, \"httpOnly\": false, \"name\": \"Hm_lvt_e99aee90ec1b2106afe7ec3b199020a7\", \"path\": \"/\", \"secure\": false, \"value\": \"1577872412\"}, {\"domain\": \".www.douyu.com\", \"httpOnly\": false, \"name\": \"acf_auth\", \"path\": \"/\", \"secure\": true, \"value\": \"ba16stVugjWvw%2BbEFYlwlrdLqksa90cqm4yEe16PsIRev20yKT2Uec1TuOYoNHxDJQ7W99HXGtcBNG1g5oRHCdFv4LxShbj17saSamTZKGYRwqeZ6ZX7GnY\"}, {\"domain\": \".www.douyu.com\", \"httpOnly\": false, \"name\": \"acf_nickname\", \"path\": \"/\", \"secure\": true, \"value\": \"%E5%B8%86%E7%9A%87%E7%81%AC%E7%89%B9%E5%B7%A5%E9%98%9F\"}, {\"domain\": \".www.douyu.com\", \"httpOnly\": false, \"name\": \"acf_phonestatus\", \"path\": \"/\", \"secure\": true, \"value\": \"1\"}, {\"domain\": \"www.douyu.com\", \"expiry\": 1893232450, \"httpOnly\": false, \"name\": \"acf_did\", \"path\": \"/\", \"secure\": false, \"value\": \"8d5cddaaa971631e8ab4328600081501\"}, {\"domain\": \".www.douyu.com\", \"httpOnly\": false, \"name\": \"acf_ccn\", \"path\": \"/\", \"secure\": true, \"value\": \"8d8598a92557436d2b87780b64cd27b1\"}, {\"domain\": \".www.douyu.com\", \"httpOnly\": false, \"name\": \"acf_groupid\", \"path\": \"/\", \"secure\": true, \"value\": \"1\"}, {\"domain\": \".www.douyu.com\", \"httpOnly\": false, \"name\": \"smidV2\", \"path\": \"/\", \"secure\": true, \"value\": \"201912261821526fb60ca76ad793b5a46c215e4dab409a006951c6a1fe403e0\"}, {\"domain\": \".www.douyu.com\", \"httpOnly\": false, \"name\": \"acf_did\", \"path\": \"/\", \"secure\": true, \"value\": \"a3a6f0ba377041ff7b2d79bd00081501\"}, {\"domain\": \".douyu.com\", \"expiry\": 1893232426, \"httpOnly\": false, \"name\": \"dy_did\", \"path\": \"/\", \"secure\": false, \"value\": \"8d5cddaaa971631e8ab4328600081501\"}]"){
            public void onLogin() {
                //登录成功

                //发送弹幕
                this.sendMsg("主播你好，今天天气不错");
            }
        };
        Thread.sleep(20*1000);
        //停止发送线程
        sendClient.isRun = false;
        Thread.sleep(15*1000);
        //停止接收线程
        receiveClient.isRun = false;
    }
}
