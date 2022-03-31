package com.example.storageapp.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.storageapp.Dataholder;
import com.example.storageapp.R;
import com.example.storageapp.model.CategoryModel;
import com.example.storageapp.views.NuevaCategoriaActivity;

import java.util.ArrayList;

public class CategoriesFragment extends Fragment {

    Button btnNuevaCat;

    private String nombreCat;
    private String descCat;
    private ArrayList<CategoryModel> categories = Dataholder.getInstance().categories;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            nombreCat =  getArguments().getString("nombreCategoria");
            descCat =  getArguments().getString("descripcionCategoria");
        }

        if (nombreCat != null && descCat != null) {
            CategoryModel categoryModel = new CategoryModel(nombreCat, descCat);
            categories.add(categoryModel);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_categories, container, false);

        TableLayout tableLayout = (TableLayout) rootView.findViewById(R.id.tableL1);

            for (int i = 0; i < categories.size(); i++) {
                crearTabla(tableLayout, i);
            }

        // Toast.makeText(getContext(), "nombre de la categoria: " + dato, Toast.LENGTH_LONG).show();
        // Toast.makeText(getContext(), "desc de la categoria: " + desc, Toast.LENGTH_LONG).show();

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

    private void crearTabla(TableLayout tableLayout, int i) {

        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setPadding(15,25,15,25);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TableRow tableRow = new TableRow(getActivity());
        TextView txtNombreCat = new TextView(getActivity());
        TextView txtDescCat = new TextView(getActivity());
        txtNombreCat.setText(categories.get(i).getNombre());
        txtNombreCat.setTextSize(16);
        txtNombreCat.setTypeface(Typeface.DEFAULT_BOLD);
        txtDescCat.setText(categories.get(i).getDescripcion());
        txtDescCat.setTextSize(16);
        linearLayout.addView(txtNombreCat);
        linearLayout.addView(txtDescCat);
        tableRow.addView(linearLayout);
        tableLayout.addView(tableRow);
    }


}