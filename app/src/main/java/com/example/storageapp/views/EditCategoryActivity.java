package com.example.storageapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.storageapp.R;

import org.w3c.dom.Text;

public class EditCategoryActivity extends AppCompatActivity {

    String nombre, descripcion;
    EditText nuevoNombreCat , nuevoDescCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nombre = getIntent().getStringExtra("nuevoNombreCategoria");
        descripcion = getIntent().getStringExtra("nuevoDescCategoria");
        setContentView(R.layout.activity_edit_category);

        nuevoNombreCat = (EditText) findViewById(R.id.nuevoNombreCategoria);
        nuevoDescCat = (EditText) findViewById(R.id.nuevoDescCategoria);

        nuevoNombreCat.setText(nombre, TextView.BufferType.EDITABLE);
        nuevoDescCat.setText(descripcion, TextView.BufferType.EDITABLE);
    }
}