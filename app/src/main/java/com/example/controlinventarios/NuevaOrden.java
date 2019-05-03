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
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.controlinventarios.Dao.AssembliesDao;
import com.example.controlinventarios.Dao.CustomersDao;
import com.example.controlinventarios.Dao.OrdersAssembliesDao;
import com.example.controlinventarios.Dao.OrdersDao;
import com.example.controlinventarios.Dao.ProductsDao;
import com.example.controlinventarios.Dao.StatusDao;
import com.example.controlinventarios.db.AppDatabase;
import com.example.controlinventarios.db.Assemblies;
import com.example.controlinventarios.db.Assemblies2;
import com.example.controlinventarios.db.Customers;
import com.example.controlinventarios.db.OrderAssemblies;
import com.example.controlinventarios.db.Orders;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

class NuevaOrdenesAdapter extends RecyclerView.Adapter<NuevaOrdenesAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout itemProduct;
        private TextView txtDescription;
        private TextView txtQuantity;
        private TextView txtPrice;

        private Orders order;
        private Assemblies2 assembly;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemProduct = itemView.findViewById(R.id.assembly_item);
            txtDescription = itemView.findViewById(R.id.assembly_name);
            txtPrice = itemView.findViewById(R.id.assembly_price);
            txtQuantity = itemView.findViewById(R.id.assembly_quantity);
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
    private List<Orders> orders;
    int[] assemblies2;
    Dialog assembliesDialog;
    private Orders order;
    public NuevaOrdenesAdapter(int[] assemblies) {
        AppDatabase db = AppDatabase.getAppDatabase(this.context);
        assemblies2=assemblies;
        AssembliesDao assembliesDao = db.assembliesDao();
        for (int i=0;i<assemblies.length;i++){
            this.assemblies.add(assembliesDao.getAssemblyById(assemblies[i]));
        }
    }
    Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycle_assemblies, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        context = viewGroup.getContext();
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

    public void addAssembly(final int id){
        AppDatabase db = AppDatabase.getAppDatabase(this.context);
        AssembliesDao assemblies2 = db.assembliesDao();

        assemblies.add(assemblies2.getAssemblyById(id));

    }
    public void addOrder(final int ClientId,int[] assemblies){
        AppDatabase db = AppDatabase.getAppDatabase(this.context);
        OrdersDao ordersDao = db.ordersDao();
        OrdersAssembliesDao ordersAssembliesDao=db.ordersAssembliesDao();
        int id=ordersDao.getMaxId()+1;
ordersDao.InserOrder(new Orders(id,0,ClientId, "2019-05-03",""));
ArrayList<OrderAssemblies> orderAssemblies=new ArrayList<OrderAssemblies>();
boolean ban=false;
for (int i=0;i<assemblies.length;i++){
    ban=false;
    for (OrderAssemblies obj:orderAssemblies){
        if(obj.getAssembly_id()==assemblies[i]){
            obj.setQty(obj.getQty()+1);
            ban=true;
        }
    }
    if(!ban){
        orderAssemblies.add(new OrderAssemblies(0,0,assemblies[i],1));
    }
}
        for (OrderAssemblies obj:orderAssemblies){
            int id2=ordersAssembliesDao.getMaxId()+1;

            ordersAssembliesDao.InserOrder(new OrderAssemblies(id2,id,obj.getAssembly_id(),obj.getQty()));
        }
            }

    public void deleteAssembly(final int position){
       assemblies.remove(position);
       int[] aux=new int[assemblies2.length-1];
       int j=0;
       for(int i=0;i<assemblies2.length;i++){
           if(i!=position){
               aux[j]=assemblies2[i];
           }
           j++;
       }
       assemblies2=aux;

    }
    public List<Assemblies2> GetAssembly(){
        return  assemblies;

    }
}
////////////////////

public class NuevaOrden extends AppCompatActivity {

    RecyclerView nuevaorden;
Spinner nuevocliente;
 Toolbar toolbar;
NuevaOrdenesAdapter adapter;
    ArrayAdapter<String> catClientes;
    public String ClienteId = "ClienteId";
    public String TIPOS = "TIPOS";
    public String assemblies = "assemblies";
int tipo;
    int tipos;
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
tipos=0;
  if(savedInstanceState!=null){
    int indexcl=savedInstanceState.getInt("ClienteId");
      tipos=indexcl;
    nuevocliente.setSelection(indexcl);
      if(savedInstanceState.getInt("TIPOS")==1){

          int newas=savedInstanceState.getInt("ENSAMBLE");
          int[] aux=new int[savedInstanceState.getIntArray("assemblies").length+1];
          for (int i=0;i<savedInstanceState.getIntArray("assemblies").length;i++){
              aux[i]=savedInstanceState.getIntArray("assemblies")[i];
          }
          aux[savedInstanceState.getIntArray("assemblies").length]=newas;

          nuevaorden.setAdapter(new NuevaOrdenesAdapter(aux));
      }else{
         // if(savedInstanceState.getIntArray("assemblies").length>0){
              nuevaorden.setAdapter(new NuevaOrdenesAdapter(savedInstanceState.getIntArray("assemblies")));
        // }else{
        //     int[] auxxx=new int[0];
        //     nuevaorden.setAdapter(new NuevaOrdenesAdapter(auxxx));

        // }
      }
  }else{
      tipo=0;
      nuevocliente.setSelection(0);
      int[] auxxx=new int[0];
      nuevaorden.setAdapter(new NuevaOrdenesAdapter(auxxx));


  }
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
                parametros.putInt("ClienteId",nuevocliente.getSelectedItemPosition());
                if(tipos==1){
                    parametros.putInt("tipo",1);
                    parametros.putIntArray("assemblies",adapter.assemblies2);
                }else{
                  parametros.putInt("tipo",0);
                }
                customersScreen.putExtras(parametros);
                startActivity(customersScreen);
                finish();
                return true;
            case R.id.save_btn:
                if(tipos==0){
                    return true;
                }
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                builder.setMessage("Seguro que desea agregar esta orden?.")
                        .setTitle("Guardar");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        adapter.addOrder(3,adapter.assemblies2);
                        Intent customersScreen = new Intent(NuevaOrden.this, Orders.class);
                        Bundle parametros = new Bundle();
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
                          .setTitle("Eliminar assembly");
                  builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int id) {
                          adapter.deleteAssembly(item.getGroupId());
                          adapter = new NuevaOrdenesAdapter(adapter.assemblies2);
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
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("ClienteId", nuevocliente.getSelectedItemPosition());
        outState.putInt("TIPOS",tipos);
        if(tipo==0){
            outState.putInt("tipo",0);

        }else{
            outState.putInt("tipo",1);
            outState.putIntArray("assemblies",adapter.assemblies2);
        }
       }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        nuevocliente.setSelection(savedInstanceState.getInt("ClienteId"));
        tipo= savedInstanceState.getInt("tipo");
        tipos= savedInstanceState.getInt("TIPOS");
        if(tipo==1){
            adapter= new NuevaOrdenesAdapter(savedInstanceState.getIntArray("assemblies"));
            nuevaorden.setAdapter(adapter);

        }
         //Evita que se abra el edittext apenas abre el activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
}
