package com.sis;

import java.util.Scanner;

public class App {

    // Preset data (Faculty account & 3 Students)
    static String facultyUser = "admin";
    static String facultyPass = "faculty123";

    static String s1ID = "S101", s1Pass = "pass101", s1Major = "IT";
    static String s2ID = "S102", s2Pass = "pass102", s2Major = "Law";
    static String s3ID = "S103", s3Pass = "pass103", s3Major = "Business";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=========================================");
        System.out.println("   STUDENT INFORMATION SYSTEM (SIS)     ");
        System.out.println("=========================================");

        // Main menu and login loop goes here
    }

    public static boolean login(String username, String password) {
        // Validation logic for faculty and students
        return false;
    }

    public static void changePassword() {
        // Password update logic[cite: 13]
    }
}