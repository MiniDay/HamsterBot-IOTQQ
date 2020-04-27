package cn.hamster3.bot.utils;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;

@SuppressWarnings("unused")
public class MessageUtils {
    /**
     * 生成文字图片
     * 因为未知原因导致群消息发不了文字内容
     * 而写了这个方法临时应急一下
     *
     * @param text 要写的文字
     * @return 图片的base64编码
     * @throws IOException 我觉得永远不可能抛出这个错误
     */
    private static String getTextImage(String text) throws IOException {
        int width = 0;
        String[] area = text.split("\n");
        for (String s : area) {
            int w = s.length() * 16 + 10;
            if (width < w) {
                width = w;
            }
        }
        BufferedImage image = new BufferedImage(width, area.length * 16, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.BLACK);
        Font font = new Font("微软雅黑", Font.PLAIN, 16);
        graphics.fillRect(0, 0, width, 800);
        graphics.setColor(Color.WHITE);
        graphics.setFont(font);
        for (int i = 0; i < area.length; i++) {
            graphics.drawString(area[i], 5, 14 + i * 16);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", outputStream);
        String s = Base64.getEncoder().encodeToString(outputStream.toByteArray());
        outputStream.close();
        return s;
    }

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
        object.addProperty("sendMsgType", "TextMsg");
        object.addProperty("content", message);
        object.addProperty("groupid", 0);
        object.addProperty("atUser", 0);
        object.add("replayInfo", null);
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

    public static JsonObject sendImageToFriend(long groupID, String message, String base64) {
        JsonObject object = new JsonObject();
        object.addProperty("toUser", groupID);
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

    public static JsonObject sendImageToGroup(long groupID, String message, URL url) {
        JsonObject object = new JsonObject();
        object.addProperty("toUser", groupID);
        object.addProperty("sendToType", 2);
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

    public static JsonObject sendImageToGroup(long groupID, String message, String base64) {
        JsonObject object = new JsonObject();
        object.addProperty("toUser", groupID);
        object.addProperty("sendToType", 2);
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
