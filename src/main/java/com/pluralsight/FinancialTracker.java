package com.pluralsight;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
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

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadTransactions();
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
    public static void loadTransactions() {
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
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader br = new BufferedReader(fileReader);
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

            //getting date
            System.out.println("Enter the month of the deposit (MM): ");
            String userMonth = scanner.nextLine().trim();

            System.out.println("Enter the date of the deposit (dd): ");
            String userDate = scanner.nextLine().trim();

            System.out.println("Enter the year of the deposit (yyyy): ");
            String userYear = scanner.nextLine().trim();

            String userDeposit = userYear + "-" + userMonth + "-" + userDate;
            LocalDate date = LocalDate.parse(userDeposit, DATE_FORMATTER);

            //getting time
            System.out.println("Enter the hour of the deposit (HH): ");
            String userHour = scanner.nextLine().trim();

            System.out.println("Enter the minute(s) of the deposit (mm): ");
            String userMin = scanner.nextLine().trim();

            System.out.println("Enter the second(s) of the deposit (ss): ");
            String userSec = scanner.nextLine().trim();

            String userTime = userHour + ":" + userMin + ":" + userSec;
            LocalTime time = LocalTime.parse(userTime, TIME_FORMATTER);

            System.out.println("Enter the description: ");
            String description = scanner.nextLine();

            System.out.println("Enter the vendor: ");
            String vendor = scanner.nextLine().trim();

            System.out.println("Enter the amount of deposit: ");
            double depositAmount = scanner.nextDouble();
            scanner.nextLine();

            //unsure how to add a new transaction to the list
            Transaction transaction = new Transaction(date, time, description, vendor, depositAmount);
            transactions.add(transaction);
            //MAKE BUFFERWRITER
            try {
                //accesses the file "transactions.csv"        append: true makes it so you can on to the file, if nothing is there, it'll default to false and rewirte the file
                FileWriter fileWriter = new FileWriter("transactions.csv", true);
                //writes in the file
                BufferedWriter buffWriter = new BufferedWriter(fileWriter);
                //turn the transaction to a string, so I can add it to the file
                String addedTransaction = "\n" + transaction.getDate() + "|" + transaction.getTime() + "|"
                        + transaction.getDescription() + "|" + transaction.getVendor() + "|" + transaction.getAmount();
                //write into the file
                buffWriter.write(addedTransaction);
                //close the writer so the info saves
                buffWriter.close();
            } catch (IOException e) {
                System.out.println("Failed to save data");
                e.printStackTrace();
            }
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

            //getting date
            System.out.println("Enter the month of the deposit (MM): ");
            String userMonth = scanner.nextLine().trim();

            System.out.println("Enter the date of the deposit (dd): ");
            String userDate = scanner.nextLine().trim();

            System.out.println("Enter the year of the deposit (yyyy): ");
            String userYear = scanner.nextLine().trim();

            String userPayment = userYear + "-" + userMonth + "-" + userDate;
            LocalDate dateOfPayment = LocalDate.parse(userPayment, DATE_FORMATTER);

            //getting time
            System.out.println("Enter the hour of the deposit (HH): ");
            String userHour = scanner.nextLine().trim();

            System.out.println("Enter the minute(s) of the deposit (mm): ");
            String userMin = scanner.nextLine().trim();

            System.out.println("Enter the second(s) of the deposit (ss): ");
            String userSec = scanner.nextLine().trim();

            String userTime = userHour + ":" + userMin + ":" + userSec;
            LocalTime timeOfPayment = LocalTime.parse(userTime, TIME_FORMATTER);

            System.out.println("Enter the description: ");
            String descriptionOfPayment = scanner.nextLine();

            System.out.println("Enter the vendor: ");
            String vendorOfPayment = scanner.nextLine().trim();

            System.out.println("Enter the amount of deposit: ");
            double paymentAmount = scanner.nextDouble();
            double realPaymentAmount = -paymentAmount;
            scanner.nextLine();

            Transaction paymentTransaction = new Transaction(dateOfPayment, timeOfPayment, descriptionOfPayment,
                    vendorOfPayment, realPaymentAmount);
            transactions.add(paymentTransaction);

            //BUFFER READER NEEDED
            try {
                //accesses the file "transactions.csv"        append: true makes it so you can on to the file, if nothing is there, it'll default to false and rewirte the file
                FileWriter fileWriter = new FileWriter("transactions.csv", true);
                //writes in the file
                BufferedWriter buffWriter = new BufferedWriter(fileWriter);
                //turn the transaction to a string, so I can add it to the file
                String addedTransaction = "\n" + paymentTransaction.getDate() + "|" + paymentTransaction.getTime() + "|"
                        + paymentTransaction.getDescription() + "|" + paymentTransaction.getVendor() + "|" + paymentTransaction.getAmount();
                //write into the file
                buffWriter.write(addedTransaction);
                //close the writer so the info saves
                buffWriter.close();
            } catch (IOException e) {
                System.out.println("Failed to save data");
                e.printStackTrace();
            }
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

    //check-ready
    private static void displayLedger() {
        // This method should display a table of all transactions in the `transactions` ArrayList.
        // The table should have columns for date, time, vendor, type, and amount.
        for (int i = 0; i < transactions.size(); i++) {
            System.out.println(transactions.get(i).toString());
        }
    }

    private static void displayDeposits() {
        // This method should display a table of all deposits in the `transactions` ArrayList.
        // The table should have columns for date, time, vendor, and amount
        // for loop --> if statement if the amount is positive, print out.
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getAmount() > 0) {
                System.out.println(transactions.get(i).toString());
            }
        }
    }

    private static void displayPayments() {
        // This method should display a table of all payments in the `transactions` ArrayList.
        // The table should have columns for date, time, vendor, and amount.
        // for loop --> print out if amount is negative
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getAmount() < 0) {
                System.out.println(transactions.get(i).toString());
            }
        }
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
                    filterTransactionsByDate(LocalDate.now().withDayOfMonth(1), LocalDate.now());
                    break;
                case "2":
                    // Generate a report for all transactions within the previous month,
                    // including the date, vendor, and amount for each transaction.
                    filterTransactionsByDate(LocalDate.now().minusMonths(2), LocalDate.now());
                    break;
                case "3":
                    // Generate a report for all transactions within the current year,
                    // including the date, vendor, and amount for each transaction.
                    Year thisYear = Year.now();
                    int currentYear = thisYear.getValue();
                    filterTransactionsByDate(LocalDate.of(currentYear, 1, 1), LocalDate.now());
                    break;
                case "4":
                    // Generate a report for all transactions within the previous year,
                    // including the date, vendor, and amount for each transaction.
                    filterTransactionsByDate(LocalDate.now().minusYears(1), LocalDate.now());
                    break;
                case "5":
                    // Prompt the user to enter a vendor name, then generate a report for all transactions
                    // with that vendor, including the date, vendor, and amount for each transaction.
                    searchTransactionsByVendor();
                    break;
                case "0":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    //NEED HELP
    private static void filterTransactionsByDate(LocalDate startDate, LocalDate endDate) {
        // This method filters the transactions by date and prints a report to the console.
        // It takes two parameters: startDate and endDate, which represent the range of dates to filter by.
        // The method loops through the transactions list and checks each transaction's date against the date range.
        // Transactions that fall within the date range are printed to the console.
        // If no transactions fall within the date range, the method prints a message indicating that there are no results.

        System.out.println("Report:");

        for (Transaction transaction : transactions) {
            if (transaction.getDate().isAfter(startDate.minusDays(1)) && transaction.getDate().isBefore(endDate.plusDays(1))) {
                System.out.println(transaction);
            }
        }
    }


    //check-ready
    private static void searchTransactionsByVendor() {
        // This method filters the transactions by vendor and prints a report to the console.
        // It takes one parameter: vendor, which represents the name of the vendor to filter by.
        // The method loops through the transactions list and checks each transaction's vendor name against the specified vendor name.
        // Transactions with a matching vendor name are printed to the console.
        // If no transactions match the specified vendor name, the method prints a message indicating that there are no results.
        System.out.println("What vendor do you want to search for: ");
        String vendor = scanner.nextLine().trim();

        try {
            for (int i = 0; i < transactions.size(); i++) {
                if (vendor.equalsIgnoreCase(transactions.get(i).getVendor())) {
                    System.out.println(transactions.get(i).toString() + "\n");
                }
            }
        } catch (Exception e) {
            System.out.println("An error has occurred! \n");
        }
    }
}