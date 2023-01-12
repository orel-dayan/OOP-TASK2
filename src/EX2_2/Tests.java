package EX2_2;

import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.*;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import static org.junit.Assert.assertEquals;

public class Tests {
	public static final Logger logger = LoggerFactory.getLogger(Tests.class);

	@Test
	public void Test(){
		CustomExecutor customExecutor = new CustomExecutor();
		Callable<String> callable1 = ()-> {
			Thread.sleep(1000);
			StringBuilder sb = new StringBuilder("HELLO");
			return sb.reverse().toString();
		};

		Callable<Integer> callable2 = ()-> {
			//simulating long task
			Thread.sleep(1000);
			int sum = 0;
			for (int i = 4; i <= 12; i++) {
				sum += i;
			}
			return sum;
		};

		Future<?>[] futures = new Future[30];
		for (int i = 0; i < futures.length; i++) {
			// Insert the less important tasks first and more important
			if (i <= futures.length/2)
				futures[i] = customExecutor.submit(callable1, TaskType.OTHER);
			else
				futures[i] = customExecutor.submit(callable2, TaskType.COMPUTATIONAL);
		}
		Object [] objects = customExecutor.getQueuePriority().toArray();
		for (int i = 0; i < customExecutor.getQueuePriority().size(); i++) {
			int end = i;
			Object[] part1 = objects;
			logger.info(()-> "the priority of " +end +" is : "+((MyFuture<?>) part1[end]).getPriority());
		}

		try {
			Thread.sleep(3000);
		} catch (InterruptedException ignored) {
		}
		objects = customExecutor.getQueuePriority().toArray();
		for (int i = 0; i < customExecutor.getQueuePriority().size(); i++) {
			int end = i;
			Object[] part2 = objects;
			logger.info(()-> "the priority of "+end +" after sleep, has priority of" +
				" "+((MyFuture<?>) part2[end]).getPriority());
		}
		customExecutor.gracefullyTerminate();
	}

	@Test
	public void partialTest(){
		CustomExecutor customExecutor = new CustomExecutor();

		var task = Task.createTask(()->{
			int sum = 0;

			for (int i = 1; i <= 10; i++)
				sum += i;

			return sum;
		}, TaskType.COMPUTATIONAL);

		var sumTask = customExecutor.submit(task);
		final int sum;

		try
		{
			sum = (int) sumTask.get(1, TimeUnit.MILLISECONDS);
		}

		catch (InterruptedException | ExecutionException | TimeoutException e)
		{
			throw new RuntimeException(e);
		}

		logger.info(()-> "Sum of 1 through 10 = " + sum);

		Callable<Double> callable1 = ()-> {
			return 1000 * Math.pow(1.02, 5);
		};

		Callable<String> callable2 = ()-> {
			StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
			return sb.reverse().toString();
		};

		// var is used to infer the declared type automatically
		var priceTask = customExecutor.submit(()-> {
			return 1000 * Math.pow(1.02, 5);
		}, TaskType.COMPUTATIONAL);

		var reverseTask = customExecutor.submit(callable2, TaskType.IO);
		final Double totalPrice;
		final String reversed;

		try
		{
			totalPrice = priceTask.get();
			reversed = reverseTask.get();
		}

		catch (InterruptedException | ExecutionException e)
		{
			throw new RuntimeException(e);
		}

		logger.info(()-> "Reversed String = " + reversed);
		logger.info(()->String.valueOf("Total Price = " + totalPrice));
		logger.info(()-> "Current maximum priority = " + customExecutor.getCurrentMax());
		customExecutor.gracefullyTerminate();
	}

	@Test
	public void checkPriority() {
		Task<Integer> task = Task.createTask(() -> {
			int sum = 0;
			for (int i = 1; i <= 10; i++) {
				sum*=i;
			}
			return sum;
		}, TaskType.COMPUTATIONAL);

		assertEquals(1, task.getTaskType().getPriorityValue());
		task.getTaskType().setPriority(2);
		assertEquals(2, task.getTaskType().getPriorityValue());

	}




}

