package com.example.controlinventarios;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.controlinventarios.Dao.ProductCategoriesDao;
import com.example.controlinventarios.Dao.ProductsDao;
import com.example.controlinventarios.db.AppDatabase;
import com.example.controlinventarios.db.Products;

import java.util.List;

public class Productos extends AppCompatActivity {

    RecyclerView productosrecycler;
    EditText buscartext;
    Spinner categoriaspinner;
    ImageButton buscarbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        productosrecycler=findViewById(R.id.productos_recycleview);
        buscarbtn=findViewById(R.id.buscarproductos_btn);
        buscartext=findViewById(R.id.buscarproductos_text);
        categoriaspinner=findViewById(R.id.categoriaproductos_spinner);

        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());

        final ProductCategoriesDao pcDao = db.productCategoriesDao();
        ArrayAdapter<String> pCategories = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        pCategories.addAll(pcDao.getProductCategories());
        pCategories.add("Todos");
        categoriaspinner.setAdapter(pCategories);

        final ProductsDao productsDao = db.productsDao();

        productosrecycler.setLayoutManager(new LinearLayoutManager(this));

        buscarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(categoriaspinner.getSelectedItemPosition()>=(pcDao.getProductCategories().size())){
                    Toast.makeText(Productos.this, "Elegiste Todos", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Productos.this, "Elegiste otro", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
