package cn.hamster3.bot.event;

import cn.hamster3.bot.core.BotCore;
import cn.hamster3.bot.data.MessageType;
import com.google.gson.JsonObject;

public class FriendMessageEvent extends Event {
    // 发送者的QQ
    private long sender;
    // 接受者的QQ
    private long toUser;

    // 消息类型
    private MessageType messageType;
    // 消息文本内容
    private String message;
    // 消息顺序号
    private long messageSequence;


    public FriendMessageEvent(BotCore botCore, JsonObject object) {
        super(botCore, object);
        JsonObject currentPacket = object.getAsJsonObject("CurrentPacket");
        JsonObject data = currentPacket.getAsJsonObject("Data");

        if ("TextMsg".equalsIgnoreCase(data.get("MsgType").getAsString())) {
            messageType = MessageType.valueOf(data.get("MsgType").getAsString());
            sender = data.get("FromUin").getAsLong();
            messageSequence = data.get("MsgSeq").getAsLong();
            toUser = data.get("ToUin").getAsLong();
            message = data.get("Content").getAsString();
        }
    }

    public long getSender() {
        return sender;
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