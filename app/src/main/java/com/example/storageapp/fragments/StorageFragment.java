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

        if (flag){
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Products").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        productModels = new ArrayList<>();
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
                                        String categoria = snapshot.child("categoria").getValue().toString();
                                        String usuarioId = snapshot.child("usuarioId").getValue().toString().trim();
                                        ProductModel productModel = new ProductModel(
                                                id,
                                                "android.resource://com.example.storageapp/drawable/tornillo",
                                                nombre,
                                                codigo,
                                                precio,
                                                cantidad,
                                                descripcion,
                                                categoria,
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

                        gridAdapter = new GridAdapter(getContext(), productModels);
                        gridView.setAdapter(gridAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            flag = false;
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