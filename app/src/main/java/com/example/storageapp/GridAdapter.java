package com.example.storageapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.storageapp.model.ProductModel;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    TextView txtName, txtCode, txtPrice;
    Context context;
    ArrayList<ProductModel> productModels;
    LayoutInflater inflater;

    public GridAdapter(Context context, ArrayList<ProductModel> productModels) {
        this.context = context;
        this.productModels = productModels;
    }

    @Override
    public int getCount() {
        return productModels.size();
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
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.grid_item_inventarios, null);

        ImageView imageProduct = convertView.findViewById(R.id.imgProducts);
        txtName = convertView.findViewById(R.id.txtNameProduct);
        txtCode = convertView.findViewById(R.id.txtCodeProduct);
        txtPrice = convertView.findViewById(R.id.txtPrice);

        imageProduct.setImageResource(productModels.get(position).getImage());
        txtName.setText(productModels.get(position).getNombre());
        txtCode.setText(productModels.get(position).getCodigo());
        txtPrice.setText(productModels.get(position).getPrecio());
        return convertView;
    }
}
