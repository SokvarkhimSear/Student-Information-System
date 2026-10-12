package com.sis;

import java.util.Scanner;

public class App {
    static final int MIN_PASSWORD_LENGTH = 6;
    static final int MAX_LOGIN_ATTEMPTS = 3;

    // preset logins
    static String facultyUser = "admin";
    static String facultyPass = "faculty123";

    static String[] studentIDs = {"2026400", "2026399", "2026123", "2026122"};
    static String[] studentPasses = {"pass101", "pass102", "pass103", "pass104"};
    static String[] studentMajors = {"IT", "Law", "Business", "IT"};

    // session
    static String currentUser = "";
    static String currentRole = "";
    static String currentMajor = "";

    // returns the student's position in the arrays, or -1 if not found
    public static int findStudentIndex(String id) {
        for (int i = 0; i < studentIDs.length; i++) {
            if (studentIDs[i].equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
    }

    public static boolean login(String username, String password) {
        if (username.equals(facultyUser) && password.equals(facultyPass)) {
            currentUser = facultyUser;
            currentRole = "FACULTY";
            currentMajor = "N/A";
            return true;
        }

        int i = findStudentIndex(username);
        if (i != -1 && password.equals(studentPasses[i])) {
            currentUser = studentIDs[i];
            currentRole = "STUDENT";
            currentMajor = studentMajors[i];
            return true;
        }

        return false;
    }

    // clears the session so nobody can reuse the previous login
    public static void logout() {
        currentUser = "";
        currentRole = "";
        currentMajor = "";
    }

    // used by both the faculty and student menus
    public static void changePassword(Scanner scanner) {
        if (currentUser.isEmpty()) {
            System.out.println("\n[Error] You must log in first before changing your password!");
            return;
        }

        boolean isFaculty = currentRole.equals("FACULTY");
        int idx = -1;
        String actualPass;
        if (isFaculty) {
            actualPass = facultyPass;
        } else {
            idx = findStudentIndex(currentUser);
            actualPass = studentPasses[idx];
        }

        System.out.print("Enter current password: ");
        String oldPass = scanner.nextLine().trim();
        if (!oldPass.equals(actualPass)) {
            System.out.println("[Error] Incorrect current password.");
            return;
        }

        System.out.print("Enter new password: ");
        String newPass = scanner.nextLine().trim();
        if (newPass.length() < MIN_PASSWORD_LENGTH) {
            System.out.println("[Error] New password must be at least " + MIN_PASSWORD_LENGTH + " characters.");
            return;
        }

        if (isFaculty) {
            facultyPass = newPass;
        } else {
            studentPasses[idx] = newPass;
        }
        System.out.println("[Success] Password updated successfully!");
    }

    // keeps asking until the number is between min and max
    public static double readDouble(Scanner scanner, String prompt, double min, double max) {
        System.out.print(prompt);
        double value = scanner.nextDouble();
        scanner.nextLine();
        while (value < min || value > max) {
            System.out.printf("[Error] Enter a number from %.2f to %.2f.%n", min, max);
            System.out.print(prompt);
            value = scanner.nextDouble();
            scanner.nextLine();
        }
        return value;
    }

    public static int readInt(Scanner scanner, String prompt, int min, int max) {
        System.out.print(prompt);
        int value = scanner.nextInt();
        scanner.nextLine();
        while (value < min || value > max) {
            System.out.println("[Error] Enter a whole number from " + min + " to " + max + ".");
            System.out.print(prompt);
            value = scanner.nextInt();
            scanner.nextLine();
        }
        return value;
    }

    public static void loginFlow(Scanner scanner) {
        for (int attempt = 1; attempt <= MAX_LOGIN_ATTEMPTS; attempt++) {
            System.out.print("Enter Username / Student ID: ");
            String user = scanner.nextLine().trim();
            System.out.print("Enter Password: ");
            String pass = scanner.nextLine().trim();

            if (login(user, pass)) {
                System.out.println("\n[Login Successful] Welcome, " + currentUser + " (" + currentRole + ")");

                if (currentRole.equals("FACULTY")) {
                    FacultyPortal.facultyMenu(scanner);
                } else {
                    StudentAcademicView.showStudentMenu(scanner, findStudentIndex(currentUser), currentMajor);
                }

                logout();
                return;
            }

            int remaining = MAX_LOGIN_ATTEMPTS - attempt;
            if (remaining > 0) {
                System.out.println("\n[Error] Invalid credentials. Attempts remaining: " + remaining + "\n");
            } else {
                System.out.println("\n[Access Denied] Too many failed login attempts. Returning to main menu.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=========================================");
            System.out.println("   STUDENT INFORMATION SYSTEM (SIS)     ");
            System.out.println("=========================================");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    loginFlow(scanner);
                    break;
                case "2":
                    System.out.println("Exiting System. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1 or 2.");
            }
        }
        scanner.close();
    }
}