package EX2_2;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class TaskFuture<T> extends FutureTask<T> implements Comparable<TaskFuture<T>> {
	private final int priority;

	public TaskFuture(Callable<T> callable) {
		super(callable);
		Task<T> task = (Task<T>) callable;
		this.priority = task.getType();
	}

	public int getPriority() {
		return priority;
	}


	@Override
	public int compareTo(TaskFuture other) {
		return Integer.compare(this.priority, other.getPriority());
	}


}
