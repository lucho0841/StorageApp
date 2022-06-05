package com.example.storageapp.controller;

import com.example.storageapp.model.CategoryModel;

import java.util.ArrayList;

public class Dataholder {

    public ArrayList<CategoryModel> categories = new ArrayList<>();
    private static Dataholder instance;

    private Dataholder() {}

    public static Dataholder getInstance() {
        if (instance == null) {
            instance = new Dataholder();
        }
        return instance;
    }
}
