package com.example.storageapp.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.storageapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin, btnRegister, btnFacebook;
    private EditText edtCorreo, edtPass;
    private ImageView btnGoogle;
    private TextView txtResetPass;
    private ProgressBar progressBarLogin;
    private RadioButton rbtnSesion;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogle;
    private boolean isActive;
    private static final int RC_SIGN_IN = 100;
    private static final String TAG = "GOOGLE_SIGN_IN_TAG";
    private static final String SHARE_PREFERENCES = "share.preference.user";
    private static final String PREFERENCE_ESTADO_SESION = "estado.sesion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(obtenerEstado()){
            startActivity(new Intent(getBaseContext(), InventarioActivity.class));
            finish();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogle = GoogleSignIn.getClient(this, gso);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        edtCorreo = (EditText) findViewById(R.id.edtCorreo);
        edtPass = (EditText) findViewById(R.id.edtContrasena);
        progressBarLogin = (ProgressBar) findViewById(R.id.progressBarLogin);
        mAuth = FirebaseAuth.getInstance();
        rbtnSesion = (RadioButton) findViewById(R.id.rbtnSesion);
        txtResetPass = (TextView) findViewById(R.id.txtResetPass);
        btnGoogle = (ImageView) findViewById(R.id.btnGoogle);

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

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = mGoogle.getSignInIntent();
                startActivityForResult(intent, RC_SIGN_IN);
                Toast.makeText(getBaseContext(), "Botón google", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (task.isSuccessful()){
               try {
                    GoogleSignInAccount signInAccount = task.getResult(ApiException.class);
                    Log.d(TAG, "firebaseAuthWithGoogle: " + signInAccount.getId());
                    firebaseAuthWithGoogle(signInAccount.getId());
               }catch (Exception ex){
                    Log.w(TAG, "Google sign in failed", ex);
               }
            } else {

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(LoginActivity.this, InventarioActivity.class));
                            finish();
                        } else {
                            Toast.makeText(getBaseContext(), "Error al iniciar sesión con Google!", Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                        }
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