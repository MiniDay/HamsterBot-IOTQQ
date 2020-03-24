package cn.hamster3.bot.test;

import cn.hamster3.bot.core.BotCore;
import cn.hamster3.bot.event.FriendMessageEvent;
import cn.hamster3.bot.listener.EventHandler;
import cn.hamster3.bot.listener.Listener;
import cn.hamster3.bot.utils.MessageUtils;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        BotCore core = new BotCore("localhost", 8888, 2644895480L);

        TestListener listener = new TestListener();
        System.out.println(core.addListener(listener));
        core.start();
        Thread.sleep(3000);
        System.out.println(core.removeListener(listener));

        core.addListener(new Listener() {

            /**
             * 复读bot
             * @param event 好友消息事件
             */
            @EventHandler(priority = 10)
            public void repetition(FriendMessageEvent event) throws IOException {
                System.out.println("收到消息: " + event.getParams());
                JsonObject object = MessageUtils.sendTextToFriend(767089578, event.getParams().getAsJsonObject("CurrentPacket").getAsJsonObject("Data").get("Content").getAsString());
                System.out.println("发送消息: " + object);
                System.out.println("消息发送完毕: " + event.getBotCore().sendMessage(object));
                System.out.println();
            }
        });
    }
}
