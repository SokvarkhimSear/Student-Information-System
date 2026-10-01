package com.sis;

import java.util.Scanner;

public class App {
    //This is the preset logins and stuff
    static String facultyUser = "admin";
    static String facultyPass = "faculty123";

    static String s1ID = "2026400", s1Pass = "pass101", s1Major = "IT";
    static String s2ID = "2026399", s2Pass = "pass102", s2Major = "Law";
    static String s3ID = "2026123", s3Pass = "pass103", s3Major = "Business";
    static String s4ID = "2026122", s4Pass = "pass104", s4Major = "IT";

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

        //Student1
        if (username.equalsIgnoreCase(s1ID) && password.equals(s1Pass)) {
            currentUser = s1ID;
            currentRole = "STUDENT";
            currentMajor = s1Major;
            return true;
        }

        //Student2
        if (username.equalsIgnoreCase(s2ID) && password.equals(s2Pass)) {
            currentUser = s2ID;
            currentRole = "STUDENT";
            currentMajor = s2Major;
            return true;
        }

        //Student3
        if (username.equalsIgnoreCase(s3ID) && password.equals(s3Pass)) {
            currentUser = s3ID;
            currentRole = "STUDENT";
            currentMajor = s3Major;
            return true;
        }

        //Student4
        if (username.equalsIgnoreCase(s4ID) && password.equals(s4Pass)) {
            currentUser = s4ID;
            currentRole = "STUDENT";
            currentMajor = s4Major;
            return true;
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
        boolean verified = false;
        if (currentRole.equals("FACULTY") && oldPass.equals(facultyPass)) verified = true;
        if (currentUser.equalsIgnoreCase(s1ID) && oldPass.equals(s1Pass)) verified = true;
        if (currentUser.equalsIgnoreCase(s2ID) && oldPass.equals(s2Pass)) verified = true;
        if (currentUser.equalsIgnoreCase(s3ID) && oldPass.equals(s3Pass)) verified = true;
        if (currentUser.equalsIgnoreCase(s4ID) && oldPass.equals(s4Pass)) verified = true;

        if (!verified) {
            System.out.println("[Error] Incorrect current password.");
            return;
        }

        //ask for new password
        System.out.print("Enter new password: ");
        String newPass = scanner.nextLine().trim();

        if (currentRole.equals("FACULTY")) facultyPass = newPass;
        else if (currentUser.equalsIgnoreCase(s1ID)) s1Pass = newPass;
        else if (currentUser.equalsIgnoreCase(s2ID)) s2Pass = newPass;
        else if (currentUser.equalsIgnoreCase(s3ID)) s3Pass = newPass;
        else if (currentUser.equalsIgnoreCase(s4ID)) s4Pass = newPass;

        System.out.println("[Success] Password updated successfully!");
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
                    System.out.print("Enter Username / Student ID: ");
                    String user = scanner.nextLine().trim();
                    System.out.print("Enter Password: ");
                    String pass = scanner.nextLine().trim();

                    if (login(user, pass)) {
                        System.out.println("\n[Login Successful] Welcome, " + currentUser + " (" + currentRole + ")");
                        
                        //sends to friend's portal 
                        if (currentRole.equals("FACULTY")) {
                            FacultyPortal.enterSemesterData();
                        } else if (currentRole.equals("STUDENT")) {
                            System.out.println("\n--- Academic View ---");
                            System.out.println("Enrolled Course: " + StudentAcademicView.getCourseName(currentMajor, 1));
                            
                            System.out.println("\n--- Degree Audit ---");
                            DegreeAuditTuition.runDegreeAudit(currentMajor, 30);
                        }
                    } else {
                        System.out.println("\n[Error] Invalid credentials. Please try again.");
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


