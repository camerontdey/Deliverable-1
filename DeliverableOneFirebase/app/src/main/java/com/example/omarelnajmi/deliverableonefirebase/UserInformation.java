package com.example.omarelnajmi.deliverableonefirebase;

public class UserInformation {

    public String userRole;
    public String userName;

    public UserInformation() {
    }

    public UserInformation(String userRole, String userName) {
        this.userRole = userRole;
        this.userName = userName;
    }

    public String getUserRole() {
        return userRole;
    }

    public String getUserName() {
        return userName;
    }
}
