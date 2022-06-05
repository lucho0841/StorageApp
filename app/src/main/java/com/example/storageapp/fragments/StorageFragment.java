package com.example.storageapp.fragments;

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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.storageapp.R;
import com.example.storageapp.controller.GridAdapter;
import com.example.storageapp.model.ProductModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StorageFragment extends Fragment {

    ArrayList<ProductModel> productModels;
    GridView gridView;
    MenuInflater getMenuInflater;
    GridAdapter gridAdapter;
    Button btnEditProduct;
    private DatabaseReference mDatabase;
    String nombre, codigo, descripcion, precio, imagen;
    int productoId, cantidad, isEdit, isDelete;
    Boolean flag = false;
    private Boolean isEnd = false;

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

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    long numHijos = snapshot.getChildrenCount();
                    for (int i = 1; i <= numHijos; i++){
                        mDatabase = FirebaseDatabase.getInstance().getReference("Products").child(String.valueOf(i));
                        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    int id = Integer.parseInt(snapshot.child("productoId").getValue().toString().trim());
                                    String image = snapshot.child("image").getValue().toString().trim();
                                    String nombre = snapshot.child("nombre").getValue().toString();
                                    String codigo = snapshot.child("codigo").getValue().toString().trim();
                                    String precio = snapshot.child("precio").getValue().toString().trim();
                                    int cantidad = Integer.parseInt(snapshot.child("cantidad").getValue().toString().trim());
                                    String descripcion = snapshot.child("descripcion").getValue().toString();
                                    String usuarioId = snapshot.child("usuarioId").getValue().toString().trim();

                                    ProductModel productModel = new ProductModel(
                                            id,
                                            image,
                                            nombre,
                                            codigo,
                                            precio,
                                            cantidad,
                                            descripcion,
                                            usuarioId,
                                            false
                                            );
                                    productModels.add(productModel);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (getArguments() != null) {
            isEdit = getArguments().getInt("editValidate", 0);
            isDelete = getArguments().getInt("deleteValidate", 0);
        } else {
            isEdit = 0;
            isDelete = 0;
        }

        if (flag){

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
                    productModels.add(new ProductModel(productModels.size() + 1, imagen, nombre, codigo, "$" + precio + ".00", cantidad,descripcion, "e004", false));
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