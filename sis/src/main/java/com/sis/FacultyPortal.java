package com.sis;

import java.util.Scanner;

public class FacultyPortal {
    // storage
    static final int COURSES = 4;
    static final int CREDITS = 3;     
    static final double RATE = 600.0;
    static Scanner scanner = new Scanner(System.in); 

    static String[] ids = {"2026400", "2026399", "2026123", "2026122"};
    static String[] names = {"Student A", "Student B", "Student C", "Student D"}; 
    static double[] grades = new double[4 * COURSES]; 
    static double[] attendance = new double[4];
    static boolean[] hasData = new boolean[4];
    static String announcement = "";

    public static void enterSemesterData() {
        int s = -1;
        while (s == -1) {
            System.out.print("Enter student ID: ");
            s = findStudent(scanner.nextLine().trim());
            if (s == -1) {
                System.out.println("Student not found.");
            }
        }
        // Choices 
        String choice;
        do {
            System.out.println("\n--- Faculty Menu (" + names[s] + ") ---");
            System.out.println("a. Enter semester data");
            System.out.println("b. Edit a grade");
            System.out.println("c. View report");
            System.out.println("d. Post announcement");
            System.out.println("e. Change password");
            System.out.println("f. Logout");
            System.out.print("Select: ");
            choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "a": enterGrades(s); break;
                case "b": editGrade(s); break;
                case "c": viewReport(s); break;
                case "d": postAnnouncement(); break;
                case "e": App.changePassword(scanner); break;
                case "f": System.out.println("Logging out..."); break;
                default:  System.out.println("Invalid choice. Pick a to f.");
            }
        } while (!choice.equals("f"));
    }

    static int findStudent(String id) {
        for (int i = 0; i < ids.length; i++) {
            if (ids[i].equals(id)) {
                return i;
            }
        }
        return -1;
    }
    //grade
    static void enterGrades(int s) {
        for (int i = 0; i < COURSES; i++) {
            grades[s * COURSES + i] = readScore("Grade for course " + (i + 1) + " (0-100): ");
        }
        attendance[s] = readScore("Attendance % (0-100): ");
        hasData[s] = true;
        System.out.println("[Success] Semester data saved.");
    }

    static double readScore(String prompt) {
        double value = -1;
        while (value < 0 || value > 100) {
            System.out.print(prompt);
            value = scanner.nextDouble();
            scanner.nextLine();                 
            if (value < 0 || value > 100) {
                System.out.println("[Error] Enter a number from 0 to 100.");
            }
        }
        return value;
    }
    //edit grade
    static void editGrade(int s) {
        if (!hasData[s]) {
            System.out.println("[Error] Enter semester data first (option a).");
            return;
        }
        int course = 0;
        while (course < 1 || course > COURSES) {
            System.out.print("Course number (1-" + COURSES + "): ");
            course = scanner.nextInt();
            scanner.nextLine();
        }
        grades[s * COURSES + (course - 1)] = readScore("New grade (0-100): ");
        System.out.println("[Success] Grade updated.");
    }
    //report
    static void viewReport(int s) {
        if (!hasData[s]) {
            System.out.println("[Error] No data for this student yet.");
            return;
        }
        double totalPoints = 0;
        System.out.println("\n===== REPORT: " + names[s] + " (" + ids[s] + ") =====");
        for (int i = 0; i < COURSES; i++) {
            double g = grades[s * COURSES + i];
            System.out.println("Course " + (i + 1) + ": " + g);
            totalPoints += gradePoint(g);
        }
        double gpa = totalPoints / COURSES;
        gpa = (int) (gpa * 100) / 100.0;  
        System.out.println("GPA: " + gpa);
        System.out.println("Attendance: " + attendance[s] + "%");
        System.out.println("Tuition: $" + (COURSES * CREDITS * RATE));
    }

    static double gradePoint(double g) {
        if (g >= 90) return 4.0;
        else if (g >= 80) return 3.0;
        else if (g >= 70) return 2.0;
        else if (g >= 60) return 1.0;
        else return 0.0;
    }
    //announcement
    static void postAnnouncement() {
        System.out.print("Enter announcement: ");
        announcement = scanner.nextLine().trim();
        System.out.println("[Success] Announcement posted.");
    }

    // TEMP: for testing only, delete later
    public static void main(String[] args) {
        App.currentUser = "admin";
        App.currentRole = "FACULTY";
        enterSemesterData();
    }
}