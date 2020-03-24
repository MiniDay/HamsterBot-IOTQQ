package cn.hamster3.bot.preset;

import cn.hamster3.bot.core.BotCore;
import cn.hamster3.bot.preset.listener.JSListener;
import cn.hamster3.bot.preset.listener.ScreenListener;

import java.awt.AWTException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException {
        BotCore core = new BotCore("localhost", 8888, 2644895480L);

        try {
            System.out.println(core.addListener(new ScreenListener()));
        } catch (AWTException e) {
            e.printStackTrace();
        }
        System.out.println(core.addListener(new JSListener()));

        core.start();
    }
}
