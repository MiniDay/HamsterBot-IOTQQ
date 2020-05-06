package cn.hamster3.bot.preset.listener;

import cn.hamster3.bot.data.MessageType;
import cn.hamster3.bot.event.MessageEvent;
import cn.hamster3.bot.listener.EventHandler;
import cn.hamster3.bot.listener.Listener;

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

    private boolean enable;
    private long lastScreenTime;

    public ScreenListener() {
        enable = true;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
            enable = false;
        }
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
    public void onScreenRequire(MessageEvent event) throws IOException {
        if (!enable) {
            return;
        }
        if (event.getMessageType() != MessageType.TextMsg) {
            return;
        }
        if (!event.getMessage().equalsIgnoreCase("/s")) {
            return;
        }
        long now = System.currentTimeMillis();
        if (lastScreenTime + 1000 >= now) {
            return;
        }
        lastScreenTime = now;
        event.replyIgnoreException("", getScreen());
        event.setCancelled(true);
    }

    @EventHandler
    public void closeMessage(MessageEvent event) {
        if (event.getMessageType() != MessageType.TextMsg) {
            return;
        }
        switch (event.getMessage().toLowerCase()) {
            case "/remove screen":
                if (event.getSender() != 767089578) {
                    event.replyIgnoreException("你没有这个权限!");
                    return;
                }
                if (!enable) {
                    event.replyIgnoreException("屏幕截图组件已经被移除了!");
                    return;
                }
                enable = false;
                event.replyIgnoreException("屏幕截图组件移除成功!");
                break;
            case "/add screen":
                if (event.getSender() != 767089578) {
                    event.replyIgnoreException("你没有这个权限!");
                    return;
                }
                if (robot == null) {
                    event.replyIgnoreException("运行BOT的计算机不支持屏幕截图组件!");
                    return;
                }
                if (enable) {
                    event.replyIgnoreException("屏幕截图组件已经被启用了!");
                    return;
                }
                enable = true;
                event.replyIgnoreException("屏幕截图组件添加成功!");
                break;
        }
    }
}
