package by.kopyshev.multithreading.reentrantlock;

import static by.kopyshev.util.ThreadUtil.join;

public class ReentrantLockTest {

    public static void main(String[] args) {
        testLocked();
        testNonLocked();
    }

    private static void testLocked() {
        test("--- For locked",
                new Thread(() -> Task.run(Task::incrementLockedFirst)),
                new Thread(() -> Task.run(Task::incrementLockedSecond)));
    }

    private static void testNonLocked() {
        test("--- For non-locked",
                new Thread(() -> Task.run(Task::increment)),
                new Thread(() -> Task.run(Task::increment)));
    }

    static void test(String message, Thread thread1, Thread thread2) {
        Task.resetCounter();

        System.out.println(message + "\nMAX_COUNTER: " + Task.MAX_COUNTER);
        thread1.start();
        thread2.start();
        join(thread1);
        join(thread2);
        System.out.println("COUNTER: " + Task.getCounter() + "\n");
    }
}
