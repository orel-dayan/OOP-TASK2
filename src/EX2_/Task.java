package EX2_;

import java.util.concurrent.Callable;


public class Task <T> implements Callable<Object> {
	private TaskType type;
	private final Callable<Object> callable;

	/**
	 * Constructor
	 * @param callable the task to execute
	 * @param type the type of the task
	 */

	public Task(Callable<Object> callable, TaskType type) {
		this.callable = callable;
		this.type = type;
	}

	/**
	 * Constructor
	 * @param callable the task to execute
	 */
	public Task(Callable<Object> callable) {
		this.callable = callable;
		this.type = TaskType.OTHER;
	}
	public static <T> Task<T> createTask(Callable<Object> task, TaskType taskType) {
		return new Task<T>(task, taskType);
	}
	public static <T> Task<T> createTask(Callable<Object> task) {
		return new Task<T>(task, TaskType.OTHER);
	}

	public TaskType getType() {
		return this.type;
	}

	public void setType(TaskType type)
	{
		this.type = type;
	}


	public Callable<Object> getCallable()
	{
		return callable;
	}


	public int compareTo(Task<Object> o)
	{
		if (this.type.getPriorityValue() <
			o.type.getPriorityValue())
		{
			return 1;
		}
		else if (this.type.getPriorityValue() > o.type.getPriorityValue()) {
			return -1;
		} else
		{
			return 0;
		}

	}

	@Override
	public Object call() throws Exception {

		return callable.call();
	}

	@Override
	public int hashCode() {
		return callable.hashCode();
	}
}