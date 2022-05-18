package com.example.storageapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.storageapp.R;
import com.example.storageapp.controller.UriResources;

public class CreateProductActivity extends AppCompatActivity {

    private EditText nombreProd, codigoProd, precioProd, cantidadProd, descripcionProd;
    String nombreProducto, codigoProducto, precioProducto, descripcionProducto, valor;
    int cantidadProducto;
    private Button btnCrear, btnSelectImage;
    private ImageView imageProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        nombreProd = (EditText) findViewById(R.id.edtNombreProductoCrear);
        codigoProd = (EditText) findViewById(R.id.edtCodigoProductoCrear);
        precioProd = (EditText) findViewById(R.id.edtPrecioProductoCrear);
        cantidadProd = (EditText) findViewById(R.id.edtCantidadProductoCrear);
        descripcionProd = (EditText) findViewById(R.id.edtDescripcionProductoCrear);

        btnCrear = (Button) findViewById(R.id.btnCrearProducto);
        btnSelectImage = (Button) findViewById(R.id.btnCrearImagenProducto);
        imageProduct = (ImageView) findViewById(R.id.imgProductCreate);

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/");
                startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicación"), 10);
            }
        });

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateProductActivity.this, InventarioActivity.class);
                nombreProducto = nombreProd.getText().toString();
                codigoProducto = codigoProd.getText().toString();
                precioProducto = precioProd.getText().toString();
                descripcionProducto = descripcionProd.getText().toString();
                cantidadProducto = Integer.parseInt(cantidadProd.getText().toString());
                intent.putExtra("nombreProductoCrear", nombreProducto);
                intent.putExtra("codigoProductoCrear", codigoProducto);
                intent.putExtra("precioProductoCrear", precioProducto);
                intent.putExtra("descripcionProductoCrear", descripcionProducto);
                intent.putExtra("cantidadProductoCrear", cantidadProducto);
                intent.putExtra("imageUriCreate", valor);
                startActivity(intent);
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CreateProductActivity.this);
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
                                Toast.makeText(CreateProductActivity.this, "Has cancelado!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create()
                        .show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CreateProductActivity.this);
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
                        Toast.makeText(CreateProductActivity.this, "Has cancelado!", Toast.LENGTH_SHORT).show();
                    }
                })
                .create()
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri path = data.getData();
            valor = UriResources.ObtenerUri(path);
            imageProduct.setImageURI(path);
        }
    }
}