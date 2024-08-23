package com.Helpers;

public enum VisibilityType {
    PUBLIC,
    PRIVATE,
    CONNECTIONS_ONLY;

    public static VisibilityType fromString(String value) {
        try {
            if(value.equalsIgnoreCase("CONNECTIONS ONLY"))
                value = "CONNECTIONS_ONLY";
            return VisibilityType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
