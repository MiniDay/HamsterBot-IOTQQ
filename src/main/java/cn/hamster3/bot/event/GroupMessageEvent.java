package cn.hamster3.bot.event;

import cn.hamster3.bot.core.BotCore;
import cn.hamster3.bot.data.MessageType;
import cn.hamster3.bot.data.Picture;
import cn.hamster3.bot.utils.MessageUtils;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class GroupMessageEvent extends MessageEvent {
    // 群号(所有消息类型均有)
    private long groupID;
    // 群名称(所有消息类型均有)
    private String groupNickName;
    // 发送者群名片(所有消息类型均有)
    private String senderNickName;
    // 消息顺序号(所有消息类型均有)
    private long messageSequence;
    // 消息发送时间(所有消息类型均有)
    private long messageTime;
    // 消息随机码(所有消息类型均有)
    private long messageRandom;

    //@ 到的用户ID(AtMsg)
    private long[] atUsers;
    //消息中的图片(PicMsg)
    private Picture[] pictures;
    //非文本消息的表示形式(AtMsg,PicMsg,BigFaceMsg,VoiceMsg)
    private String tips;
    //消息中的大表情，其中的参数不完整，只有ForwordBuf和ForwordField(BigFaceMsg)
    private Picture bigFace;
    //语音消息的url(VoiceMsg)
    private String voiceUrl;


    public GroupMessageEvent(BotCore botCore, JsonObject object) {
        super(botCore, object);
        JsonObject currentPacket = object.getAsJsonObject("CurrentPacket");
        JsonObject data = currentPacket.getAsJsonObject("Data");

        if("TextMsg".equalsIgnoreCase(data.get("MsgType").getAsString())
        ||"AtMsg".equalsIgnoreCase(data.get("MsgType").getAsString())
        ||"PicMsg".equalsIgnoreCase(data.get("MsgType").getAsString())
        ||"BigFaceMsg".equalsIgnoreCase(data.get("MsgType").getAsString())) {
            messageType = MessageType.valueOf(data.get("MsgType").getAsString());
            groupNickName = data.get("FromGroupName").getAsString();
            senderNickName = data.get("FromNickName").getAsString();

            messageRandom = data.get("MsgRandom").getAsLong();
            groupID = data.get("FromGroupId").getAsLong();
            senderID = data.get("FromUserId").getAsLong();
            messageTime = data.get("MsgTime").getAsLong();
            messageSequence = data.get("MsgSeq").getAsLong();
        }

        if ("TextMsg".equalsIgnoreCase(data.get("MsgType").getAsString())) {
            message = data.get("Content").getAsString();
        }else if("AtMsg".equalsIgnoreCase(data.get("MsgType").getAsString())){
            try {
                JSONObject content = new JSONObject(data.get("Content").getAsString());
                message=content.getString("Content");
                tips=content.getString("Tips");
                JSONArray userArray=content.getJSONArray("UserID");
                atUsers=new long[userArray.length()];
                for(int i=0;i<userArray.length();i++)
                    atUsers[i]=userArray.getLong(i);
            }catch (JSONException e){
                System.err.println("AtMsg解析失败，详细信息:");
                e.printStackTrace();
            }
        }else if("PicMsg".equalsIgnoreCase(data.get("MsgType").getAsString())){
            try{
                JSONObject content=new JSONObject(data.get("Content").getAsString());
                if(content.has("Content"))message=content.getString("Content");
                else message="";
                JSONArray pics=content.getJSONArray("GroupPic");
                pictures=new Picture[pics.length()];
                tips=content.getString("Tips");
                for(int i=0;i<pics.length();i++){
                    Picture temp=new Picture();
                    JSONObject tempObject=pics.getJSONObject(i);
                    temp.fileID=tempObject.getLong("FileId");
                    temp.fileMD5=tempObject.getString("FileMd5");
                    temp.fileSize=tempObject.getInt("FileSize");
                    temp.forwordBuf=tempObject.getString("ForwordBuf");
                    temp.url=tempObject.getString("Url");
                    temp.forwordField=tempObject.getString("ForwordField");
                    pictures[i]=temp;
                }
            }catch(JSONException e){
                System.err.println("PicMsg解析失败，详细信息:");
                e.printStackTrace();
            }
        }else if("BigFaceMsg".equalsIgnoreCase(data.get("MsgType").getAsString())){
            try{
                JSONObject content=new JSONObject(data.get("Content").getAsString());
                message=content.getString("Content");
                bigFace=new Picture();
                bigFace.forwordBuf=content.getString("ForwordBuf");
                bigFace.forwordField=content.getString("ForwordField");
            }catch (JSONException e){
                System.err.println("BigFaceMsg解析失败，详细信息:");
                e.printStackTrace();
            }
        }else if("VoiceMsg".equalsIgnoreCase(data.get("MsgType").getAsString())){
            try{
                JSONObject content=new JSONObject(data.get("Content").getAsString());
                message=content.getString("Tips");
                tips=content.getString("Tips");
                voiceUrl=content.getString("Url");
            }catch(JSONException e){
                System.err.println("VoiceMsg解析失败，详细信息:");
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
                MessageUtils.sendTextToGroup(groupID, message)
        );
    }

    @Override
    public JsonObject reply(String message, String image) throws IOException {
        return getBotCore().sendMessage(
                MessageUtils.sendImageToGroup(groupID, message, image)
        );
    }

    public long getGroupID() {
        return groupID;
    }

    public String getGroupNickName() {
        return groupNickName;
    }

    public String getSenderNickName() {
        return senderNickName;
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

    public long getMessageTime() {
        return messageTime;
    }

    public long getMessageRandom() {
        return messageRandom;
    }
    public long[] getAtUsers() {
        return atUsers;
    }
    public Picture[] getPictures() {
        return pictures;
    }
    public String getTips() {
        return tips;
    }
    public Picture getBigFace() {
        return bigFace;
    }
    public String getVoiceUrl() {
        return voiceUrl;
    }

}