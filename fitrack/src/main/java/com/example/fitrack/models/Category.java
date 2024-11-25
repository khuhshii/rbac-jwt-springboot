package com.example.fitrack.models;

public enum Category {
    TEAM("Team"),
    INDIVIDUAL("User");

    private final String displayName;

    Category(String displayName){
        this.displayName=displayName;
    }

    public String getDisplayName(){
        return displayName;
    }
}
