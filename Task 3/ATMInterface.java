import java.util.InputMismatchException;
import java.util.Scanner;

// BankAccount class to represent the user's bank account
class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.printf("Deposited: ₹%.2f%n", amount);
        } else {
            System.out.println("Invalid deposit amount. Please enter a positive value.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.printf("Withdrawn: ₹%.2f%n", amount);
            return true;
        } else {
            System.out.println("Invalid withdraw amount or insufficient balance.");
            return false;
        }
    }
}

// ATM class to handle user interaction and perform operations
class ATM {
    private BankAccount account;
    private Scanner scanner;

    public ATM(BankAccount account) {
        this.account = account;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to the ATM!");
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getUserChoice();
            switch (choice) {
                case 1 -> withdraw();
                case 2 -> deposit();
                case 3 -> checkBalance();
                case 4 -> {
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private void displayMenu() {
        System.out.println("\nATM Machine");
        System.out.println("1. Withdraw");
        System.out.println("2. Deposit");
        System.out.println("3. Check Balance");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");
    }

    private int getUserChoice() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.next(); // clear invalid input
            return -1; // invalid choice
        }
    }

    private void withdraw() {
        System.out.print("Enter amount to withdraw (INR): ");
        double amount = getUserAmount();
        if (amount != -1 && account.withdraw(amount)) {
            System.out.println("Please collect your cash.");
        }
    }

    private void deposit() {
        System.out.print("Enter amount to deposit (INR): ");
        double amount = getUserAmount();
        if (amount != -1) {
            account.deposit(amount);
        }
    }

    private void checkBalance() {
        System.out.printf("Your balance is: ₹%.3f%n", account.getBalance());
    }

    private double getUserAmount() {
        try {
            double amount = scanner.nextDouble();
            if (amount < 0) {
                throw new InputMismatchException("Negative amount");
            }
            return amount;
        } catch (InputMismatchException e) {
            scanner.next(); // clear invalid input
            System.out.println("Invalid amount. Please enter a positive number.");
            return -1;
        }
    }
}

// Main class to start the application
public class ATMInterface {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(10000); // initial balance in INR
        ATM atm = new ATM(account);
        atm.start();
    }
}
