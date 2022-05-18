package com.example.storageapp.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.storageapp.Dataholder;
import com.example.storageapp.R;
import com.example.storageapp.model.CategoryModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EditCategoryActivity extends AppCompatActivity {

    String nombre, descripcion;
    int pos;
    EditText nuevoNombreCat , nuevoDescCat;
    Button btnEditarCategoria;
    private ArrayList<CategoryModel> categories = Dataholder.getInstance().categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nombre = getIntent().getStringExtra("nuevoNombreCategoria");
        descripcion = getIntent().getStringExtra("nuevoDescCategoria");
        pos = getIntent().getIntExtra("posicion",0);
        setContentView(R.layout.activity_edit_category);

        nuevoNombreCat = (EditText) findViewById(R.id.nuevoNombreCategoria);
        nuevoDescCat = (EditText) findViewById(R.id.nuevoDescCategoria);

        nuevoNombreCat.setText(nombre, TextView.BufferType.EDITABLE);
        nuevoDescCat.setText(descripcion, TextView.BufferType.EDITABLE);

        btnEditarCategoria = (Button) findViewById(R.id.btnEditarCategoria);
        btnEditarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nuevoNombre = nuevoNombreCat.getText().toString();
                String nuevoDescripcion = nuevoDescCat.getText().toString();

                if (nombre.isEmpty()) {
                    nuevoNombreCat.setError("El nombre es requerido");
                    nuevoNombreCat.requestFocus();
                    return;
                }

                categories.get(pos).setNombre(nuevoNombre);
                categories.get(pos).setDescripcion(nuevoDescripcion);
                finish();
            }
        });

    }

}