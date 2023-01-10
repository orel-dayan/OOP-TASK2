package partB;

import java.util.Comparator;
/**
 * Comparator for tasks that compares the priority value of the task's type.
 * Tasks with higher priority values are considered greater.
 *
 */

public class ComparatorTask implements Comparator<Object> {

	/**
	 * Compares the priority values of two tasks.
	 *
	 * @param o1 The first task to compare.
	 * @param o2 The second task to compare.
	 * @return a negative integer, zero, or a positive integer as the
	 * first argument is less than, equal to, or greater than the second.
	 */

	public int compare(Object o1, Object o2) {
		if (o1 instanceof Task) {
			if (((Task<?>) o1).getType().getPriorityValue() == ((Task<?>) o2).getType().getPriorityValue()) {
				return 0;
			} else if (((Task<?>) o1).getType().getPriorityValue() <
				((Task<?>)o2).getType().getPriorityValue()) {
				return -1;
			}
			return 1;
		}
		return 0;
	}


}
