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
        Account account = authenticate();

        if (account != null) {
            displayMenu(account);
        } else {
            System.out.println("Authentication failed. Exiting...");
        }
    }

    private Account authenticate() {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        Account account = accounts.get(accountNumber);
        if (account != null && account.getPin().equals(pin)) {
            System.out.println("Authentication successful.");
            return account;
        } else {
            System.out.println("Invalid account number or PIN.");
            return null;
        }
    }

    private void displayMenu(Account account) {
        while (true) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.printf("Your balance is: %.2f\n", account.getBalance());
                    break;
                case 2:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    System.out.println("Deposit successful.");
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawalAmount = scanner.nextDouble();
                    if (account.withdraw(withdrawalAmount)) {
                        System.out.println("Withdrawal successful.");
                    }
                    break;
                case 4:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
