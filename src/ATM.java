import java.util.*;

public class ATM {
    private HashMap<String, Account> accounts;
    private Scanner scanner;

    public ATM() {
        accounts = new HashMap<>();
        scanner = new Scanner(System.in);
        seedAccounts();
    }

    private void seedAccounts() {
        accounts.put("12345", new Account("12345", "1111", 500.0));
        accounts.put("67890", new Account("67890", "2222", 1000.0));
    }

    public void start() {
        System.out.println("Welcome to the ATM!");

        while (true) {
            Account account = authenticate();
            if (account != null) {
                displayMenu(account);
            } else {
                System.out.println("Authentication failed. Exiting...");
                break;
            }
        }
    }

    private Account authenticate() {
        int maxAttempts = 3;

        while (maxAttempts > 0) {
            System.out.print("Enter Account Number: ");
            String accountNumber = scanner.nextLine().trim();

            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine().trim();

            Account account = accounts.get(accountNumber);
            if (account != null) {
                if (account.isLocked()) {
                    System.out.println("This account is locked due to multiple failed attempts.");
                    return null;
                }
                if (account.getPin().equals(pin)) {
                    System.out.println("Authentication successful.");
                    return account;
                } else {
                    maxAttempts--;
                    account.incrementFailedAttempts();
                    System.out.printf("Invalid PIN. Attempts remaining: %d\n", maxAttempts);
                    if (account.getFailedAttempts() >= 3) {
                        account.lock();
                        System.out.println("Account locked due to too many failed attempts.");
                        return null;
                    }
                }
            } else {
                System.out.println("Invalid account number.");
            }
        }
        return null;
    }

    private void displayMenu(Account account) {
        while (true) {
            try {
                System.out.println("\nATM Menu:");
                System.out.println("1. Check Balance");
                System.out.println("2. Deposit Money");
                System.out.println("3. Withdraw Money");
                System.out.println("4. View Transaction History");
                System.out.println("5. Logout");
                System.out.println("6. Exit");
                System.out.print("Choose an option: ");
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1:
                        System.out.printf("Your balance is: %.2f\n", account.getBalance());
                        break;
                    case 2:
                        System.out.print("Enter deposit amount: ");
                        double depositAmount = Double.parseDouble(scanner.nextLine().trim());
                        account.deposit(depositAmount);
                        System.out.println("Deposit successful.");
                        break;
                    case 3:
                        System.out.print("Enter withdrawal amount: ");
                        double withdrawalAmount = Double.parseDouble(scanner.nextLine().trim());
                        if (account.withdraw(withdrawalAmount)) {
                            System.out.println("Withdrawal successful.");
                        }
                        break;
                    case 4:
                        account.printTransactionHistory();
                        break;
                    case 5:
                        System.out.println("You have been logged out.");
                        return;
                    case 6:
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}

class Account {
    private String accountNumber;
    private String pin;
    private double balance;
    private boolean locked;
    private int failedAttempts;
    private List<String> transactionHistory;

    public Account(String accountNumber, String pin, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
        this.locked = false;
        this.failedAttempts = 0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isLocked() {
        return locked;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void lock() {
        this.locked = true;
    }

    public void incrementFailedAttempts() {
        this.failedAttempts++;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add(String.format("Deposited: %.2f", amount));
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add(String.format("Withdrew: %.2f", amount));
            return true;
        } else if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
        } else {
            System.out.println("Insufficient funds.");
        }
        return false;
    }

    public void printTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Transaction History:");
            transactionHistory.forEach(System.out::println);
        }
    }
}
