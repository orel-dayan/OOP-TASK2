package EX2_2;

import java.util.concurrent.*;

public class Task<T> implements Comparable<Task<T>>,Callable<T>  {
    private Future<T> future;
    private Callable<T> method;
    private TaskType type;
    public Task(Callable<T> task, TaskType taskType) {
        this.method = task;
        this.type = taskType;
    }
    public Task(Callable<T> task) {
        this.method= task;
        this.type = TaskType.OTHER;
    }
    public T get() throws InterruptedException, ExecutionException{
        return (T) future.get();
    }
    public void setFuture(Future<T> future) {
        this.future = future;
    }

    public TaskType getType() {
        return type;
    }


    public static <T> Task<T> createTask(Callable<T> task, TaskType taskType) {
        return new Task<T>(task,taskType);
    }
    public  T get(long num, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return (T) future.get(num,timeUnit);
    }


    @Override
    public int compareTo(Task task) {
        return task.type.compareTo(this.type);
    }

    public T call() throws Exception {
        return this.method.call();
    }










}
