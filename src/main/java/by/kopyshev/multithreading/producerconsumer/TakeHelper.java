package by.kopyshev.multithreading.producerconsumer;

record TakeHelper(Producer<Integer> producer) {
    public int take() {
        try {
            return producer.produce();
        } catch (InterruptedException e) {
            throw new RuntimeException("WASTED xD", e);
        }
    }

    interface Producer<R> {
        R produce() throws InterruptedException;
    }
}
