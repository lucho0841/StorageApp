package com.example.storageapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.storageapp.R;

public class NuevaCategoriaActivity extends AppCompatActivity {

    // Hacer validaciones en este archivo
    EditText edtNombreNuevaCat;
    EditText edtDescNuevaCat;
    Button btnCrearCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_categoria);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtNombreNuevaCat = (EditText) findViewById(R.id.edtNombreCategoria);
        edtDescNuevaCat = (EditText) findViewById(R.id.edtDescCategoria);
        btnCrearCategoria = (Button) findViewById(R.id.btnCrearCategoria);

        btnCrearCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = edtNombreNuevaCat.getText().toString();
                String descripcion = edtDescNuevaCat.getText().toString();
                Intent intent = new Intent(NuevaCategoriaActivity.this, InventarioActivity.class);
                intent.putExtra("nombreCategoria", nombre);
                intent.putExtra("descripcionCategoria", descripcion);
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
}