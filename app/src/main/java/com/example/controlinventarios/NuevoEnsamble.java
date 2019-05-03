package com.example.controlinventarios;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.usage.NetworkStats;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.controlinventarios.Dao.AssembliesDao;
import com.example.controlinventarios.Dao.AssemblyProductsDao;
import com.example.controlinventarios.Dao.CustomersDao;
import com.example.controlinventarios.Dao.OrdersDao;
import com.example.controlinventarios.Dao.ProductCategoriesDao;
import com.example.controlinventarios.Dao.ProductsDao;
import com.example.controlinventarios.Dao.StatusDao;
import com.example.controlinventarios.db.AppDatabase;
import com.example.controlinventarios.db.Assemblies2;
import com.example.controlinventarios.db.Products;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

class NuevoProductsAdapter extends RecyclerView.Adapter<NuevoProductsAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout itemProduct;
        private TextView txtDescription;
        private TextView txtQuantity;
        private TextView txtPrice;


        private Assemblies2 assembly;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemProduct = itemView.findViewById(R.id.product_item);
            txtDescription = itemView.findViewById(R.id.product_name);
            txtPrice = itemView.findViewById(R.id.product_price);
            txtQuantity = itemView.findViewById(R.id.product_quantity);
        }

        public void bind(Assemblies2 assembly) {
            this.assembly = assembly;
            NumberFormat formatoImporte = NumberFormat.getCurrencyInstance();
            formatoImporte = NumberFormat.getCurrencyInstance(new Locale("en","US"));
            String aux1 = "Precio: " +  formatoImporte.format(assembly.getCost()/100);
            String aux2 = "Productos: " + assembly.getNum_products();
            txtDescription.setText(assembly.getDescription());
            txtPrice.setText(aux1);
            txtQuantity.setText(aux2);
        }
    }

    List<Assemblies2> assemblies;
    ProductsDao pDao;
    Dialog assembliesDialog;

    public NuevoProductsAdapter(List<Assemblies2> assemblies) {
        this.assemblies = assemblies;
    }

    Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycle_products, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
       context = viewGroup.getContext();
        ((Activity) viewGroup.getContext()).registerForContextMenu(view);
        viewHolder.itemProduct.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    menu.add(viewHolder.getAdapterPosition(), 0, 0, "Detalles");
                    menu.add(viewHolder.getAdapterPosition(), 1, 1, "Agregar");
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
    Dialog detailsDialog;
    public void ordensDetails(int position){
        detailsDialog = new Dialog(context);
        detailsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        detailsDialog.setContentView(R.layout.aux_showlistproducts);
        ///
       // RecyclerView productosrecycler = detailsDialog.findViewById(R.id.productos_recycleview2);
       // AppDatabase db = AppDatabase.getAppDatabase(detailsDialog.getContext());
//
       // final ProductCategoriesDao pcDao = db.productCategoriesDao();
       // final ProductsDao pDao2 = db.productsDao();
       // ArrayAdapter<String> pCategories = new ArrayAdapter<>(detailsDialog.getContext(), R.layout.support_simple_spinner_dropdown_item);
       // pCategories.addAll(pcDao.getProductCategories());
       // pCategories.add("Todos");
//
       // productosrecycler.setLayoutManager(new LinearLayoutManager(detailsDialog.getContext()));
       // productosrecycler.setAdapter(null);
       // productosrecycler.setAdapter(new ProductsAdapter(pcDao,pDao2.getAllProductsByAssembly(assemblies.get(position).getId())));//
//
       // detailsDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        detailsDialog.show();
    }

}
///////
public class NuevoEnsamble extends AppCompatActivity {
    EditText Buscartext;
    RecyclerView ensamblesrecyvle;
    int clienteid;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_ensamble);
        //Evita que se abra el edittext apenas abre el activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        if(savedInstanceState!=null){
            clienteid=savedInstanceState.getInt("ClienteId");

        }
           Buscartext=findViewById(R.id.buscarensambles_text);
           ensamblesrecyvle=findViewById(R.id.nuevo_assemblies_recycleview);
           toolbar=findViewById(R.id.nuevaensambles_toolbar);
           setSupportActionBar(toolbar);
           AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
           final ProductCategoriesDao pcDao = db.productCategoriesDao();
           final AssembliesDao assembliesDao = db.assembliesDao();

        ensamblesrecyvle.setLayoutManager(new LinearLayoutManager(this));
       ensamblesrecyvle.setAdapter(new NuevoProductsAdapter( assembliesDao.getAllAssemblies(Buscartext.getText().toString())));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    private NuevoProductsAdapter adapter;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_btn:
              final   AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
                final AssembliesDao assembliesDao = db.assembliesDao();
                ensamblesrecyvle.setAdapter(new NuevoProductsAdapter( assembliesDao.getAllAssemblies(Buscartext.getText().toString())));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onContextItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case 0:
                adapter.ordensDetails(item.getGroupId());

                return true;
            case 1:
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                builder.setMessage("Seguro que desea agregar este ensamble a la orden?.")
                        .setTitle("Saliendo Sin Guardar");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent customersScreen = new Intent(NuevoEnsamble.this, NuevaOrden.class);
                        Bundle parametros = new Bundle();
                        int aux=item.getGroupId();
                        parametros.putInt("ENSAMBLE",aux);
                        parametros.putInt("ClienteId",clienteid);
                        customersScreen.putExtras(parametros);
                        startActivity(customersScreen);
                        finish();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            default:
                return true;
        }
    }
    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Salir?.")
                .setTitle("Saliendo Sin Guardar");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent customersScreen = new Intent(NuevoEnsamble.this, NuevaOrden.class);
                Bundle parametros = new Bundle();
                parametros.putInt("ClienteId",clienteid);
                customersScreen.putExtras(parametros);
                startActivity(customersScreen);
                finish();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("ClienteId", clienteid);
        outState.putString("Buscar", Buscartext.getText().toString());
          }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
     clienteid = savedInstanceState.getInt("ClienteId");
        final   AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        final AssembliesDao assembliesDao = db.assembliesDao();
        ensamblesrecyvle.setAdapter(new NuevoProductsAdapter( assembliesDao.getAllAssemblies(savedInstanceState.getString("Buscar"))));
        //Evita que se abra el edittext apenas abre el activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
}
