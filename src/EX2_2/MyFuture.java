package EX2_2;


import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class MyFuture <V> extends FutureTask<V>  implements Comparable<MyFuture> {

	private final Callable<V> callable;

	public MyFuture(Callable<V> callable)
	{
		super(callable);
		this.callable = callable;
	}

	public Callable<V> getCallable()
	{
		return this.callable;
	}

	public int getPriority()
	{
		return (((Task<?>)callable)).taskType.getPriorityValue();
	}
	@Override
	public int compareTo(MyFuture o)
	{
		return Integer.compare(((Task<?>) (this.callable)).taskType.getPriorityValue(),
			((Task<?>) (o.getCallable())).taskType.getPriorityValue());

	}
	@Override
	public String toString(){
		return ""+this.getPriority();
	}
}



