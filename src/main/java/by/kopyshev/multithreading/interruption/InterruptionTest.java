package by.kopyshev.multithreading.interruption;

import java.util.Random;

import static by.kopyshev.util.ThreadUtil.join;
import static by.kopyshev.util.ThreadUtil.sleep;

public class InterruptionTest {
    private static final Random random = new Random();
    private static final int CHECKPOINT_COUNT = random.nextInt(12345) * random.nextInt(12345);

    private static final Runnable runnable = () -> {
        System.out.println("The thread" + Thread.currentThread().getName() + " has been started");
        int checkpoint = 0;
        long count = 0;

        while (!Thread.currentThread().isInterrupted()) {
            checkpoint++;
            count++;
            double x = Math.sin(Math.random());
            if (checkpoint % CHECKPOINT_COUNT == 0) {
                System.out.println("Still in process " + count + " " + x);
                checkpoint = 0;
            }
        }
        System.out.println("The thread " + Thread.currentThread().getName() + " has been interrupted");

    };

    static class MyThread extends Thread {
        public MyThread(Runnable runnable) {
            super(runnable);
        }

        @Override
        public void interrupt() {
            System.out.println("MyThread.interrupt()");
            super.interrupt();
        }
    }

    public static void main(String[] args) {
        Thread thread = new MyThread(runnable);
        thread.start();
        sleep(4000);
        thread.interrupt();
        join(thread);
    }
}
