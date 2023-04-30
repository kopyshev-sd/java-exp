package by.kopyshev.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ThreadUtil {

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void safetyAwait(CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void safetyAcquire(Semaphore semaphore) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String threadName() {
        return Thread.currentThread().getName();
    }

    public static void awaitTermination(ExecutorService service, int time, TimeUnit unit) {
        try {
            boolean result = service.awaitTermination(time, unit);
            System.out.println(service.getClass().getSimpleName() + "@" + Integer.toHexString(service.hashCode())
                    + " awaitTermination: " + result + "\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
