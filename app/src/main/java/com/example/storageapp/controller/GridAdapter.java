package com.example.storageapp.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.storageapp.model.ProductModel;
import com.example.storageapp.views.EditProductActivity;
import com.example.storageapp.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends BaseAdapter implements Filterable {

    TextView txtName, txtCode, txtPrice;
    Context context;
    List<ProductModel> productModels;
    List<ProductModel> filterProducts;

    LayoutInflater inflater;
    Button btnEditar;

    public GridAdapter(Context context, ArrayList<ProductModel> productModels) {
        this.context = context;
        this.productModels = productModels;
        this.filterProducts = productModels;
    }

    @Override
    public int getCount() {
        return filterProducts.size();
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
        btnEditar = convertView.findViewById(R.id.btnEditProduct);

        Picasso.get().load(filterProducts.get(position).getImage()).into(imageProduct);
        txtName.setText("Nombre: " + filterProducts.get(position).getNombre());
        txtCode.setText("Código: " + filterProducts.get(position).getCodigo());
        txtPrice.setText("Precio: " + filterProducts.get(position).getPrecio());

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditProductActivity.class);
                intent.putExtra("name", filterProducts.get(position).getNombre());
                intent.putExtra("codigo", filterProducts.get(position).getCodigo());
                intent.putExtra("precio", filterProducts.get(position).getPrecio());
                intent.putExtra("imagenActual", filterProducts.get(position).getImage());
                intent.putExtra("cantidad", filterProducts.get(position).getCantidad());
                intent.putExtra("descripcion", filterProducts.get(position).getDescripcion());
                intent.putExtra("id", filterProducts.get(position).getProductoId());

                context.startActivity(intent);

            }
        });
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if (charSequence == null || charSequence.equals("")){
                    filterResults.count = productModels.size();
                    filterResults.values = productModels;
                } else {
                    String searchStr = charSequence.toString().toLowerCase();
                    List<ProductModel> resultData = new ArrayList<>();

                    for (ProductModel product: filterProducts){
                        if (product.getNombre().toLowerCase().contains(searchStr) || product.getCodigo().toLowerCase().contains(searchStr)){
                            resultData.add(product);
                        }

                        filterResults.count = resultData.size();
                        filterResults.values = resultData;
                    }
                }

                return filterResults;
            }


            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults.count > 0) {
                    filterProducts = (ArrayList<ProductModel>) filterResults.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }


        };
        return filter;
    }
}
