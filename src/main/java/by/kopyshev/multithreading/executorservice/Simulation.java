package by.kopyshev.multithreading.executorservice;

import static by.kopyshev.util.ThreadUtil.sleep;

class Simulation implements Runnable {
    private static final String DONE = "Thread %d: I'm done";
    private static final String MESSAGE = "I'm a thread %d, counter = %d\n";

    private final int id;
    private final int limit;
    private final long sleepMillis;
    private int counter = 0;

    public Simulation(int id, int limit, long sleepMillis) {
        this.id = id;
        this.limit = limit;
        this.sleepMillis = sleepMillis;
    }

    @Override
    public void run() {
        for (int i = 0; i < limit; i++) {
            System.out.printf(MESSAGE, id, counter);
            counter++;
            sleep(sleepMillis);
        }
        System.out.printf(DONE, id);
    }
}
