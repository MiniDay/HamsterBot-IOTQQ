package cn.hamster3.bot.preset.listener;

import cn.hamster3.bot.data.MessageType;
import cn.hamster3.bot.event.MessageEvent;
import cn.hamster3.bot.listener.EventHandler;
import cn.hamster3.bot.listener.Listener;

import java.util.logging.Logger;

public class ShutdownListener implements Listener {
    private static Logger logger = Logger.getLogger("Shutdown");

    @EventHandler
    public void onShutdown(MessageEvent event) {
        if (event.getMessageType() != MessageType.TextMsg) {
            return;
        }
        if (!event.getMessage().equalsIgnoreCase("/shutdown")) {
            return;
        }
        if (event.getSender() != 767089578) {
            event.replyIgnoreException("你没有这个权限!");
            return;
        }
        logger.warning(String.format("用户 %d 执行关机命令.", event.getSender()));
        event.replyIgnoreException("已关闭HamsterBot!");
        System.exit(0);
    }
}
