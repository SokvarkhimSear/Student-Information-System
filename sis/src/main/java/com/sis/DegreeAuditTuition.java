package com.sis;
import java.util.Scanner;

public class DegreeAuditTuition {
    // Step 1: how many credits each major needs
    public static int getRequiredCredits(String major) {
        switch (major) {
            case "IT":
                return 120;
            case "Law":
                return 120;
            case "Business":
                return 120;
            default:
                return 0;
        }
    }

    // Steps 2-4: the degree audit
    public static void runDegreeAudit(String major, int completedCredits) {
        int required = getRequiredCredits(major);

        if (required == 0) {
            System.out.println("Unknown major, cannot run audit.");
            return;
        }

        int remaining = required - completedCredits;
        if (remaining < 0) {
            remaining = 0;
        }

        double percent = (completedCredits * 100.0) / required;

        System.out.println("Major: " + major);
        System.out.println("Credits required: " + required);
        System.out.println("Credits completed: " + completedCredits);
        System.out.println("Credits remaining: " + remaining);
        System.out.printf("Completed: %.1f%%%n", percent);

        if (remaining == 0) {
            System.out.println("Graduation eligibility: ELIGIBLE");
        } else {
            System.out.println("Graduation eligibility: NOT ELIGIBLE");
        }
    }
    static double costPerCourse = 600;
    static double amountDue = 0;
    static double balance = 0;
    public static void calculateTuition(int coursesTaken, double gpa){
        double tuition = coursesTaken * costPerCourse;
        double discountRate;
        if (gpa >= 3.5){
            discountRate = 0.2;
        }else if (gpa >=3.0){
            discountRate = 0.1;
        }else{
            discountRate = 0.0;
        }

        double discount = tuition * discountRate;
        amountDue = tuition - discount + balance;
        System.out.println("Courses taken: " + coursesTaken);
        System.out.printf("Tuition: $%.2f%n", tuition);
        System.out.printf("Scholarship discount: -$%.2f%n", discount);
        System.out.printf("Previous balance: $%.2f%n", balance);
        System.out.printf("Amount due: $%.2f%n", amountDue);
    }
    public static void makePayment(Scanner scanner){
        if (amountDue <= 0){
            System.out.println("Nothing to pay.");
            return;
        }
        System.out.print("Enter payment amount: $");
        double paid = Double.parseDouble(scanner.nextLine().trim());

        // ask again while the amount is invalid
        while (paid < 0 || paid > amountDue) {
            System.out.println("[Error] Invalid amount. Try again.");
            System.out.print("Enter payment amount: $");
            paid = Double.parseDouble(scanner.nextLine().trim());
        }

        balance = amountDue - paid;

        if (paid == amountDue) {
            System.out.println("Payment status: PAID");
        } else if (paid > 0) {
            System.out.println("Payment status: PARTIAL");
            System.out.printf("Remaining balance (carries over): $%.2f%n", balance);
        } else {
            System.out.println("Payment status: UNPAID");
            System.out.printf("Remaining balance (carries over): $%.2f%n", balance);
        }

        amountDue = balance;
    }
}

