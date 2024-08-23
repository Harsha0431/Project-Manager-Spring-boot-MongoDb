package com.Helpers;

public enum MediaType {
    IMAGE,
    VIDEO;

    public static MediaType fromString(String value){
        try{
            return MediaType.valueOf(value);
        }
        catch (Exception e){
            return null;
        }
    }
}
