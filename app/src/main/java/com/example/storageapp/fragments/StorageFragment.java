package com.example.storageapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.storageapp.GridAdapter;
import com.example.storageapp.R;
import com.example.storageapp.model.ProductModel;
import com.example.storageapp.views.InventarioActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StorageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StorageFragment extends Fragment {

    ArrayList<ProductModel> productModels = new ArrayList<>();
    GridView gridView;

    public StorageFragment() {
        // Required empty public constructor
    }


    public static StorageFragment newInstance(String param1, String param2) {
        StorageFragment fragment = new StorageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

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

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), "You clicked on " + productModels.get(i).getNombre() , Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }
}