package by.kopyshev.multithreading.producerconsumer;

import by.kopyshev.util.FileUtil;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static by.kopyshev.util.FileUtil.append;
import static by.kopyshev.util.ThreadUtil.sleep;
import static java.lang.String.format;

public class ProducerConsumer {
    private static final int CAPACITY = 2;
    private static final int CONSUMER_SLEEP_TIME = 10;
    private static final int PRODUCER_SLEEP_TIME = 1;
    private static final int PRODUCER_LIMIT = 100;
    private static final int CONSUMER_LIMIT = 100;
    private static final int RANDOM_BOUND = 100;

    private static final String CONSUMER_OUTPUT_PATH = "consumed.txt";
    private static final String PRODUCER_OUTPUT_PATH = "produced.txt";
    private static final String MESSAGE_GENERATED = "%d: Generated value: %d. The size of the queue is %d";
    private static final String MESSAGE_TAKE = "%d: The value is %d. The size of the queue is %d";

    private static final Random random = new Random();
    private static final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(CAPACITY);

    public static void main(String[] args) {
        Thread producer = new Thread(ProducerConsumer::produce);
        Thread consumer = new Thread(ProducerConsumer::consume);
        producer.start();
        consumer.start();
    }

    private static void produce() {
        PutHelper putter = new PutHelper(queue::put);

        for (int i = 0; i < PRODUCER_LIMIT; i++) {
            int value = random.nextInt(RANDOM_BOUND);
            append(PRODUCER_OUTPUT_PATH, format(MESSAGE_GENERATED + "\n", i, value, queue.size()));
            putter.put(value);
            sleep(PRODUCER_SLEEP_TIME);
        }
    }

    private static void consume() {
        TakeHelper taker = new TakeHelper(queue::take);

        for (int i = 0; i < CONSUMER_LIMIT; i++) {
            int value = taker.take();
            FileUtil.append(CONSUMER_OUTPUT_PATH, format(MESSAGE_TAKE + "\n", i, value, queue.size()));
            sleep(CONSUMER_SLEEP_TIME);
        }
    }
}
