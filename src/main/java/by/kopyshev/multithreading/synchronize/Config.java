package by.kopyshev.multithreading.synchronize;

record Config(int count, long sleepTime, Class<?> locker) {

    @Override
    public String toString() {
        return "Config{" +
                ", count=" + count +
                ", sleepTime=" + sleepTime +
                ", locker=" + locker +
                '}';
    }
}
