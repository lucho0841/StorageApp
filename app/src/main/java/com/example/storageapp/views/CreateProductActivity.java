package com.example.storageapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.storageapp.R;
import com.example.storageapp.controller.AlertDialogs;
import com.example.storageapp.controller.UriResources;
import com.example.storageapp.model.CategoryModel;
import com.example.storageapp.model.ProductModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateProductActivity extends AppCompatActivity {

    private EditText nombreProd, codigoProd, precioProd, cantidadProd, descripcionProd;
    String nombreProducto;
    String codigoProducto;
    String precioProducto;
    String descripcionProducto;
    String categoriaProducto;
    String valor;
    String item;
    int cantidadProducto, id = 0;
    private Button btnCrear, btnSelectImage;
    private DatabaseReference mData;
    private ImageView imageProduct;
    private ProductModel product;
    private Uri path;
    private StorageReference filepath;
    private AutoCompleteTextView edtSelectCategory;

    private AlertDialog.Builder cancelProcess;
    private FirebaseAuth mAuth;
    private StorageReference mStorage;
    private static final int GALLERY_INTENT = 10;
    boolean isNotUrl = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        mStorage = FirebaseStorage.getInstance().getReference();

        nombreProd = (EditText) findViewById(R.id.edtNombreProductoCrear);
        codigoProd = (EditText) findViewById(R.id.edtCodigoProductoCrear);
        precioProd = (EditText) findViewById(R.id.edtPrecioProductoCrear);
        cantidadProd = (EditText) findViewById(R.id.edtCantidadProductoCrear);
        descripcionProd = (EditText) findViewById(R.id.edtDescripcionProductoCrear);
        edtSelectCategory = (AutoCompleteTextView) findViewById(R.id.edtSelectCategory);

        btnCrear = (Button) findViewById(R.id.btnCrearProducto);
        btnSelectImage = (Button) findViewById(R.id.btnCrearImagenProducto);
        imageProduct = (ImageView) findViewById(R.id.imgProductCreate);

        mAuth = FirebaseAuth.getInstance();
        obtenerCategorias();
        edtSelectCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                item = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicaci??n"), GALLERY_INTENT);
            }
        });

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombreProducto = nombreProd.getText().toString();
                codigoProducto = codigoProd.getText().toString();
                precioProducto = precioProd.getText().toString();
                descripcionProducto = descripcionProd.getText().toString();
                cantidadProducto = Integer.parseInt(cantidadProd.getText().toString().equals("") ? "0" : cantidadProd.getText().toString());
                categoriaProducto = edtSelectCategory.getText().toString();
                product = new ProductModel(id, valor, nombreProducto, codigoProducto, precioProducto, cantidadProducto, descripcionProducto, categoriaProducto.isEmpty() ? "Sin categor??a" : categoriaProducto, mAuth.getCurrentUser().getUid() == null ? "" : mAuth.getCurrentUser().getUid(), false);
                crearProductoBD(product);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                cancelProcess = AlertDialogs.showAlert(CreateProductActivity.this, "Aviso!", "??Est?? seguro que desea cancelar el registro?", R.drawable.ic_baseline_warning_24);
                cancelProcess.setPositiveButton("S??", new DialogInterface.OnClickListener() {
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

        cancelProcess = AlertDialogs.showAlert(CreateProductActivity.this, "Aviso!", "??Est?? seguro que desea cancelar el registro?", R.drawable.ic_baseline_warning_24);
        cancelProcess.setPositiveButton("S??", new DialogInterface.OnClickListener() {
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
            path = data.getData();
            //valor = UriResources.ObtenerUri(path);
            imageProduct.setImageURI(path);
        }
    }

    private void crearProductoBD(ProductModel product){
        if (nombreProducto.isEmpty()){
            nombreProd.setError("El nombre es requerido");
            nombreProd.requestFocus();
            return;
        }
        if (codigoProducto.isEmpty()){
            codigoProd.setError("El codigo es requerido");
            codigoProd.requestFocus();
            return;
        }
        if (cantidadProducto == 0){
            cantidadProd.setError("La cantidad debe ser superior a 0");
            cantidadProd.requestFocus();
            return;
        }
        if (precioProducto.isEmpty()){
            precioProd.setError("El precio es requerido");
            precioProd.requestFocus();
            return;
        }
        if (path == null){
            Toast.makeText(getBaseContext(), "Debe cargar una imagen para el producto", Toast.LENGTH_SHORT).show();
            return;
        }
        mData = FirebaseDatabase.getInstance().getReference("Products");
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    long numHijos = snapshot.getChildrenCount();
                    id = Integer.parseInt(String.valueOf(numHijos)) + 1;
                    filepath = mStorage.child("productImages").child(String.valueOf(id)).child("File"+path.getLastPathSegment());
                    filepath.putFile(path);
                    filepath.getDownloadUrl().addOnSuccessListener(uri -> {
                        valor = String.valueOf(uri);
                        product.setImage(valor);
                        product.setProductoId(id);
                        FirebaseDatabase.getInstance().getReference("Products")
                                .child(String.valueOf(id))
                                .setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(CreateProductActivity.this, "El producto ha sido creado con ??xito!!!", Toast.LENGTH_LONG).show();
                                            finish();
                                        }
                                    }
                                });
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void obtenerCategorias(){
        mData = FirebaseDatabase.getInstance().getReference("Category");
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    ArrayList<String> categoryList = new ArrayList<>();
                    long numHijos = snapshot.getChildrenCount();
                    for (int i = 1; i <= numHijos; i++){
                        mData = FirebaseDatabase.getInstance().getReference("Category").child(String.valueOf(i));
                        mData.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    String nombre = snapshot.child("nombre").getValue().toString();
                                    categoryList.add(nombre);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    ArrayAdapter adapter = new ArrayAdapter(getBaseContext(), R.layout.list_item, categoryList);
                    edtSelectCategory.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}