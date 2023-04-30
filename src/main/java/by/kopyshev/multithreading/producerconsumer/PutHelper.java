package by.kopyshev.multithreading.producerconsumer;

record PutHelper(Consumer<Integer> consumer) {
    public void put(int value) {
        try {
            consumer.consume(value);
        } catch (InterruptedException e) {
            throw new RuntimeException("WASTED xD", e);
        }
    }

    interface Consumer<C> {
        void consume(C c) throws InterruptedException;
    }
}
