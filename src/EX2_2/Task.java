package EX2_2;

import java.util.concurrent.Callable;

public class Task<T> implements Callable<T> {
	TaskType taskType;
	Callable<T> callable;

	private Task(Callable<T> callable)
	{
		this.callable = callable;
		taskType = TaskType.OTHER;
	}

	private Task(Callable<T> callable , TaskType type)
	{
		this.callable = callable;
		this.taskType = type;
	}

	@Override
	public T call() throws Exception {
		return this.callable.call();
	}
	public static <T> Task<T> createTask(Callable<T> task, TaskType taskType) {
		return new Task<T> (task, taskType); // use the constructor
	}
	public static <T> Task<T> createTask(Callable<T> task) {
		return new Task<T>(task, TaskType.OTHER);
	}
}