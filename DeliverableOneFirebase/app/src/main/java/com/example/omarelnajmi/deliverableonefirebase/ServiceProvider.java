package com.example.omarelnajmi.deliverableonefirebase;

public class ServiceProvider extends UserInformation {

    public String userAddress;
    public String nameOfCompany;
    public String userDescription;
    public String isLicensed;

    public ServiceProvider(String userAddress, String nameOfCompany, String userDescription, String isLicensed) {
        this.userAddress = userAddress;
        this.nameOfCompany = nameOfCompany;
        this.userDescription = userDescription;
        this.isLicensed = isLicensed;

    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getNameOfCompany() {
        return nameOfCompany;
    }

    public void setNameOfCompany(String nameOfCompany) {
        this.nameOfCompany = nameOfCompany;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public String getIsLicensed() {
        return isLicensed;
    }

    public void setIsLicensed(String isLicensed) {
        this.isLicensed = isLicensed;
    }
}
