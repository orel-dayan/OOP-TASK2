package EX2_2;

import java.util.concurrent.*;

public class CustomExecutor {
    private final PriorityBlockingQueue<Runnable> priorityBlockingQueue;
    private final ThreadPoolExecutor executor;
    private int currentMax;
    public CustomExecutor() {

       this.priorityBlockingQueue = new PriorityBlockingQueue<>();

        this.executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors()/2,
             Runtime.getRuntime().availableProcessors()-1
                      , 300, TimeUnit.MILLISECONDS, priorityBlockingQueue);

        this.currentMax = Integer.MIN_VALUE;
    }

	public PriorityBlockingQueue<Runnable> getPriorityBlockingQueue() {
		return priorityBlockingQueue;
	}

	public <T> Future<T> submit(Task<T> task) {
        currentMax = Math.max(currentMax, task.getTypePrirory());
        return executor.submit(task);
    }

    public <T> Future<T> submit(Callable<T> callable, TaskType type) {

        Task<T> task = new Task<>(callable, type);

        currentMax = Math.max(currentMax, task.getTypePrirory());

        return executor.submit(task);
    }

    public <T> Future<T> submit(Callable<T> callable) {

        Task<T> task = new Task<T>(Task.createTask(callable));

        currentMax = Math.max(currentMax, task.getTypePrirory());

        return executor.submit(task);
    }


    public void gracefullyTerminate() {
        executor.shutdown();
		try {
			if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
				executor.shutdownNow();
			}
		} catch (InterruptedException e) {
			System.out.println("error"+ e);
		}
    }


	public int getCurrentMax() {
        return currentMax;
    }



}
