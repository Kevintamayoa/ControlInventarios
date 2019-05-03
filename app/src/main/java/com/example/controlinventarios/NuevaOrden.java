package com.example.controlinventarios;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.controlinventarios.Dao.AssembliesDao;
import com.example.controlinventarios.Dao.CustomersDao;
import com.example.controlinventarios.Dao.OrdersDao;
import com.example.controlinventarios.Dao.ProductsDao;
import com.example.controlinventarios.Dao.StatusDao;
import com.example.controlinventarios.db.AppDatabase;
import com.example.controlinventarios.db.Assemblies;
import com.example.controlinventarios.db.Customers;
import com.example.controlinventarios.db.Orders;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class NuevaOrdenesAdapter extends RecyclerView.Adapter<NuevaOrdenesAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout itemProduct;
        private TextView txtDescription;
        private TextView txtQuantity;
        private TextView txtPrice;

        private Orders order;
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
            NumberFormat formatoImporte = NumberFormat.getCurrencyInstance();
            formatoImporte = NumberFormat.getCurrencyInstance(new Locale("en","US"));
            String aux1 = "Precio: " +  formatoImporte.format(assembly.getCost()/100);
            String aux2 = "Productos: " + assembly.getNum_products();
            txtDescription.setText(assembly.getDescription());
            txtPrice.setText(aux1);
            txtQuantity.setText(aux2);
        }
    }
    private List<Assemblies> assemblies;
    Customers customer;
    List<Customers> customers;
    private List<Orders> orders;
    Dialog assembliesDialog;
    private Orders order;
    public NuevaOrdenesAdapter(Customers customer,List<Assemblies> assemblies) {
        this.customer = customer;
        this.assemblies=assemblies;
    }
    Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycle_ordenes, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        context = viewGroup.getContext();
        AppDatabase db = AppDatabase.getAppDatabase(this.context);
        CustomersDao customersDao = db.customersDao();

        customers=customersDao.getAllCustomer();

        ((Activity) viewGroup.getContext()).registerForContextMenu(view);
        viewHolder.itemProduct.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    menu.add(viewHolder.getAdapterPosition(), 0, 0, "Eliminar");

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


    public void deleteAssembly(final int position){
       assemblies.remove(position);

    }
    public List<Assemblies> GetAssembly(){
        return  assemblies;

    }
    public Customers GetCustomer(){
        return  customer;

    }
}
////////////////////

public class NuevaOrden extends AppCompatActivity {

    RecyclerView nuevaorden;
Spinner nuevocliente;
 Toolbar toolbar;
NuevaOrdenesAdapter adapter;
    ArrayAdapter<String> catClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_nueva_orden);
        nuevocliente = findViewById(R.id.nuevocliente_spinner);
        nuevaorden=findViewById(R.id.nuevo_assemblies_recycleview);
        toolbar = findViewById(R.id.nuevaorden_toolbar);
        setSupportActionBar(toolbar);

        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        CustomersDao customersDao = db.customersDao();
        catClientes = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);

        catClientes.add("Todos");
        catClientes.addAll(customersDao.getAllCustomerCat());
        nuevocliente.setAdapter(catClientes);
        nuevocliente.setSelection(0);
        if(savedInstanceState!=null){
            adapter.customer=customersDao.getAllCustomer().get(savedInstanceState.getInt("ClienteId"));
        }
     //   final AssembliesDao assembliesDao = db.assembliesDao();
     //   nuevaorden.setAdapter(new NuevaOrdenesAdapter(null,null));
     //   nuevaorden.setLayoutManager(new LinearLayoutManager(this));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_btn:
                Intent customersScreen = new Intent(NuevaOrden.this, NuevoEnsamble.class);
                Bundle parametros = new Bundle();
//                parametros.putInt("ClienteId",adapter.customers.get(item.getGroupId()).getId());
                customersScreen.putExtras(parametros);
                startActivity(customersScreen);
                finish();
                return true;
            case R.id.save_btn:

                return true;
            default:
                return true;
        }
    }
    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Salir? no se guardará ninguna información.")
                .setTitle("Saliendo Sin Guardar");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent customersScreen = new Intent(NuevaOrden.this, Ordenes.class);
                Bundle parametros = new Bundle();
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
    public boolean onContextItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case 0:
                  AlertDialog.Builder builder = new AlertDialog.Builder(this);
                  builder.setMessage("Seguro que desea eliminar este ensamble de la orden?")
                          .setTitle("Eliminar Usuario");
                  builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int id) {
                          adapter.deleteAssembly(item.getGroupId());
                          adapter = new NuevaOrdenesAdapter(adapter.GetCustomer(),adapter.GetAssembly());
                          nuevaorden.setAdapter(adapter);
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
}
