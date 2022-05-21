package com.example.storageapp.controller;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.storageapp.views.InventarioActivity;
import com.example.storageapp.views.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Authenticate extends AsyncTask<Void, Void, String> {

    private static FirebaseAuth mAuth;
    private static boolean isLogin;
    private static String userEmail, userPass;
    private static final String TAG = "login.firebase";

    public static boolean userLogin(EditText email, EditText password, ProgressBar progressBar){
        try{
            mAuth = FirebaseAuth.getInstance();
            userEmail = email.getText().toString().trim();
            userPass = password.getText().toString().trim();
            if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                email.setError("El correo es requerido");
                email.requestFocus();
                return false;
            }

            if (password.getText().toString().isEmpty()){
                password.setError("La contraseña es requerida");
                password.requestFocus();
                return false;
            }
            mAuth.signInWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        isLogin = true;
                    } else {
                        isLogin = false;
                    }
                }
            });
            return isLogin;
        }catch (Exception ex){
            Log.w(TAG, "Google sign in failed", ex);
            return false;
        }
    }

    @Override
    protected void onPreExecute(){

    }

    @Override
    protected String doInBackground(Void... voids) {
        return null;
    }
}
