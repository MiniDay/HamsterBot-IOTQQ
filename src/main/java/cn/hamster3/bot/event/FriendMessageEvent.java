package cn.hamster3.bot.event;

import cn.hamster3.bot.core.BotCore;
import cn.hamster3.bot.data.MessageType;
import com.google.gson.JsonObject;

public class FriendMessageEvent extends Event {
    private MessageType type;
    private long sender;
    private String text;
    private long MsgSeq;
    private long ToUin;


    public FriendMessageEvent(BotCore botCore, JsonObject object) {
        super(botCore, object);
        JsonObject currentPacket = object.getAsJsonObject("CurrentPacket");
        JsonObject data = currentPacket.getAsJsonObject("Data");
        if ("TextMsg".equalsIgnoreCase(data.get("MsgType").getAsString())) {
            type = MessageType.TextMsg;
            sender = data.get("FromUin").getAsLong();
            MsgSeq = data.get("MsgSeq").getAsLong();
            ToUin = data.get("ToUin").getAsLong();
            text = data.get("Content").getAsString();
        }
    }

    public MessageType getType() {
        return type;
    }

    public long getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public long getMsgSeq() {
        return MsgSeq;
    }

    public long getToUin() {
        return ToUin;
    }
}