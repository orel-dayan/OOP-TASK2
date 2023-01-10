package EX2_2;

import java.util.Comparator;
import java.util.concurrent.*;


public class CustomExecutor<T> extends ThreadPoolExecutor {

	public static final int MIN_PRIORITY =11;
	int [] priorityCounts = new int[MIN_PRIORITY];
	public CustomExecutor()
	{
		super(Runtime.getRuntime().availableProcessors() / 2, Runtime.getRuntime().availableProcessors() - 1,
			300, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<>(Runtime.getRuntime().availableProcessors() / 2,
				Comparator.comparing(runnable-> ((MyFuture) runnable))));
	}

	@Override
	protected <T> RunnableFuture newTaskFor( Callable<T> callable)
	{
		return new MyFuture<T>(callable);
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
		for (int i = 10; i >= 0; i--)
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

	}
	@Override
	protected void beforeExecute(Thread t, Runnable r)
	{
		MyFuture<T> future = (MyFuture<T>) (r);
		priorityCounts[future.getPriority()]--;
	}


}