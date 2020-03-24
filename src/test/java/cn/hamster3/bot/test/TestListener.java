package cn.hamster3.bot.test;

import cn.hamster3.bot.event.Event;
import cn.hamster3.bot.event.SocketConnectedEvent;
import cn.hamster3.bot.event.SocketRegisteredEvent;
import cn.hamster3.bot.listener.EventHandler;
import cn.hamster3.bot.listener.Listener;

public class TestListener implements Listener {

    @EventHandler(priority = 3, ignoreCancelled = false)
    public void onConnected(SocketConnectedEvent event) {
        System.out.println("链接成功: " + event.getParams());
    }

    @EventHandler
    public void onRegistered(SocketRegisteredEvent event) {
        System.out.println("注册成功: " + event.getParams());
    }

    @EventHandler
    public void onEvent(Event event) {
        System.out.println("事件: " + event.getParams());
    }
}
