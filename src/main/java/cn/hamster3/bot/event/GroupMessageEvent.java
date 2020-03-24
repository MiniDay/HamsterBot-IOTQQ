package cn.hamster3.bot.event;

import cn.hamster3.bot.core.BotCore;
import cn.hamster3.bot.data.MessageType;
import com.google.gson.JsonObject;

public class GroupMessageEvent extends Event {
    // 群号
    private long groupID;
    // 群名称
    private String groupNickName;
    // 发送者ID
    private long senderID;
    // 发送者群名片
    private String senderNickName;

    // 消息类型
    private MessageType messageType;
    // 消息文本内容
    private String message;
    // 消息顺序号
    private long messageSequence;
    // 消息发送时间
    private long messageTime;
    // 消息随机码
    private long messageRandom;


    public GroupMessageEvent(BotCore botCore, JsonObject object) {
        super(botCore, object);
        JsonObject currentPacket = object.getAsJsonObject("CurrentPacket");
        JsonObject data = currentPacket.getAsJsonObject("Data");

        if ("TextMsg".equalsIgnoreCase(data.get("MsgType").getAsString())) {
            messageType = MessageType.valueOf(data.get("MsgType").getAsString());
            message = data.get("Content").getAsString();
            groupNickName = data.get("FromGroupName").getAsString();
            senderNickName = data.get("FromNickName").getAsString();

            messageRandom = data.get("MsgRandom").getAsLong();
            groupID = data.get("FromGroupId").getAsLong();
            senderID = data.get("FromUserId").getAsLong();
            messageTime = data.get("MsgTime").getAsLong();
            messageSequence = data.get("MsgSeq").getAsLong();
        }

    }

    public long getGroupID() {
        return groupID;
    }

    public String getGroupNickName() {
        return groupNickName;
    }

    public long getSenderID() {
        return senderID;
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
}