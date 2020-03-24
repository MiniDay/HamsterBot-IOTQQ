package cn.hamster3.bot.event;

import cn.hamster3.bot.core.BotCore;
import com.google.gson.JsonObject;

public class SocketRegisteredEvent extends Event {
    public SocketRegisteredEvent(BotCore botCore, JsonObject object) {
        super(botCore, object);
    }
}
