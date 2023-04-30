package by.kopyshev.multithreading.executorservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTest {
    public static final int THREADS = 7;
    public static final int MAX_SERVICE_THREADS = 3;
    public static final int ITERATION_COUNT = 5;
    public static final int SLEEP_TIME = 500;
    public static final int TIMEOUT = 5;

    public static final String SUBMIT_MESSAGE = "SUBMIT_MESSAGE: Submitting thread %d";
    public static final String ALL_SUBMITTED_MESSAGE = "SUBMITTED_MESSAGE: All the threads have been submitted. Waiting now till the finish...";
    public static final String AWAIT_MESSAGE = "AWAIT_MESSAGE: I've been waiting for all the threads. It's done now.";
    public static final String FINISH_MESSAGE = "FINISH_MESSAGE: Main thread is finished. Closing it.";

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(MAX_SERVICE_THREADS);

        for (int i = 0; i < THREADS; i++) {
            System.out.printf(SUBMIT_MESSAGE + "\n", i);
            service.submit(new Simulation(i, ITERATION_COUNT, SLEEP_TIME));
        }
        service.shutdown();
        System.out.println(ALL_SUBMITTED_MESSAGE);

        if (service.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
            System.out.println(AWAIT_MESSAGE);
        }
        System.out.println(FINISH_MESSAGE);
    }

}
