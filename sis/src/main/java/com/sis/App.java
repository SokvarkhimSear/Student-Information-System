package com.sis;

import java.util.Scanner;

public class App {
    //This is the preset logins and stuff
    static String facultyUser = "admin";
    static String facultyPass = "faculty123";

    static String[] studentIDs = {"2026400", "2026399", "2026123", "2026122"};
    static String[] studentPasses = {"pass101", "pass102", "pass103", "pass104"};
    static String[] studentMajors = {"IT", "Law", "Business", "IT"};

    //session role
    static String currentUser = "";
    static String currentRole = "";
    static String currentMajor = "";

    //Login
    public static boolean login(String username, String password) {
        //Faculty
        if (username.equals(facultyUser) && password.equals(facultyPass)) {
            currentUser = facultyUser;
            currentRole = "FACULTY";
            currentMajor = "N/A";
            return true;
        }

        for (int i = 0; i < studentIDs.length; i++) {
            if (username.equalsIgnoreCase(studentIDs[i]) && password.equals(studentPasses[i])) {
                currentUser = studentIDs[i];
                currentRole = "STUDENT";
                currentMajor = studentMajors[i];
                return true;
            }
        }

        //no matching credentials
        return false;
    }

    //change password
    public static void changePassword(Scanner scanner) {
        //check if they are logged in bc need to login to change password
        if (currentUser.isEmpty()) {
            System.out.println("\n[Error] You must log in first before changing your password!");
            return;
        }

        System.out.print("Enter current password: ");
        String oldPass = scanner.nextLine().trim();

        //verify old password
        if (currentRole.equals("FACULTY")) {
            if (oldPass.equals(facultyPass)) {
                System.out.print("Enter new password: ");
                facultyPass = scanner.nextLine().trim();
                System.out.println("[Success] Faculty password updated successfully!");
            } else {
                System.out.println("[Error] Incorrect current password.");
            }
            return;
        }

        for (int i = 0; i < studentIDs.length; i++) {
            if (currentUser.equalsIgnoreCase(studentIDs[i])) {
                if (oldPass.equals(studentPasses[i])) {
                    System.out.print("Enter new password: ");
                    studentPasses[i] = scanner.nextLine().trim();
                    System.out.println("[Success] Password updated successfully!");
                } else {
                    System.out.println("[Error] Incorrect current password.");
                }
                return;
            }
        }
    }

    //The UI ig
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=========================================");
            System.out.println("   STUDENT INFORMATION SYSTEM (SIS)     ");
            System.out.println("=========================================");
            System.out.println("1. Login");
            System.out.println("2. Change Password");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    //3 attempts to login
                    int maxAttempts = 3;

                    for (int attempt = 1; attempt <= maxAttempts; attempt++) {
                        System.out.print("Enter Username / Student ID: ");
                        String user = scanner.nextLine().trim();
                        System.out.print("Enter Password: ");
                        String pass = scanner.nextLine().trim();

                        if (login(user, pass)) {
                            System.out.println("\n[Login Successful] Welcome, " + currentUser + " (" + currentRole + ")");
                            
                            // sends to friend's portal 
                            if (currentRole.equals("FACULTY")) {
                                FacultyPortal.enterSemesterData();
                            } else if (currentRole.equals("STUDENT")) {
                                System.out.println("\n--- Academic View ---");
                                System.out.println("Enrolled Course: " + StudentAcademicView.getCourseName(currentMajor, 1));
                                
                                System.out.println("\n--- Degree Audit ---");
                                DegreeAuditTuition.runDegreeAudit(currentMajor, 30);

                                System.out.println("\n--- Tuition ---");
                                DegreeAuditTuition.calculateTuition(4, 3.5);
                                DegreeAuditTuition.makePayment(scanner);
                            }
                            break; 
                        } else {
                            int remaining = maxAttempts - attempt;
                            if (remaining > 0) {
                                System.out.println("\n[Error] Invalid credentials. Attempts remaining: " + remaining + "\n");
                            } else {
                                System.out.println("\n[Access Denied] Too many failed login attempts. Returning to main menu.");
                            }
                        }
                    }
                    break;

                case "2":
                    changePassword(scanner);
                    break;

                case "3":
                    System.out.println("Exiting System. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }
        }
        scanner.close();
    }
}