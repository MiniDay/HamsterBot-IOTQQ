package cn.hamster3.bot.event;

import cn.hamster3.bot.core.BotCore;
import cn.hamster3.bot.data.MessageType;
import cn.hamster3.bot.data.Picture;
import cn.hamster3.bot.utils.MessageUtils;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class FriendMessageEvent extends MessageEvent {
    // 接受者的QQ
    private long toUser;

    // 消息顺序号
    private long messageSequence;
    //消息中的图片或大表情
    private Picture picture;
    //非文本消息的表示形式
    private String tips;


    public FriendMessageEvent(BotCore botCore, JsonObject object) {
        super(botCore, object);
        JsonObject currentPacket = object.getAsJsonObject("CurrentPacket");
        JsonObject data = currentPacket.getAsJsonObject("Data");

        if("TextMsg".equalsIgnoreCase(data.get("MsgType").getAsString())
        ||"PicMsg".equalsIgnoreCase(data.get("MsgType").getAsString())
        ||"BigFaceMsg".equalsIgnoreCase(data.get("MsgType").getAsString())){
            messageType = MessageType.valueOf(data.get("MsgType").getAsString());
            senderID = data.get("FromUin").getAsLong();
            messageSequence = data.get("MsgSeq").getAsLong();
            toUser = data.get("ToUin").getAsLong();
        }

        if ("TextMsg".equalsIgnoreCase(data.get("MsgType").getAsString())) {
            message = data.get("Content").getAsString();
        }else if("PicMsg".equalsIgnoreCase(data.get("MsgType").getAsString())){
            try{
                JSONObject content=new JSONObject(data.get("Content").getAsString());
                message=content.getString("Content");
                tips=content.getString("Tips");
                picture=new Picture();
                picture.url=content.getString("Url");
                picture.fileMD5=content.getString("FileMd5");
                picture.path=content.getString("Path");
                picture.fileSize=content.getInt("FileSize");
            }catch(JSONException e){
                System.err.println("PicMsg解析失败，详细信息:");
                e.printStackTrace();
            }
        }else if("BigFaceMsg".equalsIgnoreCase(data.get("MsgType").getAsString())){
            try{
                JSONObject content=new JSONObject(data.get("Content").getAsString());
                message=content.getString("Content");
                tips=content.getString("Tips");
                picture=new Picture();
                picture.forwordBuf=content.getString("ForwordBuf");
                picture.forwordField=content.getString("ForwordField");
            }catch(JSONException e){
                System.err.println("PicMsg解析失败，详细信息:");
                e.printStackTrace();
            }
        }else{
            messageType=MessageType.valueOf("UnknownMsg");
            message="[此消息类型暂不支持解析]";
        }
    }

    @Override
    public JsonObject reply(String message) throws IOException {
        return getBotCore().sendMessage(
                MessageUtils.sendTextToFriend(senderID, message)
        );
    }

    @Override
    public JsonObject reply(String message, String image) throws IOException {
        return getBotCore().sendMessage(
                MessageUtils.sendImageToFriend(senderID, message, image)
        );
    }

    public long getToUser() {
        return toUser;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getMessage() {
        return message;
    }

    public long getMessageSequence() {
        return messageSequence;
    }
}