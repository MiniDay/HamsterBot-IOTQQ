package cn.hamster3.bot.event;

import cn.hamster3.bot.core.BotCore;
import cn.hamster3.bot.data.MessageType;
import com.google.gson.JsonObject;

import java.io.IOException;

public abstract class MessageEvent extends Event {
    // 发送者ID
    protected long senderID;
    // 消息内容
    protected String message;
    // 消息类型
    protected MessageType messageType;

    public MessageEvent(BotCore botCore, JsonObject params) {
        super(botCore, params);
    }

    public abstract JsonObject reply(String message) throws IOException;

    public abstract JsonObject reply(String message, String image) throws IOException;

    public JsonObject replyIgnoreException(String message) {
        try {
            return reply(message);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JsonObject replyIgnoreException(String message, String image) {
        try {
            return reply(message, image);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public long getSender() {
        return senderID;
    }

    public String getMessage() {
        return message;
    }

    public MessageType getMessageType() {
        return messageType;
    }
}
