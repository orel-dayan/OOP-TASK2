package EX2_2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.*;

/**
 * A custom thread pool that defines methods for submitting tasks to a priority queue.
 */

public class CustomExecutor extends ThreadPoolExecutor {

	/**
	 * The priorityCounts array is used to keep track of the number of threads in the queue for each priority.
	 * numOfCores is used to keep track of the number of cores in the system.
	 * MIN_PRIORITY is the minimum priority value.
	 */
	public static final int MIN_PRIORITY = 10;
	int[] priorityCounts = new int[MIN_PRIORITY];

	private static final int numOfCores = Runtime.getRuntime().availableProcessors();


	public CustomExecutor() {
		super(numOfCores / 2, numOfCores - 1,
			300, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<>(numOfCores / 2,
				Comparator.comparing(runnable -> ((MyFuture) runnable))));
	}

	/**
	 * Submits the callable task to thread pool executor
	 *
	 * @param task - The instance of task we want to submit to executor
	 * @return a FutureTask object
	 */
	public <T> Future<T> submit(Task<T> task) {
		priorityCounts[task.taskType.getPriorityValue()]++;
		return super.submit(task);
	}

	/**
	 * Submits a task to the thread pool executor with a given priority.
	 *
	 * @param call     The instance of task we want to submit to executor
	 * @param taskType The priority of the task
	 * @return a FutureTask object
	 */

	public <T> Future<T> submit(Callable<T> call, TaskType taskType) {
		Callable task1 = Task.createTask(call, taskType);
		return submit((Task<T>) task1);
	}

	/**
	 * Submits a task to the thread pool executor without priority.
	 *
	 * @param call The instance of task we want to submit to executor
	 * @return a FutureTask object
	 */


	public <T> Future<T> submit(Callable<T> call) {
		Callable task1 = Task.createTask(call);
		return submit((Task<T>) task1);
	}


	/**
	 * @return the maximum priority task that the ThreadPoolExecutor executed.
	 * * if the array is empty return zero
	 */
	public int getCurrentMax() {
		for (int i = 0; i < MIN_PRIORITY; i++) {
			if (this.priorityCounts[i] != 0) {
				return i + 1;
			}
		}
		System.out.println("there is no task in queue");
		return 0;
	}

	/**
	 * Shutdowns the ThreadPoolExecutor.
	 */
	public void gracefullyTerminate() {
		super.shutdown();
		try {
			super.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method executed before a task have been executed.
	 *
	 * @param r the runnable
	 * @param t the thread
	 */

	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		MyFuture myFuture = (MyFuture) (r);
		priorityCounts[myFuture.getPriority()]--;
	}

	/**
	 * This method wrap the callable as a MyFuture object.
	 *
	 * @param callable - the callable task being wrapped
	 * @return - the wrapped MyFuture object
	 **/
	@Override
	protected <T> RunnableFuture newTaskFor(Callable<T> callable) {
		return new MyFuture<T>(callable);
	}

	/**
	 * @return the number of tasks in the queue
	 */

	public BlockingQueue<Runnable> getQueuePriority() {
		return super.getQueue();
	}

	/**
	 * @return number of tasks that are waiting to be executed.
	 */
	@Override
	public String toString() {
		return "Queue size: " + super.getQueue().size() + "; Queue: " + super.getQueue().toString()
			+ "; Current max priority: " + getCurrentMax() + "; " + Arrays.toString(priorityCounts);
	}

}