package ru.mikheev.kirill.concurrent;

import java.util.function.Consumer;

public class Worker extends Thread {

    private final TaskQueue taskQueue;
    private final Consumer<String> taskProcessor;

    public Worker(TaskQueue taskQueue, Consumer<String> taskProcessor) {
        this.taskQueue = taskQueue;
        this.taskProcessor = taskProcessor;
    }

    public void run() {
        try {
            var task = taskQueue.take();
            while(!task.isWorkDone()) {
                taskProcessor.accept(task.getItem());
                task = taskQueue.take();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
