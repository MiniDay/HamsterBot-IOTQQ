package cn.hamster3.bot.event;

import cn.hamster3.bot.core.BotCore;
import cn.hamster3.bot.data.MessageType;
import cn.hamster3.bot.utils.MessageUtils;
import com.google.gson.JsonObject;

import java.io.IOException;

public class FriendMessageEvent extends MessageEvent {
    // 接受者的QQ
    private long toUser;

    // 消息顺序号
    private long messageSequence;

    public FriendMessageEvent(BotCore botCore, JsonObject object) {
        super(botCore, object);
        JsonObject currentPacket = object.getAsJsonObject("CurrentPacket");
        JsonObject data = currentPacket.getAsJsonObject("Data");

        if ("TextMsg".equalsIgnoreCase(data.get("MsgType").getAsString())) {
            messageType = MessageType.valueOf(data.get("MsgType").getAsString());
            senderID = data.get("FromUin").getAsLong();
            messageSequence = data.get("MsgSeq").getAsLong();
            toUser = data.get("ToUin").getAsLong();
            message = data.get("Content").getAsString();
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