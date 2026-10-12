package com.sis;

import java.util.Scanner;

public class StudentAcademicView {
    static String[] majors = {"IT", "Law", "Business"};
    static String[][] courseNames = {
        {"Programming", "Databases", "Networking", "Web Development"},
        {"Contract Law", "Criminal Law", "Constitutional Law", "Legal Writing"},
        {"Accounting", "Marketing", "Economics", "Management"}
    };

    public static String getCourseName(String major, int courseIndex) {
        for (int i = 0; i < majors.length; i++) {
            if (majors[i].equals(major)) {
                return courseNames[i][courseIndex - 1];
            }
        }
        return "Unknown course";
    }

    public static void showStudentMenu(Scanner scanner, int s, String major) {
        String choice;
        do {
            System.out.println("\n--- Student Menu (" + FacultyPortal.names[s] + ") ---");
            System.out.println("1. My courses and grades");
            System.out.println("2. My GPA");
            System.out.println("3. My attendance");
            System.out.println("4. Announcement");
            System.out.println("5. Degree audit");
            System.out.println("6. Tuition and payment");
            System.out.println("7. Change password");
            System.out.println("8. Logout");
            System.out.print("Select: ");
            choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": showGrades(s, major); break;
                case "2": showGpa(s); break;
                case "3": showAttendance(s); break;
                case "4": showAnnouncement(); break;
                case "5": showDegreeAudit(s, major); break;
                case "6": showTuition(scanner, s); break;
                case "7": changePassword(scanner); break;
                case "8": System.out.println("Logging out..."); break;
                default:  System.out.println("Invalid choice. Pick 1 to 8.");
            }
        } while (!choice.equals("8"));
    }

    static void showGrades(int s, String major) {
        if (!FacultyPortal.hasData[s]) {
            System.out.println("No grades yet. Please wait for your faculty.");
            return;
        }
        System.out.println("\n===== MY COURSES (" + major + ") =====");
        for (int i = 0; i < FacultyPortal.COURSES; i++) {
            double g = FacultyPortal.grades[s * FacultyPortal.COURSES + i];
            String status;
            if (g >= 60) {
                status = "PASS";
            } else {
                status = "FAIL";
            }
            System.out.println((i + 1) + ". " + getCourseName(major, i + 1) + " - " + g + " (" + status + ")");
        }
    }

    static double calculateGpa(int s) {
        double totalPoints = 0;
        for (int i = 0; i < FacultyPortal.COURSES; i++) {
            totalPoints += FacultyPortal.gradePoint(FacultyPortal.grades[s * FacultyPortal.COURSES + i]);
        }
        double gpa = totalPoints / FacultyPortal.COURSES;
        return (int) (gpa * 100) / 100.0;
    }

    static void showGpa(int s) {
        if (!FacultyPortal.hasData[s]) {
            System.out.println("No grades yet. Please wait for your faculty.");
            return;
        }
        System.out.println("Your GPA: " + calculateGpa(s));
    }

    static void showAttendance(int s) {
        if (!FacultyPortal.hasData[s]) {
            System.out.println("No attendance data yet.");
            return;
        }
        System.out.println("Your attendance: " + FacultyPortal.attendance[s] + "%");
        if (FacultyPortal.attendance[s] < 75) {
            System.out.println("Warning: your attendance is below 75%.");
        }
    }

    static void showAnnouncement() {
        if (FacultyPortal.announcement.equals("")) {
            System.out.println("No announcements right now.");
        } else {
            System.out.println("Announcement: " + FacultyPortal.announcement);
        }
    }

    static void showDegreeAudit(int s, String major) {
        System.out.println("\n--- Degree Audit ---");
        DegreeAuditTuition.runDegreeAudit(major, DegreeAuditTuition.countPassedCredits(s));
    }

    static void showTuition(Scanner scanner, int s) {
        if (!FacultyPortal.hasData[s]) {
            System.out.println("Tuition is not available until your grades are entered.");
            return;
        }
        System.out.println("\n--- Tuition ---");
        DegreeAuditTuition.calculateTuition(FacultyPortal.COURSES, calculateGpa(s));
        DegreeAuditTuition.makePayment(scanner);
    }

    static void changePassword(Scanner scanner) {
        System.out.print("Enter old password: ");
        String oldPass = scanner.nextLine().trim();
        System.out.print("Enter new password: ");
        String newPass = scanner.nextLine().trim();

        if (newPass.length() < 6) {
            System.out.println("[Error] New password must be at least 6 characters.");
            return;
        }

        if (App.currentUser.equals(App.s1ID) && oldPass.equals(App.s1Pass)) {
            App.s1Pass = newPass;
        } else if (App.currentUser.equals(App.s2ID) && oldPass.equals(App.s2Pass)) {
            App.s2Pass = newPass;
        } else if (App.currentUser.equals(App.s3ID) && oldPass.equals(App.s3Pass)) {
            App.s3Pass = newPass;
        } else if (App.currentUser.equals(App.s4ID) && oldPass.equals(App.s4Pass)) {
            App.s4Pass = newPass;
        } else {
            System.out.println("[Error] Old password is wrong.");
            return;
        }
        System.out.println("[Success] Password changed.");
    }
}