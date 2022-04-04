package com.example.storageapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storageapp.R;

public class EditProductActivity extends AppCompatActivity {

    ImageView imagen;
    Button btnEditImagenProduct;
    String nombre, codigo, precio, descripcion, cantidad;
    int imagenProd, idProd;
    EditText edtNombre, edtCodigo, edtPrecio, edtCantidad, edtDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        idProd = getIntent().getIntExtra("idProducto", 0);
        nombre = getIntent().getStringExtra("name");
        codigo = getIntent().getStringExtra("codigo");
        precio = getIntent().getStringExtra("precio");
        imagenProd = getIntent().getIntExtra("imagen", 0);
        cantidad = String.valueOf(getIntent().getIntExtra("cantidad", 0));
        descripcion = getIntent().getStringExtra("descripcion");

        setContentView(R.layout.activity_edit_product);
        imagen = findViewById(R.id.imageProduct);

        edtNombre = (EditText) findViewById(R.id.edtNombreProducto);
        edtCodigo = (EditText) findViewById(R.id.edtCodigoProducto);
        edtPrecio = (EditText) findViewById(R.id.edtPrecioProducto);
        edtCantidad = (EditText) findViewById(R.id.edtCantidadProducto);
        edtDescripcion = (EditText) findViewById(R.id.edtDescripcionProducto);

        edtNombre.setText(nombre, TextView.BufferType.EDITABLE);
        edtCodigo.setText(codigo, TextView.BufferType.EDITABLE);
        edtPrecio.setText(precio, TextView.BufferType.EDITABLE);
        edtCantidad.setText(cantidad, TextView.BufferType.EDITABLE);
        edtDescripcion.setText(descripcion, TextView.BufferType.EDITABLE);
        imagen.setImageResource(imagenProd);

        btnEditImagenProduct = (Button) findViewById(R.id.btnEditImagenProduct);
        btnEditImagenProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarImagen();
            }

            private void cargarImagen() {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/");
                startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicación"), 10);
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