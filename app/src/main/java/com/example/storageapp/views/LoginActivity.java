package com.example.storageapp.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.storageapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin, btnRegister;
    private EditText edtCorreo, edtPass;
    private TextView txtResetPass;
    private ProgressBar progressBarLogin;
    private RadioButton rbtnSesion;
    private FirebaseAuth mAuth;
    private boolean isActive;
    private static final String SHARE_PREFERENCES = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private static final String PREFERENCE_ESTADO_SESION = "estado.sesion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        edtCorreo = (EditText) findViewById(R.id.edtCorreo);
        edtPass = (EditText) findViewById(R.id.edtContrasena);
        progressBarLogin = (ProgressBar) findViewById(R.id.progressBarLogin);
        mAuth = FirebaseAuth.getInstance();
        rbtnSesion = (RadioButton) findViewById(R.id.rbtnSesion);
        txtResetPass = (TextView) findViewById(R.id.txtResetPass);

        isActive = rbtnSesion.isChecked();

        rbtnSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isActive){
                    rbtnSesion.setChecked(false);
                }
                isActive = rbtnSesion.isChecked();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerEstado();
                userLogin();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarEstado();
                startActivity(new Intent(getBaseContext(), RegisterActivity.class));
            }
        });

        txtResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), ResetPassActivity.class));
            }
        });
    }

    private void userLogin(){
        String email = edtCorreo.getText().toString().trim();
        String password = edtPass.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtCorreo.setError("El correo es requerido");
            edtCorreo.requestFocus();
            return;
        }

        if (password.isEmpty()){
            edtPass.setError("La contraseña es requerida");
            edtPass.requestFocus();
            return;
        }
        guardarEstado();
        progressBarLogin.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressBarLogin.setVisibility(View.GONE);
                    startActivity(new Intent(getBaseContext(), InventarioActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Error al iniciar sesion. Por favor verifica tus credenciales.", Toast.LENGTH_LONG).show();
                    progressBarLogin.setVisibility(View.GONE);
                }
            }
        });

    }

    private void guardarEstado(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARE_PREFERENCES, MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(PREFERENCE_ESTADO_SESION, rbtnSesion.isChecked()).apply();
    }

    private boolean obtenerEstado(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARE_PREFERENCES, MODE_PRIVATE);
        return sharedPreferences.getBoolean(PREFERENCE_ESTADO_SESION, false);
    }
}