package io.github.sameerbhilare.multithreading.section10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
    Simulating Blocking calls using Thread.sleep() and using a CachedThreadPool to execute the tasks.
    CachedThreadPool will create new threads as needed, and reuse previously constructed threads when they are available.
    This is ideal for IO-bound tasks that may block for a long time, as it allows for a large number of concurrent tasks without exhausting system resources.
 */
public class IoBoundApplication {
    private static final int NUMBER_OF_TASKS = 1000;

    public static void main(String[] args) {
        System.out.printf("Running %d tasks\n", NUMBER_OF_TASKS);

        long start = System.currentTimeMillis();
        performTasks();
        System.out.printf("Tasks took %dms to complete\n", System.currentTimeMillis() - start);
    }

    private static void performTasks() {
        try(ExecutorService executorService = Executors.newCachedThreadPool()) {

            for (int i = 0; i < NUMBER_OF_TASKS; i++) {
                executorService.submit(() -> blockingIoOperation());
            }
        }
    }

    // Simulates a long blocking IO
    private static void blockingIoOperation() {
        System.out.println("Executing a blocking task from thread: " + Thread.currentThread());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}