package com.example.controlinventarios;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.controlinventarios.Dao.ProductCategoriesDao;
import com.example.controlinventarios.Dao.ProductsDao;
import com.example.controlinventarios.db.AppDatabase;
import com.example.controlinventarios.db.Products;

import java.util.List;

class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDescription;
        private TextView txtPrice;
        private TextView txtQuantity;

        private Products product;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            txtDescription = itemView.findViewById(R.id.product_name);
            txtPrice = itemView.findViewById(R.id.product_price);
            txtQuantity = itemView.findViewById(R.id.product_quantity);
        }

        public void bind(Products product) {
            this.product = product;
            String aux1 = "Precio: $" + product.getPrice();
            String aux2 = "Cantidad: " + product.getQty();
            txtDescription.setText(product.getDescription());
            txtPrice.setText(aux1);
            txtQuantity.setText(aux2);
        }
    }

    private List<Products> products;

    public ProductsAdapter(List<Products> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycle_products, viewGroup, false);
        ((Activity)viewGroup.getContext()).registerForContextMenu(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(products.get(i));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


}

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

        productosrecycler = findViewById(R.id.productos_recycleview);
        buscarbtn = findViewById(R.id.buscarproductos_btn);
        buscartext = findViewById(R.id.buscarproductos_text);
        categoriaspinner = findViewById(R.id.categoriaproductos_spinner);

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
                if (categoriaspinner.getSelectedItemPosition() >= (pcDao.getProductCategories().size())) {
                    Toast.makeText(Productos.this, "Elegiste Todos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Productos.this, "Elegiste otro", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
