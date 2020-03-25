package cn.hamster3.bot.preset;

import cn.hamster3.bot.core.BotCore;
import cn.hamster3.bot.preset.listener.JSListener;
import cn.hamster3.bot.preset.listener.ScreenListener;
import cn.hamster3.bot.preset.listener.ShutdownListener;

import java.awt.AWTException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException {
        BotCore core = new BotCore("localhost", 8888, 2644895480L);

        try {
            System.out.println("添加屏幕截图组件: " + core.addListener(new ScreenListener()));
        } catch (AWTException e) {
            e.printStackTrace();
        }
        System.out.println("添加JS执行器组件: " + core.addListener(new JSListener()));
        System.out.println("添加关机执行组件: " + core.addListener(new ShutdownListener()));

        core.start();
    }
}
