package com.example.controlinventarios;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.controlinventarios.Dao.AssembliesDao;
import com.example.controlinventarios.Dao.AssemblyProductsDao;
import com.example.controlinventarios.Dao.ProductCategoriesDao;
import com.example.controlinventarios.Dao.ProductsDao;
import com.example.controlinventarios.db.AppDatabase;
import com.example.controlinventarios.db.Assemblies;
import com.example.controlinventarios.db.AssemblyProducts;
import com.example.controlinventarios.db.Products;

import java.text.Format;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

class AssambliesAdapter extends RecyclerView.Adapter<AssambliesAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout itemProduct;
        private TextView txtDescription;
        private TextView txtQuantity;
        private TextView txtPrice;


        private Assemblies assembly;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemProduct = itemView.findViewById(R.id.assembly_item);
            txtDescription = itemView.findViewById(R.id.assembly_name);
            txtPrice = itemView.findViewById(R.id.assembly_price);
            txtQuantity = itemView.findViewById(R.id.assembly_quantity);
        }

        public void bind(Assemblies assembly) {
            this.assembly = assembly;
            String aux1 = "Precio: $" +  assembly.getCost()/100;
            String aux2 = "Productos: " + assembly.getNum_products();
            txtDescription.setText(assembly.getDescription());
            txtPrice.setText(aux1);
            txtQuantity.setText(aux2);
        }
    }

    private List<Assemblies> assemblies;
    ProductsDao pDao;
    Dialog assembliesDialog;

    public AssambliesAdapter( List<Assemblies> assemblies, ProductsDao pDao) {
        this.assemblies = assemblies;
        this.pDao=pDao;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycle_assemblies, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        final Context context = viewGroup.getContext();
        assembliesDialog = new Dialog(context);
        assembliesDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        assembliesDialog.setContentView(R.layout.aux_showlistproducts);
        viewHolder.itemProduct.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
          //     Spinner categoriaspinner = assembliesDialog.findViewById(R.id.categoriaproductos_spinner);
               RecyclerView productosrecycler = assembliesDialog.findViewById(R.id.productos_recycleview2);
            //   EditText  buscartext = assembliesDialog.findViewById(R.id.buscarproductos_text);
           //    Toolbar toolbar = assembliesDialog.findViewById(R.id.productos_toolbar);
           //    setSupportActionBar(toolbar);
               AppDatabase db = AppDatabase.getAppDatabase(assembliesDialog.getContext());

             final ProductCategoriesDao pcDao = db.productCategoriesDao();
               final ProductsDao pDao2 = db.productsDao();
             ArrayAdapter<String> pCategories = new ArrayAdapter<>(assembliesDialog.getContext(), R.layout.support_simple_spinner_dropdown_item);
             pCategories.addAll(pcDao.getProductCategories());
             pCategories.add("Todos");

           //    categoriaspinner.setAdapter(pCategories);
           //    categoriaspinner.setSelection(pCategories.getCount() - 1);
               List<Products> producto=new ArrayList<Products>();
               producto.add(new Products(1,4,"asd",50,3));
               productosrecycler.setLayoutManager(new LinearLayoutManager(assembliesDialog.getContext()));
               productosrecycler.setAdapter(null);
               productosrecycler.setAdapter(new ProductsAdapter(pcDao,pDao2.getAllProductsByAssembly(assemblies.get(viewHolder.getAdapterPosition()).getId())));//

               assembliesDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

           assembliesDialog.show();
           }
       });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
       viewHolder.bind(assemblies.get(i));
    }

    @Override
    public int getItemCount() {
        return assemblies.size();
    }
}
////////////////////

public class Ensambles extends AppCompatActivity {

    RecyclerView ensamblesrecycler;
    EditText buscartext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ensambles);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        ensamblesrecycler = findViewById(R.id.ensambles_recycleview);
        buscartext = findViewById(R.id.buscarensambles_text);

       AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());

       final AssembliesDao assembliesDao = db.assembliesDao();
       ensamblesrecycler.setAdapter(new AssambliesAdapter( assembliesDao.getAllAssemblies(buscartext.getText().toString()),null));
       ensamblesrecycler.setLayoutManager(new LinearLayoutManager(this));



      Toolbar toolbar = findViewById(R.id.ensambles_toolbar);
      setSupportActionBar(toolbar);
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

         AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
         final AssembliesDao assembliesDao = db.assembliesDao();
         ensamblesrecycler.setAdapter(new AssambliesAdapter( assembliesDao.getAllAssemblies(buscartext.getText().toString()),null));

         return true;

     default:
       return super.onOptionsItemSelected(item);
 }
    }

}
