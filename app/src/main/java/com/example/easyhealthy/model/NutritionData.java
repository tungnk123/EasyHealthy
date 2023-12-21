package com.example.easyhealthy.model;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.util.Date;

public class NutritionData {
    private String type;
    private Date date;
    private String time; // Change the type to String

    private int quantity;

    public NutritionData(NutritionData documentSnapshot) {
        this.type = documentSnapshot.type;
        this.date = documentSnapshot.date;
        this.time = documentSnapshot.time;
    }

    public NutritionData(DocumentSnapshot documentSnapshot) {
        this.date = documentSnapshot.getDate("date");
        this.type = documentSnapshot.getString("type");
        this.time = documentSnapshot.getString("time");
        this.quantity = documentSnapshot.getLong("quantity").intValue();
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public NutritionData(String type, Date date, String time, int quantity) {
        this.type = type;
        this.date = date;
        this.time = time;
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
