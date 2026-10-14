package com.sis;

import java.util.Scanner;

public class DegreeAuditTuition {
    static final int REQUIRED_CREDITS = 120;
    static final double COST_PER_COURSE = 600;
    static final double PASS_MARK = 60;

    // how much each student has paid so far (one slot per student)
    static double[] paid = new double[4];

    public static int getRequiredCredits(String major) {
        switch (major) {
            case "IT":
                return REQUIRED_CREDITS;
            case "Law":
                return REQUIRED_CREDITS;
            case "Business":
                return REQUIRED_CREDITS;
            default:
                return 0;
        }
    }

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

    public static void calculateTuition(int s, int coursesTaken, double gpa) {
        double tuition = coursesTaken * COST_PER_COURSE;

        double discountRate;
        if (gpa >= 3.5) {
            discountRate = 0.2;
        } else if (gpa >= 3.0) {
            discountRate = 0.1;
        } else {
            discountRate = 0.0;
        }

        double discount = tuition * discountRate;
        double amountDue = tuition - discount - paid[s];

        System.out.println("Courses taken: " + coursesTaken);
        System.out.printf("Tuition: $%.2f%n", tuition);
        System.out.printf("Scholarship discount: -$%.2f%n", discount);
        System.out.printf("Paid so far: $%.2f%n", paid[s]);
        System.out.printf("Amount due: $%.2f%n", amountDue);
    }

    public static int countPassedCredits(int s) {
        int credits = 0;
        for (int i = 0; i < FacultyPortal.COURSES; i++) {
            double g = FacultyPortal.grades[s * FacultyPortal.COURSES + i];
            if (g >= PASS_MARK) {
                credits = credits + FacultyPortal.CREDITS;
            }
        }
        return credits;
    }

    public static void makePayment(Scanner scanner, int s, int coursesTaken, double gpa) {
        double tuition = coursesTaken * COST_PER_COURSE;

        double discountRate;
        if (gpa >= 3.5) {
            discountRate = 0.2;
        } else if (gpa >= 3.0) {
            discountRate = 0.1;
        } else {
            discountRate = 0.0;
        }

        double amountDue = tuition - (tuition * discountRate) - paid[s];

        if (amountDue <= 0) {
            System.out.println("Payment status: PAID");
            return;
        }

        double payment = App.readDouble(scanner, "Enter payment amount: $", 0, amountDue);
        paid[s] = paid[s] + payment;
        amountDue = amountDue - payment;

        if (amountDue <= 0) {
            System.out.println("Payment status: PAID");
        } else if (paid[s] > 0) {
            System.out.println("Payment status: PARTIAL");
            System.out.printf("Remaining balance (carries over): $%.2f%n", amountDue);
        } else {
            System.out.println("Payment status: UNPAID");
            System.out.printf("Remaining balance (carries over): $%.2f%n", amountDue);
        }
    }
}