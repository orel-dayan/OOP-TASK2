package EX2_2;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

//asynchronous mean: thread
public class MyFuture <V> extends FutureTask <V> implements Comparable <MyFuture>{

	public int priority;
	public MyFuture(Callable<V> callable, int priority) {
		super(callable);
		this.priority=priority;


	}
	@Override
	public int compareTo(MyFuture o) {
		if (this.priority == o.priority)
			return 0;
		else {
			return (this.priority > o.priority) ? 1 : -1;
		}
	}
}
