package com.example.storageapp.views;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.storageapp.R;
import com.example.storageapp.controller.DataShare;
import com.example.storageapp.databinding.ActivityInventarioBinding;
import com.example.storageapp.fragments.CategoriesFragment;
import com.example.storageapp.fragments.ReportsFragment;
import com.example.storageapp.fragments.StorageFragment;
import com.example.storageapp.model.ProductModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.ArrayList;


public class InventarioActivity extends AppCompatActivity implements DataShare {

    private ActivityInventarioBinding binding;
    FloatingActionButton fabOpenProduct;

    String nombreProducto, codigoProducto, descripcionProducto, precioProducto, imagen;
    int productoId, cantidadProducto;
    boolean isEdit, isDelete;
    StorageFragment storageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String nombreCategoria = getIntent().getStringExtra("nombreCategoria");
        String descripcionCategoria = getIntent().getStringExtra("descripcionCategoria");
        Bundle datos = new Bundle();
        Bundle editarProducto = new Bundle();
        Bundle crearProducto = new Bundle();

        datos.putString("nombreCategoria", nombreCategoria);
        datos.putString("descripcionCategoria", descripcionCategoria);

        isEdit = getIntent().getBooleanExtra("editValidate", false);
        isDelete = getIntent().getBooleanExtra("isDelete", false);
        if (isEdit){
            // Obtención de datos EditProductActivity //
            productoId = getIntent().getIntExtra("idProducto", 0);
            descripcionProducto = getIntent().getStringExtra("descripcionProducto");
            nombreProducto = getIntent().getStringExtra("nombreProducto");
            cantidadProducto = getIntent().getIntExtra("cantidadProducto", 0);
            codigoProducto = getIntent().getStringExtra("codigoProducto");
            precioProducto = getIntent().getStringExtra("precioProducto");
            imagen = getIntent().getStringExtra("uriImage");
            editarProducto.putInt("productoId", productoId);
            editarProducto.putString("descripcionProducto", descripcionProducto);
            editarProducto.putString("nombreProducto", nombreProducto);
            editarProducto.putInt("cantidadProducto", cantidadProducto);
            editarProducto.putString("precioProducto", precioProducto);
            editarProducto.putString("codigoProducto", codigoProducto);
            editarProducto.putString("imagen", imagen);
            editarProducto.putInt("editValidate", 1);
            storageFragment = new StorageFragment();
            if ((isEdit && nombreProducto != null)) {
                storageFragment.setArguments(editarProducto);
            }
            ReplaceFragment(storageFragment);
            ////
        } else if (isDelete) {
            // Obtencion de datos CreateProductActivity //
            productoId = getIntent().getIntExtra("productoIdDelete",0);

            crearProducto.putInt("productoIdDelete", productoId);
            crearProducto.putInt("deleteValidate", 1);

            storageFragment = new StorageFragment();
            if ((!isEdit && nombreProducto != null) || (!isEdit && productoId != 0)) {
                storageFragment.setArguments(crearProducto);
            }
            ReplaceFragment(storageFragment);

        } else {
            nombreProducto = getIntent().getStringExtra("nombreProductoCrear");
            codigoProducto = getIntent().getStringExtra("codigoProductoCrear");
            cantidadProducto = getIntent().getIntExtra("cantidadProductoCrear", 0);
            descripcionProducto = getIntent().getStringExtra("descripcionProductoCrear");
            precioProducto = getIntent().getStringExtra("precioProductoCrear");
            imagen = getIntent().getStringExtra("imageUriCreate");

            crearProducto.putString("nombreProductoCrear", nombreProducto);
            crearProducto.putString("codigoProductoCrear", codigoProducto);
            crearProducto.putInt("cantidadProductoCrear", cantidadProducto);
            crearProducto.putString("descripcionProductoCrear", descripcionProducto);
            crearProducto.putString("precioProductoCrear", precioProducto);
            crearProducto.putString("imageUriCreate", imagen);

            storageFragment = new StorageFragment();
            if ((!isEdit && nombreProducto != null) || (!isEdit && productoId != 0)) {
                storageFragment.setArguments(crearProducto);
            }
            ReplaceFragment(storageFragment);
        }

        binding = ActivityInventarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.navView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.navigation_inventario:
                    storageFragment = new StorageFragment();
                    //storageFragment.setShareableInstance(this);
                    ReplaceFragment(storageFragment);
                    break;
                case R.id.navigation_reports:
                    ReportsFragment reportsFragment = new ReportsFragment();
                    ReplaceFragment(reportsFragment);
                    break;
                case R.id.navigation_category:
                    CategoriesFragment categoriesFragment = new CategoriesFragment();
                    categoriesFragment.setArguments(datos);
                    ReplaceFragment(categoriesFragment);
                    break;
            }
            return true;
        });

        fabOpenProduct = (FloatingActionButton) findViewById(R.id.fab);

        fabOpenProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InventarioActivity.this, CreateProductActivity.class);
                startActivity(intent);
            }
        });


    }

    private void ReplaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void setData(ArrayList<ProductModel> data) {
        if(data instanceof ArrayList){
            ArrayList<ProductModel> productos = data;
        }
    }

    public static final Uri getUriToResource(@NonNull Context context, @AnyRes int resId) throws Resources.NotFoundException {

        Resources res = context.getResources();

        Uri resUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + res.getResourcePackageName(resId)
                + '/' + res.getResourceTypeName(resId)
                + '/' + res.getResourceEntryName(resId));

        return resUri;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(InventarioActivity.this);
                alertDialog.setTitle("Aviso!")
                        .setIcon(R.drawable.ic_baseline_info_24)
                        .setMessage("¿Está seguro que desea cerrar sesión?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                logout();
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(InventarioActivity.this, LoginActivity.class));
        finish();
    }
}