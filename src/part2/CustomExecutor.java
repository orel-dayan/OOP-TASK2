package part2;

import java.util.concurrent.*;

public class CustomExecutor {
	private final PriorityBlockingQueue<Runnable> priorityBlockingQueue;
	private final ThreadPoolExecutor executor;
	private int currentMax;

	public CustomExecutor() {

		this.priorityBlockingQueue = new PriorityBlockingQueue<>();

		this.executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() / 2,
			Runtime.getRuntime().availableProcessors() - 1
			, 300, TimeUnit.MILLISECONDS, priorityBlockingQueue);

		this.currentMax = Integer.MIN_VALUE;
	}

	public <T> Future<T> submit(Task<T> task) {
		currentMax = Math.max(currentMax, task.getType().getPriorityValue());
		return executor.submit(task);
	}

	public <T> Future<T> submit(Callable<T> callable, TaskType type) {

		Task<T> task = new Task<>(callable, type);

		currentMax = Math.max(currentMax, task.getType().getPriorityValue());

		return executor.submit(task);
	}

	public <T> Future<T> submit(Callable<T> callable) {

		Task<T> task = new Task<T>(Task.createTask(callable));

		currentMax = Math.max(currentMax, task.getType().getPriorityValue());

		return executor.submit(task);
	}


	public void gracefullyTerminate() {
		executor.shutdown();
	}

	public int getCurrentMax() {
		return currentMax;
	}
}