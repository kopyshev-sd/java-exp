package by.kopyshev.multithreading.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Task {
    public static final int MAX_COUNTER = 10000;
    private static final Lock lock = new ReentrantLock();
    private static int counter = 0;

    public static int getCounter() {
        return counter;
    }

    public static void increment() {
        counter++;
    }

    public static void resetCounter() {
        counter = 0;
    }

    public static void incrementLockedFirst() {
        lock.lock();
        increment();
        lock.unlock();
    }

    public static void incrementLockedSecond() {
        lock.lock();
        increment();
        lock.unlock();
    }

    public static void run(Runnable runnable) {
        for (int i = 0; i < MAX_COUNTER / 2; i++) {
            runnable.run();
        }
    }
}
