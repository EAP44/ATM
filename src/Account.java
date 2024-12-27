public class Account {
    private final String accountNumber; // Immutable field
    private final String pin;           // Immutable field
    private double balance;             // Mutable field

    public Account(String accountNumber, String pin, double balance) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be null or empty.");
        }
        if (pin == null || pin.isEmpty()) {
            throw new IllegalArgumentException("PIN cannot be null or empty.");
        }
        if (balance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public synchronized double getBalance() {
        return balance;
    }

    public synchronized void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.printf("Successfully deposited %.2f. New balance: %.2f\n", amount, balance);
        } else {
            System.out.println("Invalid deposit amount. Please enter a positive value.");
        }
    }

    public synchronized boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount. Please enter a positive value.");
            return false;
        }
        if (amount > balance) {
            System.out.println("Insufficient funds. Transaction declined.");
            return false;
        }
        balance -= amount;
        System.out.printf("Successfully withdrew %.2f. New balance: %.2f\n", amount, balance);
        return true;
    }
}
