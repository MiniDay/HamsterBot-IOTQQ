package cn.hamster3.bot.event;

import cn.hamster3.bot.core.BotCore;
import cn.hamster3.bot.data.MessageType;
import cn.hamster3.bot.data.Picture;
import cn.hamster3.bot.utils.MessageUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

public class FriendMessageEvent extends MessageEvent {
    // 接受者的QQ(所有消息类型均有)
    private long toUser;
    // 消息顺序号(所有消息类型均有)
    private long messageSequence;

    //消息中的图片或大表情(PicMsg,BigFaceMsg)
    private Picture picture;
    //非文本消息的表示形式(PicMsg,BigFaceMsg)
    private String tips;


    public FriendMessageEvent(BotCore botCore, JsonObject object) {
        super(botCore, object);
        JsonObject currentPacket = object.getAsJsonObject("CurrentPacket");
        JsonObject data = currentPacket.getAsJsonObject("Data");

        try {
            messageType = MessageType.valueOf(data.get("MsgType").getAsString());
        } catch (IllegalArgumentException e) {
            messageType = MessageType.UnknownMsg;
            message = "[此消息类型暂不支持解析]";
        }

        senderID = data.get("FromUin").getAsLong();
        messageSequence = data.get("MsgSeq").getAsLong();
        toUser = data.get("ToUin").getAsLong();

        switch (messageType) {
            case TextMsg: {
                message = data.get("Content").getAsString();
                break;
            }
            case PicMsg: {
                JsonObject content = JsonParser.parseString(data.get("Content").getAsString()).getAsJsonObject();
                message = content.get("Content").getAsString();
                tips = content.get("Tips").getAsString();
                picture = new Picture(
                        content.get("Path").getAsString(),
                        content.get("Url").getAsString(),
                        content.get("FileSize").getAsInt(),
                        content.get("FileMd5").getAsString()

                );
                break;
            }
            case BigFaceMsg: {
                JsonObject content = JsonParser.parseString(data.get("Content").getAsString()).getAsJsonObject();
                message = content.get("Content").getAsString();
                tips = content.get("Tips").getAsString();
                picture = new Picture(
                        content.get("ForwordBuf").getAsString(),
                        content.get("ForwordField").getAsString()
                );
                break;
            }
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

    public Picture getPicture() {
        return picture;
    }

    public String getTips() {
        return tips;
    }

    @Override
    public String toString() {
        return "FriendMessageEvent{" +
                "toUser=" + toUser +
                ", messageSequence=" + messageSequence +
                ", picture=" + picture +
                ", tips='" + tips + '\'' +
                ", senderID=" + senderID +
                ", message='" + message + '\'' +
                ", messageType=" + messageType +
                '}';
    }
}