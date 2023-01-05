package EX2_2;

import java.util.concurrent.*;

public class Task<T> implements Comparable<Task<T>>, Callable<T> {
    private Future<T> future; // the future object associated with this task
    private final Callable<T> method;
    private TaskType taskType;

    public Task(Callable<T> task, TaskType taskType) {
        this.method = task;
        this.taskType = taskType;
    }
    public Task(Callable<T> task)
    {
        this.method= task;
        this.taskType= TaskType.OTHER;


    }


    public static <T> Task<T> createTask(Callable<T> task, TaskType taskType) {
        return new Task<T>(task,taskType);
    }

    @Override
    public int compareTo(Task t1) {
        return t1.taskType.compareTo(this.taskType);
    }

    public T get() throws InterruptedException, ExecutionException{
        return (T) future.get();
    }

    public T call() throws Exception {
        return this.method.call();
    }

    public void setFuture(Future<T> future) {
        this.future = future;
    }

    public TaskType getTaskType() {
        return taskType;
    }























}
