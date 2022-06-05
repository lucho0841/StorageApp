package com.example.storageapp.controller;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.example.storageapp.R;

public class AlertDialogs {
    public static AlertDialog.Builder showAlert(Context context, String title, String message, int icon){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(title)
                .setIcon(icon)
                .setMessage(message);
                return alertDialog;
    }
}
