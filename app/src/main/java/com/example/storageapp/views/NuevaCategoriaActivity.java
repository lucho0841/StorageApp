package com.example.storageapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.storageapp.R;

public class NuevaCategoriaActivity extends AppCompatActivity {

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

                if (nombre.isEmpty()) {
                    edtNombreNuevaCat.setError("El nombre es requerido");
                    edtNombreNuevaCat.requestFocus();
                    return;
                }

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
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(NuevaCategoriaActivity.this);
                alertDialog.setTitle("Aviso!")
                        .setIcon(R.drawable.ic_baseline_warning_24)
                        .setMessage("¿Está seguro que desea cancelar el registro?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(NuevaCategoriaActivity.this, "Has cancelado!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create().show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(NuevaCategoriaActivity.this);
        alertDialog.setTitle("Aviso!")
                .setIcon(R.drawable.ic_baseline_warning_24)
                .setMessage("¿Está seguro que desea cancelar el registro?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(NuevaCategoriaActivity.this, "Has cancelado!", Toast.LENGTH_SHORT).show();
                    }
                })
                .create().show();
    }
}