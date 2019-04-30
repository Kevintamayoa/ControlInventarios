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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.controlinventarios.Dao.AssembliesDao;
import com.example.controlinventarios.Dao.CustomersDao;
import com.example.controlinventarios.Dao.OrdersDao;
import com.example.controlinventarios.Dao.ProductCategoriesDao;
import com.example.controlinventarios.Dao.ProductsDao;
import com.example.controlinventarios.Dao.StatusDao;
import com.example.controlinventarios.db.AppDatabase;
import com.example.controlinventarios.db.Assemblies2;
import com.example.controlinventarios.db.Orders;

import java.text.DateFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class OrdenesAdapter extends RecyclerView.Adapter<OrdenesAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout itemProduct;
        private TextView txtClient;
        private TextView txtQuantity;
        private TextView txtPrice;
        private TextView txtDate;
        private TextView txtState;
        private Orders order;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemProduct = itemView.findViewById(R.id.ordenes_item);
            txtClient = itemView.findViewById(R.id.ordenes_client);
            txtPrice = itemView.findViewById(R.id.ordenes_price);
            txtQuantity = itemView.findViewById(R.id.ordenes_qty);
            txtDate = itemView.findViewById(R.id.ordenes_date);
            txtState = itemView.findViewById(R.id.ordenes_state);
        }

        public void bind(Orders order) {
            this.order = order;
        NumberFormat formatoImporte = NumberFormat.getCurrencyInstance();
        formatoImporte = NumberFormat.getCurrencyInstance(new Locale("en","US"));
        AppDatabase db = AppDatabase.getAppDatabase(itemView.getContext());

        final OrdersDao oDao = db.ordersDao();
        final CustomersDao cDao = db.customersDao();
        final StatusDao sDao = db.statusDao();
        String aux1 = "Precio: " +  formatoImporte.format(oDao.getPriceByOrder(order.getId())/100);
        String aux2 = "Ensambles: " + oDao.getNumAssembliesByOrder(order.getId());
        String aux3 = "Cliente: " +  cDao.getCustomerById(order.getId()).getCompleteName();
        String aux4 = "Estatus: " + sDao.getOrderStatusById(order.getStatus_id()).getDescription();
        String aux5 = "Fecha: " + order.getDate();

            txtClient.setText(aux3);
        txtPrice.setText(aux1);
        txtQuantity.setText(aux2);
        txtState.setText(aux4);
        txtDate.setText(aux5);
        }
    }

    private List<Orders> orders;
    Dialog assembliesDialog;
    private Orders order;
    public OrdenesAdapter(List<Orders> orders) {
        this.orders = orders;
    }
    Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycle_ordenes, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        context = viewGroup.getContext();

        ((Activity) viewGroup.getContext()).registerForContextMenu(view);
        viewHolder.itemProduct.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                if(orders.get(viewHolder.getAdapterPosition()).getStatus_id()==0){
                    menu.add(viewHolder.getAdapterPosition(), 0, 0, "Detalles");
                    menu.add(viewHolder.getAdapterPosition(), 1, 1, "Editar");
                    menu.add(viewHolder.getAdapterPosition(), 2, 2, "Cancelar");
                    menu.add(viewHolder.getAdapterPosition(), 3, 3, "Confirmar");
                }else if(orders.get(viewHolder.getAdapterPosition()).getStatus_id()==1){
                    menu.add(viewHolder.getAdapterPosition(), 0, 0, "Detalles");
                }
                else if(orders.get(viewHolder.getAdapterPosition()).getStatus_id()==2){
                    menu.add(viewHolder.getAdapterPosition(), 0, 0, "Detalles");
                    menu.add(viewHolder.getAdapterPosition(), 4, 4, "En tránsito");
                }
                else if(orders.get(viewHolder.getAdapterPosition()).getStatus_id()==3){
                    menu.add(viewHolder.getAdapterPosition(), 0, 0, "Detalles");
                    menu.add(viewHolder.getAdapterPosition(), 5, 5, "Finalizar");
                }else{
                    menu.add(viewHolder.getAdapterPosition(), 0, 0, "Detalles");
                }

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.bind(orders.get(i));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    Dialog detailsDialog;

    public void customerDetails(int position){
        detailsDialog = new Dialog(context);
        detailsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        detailsDialog.setContentView(R.layout.aux_showassemblies);
        ///
        TextView txtClient = detailsDialog.findViewById(R.id.ordenes_client);
        TextView txtStatus = detailsDialog.findViewById(R.id.ordenes_state);
        TextView txtPrice = detailsDialog.findViewById(R.id.ordenes_price);
        TextView txtDate = detailsDialog.findViewById(R.id.ordenes_date);
        TextView txtQty = detailsDialog.findViewById(R.id.ordenes_qty);
        TextView txtLog= detailsDialog.findViewById(R.id.ordenes_change);
        RecyclerView auxAssemblies=detailsDialog.findViewById(R.id.assemblies_recycleview2);
        AppDatabase db = AppDatabase.getAppDatabase(context);
        final AssembliesDao aDao = db.assembliesDao();
        final OrdersDao oDao = db.ordersDao();
        final CustomersDao cDao = db.customersDao();
        final StatusDao sDao = db.statusDao();
        NumberFormat formatoImporte = NumberFormat.getCurrencyInstance();
        formatoImporte = NumberFormat.getCurrencyInstance(new Locale("en","US"));
        String aux1 = "Precio: " +  formatoImporte.format(oDao.getPriceByOrder(order.getId())/100);
        String aux2 = "Ensambles: " + oDao.getNumAssembliesByOrder(order.getId());
        String aux3 = "Cliente: " +  cDao.getCustomerById(order.getId()).getCompleteName();
        String aux4 = "Estatus: " + sDao.getOrderStatusById(order.getStatus_id()).getDescription();
        String aux5 = "Fecha: " + order.getDate();
        String aux6 = "Comentarios: " + order.getChange_log();
        txtClient.setText(aux3);
        txtStatus.setText(aux4);
        txtPrice.setText(aux1);
        txtDate.setText(aux5);
        txtQty.setText(aux2);
        txtLog.setText(aux6);
        auxAssemblies.setLayoutManager(new LinearLayoutManager(context));
        auxAssemblies.setAdapter(null);
        auxAssemblies.setAdapter(new AssambliesAdapter(aDao.getAllAssemblies(""),null));//

        assembliesDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        detailsDialog.show();
    }


}
////////////////////

public class Ordenes extends AppCompatActivity {
    DatePicker inicio_date;
    DatePicker final_date;
    Spinner clients_spinner;
    Spinner state_spinner;
    CheckBox inicio_check;
    CheckBox final_check;
    Toolbar toolbar;
    RecyclerView ordenesRecycler;
    private OrdenesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenes);

        clients_spinner = findViewById(R.id.orden_client_spinner);
        state_spinner = findViewById(R.id.orden_state_spinner);
        ordenesRecycler=findViewById(R.id.orders_recycle);

        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());

        //Llenado de las categorias de filtrado clientes
        ArrayAdapter<String> catClientes = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        CustomersDao customersDao = db.customersDao();
        catClientes.add("Todos");
        catClientes.addAll(customersDao.getAllCustomerCat());
        clients_spinner.setAdapter(catClientes);
        clients_spinner.setSelection(0);

        //Llenado de las categorias de filtrado status
        ArrayAdapter<String> catStatus= new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        StatusDao statusDao = db.statusDao();
        catStatus.add("Todos");
        catStatus.addAll(statusDao.getStatusList());
        state_spinner.setAdapter(catStatus);
        state_spinner.setSelection(0);

        //Realizar el filtrado desde la creacion
        OrdersDao ordersDao  = db.ordersDao();
         ordenesRecycler.setAdapter(new OrdenesAdapter(ordersDao.getAllOrders()));
        ordenesRecycler.setLayoutManager(new LinearLayoutManager(this));

        //Declaracion del toolbar como un actionbar
        toolbar = findViewById(R.id.ordenes_toolbar);
        setSupportActionBar(toolbar);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.customer_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_btn:
                Intent newCustomer = new Intent(Ordenes.this, NuevaOrden.class);
                newCustomer.putExtra("PURPOSE",0);
                startActivity(newCustomer);
                finish();
                return true;
            case R.id.search_btn:
                AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
                OrdersDao ordersDao  = db.ordersDao();
         //      switch (busquedaspinner.getSelectedItemPosition()) {
         //          case 0:
         //              adapter = new CustomerAdapter(customersDao.getCustomersByFirstName(buscartext.getText().toString()));
         //              customerRecycler.setAdapter(adapter);
         //              return true;
         //          case 1:
         //              adapter = new CustomerAdapter(customersDao.getCustomersByLastName(buscartext.getText().toString()));
         //              customerRecycler.setAdapter(adapter);
         //              return true;
         //          case 2:
         //              adapter = new CustomerAdapter(customersDao.getCustomerByAdress(buscartext.getText().toString()));
         //              customerRecycler.setAdapter(adapter);
         //              return true;
         //          case 3:
         //              adapter = new CustomerAdapter(customersDao.getCustomerByTelephone(buscartext.getText().toString()));
         //              customerRecycler.setAdapter(adapter);
         //              return true;
         //          case 4:
         //              adapter = new CustomerAdapter(customersDao.getCustomerByEmail(buscartext.getText().toString()));
         //              customerRecycler.setAdapter(adapter);
         //              return true;
         //          default:
                       return true;
         //      }
          default:
                return true;
        }
    }
    @Override
    public boolean onContextItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case 0:
                adapter.customerDetails(item.getGroupId());
                return true;
            case 1:
                Intent editCustomer = new Intent(Ordenes.this, NuevaOrden.class);
               // editCustomer.putExtra("POSITION",item.getGroupId());
               // editCustomer.putExtra("SELECTED_FILTER",busquedaspinner.getSelectedItemPosition());
               // editCustomer.putExtra("TEXT_FILTERED",buscartext.getText().toString());
               // editCustomer.putExtra("PURPOSE",1);
               // startActivity(editCustomer);
               // finish();
                return true;
            case 2:
            //    AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //    builder.setMessage("Seguro que desea eliminar este usuario?")
            //            .setTitle("Eliminar Usuario");
            //    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            //        public void onClick(DialogInterface dialog, int id) {
            //            adapter.deleteCustomer(item.getGroupId());
            //            AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
            //            CustomersDao customersDao = db.customersDao();
            //            switch (busquedaspinner.getSelectedItemPosition()) {
            //                case 0:
            //                    adapter = new CustomerAdapter(customersDao.getCustomersByFirstName(buscartext.getText().toString()));
            //                    customerRecycler.setAdapter(adapter);
            //                    break;
            //                case 1:
            //                    adapter = new CustomerAdapter(customersDao.getCustomersByLastName(buscartext.getText().toString()));
            //                    customerRecycler.setAdapter(adapter);
            //                    break;
            //                case 2:
            //                    adapter = new CustomerAdapter(customersDao.getCustomerByAdress(buscartext.getText().toString()));
            //                    customerRecycler.setAdapter(adapter);
            //                    break;
            //                case 3:
            //                    adapter = new CustomerAdapter(customersDao.getCustomerByTelephone(buscartext.getText().toString()));
            //                    customerRecycler.setAdapter(adapter);
            //                    break;
            //                case 4:
            //                    adapter = new CustomerAdapter(customersDao.getCustomerByEmail(buscartext.getText().toString()));
            //                    customerRecycler.setAdapter(adapter);
            //                    break;
            //                default:
            //                    break;
            //            }
            //        }
            //    });
            //    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            //        public void onClick(DialogInterface dialog, int id) {
            //        }
            //    });
            //    AlertDialog alertDialog = builder.create();
            //    alertDialog.show();
                return true;
            default:
                return true;
        }
    }


}
