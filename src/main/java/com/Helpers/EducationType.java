package com.Helpers;

public enum EducationType {
    PRIMARY,         // For primary or elementary education
    SECONDARY,       // For high school or secondary education
    DIPLOMA,         // For diploma or vocational education
    BACHELORS,       // For undergraduate degrees
    MASTERS,         // For postgraduate or master's degrees
    DOCTORATE,       // For PhD or doctoral degrees
    ASSOCIATE,       // For associate degrees
    CERTIFICATE,     // For professional or certificate courses
    HIGH_SCHOOL,     // Specifically for high school diploma or equivalent
    POST_DOCTORATE;  // For post-doctoral education

    public static EducationType fromString(String value) {
        try {
            return EducationType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null; // Or handle the exception as needed
        }
    }
}
