package com.example.storageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class EditProductActivity extends AppCompatActivity {

    ImageView imagen;
    Button btnEditImagenProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        imagen = findViewById(R.id.imagenProduct);
        btnEditImagenProduct = (Button) findViewById(R.id.btnEditImagenProduct);
        btnEditImagenProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarImagen();
            }

            private void cargarImagen() {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/");
                startActivity(intent.createChooser(intent, "Seleccione la Aplicación"));

            }


        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri path = data.getData();
            imagen.setImageURI(path);
        }


    }
}