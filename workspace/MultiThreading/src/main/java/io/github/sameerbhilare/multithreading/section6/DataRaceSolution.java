package io.github.sameerbhilare.multithreading.section6;

public class DataRaceSolution {
    public static void main(String[] args) {
        SharedClass sharedClass = new SharedClass();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.checkForDataRace();
            }

        });

        thread1.start();
        thread2.start();
    }

    public static class SharedClass {
        // 'volatile' solves all data races by guaranteeing execution order
        private volatile int x = 0;
        private volatile int y = 0;

        public void increment() {
            x++;
            y++;
        }

        public void checkForDataRace() {
            if (y > x) {
                // we expect that this case should never happen but it happens due to CPU optimizations
                // where few instructions may be reordered without changing the logical meaning of the program.
                System.out.println("y > x - Data Race is detected");
            }
        }
    }
}
