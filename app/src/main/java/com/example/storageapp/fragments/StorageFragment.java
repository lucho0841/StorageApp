package com.example.storageapp.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.storageapp.R;
import com.example.storageapp.controller.DataShare;
import com.example.storageapp.model.GridAdapter;
import com.example.storageapp.model.ProductModel;
import com.example.storageapp.views.EditProductActivity;

import java.util.ArrayList;

public class StorageFragment extends Fragment {

    ArrayList<ProductModel> productModels;
    GridView gridView;
    MenuInflater getMenuInflater;
    GridAdapter gridAdapter;
    Button btnEditProduct;
    String nombre, codigo, descripcion, precio, imagen;
    int productoId, cantidad, isEdit, isDelete;
    Boolean flag = false;

    private DataShare shareableInstance;

    public void setShareableInstance(DataShare dataShare){
        this.shareableInstance = dataShare;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flag = true;
        productModels = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_storage, container, false);
        gridView = rootView.findViewById(R.id.grdInventario);
        if (getArguments() != null) {
            isEdit = getArguments().getInt("editValidate", 0);
            isDelete = getArguments().getInt("deleteValidate", 0);
        } else {
            isEdit = 0;
            isDelete = 0;
        }

        if (flag){

            productModels.add(new ProductModel(1, "android.resource://com.example.storageapp/drawable/tornillo", "Tornillo", "PR001", "$200.00", 5, "tornillo de ensamble"));
            productModels.add(new ProductModel(2, "android.resource://com.example.storageapp/drawable/destornillador", "Destornillador tipo pala", "PR002", "$2500.00", 10, "destornillador tipo pala de hierro"));
            productModels.add(new ProductModel(3, "android.resource://com.example.storageapp/drawable/wiring", "Cable duplex", "PR003", "$6000.00", 9, "cable duplex 12 para telecomunicaciones."));

            gridAdapter = new GridAdapter(getContext(), productModels);
            gridView.setAdapter(gridAdapter);
            flag = false;
        }

        if (isEdit == 1){

            if (getArguments() != null) {
                productoId = getArguments().getInt("productoId");
                nombre = getArguments().getString("nombreProducto");
                codigo = getArguments().getString("codigoProducto");
                cantidad = getArguments().getInt("cantidadProducto");
                precio = getArguments().getString("precioProducto");
                descripcion = getArguments().getString("descripcionProducto");
                imagen = getArguments().getString("imagen");
            }
            for (int i = 0; i < productModels.size(); i++) {
                if (productModels.get(i).getProductoId() == productoId){
                    productModels.get(i).setImage(imagen);
                    productModels.get(i).setNombre(nombre);
                    productModels.get(i).setCodigo(codigo);
                    productModels.get(i).setCantidad(cantidad);
                    productModels.get(i).setPrecio(precio);
                    productModels.get(i).setDescripcion(descripcion);
                }
            }
        } else {
            if (getArguments() != null) {
                if (isDelete == 1){
                    productoId = getArguments().getInt("productoIdDelete");
                    productModels.remove(productoId - 1);
                    gridAdapter = new GridAdapter(getContext(), productModels);
                    gridView.setAdapter(gridAdapter);
                    Toast.makeText(getContext(), "Producto Eliminado con éxito!!", Toast.LENGTH_SHORT).show();
                } else {
                    nombre = getArguments().getString("nombreProductoCrear");
                    codigo = getArguments().getString("codigoProductoCrear");
                    cantidad = getArguments().getInt("cantidadProductoCrear");
                    precio = getArguments().getString("precioProductoCrear");
                    descripcion = getArguments().getString("descripcionProductoCrear");
                    imagen = getArguments().getString("imageUriCreate");
                    productModels.add(new ProductModel(productModels.size() + 1, imagen, nombre, codigo, "$" + precio + ".00", cantidad,descripcion ));
                }

            }

        }


        setHasOptionsMenu(true);

        return rootView;

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_filter_menu, menu);

        MenuItem menuItemSearch = menu.findItem(R.id.Search_filter);

        SearchView searchView = (SearchView) menuItemSearch.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                gridAdapter.getFilter().filter(s);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}