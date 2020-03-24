package cn.hamster3.bot.test;

import cn.hamster3.bot.core.BotCore;
import cn.hamster3.bot.utils.MessageUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class BotTest {
    private BotCore botCore;

    @Before
    public void init() throws URISyntaxException {
        botCore = new BotCore("localhost", 8888, 2644895480L);
    }

    @Test
    public void sendGroupMessage() throws IOException {
        botCore.sendMessage(
                MessageUtils.sendImageToGroup(612955360, "", new URL("https://www.hamster3.cn/images/bg3.jpg"))
        );
    }

}
