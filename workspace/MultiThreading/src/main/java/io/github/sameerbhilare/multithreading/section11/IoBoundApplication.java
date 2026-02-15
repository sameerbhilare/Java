package io.github.sameerbhilare.multithreading.section11;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
    Example: Multiple threads with longer blocking time.

    Simulating Blocking calls using Thread.sleep() and using a newVirtualThreadPerTaskExecutor to execute the tasks.
    newVirtualThreadPerTaskExecutor will create a new virtual thread for each task, which is a lightweight thread that is managed by the Java runtime rather than the operating system.
    This allows for a large number of concurrent tasks without exhausting system resources, as virtual threads are much more lightweight than traditional threads.
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
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {

            for (int i = 0; i < NUMBER_OF_TASKS; i++) {
                executorService.submit(() ->  blockingIoOperation());
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