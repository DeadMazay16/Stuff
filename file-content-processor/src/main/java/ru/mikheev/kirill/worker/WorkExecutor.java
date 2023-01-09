package ru.mikheev.kirill.worker;

import java.util.concurrent.atomic.AtomicLong;

public class WorkExecutor {

    private TaskQueue taskQueue;
    private Worker[] workers;

    private AtomicLong result = new AtomicLong(0);
    private final String wordToCount;

    private boolean isWorkDone = false;

    public WorkExecutor(String wordToCount, int workersCount, int queueSize) {
        this.wordToCount = wordToCount;
        this.taskQueue = new TaskQueue(queueSize);
        this.workers = new Worker[workersCount];
        for(int i = 0 ; i < workersCount; i++) {
            workers[i] = new Worker(taskQueue, line -> {
                var wordsInLine = line.split("(?U)\\W+");
                for (var wordIter : wordsInLine) {
                    if (wordIter.equalsIgnoreCase(wordToCount))
                        result.incrementAndGet();
                }
            });
            workers[i].start();
        }
    }

    public void putLine(String line) {
        try {
            taskQueue.put(Task.createPlainTask(line));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void completeWork() {
        isWorkDone = true;
        for(var worker : workers) {
            try {
                taskQueue.put(Task.createEndTask());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public long getResult() {
        if(isWorkDone) {
            for(var worker : workers) {
                try {
                    worker.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return result.get();
        }else{
            throw new WorkCompleteException();
        }
    }

}
