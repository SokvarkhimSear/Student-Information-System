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

    // rounds money to 2 decimal places (same trick as the GPA in FacultyPortal)
    static double roundMoney(double x) {
        return (int) (x * 100 + 0.5) / 100.0;
    }

    static double discountRate(double gpa) {
        if (gpa >= 3.5) {
            return 0.2;
        } else if (gpa >= 3.0) {
            return 0.1;
        } else {
            return 0.0;
        }
    }

    static double getTuition(int coursesTaken) {
        return roundMoney(coursesTaken * COST_PER_COURSE);
    }

    static double getDiscount(int coursesTaken, double gpa) {
        return roundMoney(getTuition(coursesTaken) * discountRate(gpa));
    }

    // always recalculated from the current GPA, so edited grades stay consistent
    static double getAmountDue(int s, int coursesTaken, double gpa) {
        double due = roundMoney(getTuition(coursesTaken) - getDiscount(coursesTaken, gpa) - paid[s]);
        if (due < 0.01) {
            due = 0;
        }
        return due;
    }

    public static void calculateTuition(int s, int coursesTaken, double gpa) {
        System.out.println("Courses taken: " + coursesTaken);
        System.out.printf("Tuition: $%.2f%n", getTuition(coursesTaken));
        System.out.printf("Scholarship discount: -$%.2f%n", getDiscount(coursesTaken, gpa));
        System.out.printf("Paid so far: $%.2f%n", paid[s]);
        System.out.printf("Amount due: $%.2f%n", getAmountDue(s, coursesTaken, gpa));
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
        double due = getAmountDue(s, coursesTaken, gpa);
        if (due == 0) {
            System.out.println("Payment status: PAID");
            return;
        }

        double payment = App.readDouble(scanner, "Enter payment amount: $", 0, due);
        paid[s] = roundMoney(paid[s] + payment);

        due = getAmountDue(s, coursesTaken, gpa);
        if (due == 0) {
            System.out.println("Payment status: PAID");
        } else if (paid[s] > 0) {
            System.out.println("Payment status: PARTIAL");
            System.out.printf("Remaining balance (carries over): $%.2f%n", due);
        } else {
            System.out.println("Payment status: UNPAID");
            System.out.printf("Remaining balance (carries over): $%.2f%n", due);
        }
    }
}