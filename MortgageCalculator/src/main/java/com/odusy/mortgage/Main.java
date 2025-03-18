package com.odusy.mortgage;

import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static final byte MONTHS_IN_YEAR = 12;
    private static final byte PERCENT = 100;
    private static final int MIN_PRINCIPAL = 100;
    private static final int MAX_PRINCIPAL = 1000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int principal = readPrincipal(scanner);
        float annualInterestRate = readAnnualInterestRate(scanner);
        byte periodInYears = readPeriodInYears(scanner);

        double mortgage = calculateMortgage(principal, annualInterestRate, periodInYears);
        String mortgageFormatted = NumberFormat.getCurrencyInstance().format(mortgage);

        System.out.println("Your Mortgage is: " + mortgageFormatted);

        scanner.close();
    }

    private static int readPrincipal(Scanner scanner) {
        int principal;
        while (true) {
            System.out.print("Enter Principal between $" + MIN_PRINCIPAL + " to $" + MAX_PRINCIPAL + ": ");
            if (scanner.hasNextInt()) {
                principal = scanner.nextInt();
                if (principal >= MIN_PRINCIPAL && principal <= MAX_PRINCIPAL)
                    break;
                else
                    System.out.println("Invalid input. Please enter a value between " + MIN_PRINCIPAL + " and " + MAX_PRINCIPAL + ".");
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Discard invalid input
            }
        }
        return principal;
    }

    private static float readAnnualInterestRate(Scanner scanner) {
        float annualInterestRate;
        while (true) {
            System.out.print("Enter Annual Interest Rate (%): ");
            if (scanner.hasNextFloat()) {
                annualInterestRate = scanner.nextFloat();
                if (annualInterestRate > 0) // Ensure positive interest rate
                    break;
                else
                    System.out.println("Interest rate must be greater than 0.");
            } else {
                System.out.println("Invalid input. Please enter a valid percentage.");
                scanner.next(); // Discard invalid input
            }
        }
        return annualInterestRate;
    }

    private static byte readPeriodInYears(Scanner scanner) {
        byte periodInYears;
        while (true) {
            System.out.print("Enter Period (Years): ");
            if (scanner.hasNextByte()) {
                periodInYears = scanner.nextByte();
                if (periodInYears > 0) // Ensure positive period
                    break;
                else
                    System.out.println("Period must be greater than 0.");
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Discard invalid input
            }
        }
        return periodInYears;
    }

    private static double calculateMortgage(int principal, float annualInterestRate, byte periodInYears) {
        float monthlyInterestRate = annualInterestRate / PERCENT / MONTHS_IN_YEAR;
        int numberOfPayments = periodInYears * MONTHS_IN_YEAR;

        return principal * (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfPayments)) /
                (Math.pow(1 + monthlyInterestRate, numberOfPayments) - 1);
    }
}
