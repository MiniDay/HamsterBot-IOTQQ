package cn.hamster3.bot.core;

import cn.hamster3.bot.listener.EventHandler;

import java.lang.reflect.Method;

class InvokeObject {
    private Object object;
    private Method method;
    private int priority;
    private boolean ignoreCancelled;

    public InvokeObject(Object object, Method method) {
        this.object = object;
        this.method = method;
        EventHandler eventHandler = method.getAnnotation(EventHandler.class);
        priority = eventHandler.priority();
        ignoreCancelled = eventHandler.ignoreCancelled();
    }

    public void invoke(Object... params) {
        try {
            method.setAccessible(true);
            method.invoke(object, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getPriority() {
        return priority;
    }

    public boolean isIgnoreCancelled() {
        return ignoreCancelled;
    }

    @Override
    public String toString() {
        return "InvokeObject{" +
                "object=" + object +
                ", method=" + method +
                ", priority=" + priority +
                ", ignoreCancelled=" + ignoreCancelled +
                '}';
    }
}
