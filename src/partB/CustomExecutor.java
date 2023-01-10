package partB;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CustomExecutor<T> {
	private final ThreadPoolExecutor executor;
	private int maxPriority = 3;

	private static final int numOfCores = Runtime.getRuntime().availableProcessors();

	/**

	 Constructor for CustomExecutor class that initializes the ThreadPoolExecutor
	 */

	public CustomExecutor()
	{
		executor = new ThreadPoolExecutor(
			numOfCores/2, numOfCores-1, 300, TimeUnit.MILLISECONDS,
			new PriorityBlockingQueue<>(10,new ComparatorTask() ));
	}

	/**
	* This function submits a task into the threadpool, and updates the maxPriority parameter
	* @param task - the Task to submit
	* @return - the Future that gets the value that will be returned from the call method in the Task
	 */
	public Future<T> submit(Task <Object> task)
	{
		Future<T> future = (Future<T>) executor.submit(task);

		if ((executor.getQueue().peek())==null) {
			maxPriority = task.getType().getPriorityValue();
		}else if((executor.getQueue().peek()) instanceof Task)
		{

			maxPriority = ((Task<?>)executor.getQueue().peek()).getType().getPriorityValue();
		}
		return future;
	}

	/*
	public Future<T> submit(Task <Object> task)
	{
		Future<T>  future = (Future<T>) executor.submit(task);
		if ((executor.getQueue().peek()) == null) {
			maxPriority = task.getType().getPriorityValue();
		} else if((executor.getQueue().peek()) instanceof Task) {
			maxPriority = ((Task<?>) Objects.requireNonNull(executor.getQueue().peek())).getType().getPriorityValue();
		}
		return future;
	}*/

	/**
	* Returns the maximum priority of the tasks currently in the queue
	* @return - the maximum priority of the tasks currently in the queue
	 */
	public int getCurrentMax()
	{
		return this.maxPriority;
	}


	public Future<T> submit(Callable<Object> callable)
	{
		Task<Object> task = Task.createTask(callable);
		return submit(task);
	}


	public Future<T> submit(Callable<Object> callable,TaskType priority)
	{
		Task <Object> task = Task.createTask(callable, priority);
		return submit(task);
	}


	public void gracefullyTerminate()
	{
		executor.shutdown();
		try {
			if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
				executor.shutdownNow(); //terminate the executor service immediately
			}
		} catch (InterruptedException e) {
			System.out.println("error" + e);
		}
	}




}
