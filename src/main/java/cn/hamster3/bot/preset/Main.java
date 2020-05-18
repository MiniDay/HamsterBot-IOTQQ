package cn.hamster3.bot.preset;

import cn.hamster3.bot.core.BotCore;
import cn.hamster3.bot.preset.listener.JavaScriptListener;
import cn.hamster3.bot.preset.listener.LogListener;
import cn.hamster3.bot.preset.listener.ScreenListener;
import cn.hamster3.bot.preset.listener.ShutdownListener;
import cn.hamster3.bot.utils.MessageUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        BotCore core = new BotCore("localhost", 8888, 372403923L);

        System.out.println("添加日志组件: " + core.addListener(new LogListener()));
        System.out.println("添加屏幕截图组件: " + core.addListener(new ScreenListener()));
        System.out.println("添加JS执行器组件: " + core.addListener(new JavaScriptListener()));
        System.out.println("添加关机执行组件: " + core.addListener(new ShutdownListener()));

        core.start();
        try {
            core.sendMessage(MessageUtils.sendTextToGroup(612955360, "仓鼠Bot已启动!"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("启动消息发送失败, Bot已关闭...");
            Thread.sleep(3000);
            System.exit(0);
        }
    }
}
