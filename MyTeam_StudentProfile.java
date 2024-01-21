package com.example.androidlearningapp;

public class StudentProfileModel {
    String email, password, name, number;

    public StudentProfileModel(){}
    public StudentProfileModel(String email, String password, String name, String number) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

}
