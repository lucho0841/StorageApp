package com.example.storageapp.views;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.SearchView;

import com.example.storageapp.R;
import com.example.storageapp.databinding.ActivityInventarioBinding;
import com.example.storageapp.fragments.CategoriesFragment;
import com.example.storageapp.fragments.ReportsFragment;
import com.example.storageapp.fragments.StorageFragment;
import com.example.storageapp.model.GridAdapter;
import com.example.storageapp.model.ProductModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class InventarioActivity extends AppCompatActivity {

    private ActivityInventarioBinding binding;

    ArrayList<ProductModel> productModels = new ArrayList<>();
    GridView gridView;
    GridAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInventarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        productModels.add(new ProductModel(1, R.drawable.tornillo, "Tornillo", "PR001", "200"));
        productModels.add(new ProductModel(2, R.drawable.destornillador, "Destornillador tipo pala", "PR002", "2500"));
        productModels.add(new ProductModel(3, R.drawable.wiring, "Cable duplex", "PR003", "6000"));

        gridAdapter = new GridAdapter(getBaseContext(), productModels);

        ReplaceFragment(new StorageFragment());

        binding.navView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.navigation_inventario:
                    ReplaceFragment(new StorageFragment());
                    break;
                case R.id.navigation_product:
                    ReplaceFragment(new ReportsFragment());
                    break;
                case R.id.navigation_category:
                    ReplaceFragment(new CategoriesFragment());
                    break;
            }
            return true;
        });
    }

    private void ReplaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_filter_menu, menu);

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
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Search_filter:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}