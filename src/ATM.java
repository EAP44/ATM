import java.util.HashMap;
import java.util.Scanner;

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
        int attempts = 3;

        while (attempts > 0) {
            System.out.print("Enter Account Number: ");
            String accountNumber = scanner.nextLine().trim();

            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine().trim();

            Account account = accounts.get(accountNumber);
            if (account != null && account.getPin().equals(pin)) {
                System.out.println("Authentication successful.");
                return account;
            } else {
                attempts--;
                System.out.printf("Invalid account number or PIN. Attempts remaining: %d\n", attempts);
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
                System.out.println("4. Logout");
                System.out.println("5. Exit");
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
                        System.out.println("You have been logged out.");
                        return;
                    case 5:
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

    public Account(String accountNumber, String pin, double balance) {
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

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        } else if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
        } else {
            System.out.println("Insufficient funds.");
        }
        return false;
    }
}

