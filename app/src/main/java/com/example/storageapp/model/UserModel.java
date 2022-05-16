package com.example.storageapp.model;

public class UserModel {
    public String fullName, email, password, company;
    int companyId;
    public UserModel(){

    }

    public UserModel(String fullName, String email, String password, String company, int companyId) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.company = company;
        this.companyId = companyId;
    }
}
