package EX2_2;
import java.util.concurrent.*;
public class CustomExecutor extends ThreadPoolExecutor {

	private int currentMax;

	public CustomExecutor() {
		// Set the number of threads to keep in the pool to half the number of processors available for the JVM


		super(Runtime.getRuntime().availableProcessors()/2,
			Runtime.getRuntime().availableProcessors() - 1, 300, TimeUnit.MILLISECONDS
			, new PriorityBlockingQueue<>());


		this.currentMax = Integer.MIN_VALUE;
	}


	//public <T> Future<T> submit(Task<T> task) {
		// Update the maximum priority if necessary
	//	currentMax= Math.max(currentMax, task.getType());
		//return super.submit(task);
	//}

	// Method for creating a Task instance from a Callable and submitting it
	public <T> Future<T> submit(Callable<T> callable, TaskType type) {

		Task<T> task = Task.createTask(callable, type) ;

		currentMax= Math.max(currentMax, task.getType());

		return super.submit(task);
	}

	// Method for creating a Task instance from a Callable and submitting it
	public <T> Future<T> submit(Callable<T> callable) {

		if(!(callable instanceof Task)){
			callable = Task.createTask(callable) ;
		}
		Task<T> t = (Task<T>) callable ;

		currentMax = Math.max(currentMax, t.getType());


		return super.submit(t);


	}

	// Method for returning the maximum priority in the queue in O(1) time and space complexity
	public int getMaxPriority()
	{
		return this.currentMax;
	}
	//Method for terminating the Pool
	public void gracefullyTerminate() {
		super.shutdown();
		try {
			if (!super.awaitTermination(800, TimeUnit.MILLISECONDS)) {
				super.shutdownNow();
			}
		} catch (InterruptedException e) {
			System.out.println("error"+ e);
		}

	}
	@Override
	protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
		return new FutureTask<>(callable) ;
	}

	//Method for getting the current max priority
	public int getCurrentMax() {
		return currentMax;
	}


}
