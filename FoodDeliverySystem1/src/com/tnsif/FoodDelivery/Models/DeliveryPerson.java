package com.tnsif.FoodDelivery.Models;

public class DeliveryPerson extends Users {
    private String vehicleType;
    private String licenseNumber;
    private boolean isAvailable;

    public DeliveryPerson() {
        super();
        this.isAvailable = true;
    }

    public DeliveryPerson(String username, String email, String password, String phone,
                          String vehicleType, String licenseNumber) {
        super(username, email, password, phone, UserType.DELIVERY);
        this.vehicleType = vehicleType;
        this.licenseNumber = licenseNumber;
        this.isAvailable = true;
    }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
}