package EX2_2;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.*;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;

class Tests {

	public static org.junit.platform.commons.logging.Logger logger = LoggerFactory.getLogger(Tests.class);
	CustomExecutor executor = new CustomExecutor();

	private final Task<Integer> task1;
	private final Task<String> task2;
	private final Task<Boolean> task3;

	public Tests() {
		task1 = Task.createTask(() -> 15);

		task2 = Task.createTask(() -> "Hello to the world!", TaskType.COMPUTATIONAL);

		task3 = Task.createTask(() -> true, TaskType.COMPUTATIONAL);
	}


	@Test
	public void partialTest() {

		CustomExecutor customExecutor = new CustomExecutor();
		var task = Task.createTask(() -> {
			int sum = 0;
			for (int i = 1; i <= 10; i++) {
				sum += i;
			}
			return sum;
		}, TaskType.COMPUTATIONAL);
		var sumTask = customExecutor.submit(task);
		final int sum;
		try {
			sum = (int) sumTask.get(1, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			throw new RuntimeException();
		}
		logger.info(() -> "Sum of 1 through 10 = " + sum);
		Callable<Double> callable1 = () -> {
			return 1000 * Math.pow(1.02, 5);
		};
		Callable<String> callable2 = () -> {
			StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
			return sb.reverse().toString();
		};

		var priceTask = customExecutor.submit(() -> {
			return 1000 * Math.pow(1.02, 5);
		}, TaskType.COMPUTATIONAL);
		var reverseTask = customExecutor.submit(callable2, TaskType.IO);
		final Double totalPrice;
		final String reversed;
		try {
			totalPrice = priceTask.get();
			reversed = reverseTask.get();
		} catch (Exception e) {
			throw new RuntimeException();
		}
		logger.info(() -> "Reversed String = " + reversed);
		logger.info(() -> String.valueOf("Total Price = " + totalPrice));
		logger.info(() -> "Current maximum priority = " +
			customExecutor.getCurrentMax());
		customExecutor.gracefullyTerminate();
	}

	@Test
	void testSubmit() {

		Future<String> t1 = executor.submit(() -> "Hi");
		Future<Integer> t2 = executor.submit(() -> 10, TaskType.COMPUTATIONAL);
		Task<Boolean> task = Task.createTask(() -> true, TaskType.IO);
		Future<Boolean> t3 = executor.submit(task);
		try {
			assertEquals("Hi", t1.get());
			assertEquals(10, t2.get());
			assertEquals(true, t3.get());
		} catch (ExecutionException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	void testEquals() {
		TaskFuture<Integer> futureTask1 = new TaskFuture<>(task1);
		TaskFuture<String> futureTask2 = new TaskFuture<>(task2);
		TaskFuture<Boolean> futureTask3 = new TaskFuture<>(task3);

		assertEquals(1, futureTask1.compareTo(futureTask2));
		assertEquals(-1, futureTask2.compareTo(futureTask1));
		assertEquals(0, futureTask2.compareTo(futureTask3));
	}

	@Test
	void testCall() {
		TaskFuture<Integer> futureTask1 = new TaskFuture<>(task1);
		TaskFuture<String> futureTask2 = new TaskFuture<>(task2);
		TaskFuture<Boolean> futureTask3 = new TaskFuture<>(task3);

		ExecutorService threadPool = Executors.newFixedThreadPool(5);

		threadPool.submit(futureTask1);
		threadPool.submit(futureTask2);
		threadPool.submit(futureTask3);

		try {
			assertEquals(15, futureTask1.get());
			assertEquals("Hello to the world!", futureTask2.get());
			assertEquals(true, futureTask3.get());
		} catch (ExecutionException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	// not working
	@Test
	void testPriorityQueue() {
		Task<String> task0 = Task.createTask(() -> {
			return "Task 0 has been executed.";
		}, TaskType.OTHER);
		Task<Boolean> task1 = Task.createTask(() -> {
			return true;
		}, TaskType.COMPUTATIONAL);
		Task<String> task2 = Task.createTask(() -> {
			return "Task 2 has been executed";
		}, TaskType.IO);
		BlockingQueue<Runnable> queue = executor.getQueue();
		executor.submit(task0);
		executor.submit(task1);
		executor.submit(task2);
		TaskFuture futureTask = null;
		int currentPriority = 1;
		while (queue.peek() != null) {
			futureTask = (TaskFuture) queue.poll();
			System.out.println(futureTask);
			System.out.println();
			assertTrue(futureTask.getPriority() >= currentPriority);
			System.out.println(futureTask.getPriority() + "   " + currentPriority);
			System.out.println();
			currentPriority = futureTask.getPriority();
			System.out.println(currentPriority);
			System.out.println();
		}
	}


}





