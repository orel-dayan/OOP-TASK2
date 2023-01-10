package EX2_2;


import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class MyFuture <T> extends FutureTask<T>  implements Comparable<MyFuture> {

	private final Callable<T> callable;

	public MyFuture(Callable<T> callable)
	{
		super(callable);
		this.callable = callable;
	}

	public Callable<T> getCallable()
	{
		return this.callable;
	}

	public int getPriority()
	{
		return (((Task<T>)callable)).taskType.getPriorityValue();
	}
	@Override
	public int compareTo(MyFuture o)
	{
		return Integer.compare(((Task<T>) (this.callable)).taskType.getPriorityValue(),
			((Task<T>) (o.getCallable())).taskType.getPriorityValue());

	}
}



