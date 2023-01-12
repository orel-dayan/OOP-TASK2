package EX2_2;

import java.util.concurrent.Callable;

/**
 * This class is a generic task with a type that returns a result and may throw an exception.
 * Each task has a priority, which is an enum type based on the TaskType enum.
 */
public class Task<T> implements Callable<T>, Comparable<Task<T>> {
	private final TaskType taskType;
	private final Callable<T> callable;

	/**
	 * a constructor that sets up the Callable, and the taskType of the task automatically have "OTHER " priority
	 *
	 * @param callable - a Callable to store as the Task's Callable and call later
	 */

	private Task(Callable<T> callable) {
		this.callable = callable;
		this.taskType = TaskType.OTHER;
	}

	/**
	 * a constructor that sets up the Callable and priority of the Task
	 *
	 * @param callable - a Callable to store as the Task's Callable and call later
	 * @param type     - priority for the Task
	 */

	private Task(Callable<T> callable, TaskType type) {
		this.callable = callable;
		this.taskType = type;
	}

	/**
	 * Factory method that create Task object
	 *
	 * @param callable- the callable method
	 * @param type      - the type of the task
	 * @return Task object
	 */
	public static <T> Task <T> createTask(Callable<T> callable, TaskType type) {
		return new Task<>(callable, type);
	}

	/**
	 * Factory method that create Task object
	 *
	 * @param callable - the callable method
	 * @return Task object with type OTHER
	 */
	public static <T> Task <T> createTask(Callable<T> callable) {
		return new Task<>(callable);
	}

	/**
	 * This method overrides the method from Callable, it runs the callable function
	 *
	 * @return the result of the callable function
	 * @throws Exception if the callable function throws an exception
	 */

	@Override
	public T call() throws Exception {
		return this.callable.call();
	}


	/**
	 * This method overrides the method from Comparable , we compare two objects of type "Task" based
	 * on their task's priority
	 *
	 * @param other the object of type Task we compare
	 * @return 1 if this task has higher priority than the other task, -1 if this task has lower priority than the other task
	 * 0 if the two tasks have the same priority
	 */
	@Override
	public int compareTo(Task other) {
		if (this.taskType.getPriorityValue() > other.taskType.getPriorityValue()) {
			return 1;
		} else if (this.taskType.getPriorityValue() == other.taskType.getPriorityValue()) {
			return 0;
		}
		return -1;
	}

	/**
	 * @return a string representation of the task
	 */

	public String toString() {

		return "(Priority of task is :" + this.taskType.getPriorityValue() + ")";
	}

	public Callable<T> getCallable() {
		return callable;
	}

	public TaskType getTaskType() {
		return taskType;
	}
}