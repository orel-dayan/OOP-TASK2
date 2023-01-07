package EX2_2;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.*;
import java.util.logging.*;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;

class Tests {

    public static org.junit.platform.commons.logging.Logger logger = LoggerFactory.getLogger(Tests.class);
	CustomExecutor executor = new CustomExecutor();

    @Test
    public void partialTest(){

        CustomExecutor customExecutor = new CustomExecutor();
        var task = Task.createTask(()->{
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
        logger.info(()-> "Sum of 1 through 10 = " + sum);
        Callable<Double> callable1 = ()-> {
            return 1000 * Math.pow(1.02, 5);
        };
        Callable<String> callable2 = ()-> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            return sb.reverse().toString();
        };

        var priceTask = customExecutor.submit(()-> {
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
        logger.info(()-> "Reversed String = " + reversed);
        logger.info(()->String.valueOf("Total Price = " + totalPrice));
        logger.info(()-> "Current maximum priority = " +
                customExecutor.getCurrentMax());
        customExecutor.gracefullyTerminate();
    }

		@Test
		void testSubmit(){

			Future<String> t1 = executor.submit(()-> "Hi") ;
			Future<Integer> t2 = executor.submit(()-> 10, TaskType.COMPUTATIONAL) ;
			Task<Boolean> task = Task.createTask(()-> true, TaskType.IO) ;
			Future<Boolean> t3 = executor.submit(task) ;
			try {
				assertEquals("Hi", t1.get());
				assertEquals(10, t2.get());
				assertEquals(true, t3.get());
			} catch (ExecutionException | InterruptedException e) {
				throw new RuntimeException(e);
			}
		}





	@Test
	void CreateTaskTest(){
		Task<Integer> task1 = Task.createTask(()-> 86) ;
		Task<String> task2 = Task.createTask(() -> "Hello!", TaskType.IO) ;

		ExecutorService threadPool = Executors.newFixedThreadPool(5) ;
		Future<Integer> res1 = threadPool.submit(task1) ;
		Future<String> res2 = threadPool.submit(task2) ;
		try {
			assertEquals(86, res1.get());
			assertEquals("Hello!", res2.get());
		} catch (ExecutionException | InterruptedException e) {
			throw new RuntimeException(e);
		}

		assertEquals(3, task1.getTypePrirory());
		assertEquals(2, task2.getTypePrirory());

		assertEquals("Task - 0", task1.getTaskName());
		assertEquals("Task - 1", task2.getTaskName());

	}


}