package EX2_2;

import java.util.concurrent.Callable;

public class Task<T> extends CustomExecutor implements Callable<T> {
	private Callable<T> callable;
	private final TaskType taskType;

	public Task(Callable<T> callable, TaskType type) {
		this.callable = callable;
		this.taskType = type;
	}

	public static <T> Task<T> createTask(Callable<T> task, TaskType taskType) {
		return new Task<T>(task, taskType);
	}


	public static <T> Task<T> createTask(Callable<T> task) {
		return new Task<T>(task, TaskType.OTHER);
	}

	@Override
	public T call() throws Exception {
		return this.callable.call();
	}

	public int getType() {
		return taskType.getPriorityValue();
	}


}