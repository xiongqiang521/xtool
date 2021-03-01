package com.xq.xtool.timer;

import java.util.TimerTask;

public class TaskBuild extends TimerTask {
    public static TaskBuild build(Runnable runnable){
        return new TaskBuild(runnable);
    }

    private final Runnable runnable;
    private TaskBuild(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void run() {
        this.runnable.run();
    }
}
