package ru.mikheev.kirill.concurrent;

public class Task {

    private final String item;
    private final boolean isWorkDone;

    private Task(String item, boolean isWorkDone) {
        this.item = item;
        this.isWorkDone = isWorkDone;
    }

    public boolean isWorkDone() {
        return isWorkDone;
    }

    public String getItem() {
        if(isWorkDone) {
            throw new WorkCompleteException();
        }else{
            return item;
        }
    }

    public static Task createPlainTask(String item) {
        var task = new Task(item, false);
        return task;
    }

    public static Task createEndTask() {
        var task = new Task(null, true);
        return task;
    }
}
