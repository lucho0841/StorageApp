package com.example.storageapp.controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.storageapp.R;
import com.example.storageapp.model.CategoryModel;
import com.example.storageapp.views.EditCategoryActivity;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {

    TextView txtName, txtCodigo;
    Button btnEditCategory;

    List<CategoryModel> categoryModels;
    Context context;

    public CategoryAdapter(List<CategoryModel> categoryModels, Context context) {
        this.categoryModels = categoryModels;
        this.context = context;
    }

    @Override
    public int getCount() {
        return categoryModels.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.grid_item_category, null);
            txtName = (TextView) convertView.findViewById(R.id.txtNameCategory);
            txtCodigo = (TextView) convertView.findViewById(R.id.txtCodeCategory);
            btnEditCategory = (Button) convertView.findViewById(R.id.btnEditCategory);
            txtName.setText("nombre: " + categoryModels.get(position).getNombre());
            txtCodigo.setText("Código: " + categoryModels.get(position).getCodigo());

            btnEditCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, EditCategoryActivity.class);
                    intent.putExtra("nombre", categoryModels.get(position).getNombre());
                    intent.putExtra("codigo", categoryModels.get(position).getCodigo());
                    intent.putExtra("descripcion", categoryModels.get(position).getDescripcion());
                    intent.putExtra("categoryId", categoryModels.get(position).getCategoriaId());
                    context.startActivity(intent);

                }
            });
        }
        return convertView;
    }
}
