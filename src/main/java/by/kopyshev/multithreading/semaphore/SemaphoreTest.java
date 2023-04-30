package by.kopyshev.multithreading.semaphore;

import by.kopyshev.util.ThreadUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static by.kopyshev.multithreading.semaphore.Connection.getMaxConnections;
import static by.kopyshev.util.ThreadUtil.awaitTermination;

public class SemaphoreTest {
    private static final int THREADS = 10;
    private static final int TIMEOUT = 10;

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(THREADS);
        Connection connection = Connection.getInstance();

        for (int i = 0; i < THREADS; i++) {
            service.submit(connection::work);
        }
        service.shutdown();
        awaitTermination(service, TIMEOUT, TimeUnit.SECONDS);

        System.out.println("Max connections " + getMaxConnections());
    }
}
