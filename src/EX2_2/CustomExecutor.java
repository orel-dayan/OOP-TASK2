package EX2_2;

import java.util.Comparator;
import java.util.concurrent.*;


public class CustomExecutor extends ThreadPoolExecutor {


	public static final int MIN_PRIORITY= 10;
	int [] priorityCounts = new int[MIN_PRIORITY];

	private static final int numOfCores = Runtime.getRuntime().availableProcessors();


	public CustomExecutor()
	{
		super(numOfCores/ 2, numOfCores - 1,
			300, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<>(numOfCores/2,
				Comparator.comparing(runnable-> ((MyFuture) runnable))));
	}

	@Override
	protected <V>  RunnableFuture  <V> newTaskFor( Callable<V> callable)
	{
		return new MyFuture<>(callable);
	}
	public <V> Future<V> submit(Task <V> task)
	{
		priorityCounts[task.taskType.getPriorityValue()]++;
		return super.submit((Callable<V>) task);
	}
	public <V> Future<V> submit (Callable <V> task, TaskType taskType)
	{
		Callable<V> t = Task.createTask(task, taskType);
		return submit((Task)t);
	}

	public <V> Future<V> submit (Callable<V> task)
	{
		Callable <V> t = Task.createTask(task);
		return submit((Task)t);
	}

	public int getCurrentMax()
	{

		for (int i = 9; i >= 0; i--)
		{
			if (priorityCounts[i] > 0)
			{
				return i;
			}

		}
		System.out.println("there is no task in queue");
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
		MyFuture future = (MyFuture) (r);
		priorityCounts[future.getPriority()]--;
	}
	public BlockingQueue<Runnable> getQueuePriority() {
		return super.getQueue();
	}





}