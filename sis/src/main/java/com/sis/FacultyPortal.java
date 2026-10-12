package com.sis;

import java.util.Scanner;

public class FacultyPortal {
    static final int COURSES = 4;
    static final int CREDITS = 3;

    static String[] names = {"Student A", "Student B", "Student C", "Student D"};
    static double[] grades = new double[4 * COURSES];
    static double[] attendance = new double[4];
    static boolean[] hasData = new boolean[4];
    static String announcement = "";

    public static void facultyMenu(Scanner scanner) {
        String choice;
        int s;
        do {
            System.out.println("\n--- Faculty Menu ---");
            System.out.println("a. Enter semester data");
            System.out.println("b. Edit a grade");
            System.out.println("c. View report");
            System.out.println("d. Post announcement");
            System.out.println("e. Change password");
            System.out.println("f. Logout");
            System.out.print("Select: ");
            choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "a":
                    s = selectStudent(scanner);
                    if (s != -1) {
                        enterGrades(scanner, s);
                    }
                    break;
                case "b":
                    s = selectStudent(scanner);
                    if (s != -1) {
                        editGrade(scanner, s);
                    }
                    break;
                case "c":
                    s = selectStudent(scanner);
                    if (s != -1) {
                        viewReport(s);
                    }
                    break;
                case "d": postAnnouncement(scanner); break;
                case "e": App.changePassword(scanner); break;
                case "f": System.out.println("Logging out..."); break;
                default:  System.out.println("Invalid choice. Pick a to f.");
            }
        } while (!choice.equals("f"));
    }

    // asks for a student ID each time, so faculty can switch students; blank input cancels
    static int selectStudent(Scanner scanner) {
        int s = -2;
        while (s == -2) {
            System.out.print("Enter student ID (press Enter to cancel): ");
            String id = scanner.nextLine().trim();
            if (id.isEmpty()) {
                s = -1;
            } else {
                int found = App.findStudentIndex(id);
                if (found == -1) {
                    System.out.println("Student not found.");
                } else {
                    System.out.println("Selected: " + names[found] + " (" + App.studentIDs[found] + ")");
                    s = found;
                }
            }
        }
        return s;
    }

    static void enterGrades(Scanner scanner, int s) {
        for (int i = 0; i < COURSES; i++) {
            grades[s * COURSES + i] = App.readDouble(scanner, "Grade for course " + (i + 1) + " (0-100): ", 0, 100);
        }
        attendance[s] = App.readDouble(scanner, "Attendance % (0-100): ", 0, 100);
        hasData[s] = true;
        System.out.println("[Success] Semester data saved.");
    }

    static void editGrade(Scanner scanner, int s) {
        if (!hasData[s]) {
            System.out.println("[Error] Enter semester data first (option a).");
            return;
        }
        int course = App.readInt(scanner, "Course number (1-" + COURSES + "): ", 1, COURSES);
        grades[s * COURSES + (course - 1)] = App.readDouble(scanner, "New grade (0-100): ", 0, 100);
        System.out.println("[Success] Grade updated.");
    }

    static void viewReport(int s) {
        if (!hasData[s]) {
            System.out.println("[Error] No data for this student yet.");
            return;
        }
        System.out.println("\n===== REPORT: " + names[s] + " (" + App.studentIDs[s] + ") =====");
        for (int i = 0; i < COURSES; i++) {
            System.out.println("Course " + (i + 1) + ": " + grades[s * COURSES + i]);
        }
        double gpa = calculateGpa(s);
        System.out.println("GPA: " + gpa);
        System.out.println("Attendance: " + attendance[s] + "%");

        // same tuition numbers the student sees (includes the scholarship discount)
        System.out.println("\n--- Tuition ---");
        DegreeAuditTuition.calculateTuition(s, COURSES, gpa);
    }

    static double gradePoint(double g) {
        if (g >= 90) return 4.0;
        else if (g >= 80) return 3.0;
        else if (g >= 70) return 2.0;
        else if (g >= 60) return 1.0;
        else return 0.0;
    }

    public static double calculateGpa(int s) {
        double totalPoints = 0;
        for (int i = 0; i < COURSES; i++) {
            totalPoints += gradePoint(grades[s * COURSES + i]);
        }
        double gpa = totalPoints / COURSES;
        return (int) (gpa * 100) / 100.0;
    }

    static void postAnnouncement(Scanner scanner) {
        System.out.print("Enter announcement: ");
        announcement = scanner.nextLine().trim();
        System.out.println("[Success] Announcement posted.");
    }
}