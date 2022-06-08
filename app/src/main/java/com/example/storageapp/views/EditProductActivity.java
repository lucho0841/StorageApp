package com.example.storageapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storageapp.R;
import com.example.storageapp.controller.UriResources;
import com.example.storageapp.model.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EditProductActivity extends AppCompatActivity {

    EditText edtNombreProducto, edtCodigoProducto, edtPrecioProducto, edtDescripcionProducto, edtCantidadProducto;
    ImageView imagen;
    Button btnEditImagenProduct, btnEditarProducto, btnEliminarProducto;
    String nombre, codigo, precio, descripcion, cantidad, nombreProductoEdt, codigoProductoEdt, precioProductoEdt, descripcionProductoEdt, imagenProd, valor, item;
    int idProd, cantidadProductoEdt, id;
    EditText edtNombre, edtCodigo, edtPrecio, edtCantidad, edtDescripcion;
    AutoCompleteTextView edtSelectCategory;
    DatabaseReference mDatabase;
    Uri path;
    private StorageReference filepath;
    private StorageReference mStorage;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStorage = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        idProd = getIntent().getIntExtra("id", 0);
        nombre = getIntent().getStringExtra("name");
        codigo = getIntent().getStringExtra("codigo");
        precio = getIntent().getStringExtra("precio");
        imagenProd = getIntent().getStringExtra("imagenActual");
        cantidad = String.valueOf(getIntent().getIntExtra("cantidad", 0));
        descripcion = getIntent().getStringExtra("descripcion");

        setContentView(R.layout.activity_edit_product);
        imagen = findViewById(R.id.imgProductEdit);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        edtNombre = (EditText) findViewById(R.id.edtNombreProducto);
        edtCodigo = (EditText) findViewById(R.id.edtCodigoProducto);
        edtPrecio = (EditText) findViewById(R.id.edtPrecioProducto);
        edtCantidad = (EditText) findViewById(R.id.edtCantidadProducto);
        edtDescripcion = (EditText) findViewById(R.id.edtDescripcionProducto);
        edtSelectCategory = (AutoCompleteTextView) findViewById(R.id.edtEditSelectCategory);
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

        edtNombre.setText(nombre, TextView.BufferType.EDITABLE);
        edtCodigo.setText(codigo, TextView.BufferType.EDITABLE);
        edtPrecio.setText(precio, TextView.BufferType.EDITABLE);
        edtCantidad.setText(cantidad, TextView.BufferType.EDITABLE);
        edtDescripcion.setText(descripcion, TextView.BufferType.EDITABLE);
        Picasso.get().load(imagenProd).into(imagen);

        btnEditImagenProduct = (Button) findViewById(R.id.btnEditarImagenProducto);
        btnEditImagenProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarImagen();
            }

            private void cargarImagen() {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/");
                startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicación"), 10);
            }
        });

        btnEliminarProducto = (Button) findViewById(R.id.btnEliminarProducto);
        btnEliminarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditProductActivity.this);
                alertDialog.setTitle("Aviso!")
                        .setIcon(R.drawable.ic_baseline_info_24)
                        .setMessage("¿Está seguro que desea Eliminar el Producto? esta acción no se puede deshacer.")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mDatabase.child("Products").child(String.valueOf(idProd)).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getBaseContext(), "Se ha eliminado el producto correctamente.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getBaseContext(), InventarioActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getBaseContext(), "El producto no se pudo eliminar", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(EditProductActivity.this, "Continua!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create()
                        .show();
            }
        });

        btnEditarProducto = (Button) findViewById(R.id.btnEditarProducto);
        btnEditarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtNombreProducto = (EditText) findViewById(R.id.edtNombreProducto);
                nombreProductoEdt = edtNombreProducto.getText().toString();

                edtCodigoProducto = (EditText) findViewById(R.id.edtCodigoProducto);
                codigoProductoEdt = edtCodigoProducto.getText().toString();

                edtPrecioProducto = (EditText) findViewById(R.id.edtPrecioProducto);
                precioProductoEdt = edtPrecioProducto.getText().toString();

                edtCantidadProducto = (EditText) findViewById(R.id.edtCantidadProducto);
                cantidadProductoEdt = Integer.parseInt(edtCantidadProducto.getText().toString());

                edtDescripcionProducto = (EditText) findViewById(R.id.edtDescripcionProducto);
                descripcionProductoEdt = edtDescripcionProducto.getText().toString();

                ProductModel product = new ProductModel(idProd, imagenProd, nombreProductoEdt, codigoProductoEdt, precioProductoEdt, cantidadProductoEdt, descripcionProductoEdt, "Sin categoría", mAuth.getCurrentUser().getUid() == null ? "" : mAuth.getCurrentUser().getUid(), false);
                editarProductoBD(product);

                Intent intent = new Intent(EditProductActivity.this, InventarioActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void editarProductoBD(ProductModel product){
        if (nombreProductoEdt.isEmpty()){
            edtNombreProducto.setError("El nombre es requerido");
            edtNombreProducto.requestFocus();
            return;
        }
        if (codigoProductoEdt.isEmpty()){
            edtCodigoProducto.setError("El codigo es requerido");
            edtCodigoProducto.requestFocus();
            return;
        }
        if (cantidadProductoEdt == 0){
            edtCantidadProducto.setError("La cantidad debe ser superior a 0");
            edtCantidadProducto.requestFocus();
            return;
        }
        if (precioProductoEdt.isEmpty()){
            edtPrecioProducto.setError("El precio es requerido");
            edtPrecioProducto.requestFocus();
            return;
        }
        mDatabase = FirebaseDatabase.getInstance().getReference("Products");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    filepath = mStorage.child("productImages").child(String.valueOf(idProd)).child("File"+path.getLastPathSegment());
                    if (filepath == null){
                        FirebaseDatabase.getInstance().getReference("Products")
                                .child(String.valueOf(idProd))
                                .setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(EditProductActivity.this, "El producto ha sido modificado con Éxito!!!", Toast.LENGTH_LONG).show();
                                            finish();
                                        }
                                    }
                                });
                    }else {
                        filepath.putFile(path);
                        filepath.getDownloadUrl().addOnSuccessListener(uri -> {
                            valor = String.valueOf(uri);
                            product.setImage(valor);
                            FirebaseDatabase.getInstance().getReference("Products")
                                    .child(String.valueOf(idProd))
                                    .setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(EditProductActivity.this, "El producto ha sido modificado con Éxito!!!", Toast.LENGTH_LONG).show();
                                                finish();
                                            }
                                        }
                                    });
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void obtenerCategorias(){
        mDatabase = FirebaseDatabase.getInstance().getReference("Category");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    ArrayList<String> categoryList = new ArrayList<>();
                    long numHijos = snapshot.getChildrenCount();
                    for (int i = 1; i <= numHijos; i++){
                        mDatabase = FirebaseDatabase.getInstance().getReference("Category").child(String.valueOf(i));
                        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            path = data.getData();
            valor = UriResources.ObtenerUri(path);
            imagen.setImageURI(path);
        }
    }
}