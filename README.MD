# 仓鼠BOT
一个基于[IOTQQ](https://github.com/IOTQQ/IOTQQ)的Java版本QQ机器人框架。

# 这是什么？
一个使用Java语言编写的QQ机器人框架，你可以使用HamsterBot创造出自己的机器人。  
使用代码监听群信息、好友信息，并作出相应的处理。


# 为什么不使用酷Q？
因为酷Q只能运行在windows环境，而且发图片等功能需要收费（酷Q Pro）。  
我想要把我的Bot运行在树莓派上，因此我需要一个高效、跨平台的QQ机器人。  
[IOTQQ](https://github.com/IOTQQ/IOTQQ)满足了我的所有需求。

# 我应该如何使用它？
使用HamsterBot很简单，你只需要clone下来代码，并 `new BotCore("host", port, QQ号).start();`即可。

# 我该怎么自定义我的Bot？
这里有一份示例代码：
监听器代码: 
```java
import cn.hamster3.bot.event.Event;
import cn.hamster3.bot.event.FriendMessageEvent;
import cn.hamster3.bot.event.SocketConnectedEvent;
import cn.hamster3.bot.event.SocketRegisteredEvent;
import cn.hamster3.bot.listener.EventHandler;
import cn.hamster3.bot.listener.Listener;

public class TestListener implements Listener {

    @EventHandler
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

    @EventHandler
    public void onFriendMessage(FriendMessageEvent event) {

    }
}
```

Main类代码：
```java
import cn.hamster3.bot.core.BotCore;
import cn.hamster3.bot.utils.MessageUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) {
        BotCore core = new BotCore("localhost", 8888, 2644895480L);
        System.out.println(core.addListener(new TestListener()));
        core.start();
    }
}

```
需要注意：
1. 你的监听器类必须`implements cn.hamster3.bot.listener.Listener`  
2. 事件处理器方法必须写上`@cn.hamster3.bot.listener.EventHandler`注解
3. 方法的参数只允许有一个，且类型为`cn.hamster3.bot.event.Event`或它的的子类

**以上三点任何一点未达成，都会导致方法不被调用。**

`@cn.hamster3.bot.listener.EventHandler`注解可以添加参数：设置该事件处理器的优先级、是否忽略取消事件。  
例子: `@EventHandler(priority = 3, ignoreCancelled = false)`  
优先级数字越低的事件处理器会越早于其他处理器执行。  
你可以在方法内部调用`event.setCancelled(true);`，这样在你的处理器之后的方法会不处理这个事件。
除非它的注解上写了`ignoreCancelled = false`。

更多代码可以参见`cn.hamster3.bot.preset`包

# 如何让Bot发送消息？
使用BotCore实例的`sendMessage()`即可。  
该方法接受一个JsonObject对象作为参数，`MessageUtils` 类应该可以帮助你快速构建发送的消息内容。
`System.out.println(core.sendMessage(MessageUtils.sendTextToFriend(767089578, "测试")));`

# 为什么只能发送文字和图片消息？
 因为HamsterBot目前还在开发中，自然还有很多IOTQQ的功能未被实现。  
 但IOTQQ所有的功能都可以使用http请求来实现，也许你可以试着自己写一些实现。
 
 # 后言
 HamsterBot的作者（仓鼠）并不是资深git用户，可能在大多数地方做的并不能让您满意，请多包涵~ 
