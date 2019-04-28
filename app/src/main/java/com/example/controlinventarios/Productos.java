package com.example.controlinventarios;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.controlinventarios.Dao.ProductCategoriesDao;
import com.example.controlinventarios.Dao.ProductsDao;
import com.example.controlinventarios.db.AppDatabase;
import com.example.controlinventarios.db.Products;
import com.facebook.stetho.Stetho;

import java.util.List;
import java.util.Locale;

class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

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
            String aux1 = "Precio: $" + product.getPrice() / 100;
            String aux2 = "Cantidad: " + product.getQty();
            txtDescription.setText(product.getDescription());
            txtPrice.setText(aux1);
            txtQuantity.setText(aux2);
        }
    }

    ProductCategoriesDao pcDao;
    private List<Products> products;
    Dialog productDialog;

    public ProductsAdapter(ProductCategoriesDao pcDao, List<Products> products) {
        this.pcDao = pcDao;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
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
                String categorytxt = "Categoria: " + pcDao.getProductCategory(products.get(viewHolder.getAdapterPosition()).getCategory_id());
                String producttxt = "Nombre del producto: " + products.get(viewHolder.getAdapterPosition()).getDescription();
                String pricetxt = "Precio: $" + products.get(viewHolder.getAdapterPosition()).getPrice() / 100;
                String qtytxt = "Cantidad: " + products.get(viewHolder.getAdapterPosition()).getQty();
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
        viewHolder.bind(products.get(i));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
////////////////////




public class Productos extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView productosrecycler;
    EditText buscartext;
    Spinner categoriaspinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        //Evita que se abra el edittext apenas abre el activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //Declaracion del toolbar como un actionbar
        toolbar = findViewById(R.id.productos_toolbar);
        setSupportActionBar(toolbar);
        //Inicializacion de los elementos
        productosrecycler = findViewById(R.id.productos_recycleview);
        buscartext = findViewById(R.id.buscarproductos_text);
        categoriaspinner = findViewById(R.id.categoriaproductos_spinner);
        AppDatabase db = AppDatabase.getAppDatabase(this);
        final ProductCategoriesDao pcDao = db.productCategoriesDao();
        final ProductsDao productsDao = db.productsDao();
        ArrayAdapter<String> pCategories = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        pCategories.addAll(pcDao.getProductCategories());
        pCategories.add("Todos");
        categoriaspinner.setAdapter(pCategories);
        categoriaspinner.setSelection(pCategories.getCount() - 1);
        productosrecycler.setLayoutManager(new LinearLayoutManager(this));
        productosrecycler.setAdapter(new ProductsAdapter(pcDao, productsDao.getAllProductsByDescription(buscartext.getText().toString())));
        buscartext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                    // NOTE: In the author's example, he uses an identifier
                    // called searchBar. If setting this code on your EditText
                    // then use v.getWindowToken() as a reference to your
                    // EditText is passed into this callback as a TextView
                    in.hideSoftInputFromWindow(v
                                    .getApplicationWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    // Must return true here to consume event
                    return true;

                }
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_btn:
                final ProductCategoriesDao pcDao = AppDatabase.getAppDatabase(getApplicationContext()).productCategoriesDao();
                final ProductsDao productsDao = AppDatabase.getAppDatabase(getApplicationContext()).productsDao();

                if (categoriaspinner.getSelectedItemPosition() >= (pcDao.getProductCategories().size())) {
                    productosrecycler.setAdapter(new ProductsAdapter(pcDao, productsDao.getAllProductsByDescription(buscartext.getText().toString())));
                } else {
                    productosrecycler.setAdapter(new ProductsAdapter(pcDao,
                            productsDao.getProductsByCategoryAndDescription(categoriaspinner.getSelectedItemPosition(),
                                    buscartext.getText().toString())));
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
