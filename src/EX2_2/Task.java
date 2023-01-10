package EX2_2;

import java.util.concurrent.Callable;

public class Task<V> implements Callable<V> {
	TaskType taskType;
	Callable<V> callable;

	private Task(Callable<V> callable)
	{
		this.callable = callable;
		taskType = TaskType.OTHER;
	}

	private Task(Callable<V> callable , TaskType type)
	{
		this.callable = callable;
		this.taskType = type;
	}

	@Override
	public V call() throws Exception {
		return this.callable.call();
	}
	public static  Task createTask(Callable task, TaskType taskType) {
		return new Task(task, taskType); // use the constructor
	}

	public static  Task createTask(Callable task) {
		return new Task(task);
	}
	public String toString(){

		return "(Priority of task is :"+ this.taskType.getPriorityValue()+")";
	}


}