package cn.hamster3.bot.preset.listener;

import cn.hamster3.bot.event.FriendMessageEvent;
import cn.hamster3.bot.event.GroupMessageEvent;
import cn.hamster3.bot.listener.EventHandler;
import cn.hamster3.bot.listener.Listener;

public class LogListener implements Listener {
    @EventHandler
    public void logGroupMessage(GroupMessageEvent event) {
        System.out.println(
                String.format(
                        "{群聊} [%d - %s] [%d - %s]: %s",
                        event.getGroupID(),
                        event.getGroupNickName(),
                        event.getSender(),
                        event.getSenderNickName(),
                        event.getMessage()
                )
        );
    }

    @EventHandler
    public void logFriendMessage(FriendMessageEvent event) {
        System.out.println(
                String.format(
                        "{私聊} [%d]: %s",
                        event.getSender(),
                        event.getMessage()
                )
        );
    }
}
