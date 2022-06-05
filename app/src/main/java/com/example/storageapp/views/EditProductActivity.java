package com.example.storageapp.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.example.storageapp.controller.UriResources;

public class EditProductActivity extends AppCompatActivity {

    EditText edtNombreProducto, edtCodigoProducto, edtPrecioProducto, edtDescripcionProducto, edtCantidadProducto;
    ImageView imagen;
    Button btnEditImagenProduct, btnEditarProducto, btnEliminarProducto;
    String nombre, codigo, precio, descripcion, cantidad, nombreProductoEdt, codigoProductoEdt, precioProductoEdt, descripcionProductoEdt, imagenProd, valor;
    int idProd, cantidadProductoEdt, codeImageProducto;
    EditText edtNombre, edtCodigo, edtPrecio, edtCantidad, edtDescripcion;
    Boolean isDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        idProd = getIntent().getIntExtra("idProducto", 0);
        nombre = getIntent().getStringExtra("name");
        codigo = getIntent().getStringExtra("codigo");
        precio = getIntent().getStringExtra("precio");
        imagenProd = getIntent().getStringExtra("imagenActual");
        cantidad = String.valueOf(getIntent().getIntExtra("cantidad", 0));
        descripcion = getIntent().getStringExtra("descripcion");

        setContentView(R.layout.activity_edit_product);
        imagen = findViewById(R.id.imgProductEdit);

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
        //imagen.setImageURI(Uri.parse(imagenProd));

        btnEditImagenProduct = (Button) findViewById(R.id.btnEditarImagenProducto);
        btnEditImagenProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarImagen();
            }

            private void cargarImagen() {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/");
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
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
                        .setMessage("¿Está seguro que desea Eliminar el Producto?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(EditProductActivity.this, InventarioActivity.class);
                                intent.putExtra("productoIdDelete", idProd);
                                isDelete = true;
                                intent.putExtra("isDelete", isDelete);
                                startActivity(intent);
                                finish();
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

                Intent intent = new Intent(EditProductActivity.this, InventarioActivity.class);
                intent.putExtra("nombreProducto", nombreProductoEdt);
                intent.putExtra("codigoProducto", codigoProductoEdt);
                intent.putExtra("precioProducto", precioProductoEdt);
                intent.putExtra("cantidadProducto", cantidadProductoEdt);
                intent.putExtra("descripcionProducto", descripcionProductoEdt);
                intent.putExtra("idProducto", idProd);
                intent.putExtra("uriImage", valor);
                intent.putExtra("editValidate", true);
                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri path = data.getData();
            valor = UriResources.ObtenerUri(path);
            imagen.setImageURI(path);
        }
    }
}