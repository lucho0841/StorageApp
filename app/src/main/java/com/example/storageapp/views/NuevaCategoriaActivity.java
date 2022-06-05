package com.example.storageapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.storageapp.R;
import com.example.storageapp.model.CategoryModel;
import com.example.storageapp.model.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.EmptyStackException;

public class NuevaCategoriaActivity extends AppCompatActivity {



    private DatabaseReference mData;
    EditText edtNombreNuevaCat;
    EditText edtDescNuevaCat;
    EditText edtCodNuevaCat;
    Button btnCrearCategoria;
    int id = 0;
    private FirebaseAuth mAuth;
    private CategoryModel category;
    String nombreCategoria, codigoCategoria, descripcionCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_categoria);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();

        edtNombreNuevaCat = (EditText) findViewById(R.id.edtNombreCategoria);
        edtDescNuevaCat = (EditText) findViewById(R.id.edtDescCategoria);
        edtCodNuevaCat = (EditText) findViewById(R.id.edtCodNuevaCat);

        btnCrearCategoria = (Button) findViewById(R.id.btnCrearCategoria);
        btnCrearCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombreCategoria = edtNombreNuevaCat.getText().toString();
                descripcionCategoria = edtDescNuevaCat.getText().toString();
                codigoCategoria = edtCodNuevaCat.getText().toString();

                category = new CategoryModel(mAuth.getCurrentUser().getUid(),codigoCategoria,nombreCategoria,descripcionCategoria, id);
                crearCategoriaBD(category);

                if (nombreCategoria.isEmpty()) {
                    edtNombreNuevaCat.setError("El nombre es requerido");
                    edtNombreNuevaCat.requestFocus();
                    return;
                }

                Intent intent = new Intent(NuevaCategoriaActivity.this, InventarioActivity.class);
                intent.putExtra("nombreCategoria", nombreCategoria);
                intent.putExtra("descripcionCategoria", descripcionCategoria);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void crearCategoriaBD(CategoryModel category){
        if (nombreCategoria.isEmpty() && codigoCategoria.isEmpty() ){
            edtNombreNuevaCat.setError("El nombre es requerido");
            edtNombreNuevaCat.requestFocus();
            return;
        }
        if (codigoCategoria.isEmpty()){
            edtCodNuevaCat.setError("El codigo es requerido");
            edtCodNuevaCat.requestFocus();
            return;
        }

        mData = FirebaseDatabase.getInstance().getReference("Category");
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    long numHijos = snapshot.getChildrenCount();
                    id = Integer.parseInt(String.valueOf(numHijos)) + 1;

                    category.setProductoId(id);
                    FirebaseDatabase.getInstance().getReference("Category")
                            .child(String.valueOf(id))
                            .setValue(category).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(NuevaCategoriaActivity.this, "La categoría ha sido creado con Éxito!!!", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}