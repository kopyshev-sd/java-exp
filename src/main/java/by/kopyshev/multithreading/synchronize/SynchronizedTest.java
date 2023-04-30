package by.kopyshev.multithreading.synchronize;

import java.util.function.Consumer;

import static by.kopyshev.util.ThreadUtil.sleep;
import static by.kopyshev.util.ThreadUtil.threadName;

public class SynchronizedTest {
    private static final Config config1 = new Config(300, 200, SynchronizedTest.class);
    private static final Config config2 = new Config(800, 200, SynchronizedTest.class);
    private static final Consumer<Config> task = SynchronizedTest::run;

    public static void main(String[] args) {
        testSynchronized();
    }

    private static synchronized void testSynchronized() {
        new Thread(() -> task.accept(config1)).start();
        new Thread(() -> task.accept(config2)).start();
    }

    private static void printAsThread(String message) {
        System.out.println(Thread.currentThread().getName() + ": " + message);
    }

    private static void run(Config c) {
        System.out.println(Thread.currentThread().getName() + " just started \n");

        for (int i = 0; i < c.count(); i++) {
            synchronized (c.locker()) {
                printAsThread(threadName() + ": inside synchronized block, gonna sleep now");
                sleep(c.sleepTime());
                printAsThread(threadName() + " is waked up! (" + i + ")\n");
            }
        }
    }
}
