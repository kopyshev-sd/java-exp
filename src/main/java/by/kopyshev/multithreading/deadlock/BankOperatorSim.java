package by.kopyshev.multithreading.deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

import static by.kopyshev.multithreading.deadlock.Account.transferRandomAmount;
import static by.kopyshev.util.ThreadUtil.sleep;

public class BankOperatorSim {
    private static final int TRANSFER_COUNT = 1000;

    protected final Account account1 = new Account();
    protected final Account account2 = new Account();
    protected final ReentrantLock lock1 = new ReentrantLock();
    protected final ReentrantLock lock2 = new ReentrantLock();
    protected boolean deadlock = false;

    /**
     *  Broken actions
     */
    public void firstBroken() {
        simulateBroken(account1, account2);
    }

    public void secondBroken() {
        simulateBroken(account2, account1);
    }

    private void simulateBroken(Account payer, Account receiver) {
        for (int i = 0; i < TRANSFER_COUNT; i++) {
            transferRandomAmount(payer, receiver);
        }
    }

    /**
     *  Synchronized lockable actions
     */
    public void firstSynchronizedLockable() {
        simulateSynchronizedLockable(account1, account2);
    }

    public void secondSynchronizedLockable() {
        simulateSynchronizedLockable(account2, account1);
    }

    private void simulateSynchronizedLockable(Account payer, Account receiver) {
        for (int i = 0; i < TRANSFER_COUNT; i++) {
            synchronized (payer) {
                isDeadlock(payer::lock, payer::isLocked, receiver::isLocked);
                synchronized (receiver) {
                    transferRandomAmount(payer, receiver);
                    receiver.unlock();
                }
                payer.unlock();
            }
        }
    }


    /**
     * Lockable actions
     */
    public void firstLockable() {
        simulateLockable(account1, account2, lock1, lock2);
    }

    public void secondLockable() {
        simulateLockable(account2, account1, lock2, lock1);
    }

    private void simulateLockable(Account payer, Account receiver, ReentrantLock lock1, ReentrantLock lock2) {
        for (int i = 0; i < TRANSFER_COUNT; i++) {
            try {
                lock1.lock();
                isDeadlock(payer::lock, lock1::isLocked, lock2::isLocked);
                lock2.lock();
                transferRandomAmount(payer, receiver);
            } finally {
                lock2.unlock();
                lock1.unlock();
            }
        }
    }

    /**
     *  Fixed actions
     */
    public void firstFixed() {
        simulateFixed(account1, account2, lock1, lock2);
    }

    public void secondFixed() {
        simulateFixed(account2, account1, lock1, lock2);
    }

    private void simulateFixed(Account payer, Account receiver, Lock lock1, Lock lock2) {
        for (int i = 0; i < TRANSFER_COUNT; i++) {
            takeLocks(lock1, lock2);
            try {
                transferRandomAmount(payer, receiver);
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    private synchronized void isDeadlock(Runnable payerLock,
                                         Supplier<Boolean> isPayerLocked, Supplier<Boolean> isReceiverLocked) {
        payerLock.run();
        if (isPayerLocked.get() && isReceiverLocked.get() && !deadlock) {
            deadlock = true;
            System.out.println("---Both accounts are locked. Deadlock.");
            System.out.println("---Expected interrupt in a few seconds");
        }
    }

    private void takeLocks(Lock lock1, Lock lock2) {
        boolean first = false;
        boolean second = false;
        boolean both = false;
        while (!both) {
            try {
                first = lock1.tryLock();
                second = lock2.tryLock();
            } finally {
                if (first && second) {
                    both = true;
                } else {
                    if (first) {
                        lock1.unlock();
                    } else if (second) {
                        lock2.unlock();
                    }
                    sleep(1);
                }
            }
        }
    }
}
