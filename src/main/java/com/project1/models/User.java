package com.project1.models;

public class User {

    private String firstName;
    private String lastName;
    private String userName;
    private UserType type;

    public User(String firstName, String lastName, String userName, UserType type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.type = type;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public UserType getType() {
        return type;
    }
}
