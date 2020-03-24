package cn.hamster3.bot.utils;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import java.net.URL;

public class MessageUtils {
    public static JsonObject sendTextToFriend(long qq, String message) {
        return sendTextToFriend(qq, message, null);
    }

    public static JsonObject sendTextToFriend(long qq, String message, JsonObject replayInfo) {
        JsonObject object = new JsonObject();
        object.addProperty("toUser", qq);
        object.addProperty("sendToType", 1);
        object.addProperty("sendMsgType", "TextMsg");
        object.addProperty("content", message);
        object.addProperty("groupid", 0);
        object.addProperty("atUser", 0);
        object.add("replayInfo", replayInfo);
        return object;
    }

    public static JsonObject sendTextToGroup(long groupID, String message) {
        return sendTextToGroup(groupID, message, 0);
    }

    public static JsonObject sendTextToGroup(long groupID, String message, long atUser) {
        JsonObject object = new JsonObject();
        object.addProperty("toUser", groupID);
        object.addProperty("sendToType", 2);
        object.addProperty("sendMsgType", "sendMsgType");
        object.addProperty("content", message);
        object.addProperty("groupid", 0);
        object.addProperty("atUser", atUser);
        return object;
    }

    public static JsonObject sendImageToFriend(long qq, String message, URL url) {
        JsonObject object = new JsonObject();
        object.addProperty("toUser", qq);
        object.addProperty("sendToType", 1);
        object.addProperty("sendMsgType", "PicMsg");
        object.addProperty("content", message);
        object.addProperty("groupid", 0);
        object.addProperty("atUser", 0);
        object.add("replayInfo", JsonNull.INSTANCE);

        object.addProperty("picBase64Buf", "");
        object.addProperty("picUrl", url.toString());
        object.addProperty("fileMd5", "");
        return object;
    }

    public static JsonObject sendImageToFriend(long qq, String message, String base64) {
        JsonObject object = new JsonObject();
        object.addProperty("toUser", qq);
        object.addProperty("sendToType", 1);
        object.addProperty("sendMsgType", "PicMsg");
        object.addProperty("content", message);
        object.addProperty("groupid", 0);
        object.addProperty("atUser", 0);
        object.add("replayInfo", JsonNull.INSTANCE);

        object.addProperty("picBase64Buf", base64);
        object.addProperty("picUrl", "");
        object.addProperty("fileMd5", "");
        return object;
    }

}
