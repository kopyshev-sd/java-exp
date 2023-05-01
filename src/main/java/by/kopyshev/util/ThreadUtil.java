package by.kopyshev.util;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ThreadUtil {

    public static void sleep(long millis) {
        sleep(millis, 0);
    }

    public static void sleep(long millis, int nanos) {
        try {
            Thread.sleep(millis, nanos);
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
        join(thread, Long.MAX_VALUE);
    }

    public static void join(Thread thread, long millis) {
        try {
            thread.join(millis);
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
