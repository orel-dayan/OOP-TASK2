
package EX2_2;

import java.util.concurrent.*;

    public class CustomExecutor {

        private final ExecutorService executorService;
        private final PriorityBlockingQueue<Runnable> priorityBlockingQueue; // the priority queue of tasks

        /**
         * Creates a new custom executor with number of threads depends on available processors.
         */
        public CustomExecutor() {
            // number of processors available for the JVM
            int processors = Runtime.getRuntime().availableProcessors();
            this.priorityBlockingQueue = new PriorityBlockingQueue<>();
            this.executorService = new ThreadPoolExecutor(processors / 2, processors - 1,
                    300, TimeUnit.MILLISECONDS, priorityBlockingQueue);
        }

        public <T> Task<T> submit(Task<T> task) {
            submitTask(task);
            return task;
        }

        public <T> Task<T> submit(Callable<T> taskSupplier, TaskType type) {
            final Task<T> task = Task.createTask(taskSupplier, type);
            submitTask(task);
            return task;
        }

        private void submitTask(Task<?> task) {
            final Future future = this.executorService.submit(task);
            task.setFuture(future);
        }

        public String getCurrentMax() {
            Task task = (Task) priorityBlockingQueue.peek();
            if (task != null) {
                String max = task.getType().toString();
                return max;
            } else {
                return "No tasks in queue";
            }
        }

        /**
         * Shuts down the executor service.
         */
        public void gracefullyTerminate() {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                System.out.println(e);
            }

        }
    }













