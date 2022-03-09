package com.example.storageapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.storageapp.R;

public class CategoriesFragment extends Fragment {






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /*Button btnNuevaCat = (Button) findViewById(R.id.btnNuevaCategoria);
        btnNuevaCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), nuevaCategoria.class);
                startActivity(intent);
            }
        }); */

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }



}