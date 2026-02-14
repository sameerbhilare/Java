package io.github.sameerbhilare.multithreading.section9;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

public class StackSolutionWithLockFreeAtomicReference {

    public static void main(String[] args) throws InterruptedException {
        //StandardStack<Integer> stack = new StandardStack<>();
        LockFreeStack<Integer> stack = new LockFreeStack<>();
        Random random = new Random();

        for (int i = 0; i < 100000; i++) {
            stack.push(random.nextInt());
        }

        List<Thread> threads = new ArrayList<>();

        int pushingThreads = 2;
        int poppingThreads = 2;

        for (int i = 0; i < pushingThreads; i++) {
            Thread thread = new Thread(() -> {
                while (true) {
                    stack.push(random.nextInt());
                }
            });

            thread.setDaemon(true);
            threads.add(thread);
        }

        for (int i = 0; i < poppingThreads; i++) {
            Thread thread = new Thread(() -> {
                while (true) {
                    stack.pop();
                }
            });

            thread.setDaemon(true);
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }

        Thread.sleep(10000);

        /*
        Clear Performance improvements with lock free atomic operations:
            Number of operations with lock free atomic classes (LockFreeStack class below) => 442,607,458 operations were performed in 10 seconds
            Number of operations with Synchronized stack version (StandardStack class below) =>242,665,398 operations were performed in 10 seconds
         */
        System.out.println(String.format("%,d operations were performed in 10 seconds ", stack.getCounter()));
    }

    public static class LockFreeStack<T> {
        // The AtomicReference wraps a reference to an object and allows us to perform multiple atomic operations, including compareAndSet().
        private AtomicReference<StackNode<T>> head = new AtomicReference<>();
        private AtomicInteger counter = new AtomicInteger(0);

        public void push(T value) {
            StackNode<T> newHeadNode = new StackNode<>(value);

            /*
            We need a loop, because many threads may try to either push or pop items onto or from the stack in the same time,
            and we may need a few attempts to succeed.
             */
            while (true) {
                // read current value
                StackNode<T> currentHeadNode = head.get();
                // then, based on that value, we calculate the new candidate to replace the head.
                newHeadNode.next = currentHeadNode;
                // then, check that the value that we just read (currentHeadNode) has not changed.
                if (head.compareAndSet(currentHeadNode, newHeadNode)) {
                    break; // means we successfully pushed value onto the stack
                } else {
                    LockSupport.parkNanos(1); // means head has changed by another thread, so wait before retrying
                }
            }
            counter.incrementAndGet();
        }

        public T pop() {
            // read current value
            StackNode<T> currentHeadNode = head.get();
            StackNode<T> newHeadNode;

            while (currentHeadNode != null) {
                // then, based on that value, we calculate the new candidate to replace the head.
                newHeadNode = currentHeadNode.next;
                // then, check that the value that we just read (currentHeadNode) has not changed.
                if (head.compareAndSet(currentHeadNode, newHeadNode)) {
                    break;
                } else {
                    LockSupport.parkNanos(1); // means head has changed by another thread, so wait before retrying
                    currentHeadNode = head.get();
                }
            }
            counter.incrementAndGet();
            return currentHeadNode != null ? currentHeadNode.value : null;
        }

        public int getCounter() {
            return counter.get();
        }
    }

    public static class StandardStack<T> {
        private StackNode<T> head;
        private int counter = 0;

        public synchronized void push(T value) {
            StackNode<T> newHead = new StackNode<>(value);
            newHead.next = head;
            head = newHead;
            counter++;
        }

        public synchronized T pop() {
            if (head == null) {
                counter++;
                return null;
            }

            T value = head.value;
            head = head.next;
            counter++;
            return value;
        }

        public int getCounter() {
            return counter;
        }
    }

    private static class StackNode<T> {
        public T value;
        public StackNode<T> next;

        public StackNode(T value) {
            this.value = value;
            this.next = next;
        }
    }
}
