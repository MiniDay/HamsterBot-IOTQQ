package cn.hamster3.bot.preset.listener;

import cn.hamster3.bot.data.MessageType;
import cn.hamster3.bot.event.FriendMessageEvent;
import cn.hamster3.bot.event.GroupMessageEvent;
import cn.hamster3.bot.listener.EventHandler;
import cn.hamster3.bot.listener.Listener;
import cn.hamster3.bot.utils.MessageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * 当用户输入/s 时即可截屏bot所在机器的桌面
 * 需要运行bot的电脑有图形用户界面
 */
public class ScreenListener implements Listener {
    private Robot robot;
    private Rectangle rectangle;

    public ScreenListener() throws AWTException {
        robot = new Robot();
        rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

    }

    private String getScreen() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(robot.createScreenCapture(rectangle), "PNG", outputStream);
        String s = Base64.getEncoder().encodeToString(outputStream.toByteArray());
        outputStream.close();
        System.gc();
        return s;
    }

    @EventHandler
    public void onFriendMessage(FriendMessageEvent event) throws IOException {
        if (event.getMessageType() != MessageType.TextMsg) {
            return;
        }
        if (event.getMessage().equalsIgnoreCase("/s")) {
            event.getBotCore().sendMessage(MessageUtils.sendImageToFriend(
                    event.getSender(),
                    "",
                    getScreen()
            ));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onGroupMessage(GroupMessageEvent event) throws IOException {
        if (event.getMessageType() != MessageType.TextMsg) {
            return;
        }
        if (event.getMessage().equalsIgnoreCase("/s")) {
            event.getBotCore().sendMessage(MessageUtils.sendImageToGroup(
                    event.getGroupID(),
                    "",
                    getScreen()
            ));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void closeMessage(FriendMessageEvent event) throws IOException {
        if (event.getMessageType() != MessageType.TextMsg) {
            return;
        }
        if (event.getMessage().equalsIgnoreCase("/close screen")) {
            String message;
            if (event.getBotCore().removeListener(this)) {
                message = "关闭成功!";
            } else {
                message = "关闭失败!";
            }
            event.getBotCore().sendMessage(MessageUtils.sendTextToFriend(event.getSender(), message));
            event.setCancelled(true);
        }
    }
}
