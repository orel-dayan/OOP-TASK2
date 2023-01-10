package EX2_2;

import java.util.concurrent.*;


public class Task<T> implements Comparable<Task<T>>, Callable<T> {

	final TaskType taskType;
	private Callable <T> callable;

	public Task (Callable<T> callable, TaskType taskType) {
		this.callable = callable;
		this.taskType = taskType;
	}

	//factory Method mission asinchrone||
	//<T> to use generi
	public static <T> Task<T> createTask(Callable<T> task, TaskType taskType) {
		return new Task<T> (task, taskType); // use the constructor
	}
	public static <T> Task<T> createTask(Callable<T> task) {
		return new Task<T>(task, TaskType.OTHER);
	}

	public Callable<T> getCallable() {
		return callable;
	}

	//q2
	@Override
	public T call() throws Exception {
		return this.callable.call();
	}
	public int getType() {
		return taskType.getPriorityValue();
	}

	@Override
	public String toString() {
		return "Task{" +
			"taskType=" + taskType +
			", callable=" + callable +
			'}';
	}
	@Override
	public int compareTo(Task<T> o) {
		if (this.getType() == o.getType())
			return 0;
		else {
			return (this.taskType.getPriorityValue() > o.taskType.getPriorityValue()) ? 1 : -1;
		}
	}
}