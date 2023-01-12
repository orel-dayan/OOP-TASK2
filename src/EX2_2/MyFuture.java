package EX2_2;

/**
 * This is an Adapter class, which extends the FutureTask class,
 * and implements the Comparable interface.
 * The main purpose of this class is to allow the Task objects,
 * which are passed to the submit method of the CustomExecutor class,
 * to be used as elements in the priority queue that is used by the thread pool.
 */

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class MyFuture<V> extends FutureTask<V> implements Comparable<MyFuture<V>> {

	 final Task<V> task;


	public MyFuture(Callable<V> callable) {
		super(callable);
		this.task = (Task<V>) callable;
	}
	/**
	 * @return the priority of the task
	 */

	public int getPriority() {
		return ((task).getTaskType().getPriorityValue());
	}


	/**
	 * This method overrides the method from Comparable , we compare two objects of type "MyFuture" based
	 * on their task's priority
	 * @param other the object of type MyFuture we compare
	 * @return 1 if this task has higher priority than the other task, -1 if this task has lower priority than the other task
	 * 0 if the two tasks have the same priority
	 */

	@Override
	public int compareTo(MyFuture other) {

		return Integer.compare(task.getTaskType().getPriorityValue(),
			other.task.getTaskType().getPriorityValue());

	}
}



