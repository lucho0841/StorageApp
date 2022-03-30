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
import android.widget.Toast;

import com.example.storageapp.Dataholder;
import com.example.storageapp.R;
import com.example.storageapp.model.CategoryModel;
import com.example.storageapp.views.NuevaCategoriaActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CategoriesFragment extends Fragment {

    Button btnNuevaCat;
    TextView txtNombre;
    TextView txtDescripcion;

    private String dato;
    private String desc;
    private ArrayList<CategoryModel> categories = Dataholder.getInstance().categories;
    //private ArrayList<CategoryModel> categories = new ArrayList<CategoryModel>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            dato =  getArguments().getString("nombre");
            desc =  getArguments().getString("descripcion");
            // System.out.println("este es el nombre"+dato+" y esta es la descripcion"+desc);
        }

        if (dato != null && desc != null) {
            CategoryModel categoryModel = new CategoryModel(dato, desc);
            categories.add(categoryModel);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_categories, container, false);

        TableLayout tableLayout = (TableLayout) rootView.findViewById(R.id.tableL1);

       // if (categories != null) {

            for (int i = 0; i < categories.size(); i++) {
                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setPadding(15,25,15,25);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                TableRow tableRow = new TableRow(getActivity());
                TextView txtNombre1 = new TextView(getActivity());
                TextView txtDesc1 = new TextView(getActivity());
                txtNombre1.setText(categories.get(i).getNombre());
                //txtNombre1.setText(dato);
                txtNombre1.setTextSize(16);
                txtNombre1.setTypeface(Typeface.DEFAULT_BOLD);
                txtDesc1.setText(categories.get(i).getDescripcion());
                // txtDesc1.setText(desc);
                txtDesc1.setTextSize(16);

                linearLayout.addView(txtNombre1);
                linearLayout.addView(txtDesc1);
                tableRow.addView(linearLayout);
                tableLayout.addView(tableRow);
            }
       // }


/*
        txtNombre = (TextView) rootView.findViewById(R.id.txtCategoryName);
        txtNombre.setText(dato);
        txtDescripcion = (TextView) rootView.findViewById(R.id.txtCategoryDescription);
        txtDescripcion.setText(desc);
*/
        //Toast.makeText(getContext(), "nombre de la categoria: " + dato, Toast.LENGTH_LONG).show();
        Toast.makeText(getContext(), "desc de la categoria: " + desc, Toast.LENGTH_LONG).show();

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