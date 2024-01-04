package com.example.easyhealthy.model;

import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;

public class User implements Serializable {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // email, pass, ten, chieu cao, can nang, bmi, gioi tinh, tuoi, ngay sinh
    private String email;
    private String password;
    private String name;
    private int height;
    private int weight;
    private double bmi;
    private String gender;
    private int age;
    private String dateOfBirth;

    // constructor
    public User(DocumentSnapshot documentSnapshot) {
        this.email = documentSnapshot.getId();
        this.password = documentSnapshot.getString("password");
        this.name = documentSnapshot.getString("name");
        this.height = documentSnapshot.getLong("height").intValue();
        this.weight = documentSnapshot.getLong("weight").intValue();
        this.bmi = (double) weight / (height * height);
        this.gender = documentSnapshot.getString("gender");
        this.age = documentSnapshot.getLong("age").intValue();
        this.dateOfBirth = documentSnapshot.getString("dateOfBirth");
    }

    public User() {
    }
}
