package com.example.omarelnajmi.deliverableonefirebase;

public class UserObject {

    private String serviceName;
    private String availability;
    private String rate;

    public UserObject(String service, String availability, String rate){
        this.serviceName = service;
        this.availability= availability;
        this.rate = rate;
    }

    public String getServiceName(){
        return serviceName;
    }
    public String getAvailability() {return availability;}
    public String getRate(){return rate;}
}
