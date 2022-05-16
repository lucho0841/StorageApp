package com.example.storageapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.storageapp.R;
import com.example.storageapp.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    String[] companies = {"HomeCenter", "Exito", "Jumbo", "Totto"};
    String item;
    AutoCompleteTextView acSelectCompany;

    ArrayAdapter<String> arrayAdapter;
    private ProgressBar progressBar;
    private EditText edtNombre, edtCorreo, edtPassword, edtConfirmPass;
    private Button btnRegisterUser;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        edtNombre = (EditText) findViewById(R.id.edtUserName);
        edtCorreo = (EditText) findViewById(R.id.edtCorreoRegister);
        edtPassword = (EditText) findViewById(R.id.edtContrasenaRegister);
        edtConfirmPass = (EditText) findViewById(R.id.edtConfirmPass);
        acSelectCompany = findViewById(R.id.selectCompany);
        progressBar = (ProgressBar) findViewById(R.id.progressBarRegister);
        btnRegisterUser = (Button) findViewById(R.id.btnNewRegister);

        btnRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, companies);
        acSelectCompany.setAdapter(arrayAdapter);
        acSelectCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void registerUser(){
        String email = edtCorreo.getText().toString().trim();
        String name = edtNombre.getText().toString();
        String password = edtPassword.getText().toString();
        String confirmPass = edtConfirmPass.getText().toString();
        String company = acSelectCompany.getText().toString();

        

        if (name.isEmpty()){
            edtNombre.setError("El nombre es requerido");
            edtNombre.requestFocus();
            return;
        }
        if (password.isEmpty()){
            edtPassword.setError("La contraseña es requerida");
            edtPassword.requestFocus();
            return;
        }
        if (password.length() < 6){
            edtPassword.setError("El tamaño mínimo de la contraseña es de 6 carácteres");
            edtPassword.requestFocus();
            return;
        }
        if (confirmPass.isEmpty()){
            edtConfirmPass.setError("El nombre es requerido");
            edtConfirmPass.requestFocus();
            return;
        }
        if(!confirmPass.equals(password)){
            edtConfirmPass.setError("Asegúrese de que este campo coincida con la contraseña ingresada!");
            edtConfirmPass.requestFocus();
            return;
        }
        if (company.isEmpty()){
            acSelectCompany.setError("El nombre es requerido");
            acSelectCompany.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtCorreo.setError("Por favor ingrese un correo válido");
            edtCorreo.requestFocus();
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            UserModel user = new UserModel(name, email, password, company, 1);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "El usuario ha sido creado con Éxito!!!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        finish();
                                    }else {
                                        Toast.makeText(RegisterActivity.this, "Ha ocurrido un problema creando el usuario, inténtelo mas tarde!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        finish();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(RegisterActivity.this, "Error al registrar un usuario.", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }


}