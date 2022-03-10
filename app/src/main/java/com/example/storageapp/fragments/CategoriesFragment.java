package com.example.storageapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storageapp.R;
import com.example.storageapp.views.NuevaCategoriaActivity;

public class CategoriesFragment extends Fragment {

    Button btnNuevaCat;
    TextView txtNombre;

    private String dato;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            dato =  getArguments().getString("nombre");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_categories, container, false);

        txtNombre = (TextView) rootView.findViewById(R.id.txtNameCategory);
        txtNombre.setText(dato);
        Toast.makeText(getContext(), "nombre de la categoria: " + dato, Toast.LENGTH_LONG).show();

        btnNuevaCat = (Button) rootView.findViewById(R.id.btnNuevaCategoria);
        btnNuevaCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NuevaCategoriaActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }



}