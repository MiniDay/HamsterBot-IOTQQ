package cn.hamster3.bot.preset.listener;

import cn.hamster3.bot.data.MessageType;
import cn.hamster3.bot.event.GroupMessageEvent;
import cn.hamster3.bot.listener.EventHandler;
import cn.hamster3.bot.listener.Listener;
import cn.hamster3.bot.preset.thread.TimeLimitThread;
import cn.hamster3.bot.utils.MessageUtils;

import javax.imageio.ImageIO;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Logger;

public class JSListener implements Listener {
    private static Logger logger = Logger.getLogger("JavaScript");
    private ScriptEngine engine;

    public JSListener() {
        engine = new ScriptEngineManager().getEngineByName("JavaScript");
        try {
            engine.eval("function shutdown() {\n" +
                    "    Java.type('java.lang.System').exit(0);\n" +
                    "}");
            engine.eval("function eval() {}");
            engine.eval("Java = undefined;");
            engine.eval("java = undefined;");
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成文字图片
     * 因为未知原因导致群消息发不了文字内容
     * 而写了这个方法临时应急一下
     *
     * @param text 要写的文字
     * @return 图片的base64编码
     * @throws IOException 我觉得永远不可能抛出这个错误
     */
    public static String getTextImage(String text) throws IOException {
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

    @EventHandler
    public void onGroupEvent(GroupMessageEvent event) {
        if (event.getMessageType() != MessageType.TextMsg) {
            return;
        }
        if (!event.getMessage().startsWith("/js")) {
            return;
        }
        String code = event.getMessage().substring(3);
        String forbidCode = null;
        if (code.contains("System")) {
            forbidCode = "System";
        } else if (code.contains("Runtime")) {
            forbidCode = "Runtime";
        }
        if (forbidCode != null) {
            logger.warning(String.format("用户 %d 在群 %d 中执行危险JS代码: %s", event.getSenderID(), event.getGroupID(), code));
            try {
                event.getBotCore().sendMessage(
                        MessageUtils.sendImageToGroup(event.getGroupID(), "", getTextImage("禁止使用危险代码: " + forbidCode))
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        logger.info(String.format("用户 %d 在群 %d 中执行JS代码: %s", event.getSenderID(), event.getGroupID(), code));

        new TimeLimitThread(3000) {
            @Override
            public void run() {
                try {
                    Object result = engine.eval(code);
                    if (result == null) {
                        result = "JS执行完成: 无输出";
                    } else {
                        result = "JS执行完成, 输出: \n" + result;
                    }
                    try {
                        event.getBotCore().sendMessage(
                                MessageUtils.sendImageToGroup(event.getGroupID(), "", getTextImage(result.toString()))
                        );
                    } catch (IOException ignored) {
                    }
                } catch (ScriptException e) {
                    logger.warning(String.format("用户 %d 在群 %d 中执行JS代码 %s 时出错: %s", event.getSenderID(), event.getGroupID(), code, e.getMessage()));
                    try {
                        event.getBotCore().sendMessage(
                                MessageUtils.sendImageToGroup(event.getGroupID(), "", getTextImage("JS代码执行异常: \n" + e.toString()))
                        );
                    } catch (IOException ignored) {
                    }
                }
                setFinished(true);
            }

            @Override
            public void timeout() {
                try {
                    event.getBotCore().sendMessage(
                            MessageUtils.sendImageToGroup(event.getGroupID(), "", getTextImage("JS代码执行超时!"))
                    );
                } catch (IOException ignored) {
                }
            }
        }.start();
    }

}
