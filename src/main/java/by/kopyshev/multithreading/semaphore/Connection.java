package by.kopyshev.multithreading.semaphore;

import java.util.concurrent.Semaphore;

import static by.kopyshev.util.ThreadUtil.safetyAcquire;
import static by.kopyshev.util.ThreadUtil.sleep;
import static java.util.Objects.isNull;

public class Connection {
    private static final int SLEEP_TIME = 1000;
    private static final int PERMITS = 3;

    private static final Semaphore semaphore = new Semaphore(PERMITS);
    private static volatile Connection instance;
    private static int connectionsCount = 0;
    private static int maxConnections = 0;

    private Connection() {
    }

    public static Connection getInstance() {
        if (isNull(instance)) {
            synchronized (Connection.class) {
                if (isNull(instance)) {
                    instance = new Connection();
                }
            }
        }
        return instance;
    }

    public void work() {
        safetyAcquire(semaphore);
        try {
            doWork();
        } finally {
            semaphore.release();
        }
    }

    private static void doWork() {
        synchronized (Connection.class) {
            connectionsCount++;
            if (connectionsCount > maxConnections) {
                maxConnections = connectionsCount;
            }
        }

        sleep(SLEEP_TIME);

        synchronized (Connection.class) {
            connectionsCount--;
        }
    }

    public static int getMaxConnections() {
        return maxConnections;
    }
}
