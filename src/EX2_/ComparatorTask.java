package EX2_;

import java.util.Comparator;

public class ComparatorTask implements Comparator<Object> {

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
