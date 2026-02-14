package io.github.sameerbhilare.multithreading.section8;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerUsingBlockingQueue {

    public static void main(String[] args) {

        /**
         * BlockingQueue is Better than Semaphore Version.
         * Internally it already uses: Locks, Conditions, Efficient thread signaling.
         * ✔ Less error-prone
         * ✔ No deadlocks from incorrect semaphore usage
         * ✔ Cleaner code
         * ✔ High performance
         */

        /*
        We can scale this easily:
            BlockingQueue<Task> queue = new LinkedBlockingQueue<>(100);
            // multiple producers
            // multiple consumers (worker pool)
         */
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1); // capacity = 1 → behaves like single-task buffer

        Thread producer = new Thread(new Producer(queue));
        Thread consumer = new Thread(new Consumer(queue));

        producer.start();
        consumer.start();
    }

    static class Producer implements Runnable {

        private BlockingQueue<Integer> queue;

        public Producer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                for (int i = 1; i <= 5; i++) {
                    queue.put(i); // waits if queue is full
                    System.out.println("Produced: " + i);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static class Consumer implements Runnable {

        private BlockingQueue<Integer> queue;

        public Consumer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                for (int i = 1; i <= 5; i++) {
                    int value = queue.take(); // waits if queue is empty
                    System.out.println("Consumed: " + value);
                    Thread.sleep(800);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
