package com.example.controlinventarios;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.controlinventarios.Dao.ProductCategoriesDao;
import com.example.controlinventarios.Dao.ProductsDao;
import com.example.controlinventarios.db.AppDatabase;
import com.example.controlinventarios.db.ProductCategories;
import com.example.controlinventarios.db.Products;

import java.util.List;

class MissingProductsAdapter extends RecyclerView.Adapter<MissingProductsAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout itemProduct;
        private TextView txtDescription;
        private TextView txtPrice;
        private TextView txtQuantity;

        private Products product;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemProduct = itemView.findViewById(R.id.product_item);
            txtDescription = itemView.findViewById(R.id.product_name);
            txtPrice = itemView.findViewById(R.id.product_price);
            txtQuantity = itemView.findViewById(R.id.product_quantity);
        }

        public void bind(Products product) {
            this.product = product;
            String aux1 = "Precio por unidad: $" + product.getPrice() / 100;
            String aux2 = "Cantidad faltante: " + product.getQty();
            txtDescription.setText(product.getDescription());
            txtPrice.setText(aux1);
            txtQuantity.setText(aux2);
        }
    }

    ProductCategoriesDao pcDao;
    List<Products> missingProducts;
    Dialog productDialog;

    public MissingProductsAdapter(ProductCategoriesDao pcDao, List<Products> missingProducts){
        this.pcDao = pcDao;
        this.missingProducts = missingProducts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycle_products, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        final Context context = viewGroup.getContext();

        productDialog = new Dialog(context);
        productDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        productDialog.setContentView(R.layout.aux_showproduct);

        viewHolder.itemProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView auxcategoria = productDialog.findViewById(R.id.aux_productcat);
                TextView auxproducto = productDialog.findViewById(R.id.aux_productname);
                TextView auxprice = productDialog.findViewById(R.id.aux_productprice);
                TextView auxqty = productDialog.findViewById(R.id.aux_productqty);

                String categorytxt = "Categoria: " + pcDao.getProductCategory(missingProducts.get(viewHolder.getAdapterPosition()).getCategory_id());
                String producttxt = "Producto faltante: " + missingProducts.get(viewHolder.getAdapterPosition()).getDescription();
                String pricetxt = "Precio por unidad: $" + missingProducts.get(viewHolder.getAdapterPosition()).getPrice() / 100;
                String qtytxt = "Cantidad faltante: " +missingProducts.get(viewHolder.getAdapterPosition()).getQty();
                auxcategoria.setText(categorytxt);
                auxproducto.setText(producttxt);
                auxprice.setText(pricetxt);
                auxqty.setText(qtytxt);
                productDialog.show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.bind(missingProducts.get(i));
    }

    @Override
    public int getItemCount() {
        return missingProducts.size();
    }
}

public class ProductosFaltantes extends AppCompatActivity {

    RecyclerView recycleMissingProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_faltantes);
        recycleMissingProducts = findViewById(R.id.missing_products_recycleview);
        ProductCategoriesDao pcDao = AppDatabase.getAppDatabase(this).productCategoriesDao();
        recycleMissingProducts.setLayoutManager(new LinearLayoutManager(this));
        recycleMissingProducts.setAdapter(new MissingProductsAdapter(pcDao, AppDatabase.getAppDatabase(this).productsDao().getMissingProducts()));

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
