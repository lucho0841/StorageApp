package com.example.storageapp.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storageapp.R;
import com.example.storageapp.controller.Dataholder;
import com.example.storageapp.model.CategoryModel;
import com.example.storageapp.model.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EditCategoryActivity extends AppCompatActivity {

    String nombre, descripcion, nombreCategoriaEdt, codigoCategoriaEdt,descripcionCategoria;
    int pos, categoriaId;
    EditText nuevoNombreCat , nuevoDescCat,edtNombreCategoria, edtCodigoCategoria, edtDescripcionCategoria;
    Button btnEditarCategoria;
    private ArrayList<CategoryModel> categories = Dataholder.getInstance().categories;
    private FirebaseAuth mAuth;
    String nombreCat, codigoCat,descripcionCat;
    int codigoCatId;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nombre = getIntent().getStringExtra("nuevoNombreCategoria");
        descripcion = getIntent().getStringExtra("nuevoDescCategoria");
        pos = getIntent().getIntExtra("posicion",0);
        setContentView(R.layout.activity_edit_category);

        nuevoNombreCat = (EditText) findViewById(R.id.nuevoNombreCategoria);
        nuevoDescCat = (EditText) findViewById(R.id.nuevoDescCategoria);
        nombreCat= getIntent().getStringExtra("nombre");
        //codigoCat= getIntent().getStringExtra("codigo");
        descripcionCat= getIntent().getStringExtra("descripcion");

        nuevoNombreCat.setText(nombreCat, TextView.BufferType.EDITABLE);
        nuevoDescCat.setText(descripcionCat, TextView.BufferType.EDITABLE);
        mAuth = FirebaseAuth.getInstance();


        btnEditarCategoria = (Button) findViewById(R.id.btnEditarCategoria);
        btnEditarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtNombreCategoria = (EditText) findViewById(R.id.edtNombreCategoria);
                nombreCategoriaEdt = edtNombreCategoria.getText().toString();

                edtDescripcionCategoria = (EditText) findViewById(R.id.nuevoDescCategoria);
                descripcionCategoria = edtDescripcionCategoria.getText().toString();

                CategoryModel category = new CategoryModel(mAuth.getCurrentUser().getUid(), codigoCategoriaEdt, nombreCategoriaEdt, descripcionCategoria,codigoCatId );
                editarCategoryBD(category);

                Intent intent = new Intent(EditCategoryActivity.this, InventarioActivity.class);
                startActivity(intent);
                finish();
            }

        });


    }
    private void editarCategoryBD(CategoryModel category){
        if (nombreCategoriaEdt.isEmpty()){
            edtNombreCategoria.setError("El nombre es requerido");
            edtNombreCategoria.requestFocus();
            return;
        }

        if (descripcionCategoria.isEmpty()) {
            edtDescripcionCategoria.setError("La descripción es requerida");
            edtDescripcionCategoria.requestFocus();
            return;
        }

        /*mDatabase = FirebaseDatabase.getInstance().getReference("Category").child(String.valueOf(codigoCatId))
                .setValue(category) {



                    @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(EditCategoryActivity.this, "El producto ha sido modificado con Éxito!!!", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }
                        });

*/




    }

}