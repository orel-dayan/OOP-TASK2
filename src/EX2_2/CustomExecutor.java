package EX2_2;

import java.util.concurrent.*;


public class CustomExecutor extends ThreadPoolExecutor {

	private int processorMax;
	public CustomExecutor() {

		super(Runtime.getRuntime().availableProcessors() / 2,
			Runtime.getRuntime().availableProcessors() - 1, 300, TimeUnit.MILLISECONDS
			, new PriorityBlockingQueue<>());
		this.processorMax = Integer.MIN_VALUE;

	}

	public <T>MyFuture <T> Mysubmit(Task t) {
		if (t == null) throw new NullPointerException();
		MyFuture <T> futuretask = new MyFuture(t.getCallable());
		execute(futuretask);
		return futuretask;
	}

	public <T> MyFuture<T> submit(Callable<T> callable, TaskType t) { // q2 because submit runnable and callable
		return Mysubmit(Task.createTask(callable,t));

	}


	// Method for creating a Task instance from a Callable and submitting it
	public <T> MyFuture<T> submit(Callable<T> callable) {

		if (!(callable instanceof Task)) {
			return Mysubmit(Task.createTask(callable));
		}
		return Mysubmit((Task) callable); // casting

	}

	// Method for returning the maximum priority in the queue in O(1) time and space complexity
	public int getMaxPriority() {
		return this.processorMax;
	}

	//Method for terminating the Pool
	public void gracefullyTerminate() { //q11
		super.shutdown();
		try {
			if (!super.awaitTermination(800, TimeUnit.MILLISECONDS)) {
				super.shutdownNow(); //terminate the executor service immediately
			}
		} catch (InterruptedException e) {
			System.out.println("error" + e);
		}

	}
	@Override
	protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
		return new FutureTask<>(callable);
	}

	//Method for getting the current max priority
	public BlockingQueue<Runnable> getCurrentMax() {
		return super.getQueue();
	}


}