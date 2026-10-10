package com.sis;

public class DegreeAuditTuition {
    // This method will run the degree audit for a student based on their major and completed credits
    // Implementation will be added here
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
    public static void calculateTuition(){

    }
    public static void makePayment(){
        
    }
}

