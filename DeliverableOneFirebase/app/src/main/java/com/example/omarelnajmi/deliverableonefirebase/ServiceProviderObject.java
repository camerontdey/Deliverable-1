package com.example.omarelnajmi.deliverableonefirebase;

public class ServiceProviderObject extends UserInformation {
    public String serviceName;
    public String availability;

    public ServiceProviderObject(String serviceName, String availability){
        this.serviceName = serviceName;
        this.availability = availability;
    }

    public String getServiceName(){
        return this.serviceName;
    }

    @Override
    public String toString() {
        return "ServiceProviderObject{" +
                "serviceName='" + serviceName + '\'' +
                ", availability='" + availability + '\'' +
                '}';
    }
}
