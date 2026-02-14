package io.github.sameerbhilare.multithreading.section8;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerSingleTaskUsingSemaphore {

    public static void main(String[] args) {

        SingleTaskBuffer buffer = new SingleTaskBuffer();

        Thread producer = new Thread(new Producer(buffer));
        Thread consumer = new Thread(new Consumer(buffer));

        producer.start();
        consumer.start();
    }

    static class SingleTaskBuffer {

        private int task;          // shared resource
        private boolean hasTask = false;

        /**
         * empty = 1 → buffer initially empty (producer allowed)
         * full = 0 → buffer initially empty (consumer blocked)
         * mutex = 1 → ensures only one thread accesses buffer at a time
         * OR
         * lock => ensures only one thread accesses buffer at a time
         */
        // Semaphores
        private Semaphore empty = new Semaphore(1); // buffer empty
        private Semaphore full = new Semaphore(0);  // buffer full
        //private Semaphore mutex = new Semaphore(1); // mutual exclusion => also works with semaphore, but we can also use Lock
        private Lock lock = new ReentrantLock();

        public void produce(int value) throws InterruptedException {
            empty.acquire();   // wait if buffer not empty
            //mutex.acquire();   // enter critical section
            lock.lock();   // enter critical section

            task = value;
            hasTask = true;
            System.out.println("Produced: " + value);

            //mutex.release();   // exit critical section
            lock.unlock();  // exit critical section
            full.release();    // signal item available
        }

        public void consume() throws InterruptedException {
            full.acquire();    // wait if nothing to consume
            //mutex.acquire();   // enter critical section
            lock.lock();   // enter critical section

            if (hasTask) {
                System.out.println("Consumed: " + task);
                hasTask = false;
            }

            //mutex.release();   // exit critical section
            lock.unlock();  // exit critical section
            empty.release();   // signal buffer empty
        }
    }

    /**
     * Producer
     * 1. Wait for empty slot
     * 2. Lock mutex
     * 3. Produce item
     * 4. Release mutex
     * 5. Signal full
     */
    static class Producer implements Runnable {

        private SingleTaskBuffer buffer;

        public Producer(SingleTaskBuffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            try {
                for (int i = 1; i <= 5; i++) {
                    buffer.produce(i);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Consumer
     * 1. Wait for full item
     * 2. Lock mutex
     * 3. Consume item
     * 4. Release mutex
     * 5. Signal empty
     */
    static class Consumer implements Runnable {

        private SingleTaskBuffer buffer;

        public Consumer(SingleTaskBuffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            try {
                for (int i = 1; i <= 5; i++) {
                    buffer.consume();
                    Thread.sleep(800);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
