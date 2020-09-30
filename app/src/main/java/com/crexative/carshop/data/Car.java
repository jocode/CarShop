package com.crexative.carshop.data;

import com.orm.SugarRecord;

import java.io.Serializable;

public class Car extends SugarRecord implements Serializable {

    int seats;
    double price;
    String state;
    String model;
    String dateReleased;
    int category;
    String battCapacity;
    String spaceCapacity;
    String maxPayload;

    // Default constructor is necessary for SugarRecord
    public Car() {
    }

    public Car(int seats, double price, String state, String model, String dateReleased, int category,
               String battCapacity, String spaceCapacity, String maxPayload) {
        this.seats = seats;
        this.price = price;
        this.state = state;
        this.model = model;
        this.dateReleased = dateReleased;
        this.category = category;
        this.battCapacity = battCapacity;
        this.spaceCapacity = spaceCapacity;
        this.maxPayload = maxPayload;
    }


    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDateReleased() {
        return dateReleased;
    }

    public void setDateReleased(String dateReleased) {
        this.dateReleased = dateReleased;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getBattCapacity() {
        return battCapacity;
    }

    public void setBattCapacity(String battCapacity) {
        this.battCapacity = battCapacity;
    }

    public String getSpaceCapacity() {
        return spaceCapacity;
    }

    public void setSpaceCapacity(String spaceCapacity) {
        this.spaceCapacity = spaceCapacity;
    }

    public String getMaxPayload() {
        return maxPayload;
    }

    public void setMaxPayload(String maxPayload) {
        this.maxPayload = maxPayload;
    }
}
