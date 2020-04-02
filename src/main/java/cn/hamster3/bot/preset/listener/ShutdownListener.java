package cn.hamster3.bot.preset.listener;

import cn.hamster3.bot.data.MessageType;
import cn.hamster3.bot.event.FriendMessageEvent;
import cn.hamster3.bot.event.GroupMessageEvent;
import cn.hamster3.bot.listener.EventHandler;
import cn.hamster3.bot.listener.Listener;
import cn.hamster3.bot.utils.MessageUtils;

import java.io.IOException;
import java.util.logging.Logger;

public class ShutdownListener implements Listener {
    private static Logger logger = Logger.getLogger("ShutDown");
    @EventHandler
    public void onShutdown(FriendMessageEvent event) {
        if (event.getMessageType() != MessageType.TextMsg) {
            return;
        }
        if (!event.getMessage().equalsIgnoreCase("/shutdown")) {
            return;
        }
        if (event.getSender() != 767089578) {
            try {
                event.getBotCore().sendMessage(MessageUtils.sendTextToFriend(event.getSender(), "你没有这个权限!"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        logger.warning(String.format("用户 %d 私聊执行关机命令.", event.getSender()));
        try {
            event.getBotCore().sendMessage(
                    MessageUtils.sendTextToFriend(event.getSender(), "准备关闭HamsterBot!")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
    @EventHandler
    public void onShutdown(GroupMessageEvent event) {
        if (event.getMessageType() != MessageType.TextMsg) {
            return;
        }
        if (!event.getMessage().equalsIgnoreCase("/shutdown")) {
            return;
        }
        if (event.getSenderID() != 767089578) {
            try {
                event.getBotCore().sendMessage(MessageUtils.sendTextToGroup(event.getGroupID(), "你没有这个权限!"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        logger.warning(String.format("用户 %d 在群 %d 中执行关机命令.", event.getSenderID(), event.getGroupID()));
        try {
            event.getBotCore().sendMessage(
                    MessageUtils.sendTextToGroup(event.getGroupID(),"准备关闭HamsterBot!")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
