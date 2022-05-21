package com.example.storageapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.storageapp.controller.AlertDialogs;
import com.example.storageapp.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterGoogleAccountActivity extends AppCompatActivity {

    String[] companies = {"HomeCenter", "Exito", "Jumbo", "Totto"};
    String item;
    AutoCompleteTextView acSelectCompany;

    ArrayAdapter<String> arrayAdapter;
    private ProgressBar progressBar;
    private EditText edtNombre, edtCorreo, edtPassword, edtConfirmPass;
    private Button btnRegisterUser;
    private AlertDialog.Builder backAlert;

    private static final String TAG = "GoogleSignIn";
    private static final String SHARE_PREFERENCES = "share.preference.user";
    private static final String PREFERENCE_ESTADO_SESION = "estado.sesion";

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_google_account);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        edtNombre = (EditText) findViewById(R.id.edtUserNameGoogle);
        edtCorreo = (EditText) findViewById(R.id.edtCorreoRegisterGoogle);
        edtPassword = (EditText) findViewById(R.id.edtContrasenaRegisterGoogle);
        edtConfirmPass = (EditText) findViewById(R.id.edtConfirmPassGoogle);
        acSelectCompany = findViewById(R.id.selectCompanyGoogle);
        progressBar = (ProgressBar) findViewById(R.id.progressBarRegisterGoogle);
        btnRegisterUser = (Button) findViewById(R.id.btnNewRegisterGoogle);

        edtNombre.setText(currentUser.getDisplayName());
        edtCorreo.setText(currentUser.getEmail());

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
        UserModel user = new UserModel(name, email, password, company, 1);
        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterGoogleAccountActivity.this, "El usuario ha sido creado con Éxito!!!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(getBaseContext(), InventarioActivity.class));
                            finish();
                        }else {
                            Toast.makeText(RegisterGoogleAccountActivity.this, "Ha ocurrido un problema creando el usuario, inténtelo mas tarde!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            finish();
                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                backAlert = AlertDialogs.showAlert(RegisterGoogleAccountActivity.this, "Aviso!", "¿Está seguro que desea cancelar el registro?", R.drawable.ic_baseline_info_24);
                backAlert.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show();
        }
        return super.onOptionsItemSelected(item);
    }
}