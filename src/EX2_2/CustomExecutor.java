package EX2_2;

import java.util.Comparator;
import java.util.concurrent.*;


public class CustomExecutor<T> extends ThreadPoolExecutor {

	public static final int MIN_PRIORITY = 11;
	public static final int MIN= 10;
	int [] priorityCounts = new int[MIN_PRIORITY];

	private static final int numOfCores = Runtime.getRuntime().availableProcessors();


	public CustomExecutor()
	{
		super(numOfCores/ 2, numOfCores - 1,
			300, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<>(numOfCores/2,
				Comparator.comparing(runnable-> ((MyFuture) runnable))));
	}

	@Override
	protected <V> RunnableFuture newTaskFor( Callable<V> callable)
	{
		return new MyFuture<>(callable);
	}
	public Future<T> submit(Task task)
	{
		priorityCounts[task.taskType.getPriorityValue()]++;
		return super.submit((Callable<T>) task);
	}
	public Future<T> submit (Callable task, TaskType taskType)
	{
		Callable task1 = Task.createTask(task, taskType);
		return submit((Task)task1);
	}

	public Future<T> submit (Callable task)
	{
		Callable t = Task.createTask(task);
		return submit((Task)t);
	}

	public int getCurrentMax()
	{
		int currentMax = 0;
		for (int i = MIN; i >= 0; i--)
		{
			if (priorityCounts[i] > 0)
			{
				return (currentMax = i);
			}
		}
		return 0;
	}


	public void gracefullyTerminate()
	{
		super.shutdown();
		try {
			super.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void beforeExecute(Thread t, Runnable r)
	{
		MyFuture<T> future = (MyFuture<T>) (r);
		priorityCounts[future.getPriority()]--;
	}


}