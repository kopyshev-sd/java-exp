package by.kopyshev.multithreading.deadlock;

import java.util.*;

import static by.kopyshev.util.ThreadUtil.join;

public class BankOperatorSimMain {
    private static final int TIMEOUT_MILLIS = 3000;
    private static BankOperatorSim sim = new BankOperatorSim();

    public static void main(String[] args) {
        test(sim::firstFixed, sim::secondFixed, "fixed");
        test(sim::firstBroken, sim::secondBroken, "broken");
        test(sim::firstSynchronizedLockable, sim::secondSynchronizedLockable, "synchronized lockable");
        test(sim::firstLockable, sim::secondLockable, "lockable");
        System.exit(0);
    }

    private static void test(Runnable runnable1, Runnable runnable2, String descriptor) {
        System.out.println("Test with " + descriptor + " action. " +
                "Total balance is " + (sim.account1.balance() + sim.account2.balance()));

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        thread1.start();
        thread2.start();
        join(thread1, TIMEOUT_MILLIS);
        join(thread2, TIMEOUT_MILLIS);

        int totalBalance = sim.account1.balance() + sim.account2.balance();
        int totalTransactions = sim.account1.transactions() + sim.account2.transactions();
        String pattern = "%s balance: %d, transactions: %d\n";
        System.out.printf(pattern, "Account 1", sim.account1.balance(), sim.account1.transactions());
        System.out.printf(pattern, "Account 2", sim.account2.balance(), sim.account1.transactions());
        System.out.printf(pattern, "Total", totalBalance, totalTransactions);

        System.out.print("Alive threads: ");
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        List<Thread> threadList = new ArrayList<>(threadSet);
        threadList.sort(Comparator.comparing(Thread::getName, Comparator.reverseOrder()));
        for (Thread thread : threadList) {
            if (thread.isAlive() && !thread.isDaemon()) {
                System.out.print(thread.getName() + " ");
            }
        }
        System.out.println("\n");
        sim.account1.restoreDefaults();
        sim.account2.restoreDefaults();
        sim.deadlock = false;
    }
}
