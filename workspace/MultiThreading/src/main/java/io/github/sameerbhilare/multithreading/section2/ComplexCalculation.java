package io.github.sameerbhilare.multithreading.section2;

import java.math.BigInteger;
 
public class ComplexCalculation {
    public BigInteger calculateResult(BigInteger base1, 
                                      BigInteger power1, 
                                      BigInteger base2, 
                                      BigInteger power2) {
        BigInteger result;
        PowerCalculatingThread thread1 = new PowerCalculatingThread(base1, power1);
        PowerCalculatingThread thread2 = new PowerCalculatingThread(base2, power2);
 
        thread1.start();
        thread2.start();
 
        try {
            System.out.println("Waiting");
            thread1.join();
            thread2.join();
            System.out.println("Result");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Done");
        result = thread1.getResult().add(thread2.getResult());
        return result;
    }

    static void main() {
        ComplexCalculation complexCalculation = new ComplexCalculation();
        BigInteger addition = complexCalculation.calculateResult(new BigInteger("2"), new BigInteger("5"), new BigInteger("2"), new BigInteger("10"));
        System.out.println(addition);
    }
 
    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;
 
        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }
 
        @Override
        public void run() {
            for(BigInteger i = BigInteger.ZERO;
                i.compareTo(power) !=0;
                i = i.add(BigInteger.ONE)) {
                result = result.multiply(base);
            }
        }
 
        public BigInteger getResult() {
            return result;
        }
    }
}
