package com.example.easyhealthy.model;

import com.google.firebase.Timestamp;

public class NutritionData {
    private String type;
    private Timestamp date;
    private String time; // Change the type to String

    private int quantity;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public NutritionData(String type, Timestamp date, String time, int quantity) {
        this.type = type;
        this.date = date;
        this.time = time;
        this.quantity = quantity;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getTime() { // Change the return type to String
        return time;
    }

    public void setTime(String time) { // Change the parameter type to String
        this.time = time;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
