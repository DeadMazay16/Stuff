package ru.mikheev.kirill.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TaskQueue {

    private Task[] tasks;

    private int count = 0;
    private int takePivot = 0;
    private int putPivot = 0;

    private ReentrantLock lock = new ReentrantLock(true);
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();


    public TaskQueue() {
        tasks = new Task[10];
    }

    public TaskQueue(int capacity) {
        tasks = new Task[capacity];
    }

    public void put(Task task) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while(count == tasks.length)
                notFull.await();
            tasks[putPivot] = task;
            if(++putPivot == tasks.length) putPivot = 0;
            count++;
            notEmpty.signal();
        }finally {
            lock.unlock();
        }
    }

    public Task take() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            Task task = tasks[takePivot];
            if(++takePivot == tasks.length) takePivot = 0;
            count--;
            notFull.signal();
            return task;
        }finally {
            lock.unlock();
        }
    }
}
