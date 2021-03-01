package com.xq.xtool.timer;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TimerHandle {

    private final static Map<String, Timer> TIMER_MAP = new HashMap<>();
    private final static Map<String, Map<String, TimerTask>> TASKS_MAP = new HashMap<>();
    private final static AtomicInteger indexTask = new AtomicInteger(0);

    public boolean addTimer(String timerID, Date date, Runnable... runs) {
        Map<String, Runnable> collect = Arrays.stream(runs).collect(Collectors.toMap(
                (key) -> this.makeTaskIDFun(timerID),
                Function.identity()));
        return addTimer(timerID, date, collect);
    }

    public synchronized boolean addTimer(String timerID, Date date, Map<String, Runnable> runMap) {
        Timer timer = TIMER_MAP.getOrDefault(timerID, new Timer(timerID));
        Map<String, TimerTask> tasks = TASKS_MAP.getOrDefault(timerID, new HashMap<>());

        runMap.forEach((taskID, run) ->{
            if (tasks.containsKey(taskID)) {
                removeTask(timerID, taskID);
            }

            TaskBuild build = TaskBuild.build(run);
            timer.schedule(build, date);
            tasks.put(taskID, build);
        });

        TASKS_MAP.put(timerID, tasks);
        TIMER_MAP.put(timerID, timer);
        return true;
    }

    public Timer getTimer(String timerID) {
        return TIMER_MAP.get(timerID);
    }

    public Map<String, TimerTask> getTimerTasks(String timerID) {
        return TASKS_MAP.get(timerID);
    }

    public synchronized boolean removeTimer(String timerID) {
        Timer remove = TIMER_MAP.remove(timerID);
        remove.cancel();
        return true;
    }

    public synchronized int removeTask(String timerID, String taskID) {
        Map<String, TimerTask> taskMap = TASKS_MAP.get(timerID);
        TimerTask task = taskMap.get(taskID);
        task.cancel();
        Timer timer = TIMER_MAP.get(timerID);
        return timer.purge();
    }

    private String makeTaskIDFun(String TimerID) {
        return String.format("%s-task-%s", TimerID, indexTask.getAndIncrement());
    }

    ;

}
