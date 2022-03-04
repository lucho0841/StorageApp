package com.example.storageapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SearchView;

import com.example.storageapp.model.GridAdapter;
import com.example.storageapp.R;
import com.example.storageapp.model.ProductModel;

import java.util.ArrayList;

public class StorageFragment extends Fragment {

    ArrayList<ProductModel> productModels = new ArrayList<>();
    GridView gridView;
    MenuInflater getMenuInflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_storage, container, false);
        gridView = rootView.findViewById(R.id.grdInventario);

        productModels.add(new ProductModel(1, R.drawable.tornillo, "Tornillo", "PR001", "200"));
        productModels.add(new ProductModel(2, R.drawable.destornillador, "Destornillador tipo pala", "PR002", "2500"));
        productModels.add(new ProductModel(3, R.drawable.wiring, "Cable duplex", "PR003", "6000"));

        GridAdapter gridAdapter = new GridAdapter(getContext(), productModels);
        gridView.setAdapter(gridAdapter);

        return rootView;
    }
}