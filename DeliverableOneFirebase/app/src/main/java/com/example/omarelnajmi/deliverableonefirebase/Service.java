package com.example.omarelnajmi.deliverableonefirebase;

public class Service {

    public String serviceName;
    public double hourlyWage;

    public Service(String serviceName, double hourlyWage) {
        this.serviceName = serviceName;
        this.hourlyWage = hourlyWage;
    }

    public Service(){

    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }
}
