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
        //Takes in user input for the date and time of user's deposit
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

            Transaction transaction = new Transaction(date, time, description, vendor, depositAmount);
            transactions.add(transaction);

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

    private static void addPayment(Scanner scanner) {
        /*takes the user input for the date and time of the payment they made */
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


            try {
                //accesses the file "transactions.csv"   append: true makes it so you can on to the file, if nothing is there, it'll default to false and rewirte the file
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

    private static void displayLedger() {
        // This method should display a table of all transactions in the `transactions` ArrayList.
        // The table should have columns for date, time, vendor, type, and amount.
        for (int i = 0; i < transactions.size(); i++) {
            System.out.println(transactions.get(i).toString());
        }
    }

    private static void displayDeposits() {
        //goes through everything in transactions list and searches the amount
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getAmount() > 0) {
                System.out.println(transactions.get(i).toString());
            }
        }
    }

    private static void displayPayments() {
        //same function as displayDeposits method but for negative values
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
                    //takes today's date, specifically the month for the start, and ends the filter to today
                    filterTransactionsByDate(LocalDate.now().withDayOfMonth(1), LocalDate.now());
                    break;
                case "2":
                    // takes today's date, goes back a month and ends to today
                    filterTransactionsByDate(LocalDate.now().minusMonths(2), LocalDate.now());
                    break;
                case "3":
                    // creates a variable for this year
                    Year thisYear = Year.now();
                    //change the year variable to an int
                    int currentYear = thisYear.getValue();
                    /*takes the current year, changes the format to LocalDate, and starts
                    at the first day of the first month and ends at today's date'*/
                    filterTransactionsByDate(LocalDate.of(currentYear, 1, 1), LocalDate.now());
                    break;
                case "4":
                    //takes today's date, start it back from 1 year, and ends the filter at today's date
                    filterTransactionsByDate(LocalDate.now().minusYears(1), LocalDate.now());
                    break;
                case "5":
                    //calls the searchTransactionsByVendor() method
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

    private static void filterTransactionsByDate(LocalDate startDate, LocalDate endDate) {
        //print out "Report"
        System.out.println("Report:");
        //makes a new Transaction object named transaction and goes through the transactions list.
        for (Transaction transaction : transactions) {
           /* if any dates in the transaction list is after the startDate-1 (so the starting day -1 day,
            so it includes the starting day), and also is before the endDate+1 (so it includes the end day)
            print out that String from the transaction list*/
            if (transaction.getDate().isAfter(startDate.minusDays(1)) && transaction.getDate().isBefore(endDate.plusDays(1))) {
                System.out.println(transaction);
            }
        }
    }

    private static void searchTransactionsByVendor() {
        //input which vendor you want to look for
        System.out.println("What vendor do you want to search for: ");
        String vendor = scanner.nextLine().trim();

        try {
            /*goes through every transaction in the transactions list, and if any of
            vendors match the vendor inputted by the user, it will print it out*/
            for (int i = 0; i < transactions.size(); i++) {
                if (vendor.equalsIgnoreCase(transactions.get(i).getVendor())) {
                    System.out.println(transactions.get(i).toString() + "\n");
                }
            }
        } catch (Exception e) {
            /*if something unexpected that doesn't align with the code happens,
            this text will appear and end the code*/
            System.out.println("An error has occurred! \n");
        }
    }
}