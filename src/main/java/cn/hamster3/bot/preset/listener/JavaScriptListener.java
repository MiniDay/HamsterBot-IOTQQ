package cn.hamster3.bot.preset.listener;

import cn.hamster3.bot.data.MessageType;
import cn.hamster3.bot.event.GroupMessageEvent;
import cn.hamster3.bot.listener.EventHandler;
import cn.hamster3.bot.listener.Listener;
import cn.hamster3.bot.preset.thread.TimeLimitThread;
import cn.hamster3.bot.utils.MessageUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.logging.Logger;

public class JavaScriptListener implements Listener {
    private static Logger logger = Logger.getLogger("JavaScript");
    private ScriptEngine engine;

    public JavaScriptListener() {
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
            logger.warning(String.format("用户 %d 在群 %d 中执行危险JavaScript代码: %s", event.getSender(), event.getGroupID(), code));
            event.getBotCore().sendMessageIgnoreException(
                    MessageUtils.sendTextToGroup(event.getGroupID(), "禁止使用危险代码: " + forbidCode)
            );
            return;
        }
        logger.info(String.format("用户 %d 在群 %d 中执行JavaScript代码: %s", event.getSender(), event.getGroupID(), code));

        new TimeLimitThread(3000) {
            @Override
            public void run() {
                try {
                    Object result = engine.eval(code);
                    if (result == null) {
                        result = "JavaScript代码执行完成: 无输出";
                    } else {
                        result = "JavaScript代码执行完成, 输出: \n" + result;
                    }
                    event.getBotCore().sendMessageIgnoreException(
                            MessageUtils.sendTextToGroup(event.getGroupID(), result.toString())
                    );
                } catch (ScriptException e) {
                    logger.warning(String.format("用户 %d 在群 %d 中执行JavaScript代码 %s 时出错: %s", event.getSender(), event.getGroupID(), code, e.getMessage()));
                    event.getBotCore().sendMessageIgnoreException(
                            MessageUtils.sendTextToGroup(event.getGroupID(), "JavaScript代码执行异常: \n" + e.toString())
                    );
                }
                setFinished(true);
            }

            @Override
            public void timeout() {
                event.getBotCore().sendMessageIgnoreException(
                        MessageUtils.sendTextToGroup(event.getGroupID(), "JavaScript代码执行超时!")
                );
            }
        }.start();
    }

}
