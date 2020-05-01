package cn.hamster3.bot.test;

import cn.hamster3.bot.core.BotCore;
import cn.hamster3.bot.event.GroupMessageEvent;
import cn.hamster3.bot.preset.thread.TimeLimitThread;
import cn.hamster3.bot.utils.MessageUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class BotTest {
    private BotCore botCore;

    @Before
    public void init() throws URISyntaxException {
        botCore = new BotCore("localhost", 8888, 2644895480L);
    }

    @Test
    public void sendGroupMessage() throws IOException {
        JsonObject object = botCore.sendMessage(
                MessageUtils.sendImageToGroup(612955360, "", new URL("https://www.hamster3.cn/images/bg3.jpg"))
        );
        System.out.println(object.toString());
    }

    @Test
    public void textImage() throws IOException {
        JsonObject object = botCore.sendMessage(
                MessageUtils.sendTextToGroup(767089578, "test")
        );
        System.out.println(object.toString());
    }

    @Test
    public void timeLimit() {
        new TimeLimitThread(3000) {
            @Override
            public void run() {
                int i = 0;
                while (!isFinished()) {
                    System.out.println(i++);
                }
            }
        }.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**Test for newly added events*/
    @Test
    public void validateEventParse(){
        //GroupMessages
        //AtMsg
        JsonObject object= JsonParser.parseString("{\"CurrentPacket\":{\"WebConnId\":\"EYyMODa6gWWaKq3wdbMR\",\"Data\":{\"FromNickName\":\"演变–cnlimiter\",\"MsgRandom\":524907237,\"Content\":\"{\\\"Content\\\":\\\"@只需一次 @只需一次 原始\\\",\\\"MsgSeq\\\":325035,\\\"SrcContent\\\":\\\"神之开局那个斧子有人知道是啥mod吗\\\",\\\"Tips\\\":\\\"[回复]\\\",\\\"UserID\\\":[3260914935,3260914935]}\",\"MsgSeq\":325040,\"FromGroupName\":\"我的世界-遗落之地\",\"FromUserId\":1812165465,\"MsgType\":\"AtMsg\",\"RedBaginfo\":null,\"MsgTime\":1588212571,\"FromGroupId\":1014431051}},\"CurrentQQ\":1260717118}").getAsJsonObject();
        GroupMessageEvent ge=new GroupMessageEvent(null,object);
        //PicMsg
        object=JsonParser.parseString("{\"CurrentPacket\":{\"WebConnId\":\"EYyMODa6gWWaKq3wdbMR\",\"Data\":{\"FromNickName\":\"只需一次\",\"MsgRandom\":2723851740,\"Content\":\"{\\\"GroupPic\\\":[{\\\"FileId\\\":2844774955,\\\"FileMd5\\\":\\\"4LKm2ZzXW0TPmFGG72HQuw==\\\",\\\"FileSize\\\":621,\\\"ForwordBuf\\\":\\\"Eip7RTBCMkE2RDktOUNENy01QjQ0LUNGOTgtNTE4NkVGNjFEMEJCfS5qcGciACoEAAAAADJcFTYgOTJrQTFDYTk4ZmQyMmI3OTVjYjFhMyAgICAgIDUwICAgICAgICAgICAgICAgIHtFMEIyQTZEOS05Q0Q3LTVCNDQtQ0Y5OC01MTg2RUY2MUQwQkJ9LmpwZ0E4q6S/zApAo+PyygdIUFBDWgBgAWoQ4LKm2ZzXW0TPmFGG72HQu3JcL2djaGF0cGljX25ldy8zMjYwOTE0OTM1LzEwMTQ0MzEwNTEtMjg0NDc3NDk1NS1FMEIyQTZEOTlDRDc1QjQ0Q0Y5ODUxODZFRjYxRDBCQi8xOTg/dGVybT0yNTWCAVovZ2NoYXRwaWNfbmV3LzMyNjA5MTQ5MzUvMTAxNDQzMTA1MS0yODQ0Nzc0OTU1LUUwQjJBNkQ5OUNENzVCNDRDRjk4NTE4NkVGNjFEMEJCLzA/dGVybT0yNTWgAegHsAEtuAEUyAHtBNgBLeABFPoBXC9nY2hhdHBpY19uZXcvMzI2MDkxNDkzNS8xMDE0NDMxMDUxLTI4NDQ3NzQ5NTUtRTBCMkE2RDk5Q0Q3NUI0NENGOTg1MTg2RUY2MUQwQkIvNDAwP3Rlcm09MjU1gAItiAIU\\\",\\\"ForwordField\\\":8,\\\"Url\\\":\\\"http://gchat.qpic.cn/gchatpic_new/3260914935/1014431051-2534335053-E0B2A6D99CD75B44CF985186EF61D0BB/0?vuin=1260717118\\\\u0026term=255\\\\u0026pictype=0\\\"}],\\\"Tips\\\":\\\"[群图片]\\\"}\",\"MsgSeq\":325037,\"FromGroupName\":\"我的世界-遗落之地\",\"FromUserId\":3260914935,\"MsgType\":\"PicMsg\",\"RedBaginfo\":null,\"MsgTime\":1588212542,\"FromGroupId\":1014431051}},\"CurrentQQ\":1260717118}").getAsJsonObject();
        ge=new GroupMessageEvent(null,object);
        //BigFaceMsg
        object=JsonParser.parseString("{\"CurrentPacket\":{\"WebConnId\":\"8CZMMRac9JB_6HuwBEKE\",\"Data\":{\"FromNickName\":\"WD小哥哥\",\"MsgRandom\":388016254,\"Content\":\"{\\\"Content\\\":\\\"[偷瞧][偷瞧]\\\",\\\"ForwordBuf\\\":\\\"Cghb5YG3556nXRAGGAEiEIHqVL5VRik3xAKNNYFILyIozroMMAM6EGQwYWM2YmViN2I0Mzk0MzZIAFDIAVjIAWoSCgYIrAIQrAIKBgjIARDIAUAB\\\",\\\"ForwordField\\\":6,\\\"Tips\\\":\\\"[大表情]\\\"}\",\"MsgSeq\":327497,\"FromGroupName\":\"我的世界-遗落之地\",\"FromUserId\":1304793916,\"MsgType\":\"BigFaceMsg\",\"RedBaginfo\":null,\"MsgTime\":1588264369,\"FromGroupId\":1014431051}},\"CurrentQQ\":1260717118}").getAsJsonObject();
        ge=new GroupMessageEvent(null,object);
        //VoiceMsg
        object=JsonParser.parseString("{\"CurrentPacket\":{\"WebConnId\":\"K-m_MKjgsuG-9WdlOb7Y\",\"Data\":{\"FromNickName\":\"森森\",\"MsgRandom\":708492231,\"Content\":\"{\\\"Tips\\\":\\\"[语音]\\\",\\\"Url\\\":\\\"http://grouptalk.c2c.qq.com/?ver=0\\\\u0026rkey=3062020101045b305902010102010102044b25043e04243938504f44647259554a6b3934556a646a6e6b5072565f7862743256645a6b513159355a02045eab83d3041f0000000866696c6574797065000000013100000005636f64656300000001310400\\\\u0026filetype=1\\\\u0026voice_codec=1\\\"}\",\"MsgSeq\":96462,\"FromGroupName\":\"☘-\uD83E\uDD16️IOTBOT\uD83E\uDD16️ - ☘\",\"FromUserId\":1260717118,\"MsgType\":\"VoiceMsg\",\"RedBaginfo\":null,\"MsgTime\":1588298707,\"FromGroupId\":757360354}},\"CurrentQQ\":1260717118}\n").getAsJsonObject();
        ge=new GroupMessageEvent(null,object);
        //FriendMessages
        //BigFaceMsg
        object=JsonParser.parseString("{\"CurrentPacket\":{\"WebConnId\":\"K-m_MKjgsuG-9WdlOb7Y\",\"Data\":{\"Content\":\"{\\\"Content\\\":\\\"[改不了][改不了]\\\",\\\"ForwordBuf\\\":\\\"Cgtb5pS55LiN5LqGXRAGGAEiEH5yn9r7YwMSGnA4IU9H1HYoh6IMMAM6EGRjM2UwZTNjYTEyODUzYTlIAFDIAVjIAWoSCgYIrAIQrAIKBgjIARDIAUAB\\\",\\\"ForwordField\\\":6,\\\"Tips\\\":\\\"[大表情]\\\"}\",\"MsgSeq\":59827,\"FromUin\":1260717118,\"MsgType\":\"BigFaceMsg\",\"ToUin\":1260717118,\"RedBaginfo\":null}},\"CurrentQQ\":1260717118}\n").getAsJsonObject();
        //PicMsg
        object=JsonParser.parseString("{\"CurrentPacket\":{\"WebConnId\":\"K-m_MKjgsuG-9WdlOb7Y\",\"Data\":{\"Content\":\"{\\\"Content\\\":\\\"\\\",\\\"FileMd5\\\":\\\"I77d7EgNvFwrSuGHEMCt/Q==\\\",\\\"FileSize\\\":80517,\\\"Path\\\":\\\"/1260717118-2522977340-23BEDDEC480DBC5C2B4AE18710C0ADFD\\\",\\\"Tips\\\":\\\"[好友图片]\\\",\\\"Url\\\":\\\"http://c2cpicdw.qpic.cn/offpic_new/1260717118/1260717118-2522977340-23BEDDEC480DBC5C2B4AE18710C0ADFD/0\\\"}\",\"MsgSeq\":59826,\"FromUin\":1260717118,\"MsgType\":\"PicMsg\",\"ToUin\":1260717118,\"RedBaginfo\":null}},\"CurrentQQ\":1260717118}\n").getAsJsonObject();
    }
}
