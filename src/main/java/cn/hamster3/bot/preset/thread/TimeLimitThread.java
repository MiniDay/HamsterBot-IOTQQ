package cn.hamster3.bot.preset.thread;

public abstract class TimeLimitThread extends Thread {
    private long limitTime;
    private boolean finished;

    public TimeLimitThread(long limitTime) {
        this.limitTime = limitTime;
    }

    @Override
    public abstract void run();

    public void timeout() {
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    @SuppressWarnings("deprecation")
    public synchronized void start() {
        super.start();
        new Thread(() -> {
            try {
                Thread.sleep(limitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isFinished()) {
                return;
            }
            timeout();
            TimeLimitThread.this.stop();
        }).start();
    }
}
