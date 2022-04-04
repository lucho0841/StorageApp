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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.storageapp.R;
import com.example.storageapp.model.GridAdapter;
import com.example.storageapp.model.ProductModel;

import java.util.ArrayList;

public class StorageFragment extends Fragment {

    ArrayList<ProductModel> productModels = new ArrayList<>();
    GridView gridView;
    MenuInflater getMenuInflater;
    GridAdapter gridAdapter;
    Button btnEditProduct;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        productModels.add(new ProductModel(1, R.drawable.tornillo, "Tornillo", "PR001", "$200.00", 5, "tornillo de ensamble"));
        productModels.add(new ProductModel(2, R.drawable.destornillador, "Destornillador tipo pala", "PR002", "$2500.00", 10, "destornillador tipo pala de hierro"));
        productModels.add(new ProductModel(3, R.drawable.wiring, "Cable duplex", "PR003", "$6000.00", 9, "cable duplex 12 para telecomunicaciones."));
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_storage, container, false);
        gridView = rootView.findViewById(R.id.grdInventario);

        gridAdapter = new GridAdapter(getContext(), productModels);
        gridView.setAdapter(gridAdapter);


        setHasOptionsMenu(true);

        return rootView;

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_filter_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.Search_filter);

        SearchView searchView = (SearchView) menuItem.getActionView();

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