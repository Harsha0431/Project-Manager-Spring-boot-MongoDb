package com.Helpers;

public enum ProjectStatus {
    INITIATION,  // Define the project's goals, scope, budget, and timeline
    PLANNING,  // Create a comprehensive project plan
    UNDER_DEVELOPMENT,  // Put the plan into action and keep the team on track
    MONITORING_AND_CONTROLLING,
    COMPLETED;  // The final phase of the project

    public static ProjectStatus fromString(String status){
        try{
            if(status.equalsIgnoreCase("under development"))
                status = "UNDER_DEVELOPMENT";
            else if (status.equalsIgnoreCase("monitoring and controlling")) {
                status = "MONITORING_AND_CONTROLLING";
            }
            return ProjectStatus.valueOf(status.toUpperCase());
        }
        catch (Exception e){
            return  null;
        }
    }
}
