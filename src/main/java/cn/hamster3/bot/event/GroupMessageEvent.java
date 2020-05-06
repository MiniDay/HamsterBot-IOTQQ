package cn.hamster3.bot.event;

import cn.hamster3.bot.core.BotCore;
import cn.hamster3.bot.data.MessageType;
import cn.hamster3.bot.data.Picture;
import cn.hamster3.bot.utils.MessageUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.Arrays;

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

        try {
            messageType = MessageType.valueOf(data.get("MsgType").getAsString());
        } catch (IllegalArgumentException e) {
            messageType = MessageType.UnknownMsg;
            message = "[此消息类型暂不支持解析]";
        }

        groupNickName = data.get("FromGroupName").getAsString();
        senderNickName = data.get("FromNickName").getAsString();

        messageRandom = data.get("MsgRandom").getAsLong();
        groupID = data.get("FromGroupId").getAsLong();
        senderID = data.get("FromUserId").getAsLong();
        messageTime = data.get("MsgTime").getAsLong();
        messageSequence = data.get("MsgSeq").getAsLong();

        switch (messageType) {
            case TextMsg: {
                message = data.get("Content").getAsString();
                break;
            }
            case AtMsg: {
                JsonObject content = JsonParser.parseString(data.get("Content").getAsString()).getAsJsonObject();
                message = content.get("Content").getAsString();
                tips = content.get("Tips").getAsString();
                JsonArray userArray = content.getAsJsonArray("UserID");
                atUsers = new long[userArray.size()];
                for (int i = 0; i < userArray.size(); i++) {
                    atUsers[i] = userArray.get(i).getAsLong();
                }
                break;
            }
            case PicMsg: {
                JsonObject content = JsonParser.parseString(data.get("Content").getAsString()).getAsJsonObject();
                if (content.has("Content")) {
                    message = content.get("Content").getAsString();
                } else {
                    message = "";
                }
                tips = content.get("Tips").getAsString();
                JsonArray pics = content.getAsJsonArray("GroupPic");
                pictures = new Picture[pics.size()];
                for (int i = 0; i < pics.size(); i++) {
                    JsonObject pic = pics.get(i).getAsJsonObject();
                    Picture temp = new Picture(
                            pic.get("Url").getAsString(),
                            pic.get("FileId").getAsLong(),
                            pic.get("FileSize").getAsInt(),
                            pic.get("FileMd5").getAsString(),
                            pic.get("ForwordBuf").getAsString(),
                            pic.get("ForwordField").getAsString()
                    );
                    pictures[i] = temp;
                }
                break;
            }
            case BigFaceMsg: {
                JsonObject content = JsonParser.parseString(data.get("Content").getAsString()).getAsJsonObject();
                message = content.get("Content").getAsString();
                bigFace = new Picture(
                        content.get("ForwordBuf").getAsString(),
                        content.get("ForwordField").getAsString()
                );
                break;
            }
            case VoiceMsg: {
                JsonObject content = JsonParser.parseString(data.get("Content").getAsString()).getAsJsonObject();
                message = content.get("message").getAsString();
                tips = content.get("Tips").getAsString();
                voiceUrl = content.get("Url").getAsString();
                break;
            }
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

    @Override
    public String toString() {
        return "GroupMessageEvent{" +
                "groupID=" + groupID +
                ", groupNickName='" + groupNickName + '\'' +
                ", senderNickName='" + senderNickName + '\'' +
                ", messageSequence=" + messageSequence +
                ", messageTime=" + messageTime +
                ", messageRandom=" + messageRandom +
                ", atUsers=" + Arrays.toString(atUsers) +
                ", pictures=" + Arrays.toString(pictures) +
                ", tips='" + tips + '\'' +
                ", bigFace=" + bigFace +
                ", voiceUrl='" + voiceUrl + '\'' +
                ", senderID=" + senderID +
                ", message='" + message + '\'' +
                ", messageType=" + messageType +
                '}';
    }
}