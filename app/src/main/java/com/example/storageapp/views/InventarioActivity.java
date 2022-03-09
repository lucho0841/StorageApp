package com.example.storageapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.storageapp.R;
import com.example.storageapp.databinding.ActivityInventarioBinding;
import com.example.storageapp.fragments.CategoriesFragment;
import com.example.storageapp.fragments.ReportsFragment;
import com.example.storageapp.fragments.StorageFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class InventarioActivity extends AppCompatActivity {

    private ActivityInventarioBinding binding;
    FloatingActionButton fabOpenProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInventarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        fabOpenProduct = (FloatingActionButton) findViewById(R.id.fab);

        fabOpenProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InventarioActivity.this, CreateProductActivity.class);
                startActivity(intent);
            }
        });
    }

    private void ReplaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}