package com.example.storageapp.controller;

import android.net.Uri;

public class UriResources {
    static String dato;

    public static String ObtenerUri(Uri uri){
        if (uri != null){
            dato = uri.toString();
        }
        return dato;
    }
}
