package by.kopyshev.multithreading.deadlock;

import java.util.Random;

public class Account {
    private static final Random random = new Random();
    private static final int DEFAULT_BALANCE = 10000;
    private int balance = DEFAULT_BALANCE;
    private int transactions = 0;
    private boolean locked = false;

    public Account() {
    }

    public int transactions() {
        return transactions;
    }

    public boolean isLocked() {
        return locked;
    }

    public void lock() {
        locked = true;
    }

    public void unlock() {
        locked = false;
    }

    public int balance() {
        return balance;
    }

    public void deposit(int amount) {
        balance += amount;
        transactions++;
    }

    public void withdraw(int amount) {
        balance -= amount;
        transactions++;
    }

    public void restoreDefaults() {
        this.balance = DEFAULT_BALANCE;
        this.transactions = 0;
        this.locked = false;
    }

    public static void transferRandomAmount(Account payer, Account receiver) {
        int amount = random.nextInt(200);
        payer.withdraw(amount);
        receiver.deposit(amount);
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                ", transactions=" + transactions +
                ", locked=" + locked +
                '}';
    }
}
