///ATM INTERFACE

//User is prompted to enter their credentials.
//After successful login, different functionalities are unlocked.
//User can view their transactions history, withdraw money, deposit money, Transfer money to other accounts.

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class User 
{
    private String userId;
    private int pin;

    public User(String userId, int pin) 
    {
        this.userId = userId;
        this.pin = pin;
    }
    public String getUserId() 
    {
        return userId;
    }
    public int getPin() 
    {
        return pin;
    }
}

class Transaction 
{
    private double amount;
    private String type;
    private String timestamp;

    public Transaction(double amount, String type, String timestamp) 
    {
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() 
    {
        return "Type: " + type + ", Amount: $" + amount + ", Timestamp: " + timestamp;
    }
}

class Account 
{
    private double balance;
    private List<Transaction> transactionHistory;

    public Account() 
    {
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public double getBalance() 
    {
        return balance;
    }

    public List<Transaction> getTransactionHistory()
    {
        return transactionHistory;
    }

    public void deposit(double amount) 
    {
        balance += amount;
        Transaction transaction = new Transaction(amount, "DEPOSIT", getCurrentTimestamp());
        transactionHistory.add(transaction);
    }

    public void withdraw(double amount) 
    {
        if (amount <= balance) 
        {
            balance -= amount;
            Transaction transaction = new Transaction(amount, "WITHDRAW", getCurrentTimestamp());
            transactionHistory.add(transaction);
        } else 
            System.out.println("Insufficient balance!");
    }

    public void transfer(Account recipient, double amount) 
    {
        if (amount <= balance) 
        {
            balance -= amount;
            recipient.deposit(amount);
            Transaction transaction = new Transaction(amount, "TRANSFER", getCurrentTimestamp());
            transactionHistory.add(transaction);
        } else 
            System.out.println("Insufficient balance!");
    }

    private String getCurrentTimestamp() 
    {
        return "2023-07-27 12:34:56";
    }
}

class ATM 
{
    private User user;
    private Account account;

    public ATM(User user, Account account) 
    {
        this.user = user;
        this.account = account;
    }

    public void start() 
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the ATM!");
        System.out.println("Please enter your user id: ");
        String userIdInput = scanner.nextLine();
        System.out.println("Please enter your pin: ");
        int pinInput = scanner.nextInt();

        if (isValidUser(userIdInput, pinInput)) 
            showMenu();
        else 
            System.out.println("Invalid user id or pin. Exiting...");
    }

    private boolean isValidUser(String userId, int pin) 
    {
        return user.getUserId().equals(userId) && user.getPin() == pin;
    }

    private void showMenu() 
    {
        Scanner scanner = new Scanner(System.in);

        while (true) 
        {
            System.out.println("\nMain Menu");
            System.out.println("1. Transactions History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.println("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) 
            {
                case 1:
                    showTransactionHistory();
                    break;
                case 2:
                    performWithdraw();
                    break;
                case 3:
                    performDeposit();
                    break;
                case 4:
                    performTransfer();
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showTransactionHistory() 
    {
        List<Transaction> transactionHistory = account.getTransactionHistory();
        System.out.println("\nTransaction History:");
        for (Transaction transaction : transactionHistory) 
            System.out.println(transaction);
    }

    private void performWithdraw() 
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the amount to withdraw: ");
        double amount = scanner.nextDouble();
        account.withdraw(amount);
    }

    private void performDeposit() 
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the amount to deposit: ");
        double amount = scanner.nextDouble();
        account.deposit(amount);
    }

    private void performTransfer() 
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the recipient's user id: ");
        String recipientUserId = scanner.nextLine();
        System.out.println("Enter the amount to transfer: ");
        double amount = scanner.nextDouble();

        Account recipientAccount = new Account();
        account.transfer(recipientAccount, amount);
    }
}

public class ATMInterface
{
    public static void main(String[] args) 
    {
        User user = new User("user123", 1234);
        Account account = new Account();
        ATM atm = new ATM(user, account);
        atm.start();
    }
}
