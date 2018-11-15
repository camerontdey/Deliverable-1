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



//    public abstract String getUserAddress();
//
//    public abstract void setUserAddress(String userAddress);
//
//    public abstract String getNameOfCompany();
//
//    public abstract void setNameOfCompany(String nameOfCompany);
//
//    public abstract String getUserDescription();
//
//    public abstract void setUserDescription(String userDescription);
//
//    public abstract String getIsLicensed();
//
//    public abstract void setIsLicensed(String isLicensed);


}
