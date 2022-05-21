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
import com.example.storageapp.controller.AlertDialogs;
import com.example.storageapp.controller.UriResources;
import com.example.storageapp.model.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;

public class CreateProductActivity extends AppCompatActivity {

    private EditText nombreProd, codigoProd, precioProd, cantidadProd, descripcionProd;
    String nombreProducto, codigoProducto, precioProducto, descripcionProducto, valor;
    int cantidadProducto, id = 0;
    private Button btnCrear, btnSelectImage;
    private DatabaseReference mData;
    private ImageView imageProduct;
    private ProductModel product;
    private AlertDialog.Builder cancelProcess;
    private FirebaseAuth mAuth;

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

        mAuth = FirebaseAuth.getInstance();

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
                nombreProducto = nombreProd.getText().toString();
                codigoProducto = codigoProd.getText().toString();
                precioProducto = precioProd.getText().toString();
                descripcionProducto = descripcionProd.getText().toString();
                cantidadProducto = Integer.parseInt(cantidadProd.getText().toString());
                product = new ProductModel(id, valor, nombreProducto, codigoProducto, precioProducto, cantidadProducto, descripcionProducto, mAuth.getCurrentUser().getUid(), false);
                crearProductoBD(product);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                cancelProcess = AlertDialogs.showAlert(CreateProductActivity.this, "Aviso!", "¿Está seguro que desea cancelar el registro?", R.drawable.ic_baseline_warning_24);
                cancelProcess.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        cancelProcess = AlertDialogs.showAlert(CreateProductActivity.this, "Aviso!", "¿Está seguro que desea cancelar el registro?", R.drawable.ic_baseline_warning_24);
        cancelProcess.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("Cancelar", null)
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

    private void crearProductoBD(ProductModel product){
        mData = FirebaseDatabase.getInstance().getReference("Products");
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    long numHijos = snapshot.getChildrenCount();
                    id = Integer.parseInt(String.valueOf(numHijos)) + 1;
                    product.setProductoId(id);
                    FirebaseDatabase.getInstance().getReference("Products")
                            .child(String.valueOf(id))
                            .setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(CreateProductActivity.this, "El producto ha sido creado con Éxito!!!", Toast.LENGTH_LONG).show();
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