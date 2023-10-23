package com.pluralsight;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class FinancialTracker {

    private static ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private static final String FILE_NAME = "transactions.csv";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    public static void main(String[] args) {
        loadTransactions(FILE_NAME);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Welcome to TransactionApp");
            System.out.println("Choose an option:");
            System.out.println("A) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A":
                    addDeposit(scanner);
                    break;
                case "P":
                    addPayment(scanner);
                    break;
                case "L":
                    ledgerMenu(scanner);
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }

        scanner.close();
    }

    //Raymond checked this good
    public static void loadTransactions(String fileName) {
        // This method should load transactions from a file with the given file name.
        // If the file does not exist, it should be created.
        // The transactions should be stored in the `transactions` ArrayList.
        // Each line of the file represents a single transaction in the following format:
        // <date>,<time>,<vendor>,<type>,<amount>
        // For example: 2023-04-29,13:45:00,Amazon,PAYMENT,29.99
        // After reading all the transactions, the file should be closed.
        // If any errors occur, an appropriate error message should be displayed.
/*make transaction reader
ArrayList transaction = transactionReader.toString
* line.split("\\|") to split the string into sections by each pipe
* ArrayList<> (category) to make an ArrayList for each category
* ArrayList<String>(vendor) = transaction(3);  <---- this makes an array for the vendor*/
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader("transactions.csv"));
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                String date = (parts[0]);
                LocalDate realDate = LocalDate.parse(date, DATE_FORMATTER);
                String time = parts[1];
                LocalTime realTime = LocalTime.parse(time, TIME_FORMATTER);
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);
                //this changes arrays to arrayLists
                transactions.add(new Transaction(realDate, realTime, description, vendor, amount));
            }
            br.close();
        } catch (Exception e) {
            System.out.println("An error has occurred!");
        }

    }

    //have Raymond check this
    private static void addDeposit(Scanner scanner) {
        // This method should prompt the user to enter the date, time, vendor, and amount of a deposit.
        // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
        // The amount should be a positive number.

        // After validating the input, a new `Deposit` object should be created with the entered values.
        // The new deposit should be added to the `transactions` ArrayList.
        try {
            System.out.println("Please be careful to follow the format correctly\n");
            System.out.println("Enter the date (yyyy-MM-dd): ");
            String userDate = scanner.nextLine().trim();
            LocalDate date = LocalDate.parse(userDate, DATE_FORMATTER);

            System.out.println("Enter the time (HH:mm:ss): ");
            String userTime = scanner.nextLine().trim();
            LocalTime time = LocalTime.parse(userTime, TIME_FORMATTER);

            System.out.println("Enter the description: ");
            String description = scanner.nextLine();

            System.out.println("Enter the vendor: ");
            String vendor = scanner.nextLine().trim();

            System.out.println("Enter the amount of deposit: ");
            double depositAmount = scanner.nextDouble();
            scanner.nextLine();

            //unsure how to add a new transaction to the list
            Transaction transaction = new Transaction(date, time, vendor, description, depositAmount);
            transactions.add(transaction);
            //MAKE BUFFERWRITER
            try {
                FileWriter fileWriter = new FileWriter("transactions.csv");
                BufferedWriter buffWriter = new BufferedWriter(fileWriter);
                //write into the file
                buffWriter.transaction;
                //close the writer so the info saves
                buffWriter.close();
            } catch (IOException e) {
                System.out.println("Failed to save data");
            }


          /* this isnt needed
           System.out.println("Completed transaction: " + transaction.getDate() + ", " + transaction.getTime()
             + ", " + transaction.getVendor() + ", " + transaction.getDescription() + ", " + transaction.getAmount());*/


        } catch (Exception yo) {
            System.out.println("You did this wrong.\nPlease start over.\n");
        }

    }

    //Have Raymond check this
    private static void addPayment(Scanner scanner) {
        // This method should prompt the user to enter the date, time, vendor, and amount of a payment.
        // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
        // The amount should be a positive number.
        // After validating the input, a new `Payment` object should be created with the entered values.
        // The new payment should be added to the `transactions` ArrayList.
        try {
            System.out.println("So you're adding a payment, huh. Well make sure to follow the correct format " +
                    "as you enter the following information");

            System.out.println("Enter the date (yyyy-MM-dd): ");
            String userDate = scanner.nextLine().trim();
            LocalDate dateOfPayment = LocalDate.parse(userDate, DATE_FORMATTER);

            System.out.println("Enter the time (HH:mm:ss): ");
            String userTime = scanner.nextLine().trim();
            LocalTime timeOfPayment = LocalTime.parse(userTime, TIME_FORMATTER);

            System.out.println("Enter the description: ");
            String descriptionOfPayment = scanner.nextLine();

            System.out.println("Enter the vendor: ");
            String vendorOfPayment = scanner.nextLine().trim();

            System.out.println("Enter the amount of deposit: ");
            double paymentAmount = scanner.nextDouble();
            scanner.nextLine();

            Transaction paymentTransaction = new Transaction(dateOfPayment, timeOfPayment, descriptionOfPayment, vendorOfPayment, paymentAmount);
            transactions.add(paymentTransaction);


            //BUFFER READER NEEDED

          /* not needed
           System.out.println("Payment successfully made on: " + paymentTransaction.getDate() + " | Time: " + paymentTransaction.getTime()
                    + " | Vendor: " + paymentTransaction.getVendor() + " | Payment Amount: " + paymentTransaction.getAmount());*/
        } catch (Exception e) {
            System.out.println("You input some information incorrectly. Please be careful next time and try again\n");
        }

    }

    //no need to check this one
    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("\nLedger");
            System.out.println("Choose an option:");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A":
                    displayLedger();
                    break;
                case "D":
                    displayDeposits();
                    break;
                case "P":
                    displayPayments();
                    break;
                case "R":
                    reportsMenu(scanner);
                    break;
                case "H":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    //In-progress
    //this is just displaying "ledger"
    private static void displayLedger() {
        // This method should display a table of all transactions in the `transactions` ArrayList.
        // The table should have columns for date, time, vendor, type, and amount.
        try {
            for (int i = 0; i < transactions.size(); i++) {
                System.out.println(transactions.get(i).toString());
            }
        } catch (Exception e) {
            System.out.println("An error has occurred!");
        }

    }

    private static void displayDeposits() {
        // This method should display a table of all deposits in the `transactions` ArrayList.
        // The table should have columns for date, time, vendor, and amount
        // for loop --> if statement if the amount is positive, print out.
    }

    private static void displayPayments() {
        // This method should display a table of all payments in the `transactions` ArrayList.
        // The table should have columns for date, time, vendor, and amount.
        // for loop --> print out if amount is negative
    }

    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Reports");
            System.out.println("Choose an option:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    // Generate a report for all transactions within the current month,
                    // including the date, vendor, and amount for each transaction.
                case "2":
                    // Generate a report for all transactions within the previous month,
                    // including the date, vendor, and amount for each transaction.
                case "3":
                    // Generate a report for all transactions within the current year,
                    // including the date, vendor, and amount for each transaction.

                case "4":
                    // Generate a report for all transactions within the previous year,
                    // including the date, vendor, and amount for each transaction.
                case "5":
                    // Prompt the user to enter a vendor name, then generate a report for all transactions
                    // with that vendor, including the date, vendor, and amount for each transaction.
                case "0":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }


    private static void filterTransactionsByDate(LocalDate startDate, LocalDate endDate) {
        // This method filters the transactions by date and prints a report to the console.
        // It takes two parameters: startDate and endDate, which represent the range of dates to filter by.
        // The method loops through the transactions list and checks each transaction's date against the date range.
        // Transactions that fall within the date range are printed to the console.
        // If no transactions fall within the date range, the method prints a message indicating that there are no results.
    }

    private static void filterTransactionsByVendor(String vendor) {
        // This method filters the transactions by vendor and prints a report to the console.
        // It takes one parameter: vendor, which represents the name of the vendor to filter by.
        // The method loops through the transactions list and checks each transaction's vendor name against the specified vendor name.
        // Transactions with a matching vendor name are printed to the console.
        // If no transactions match the specified vendor name, the method prints a message indicating that there are no results.
    }
}