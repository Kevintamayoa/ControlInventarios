package com.example.controlinventarios;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.util.Log;
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
import android.widget.CompoundButton;
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
import com.example.controlinventarios.db.Customers;
import com.example.controlinventarios.db.Orders;

import java.io.Console;
import java.text.DateFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
        String aux3 = "Cliente: " +  cDao.getCustomerById(order.getCustomer_id()).getCompleteName();
        String aux4 = "Estatus: " + sDao.getOrderStatusById(order.getStatus_id()).getDescription();
        String aux5 = "Fecha: " + order.getDate();

            txtClient.setText(aux3);
        txtPrice.setText(aux1);
        txtQuantity.setText(aux2);
        txtState.setText(aux4);
        txtDate.setText(aux5);
        }
    }
    List<Customers> customers;
    private List<Orders> orders;
    Dialog assembliesDialog;
    private Orders order;
    public OrdenesAdapter(List<Orders> orders,List<Customers> customers) {
        this.orders = orders;
        this.customers=customers;
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
                    menu.add(viewHolder.getAdapterPosition(), 4, 1, "En tránsito");
                }
                else if(orders.get(viewHolder.getAdapterPosition()).getStatus_id()==3){
                    menu.add(viewHolder.getAdapterPosition(), 0, 0, "Detalles");
                    menu.add(viewHolder.getAdapterPosition(), 5, 1, "Finalizar");
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
    List<Assemblies2> assemblies2;

    public void ordensDetails(int position){
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
    AppDatabase db = AppDatabase.getAppDatabase(detailsDialog.getContext());
    final AssembliesDao aDao = db.assembliesDao();
   final OrdersDao oDao = db.ordersDao();
        final ProductsDao pDao = db.productsDao();
    final CustomersDao cDao = db.customersDao();
    final StatusDao sDao = db.statusDao();
    NumberFormat formatoImporte = NumberFormat.getCurrencyInstance();
    formatoImporte = NumberFormat.getCurrencyInstance(new Locale("en","US"));
    String aux1 = "Precio: " +  formatoImporte.format(oDao.getPriceByOrder(orders.get(position).getId())/100);
    String aux2 = "Ensambles: " + oDao.getNumAssembliesByOrder(orders.get(position).getId());
     String aux3 = "Cliente: " +  cDao.getCustomerById(orders.get(position).getCustomer_id()).getCompleteName();
     String aux4 = "Estatus: " + sDao.getOrderStatusById(orders.get(position).getStatus_id()).getDescription();
     String aux5 = "Fecha: " + orders.get(position).getDate();
     String aux6 = "Comentarios: " + orders.get(position).getChange_log();
     txtClient.setText(aux3);
     txtStatus.setText(aux4);
     txtPrice.setText(aux1);
     txtDate.setText(aux5);
     txtQty.setText(aux2);
     txtLog.setText(aux6);
  auxAssemblies.setLayoutManager(new LinearLayoutManager(detailsDialog.getContext()));
  auxAssemblies.setAdapter(new AssambliesAdapter(aDao.getAllAssembliesByOrder(orders.get(position).getId())));//

  //  assembliesDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        detailsDialog.show();
    }
    public void cancelOrden(final int position) {
        Orders order=orders.get(position);
        order.setStatus_id(1);
        AppDatabase.getAppDatabase(context).ordersDao().UpdateOrder(order);
    }
    public void confirmarOrden(final int position) {
        Orders order=orders.get(position);
        order.setStatus_id(2);
        AppDatabase.getAppDatabase(context).ordersDao().UpdateOrder(order);
    }
    public void transitoOrden(final int position) {
        Orders order=orders.get(position);
        order.setStatus_id(3);
        AppDatabase.getAppDatabase(context).ordersDao().UpdateOrder(order);
    }
    public void finalizarOrden(final int position) {
        Orders order=orders.get(position);
        order.setStatus_id(4);
        AppDatabase.getAppDatabase(context).ordersDao().UpdateOrder(order);
    }

}
////////////////////

public class Ordenes extends AppCompatActivity {

    private static final String CERO = "0";
    private static final String BARRA = "/";
    public String ClienteId = "ClienteId";
    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);



    //Widgets
    EditText inicio_date_text;
    ImageButton inicio_date;
    EditText final_date_text;
    ImageButton final_date;
    Spinner clients_spinner;
    Spinner state_spinner;
    CheckBox inicio_check;
    CheckBox final_check;
    Toolbar toolbar;
    RecyclerView ordenesRecycler;
    private OrdenesAdapter adapter;
    ArrayAdapter<String> catClientes;
    MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenes);
//Evita que se abra el edittext apenas abre el activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        clients_spinner = findViewById(R.id.orden_client_spinner);
        state_spinner = findViewById(R.id.orden_state_spinner);
        ordenesRecycler=findViewById(R.id.orders_recycle);

        inicio_date= findViewById(R.id.inicio_date);
        inicio_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        obtenerFecha();

            }
        });
        inicio_date_text=  findViewById(R.id.inicio_date_text);
        inicio_check=findViewById(R.id.inicio_check);
        inicio_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!compoundButton.isChecked()){
                   inicio_date_text.setText("");
                }
            }
        }) ;
        final_date= findViewById(R.id.final_date);
        final_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                obtenerFecha2();

            }
        });
        final_date_text=  findViewById(R.id.final_date_text);
        final_check=findViewById(R.id.final_check);
        final_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!compoundButton.isChecked()){
                    final_date_text.setText("");
                }
            }
        }) ;
        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());


           //Llenado de las categorias de filtrado clientes
        catClientes = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);


        CustomersDao customersDao = db.customersDao();
        catClientes.add("Todos");
        catClientes.addAll(customersDao.getAllCustomerCat());
        clients_spinner.setAdapter(catClientes);
        clients_spinner.setSelection(0);

        //Llenado de las categorias de filtrado status
        ArrayAdapter<String> catStatus= new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        StatusDao statusDao = db.statusDao();
        catStatus.add("Selecciona los estatus:");
        catStatus.addAll(statusDao.getStatusList());
       // state_spinner.setAdapter(catStatus);
       // state_spinner.setSelection(0);

        ArrayList<StateVO> listVOs = new ArrayList<>();

        for (int i = 0; i < catStatus.getCount(); i++) {
            StateVO stateVO = new StateVO();
            stateVO.setTitle(catStatus.getItem(i));
            if(i==0){
                stateVO.setSelected(false);
            }else{            stateVO.setSelected(true);}
            listVOs.add(stateVO);
        }
        myAdapter = new MyAdapter(Ordenes.this, 0,
                listVOs);
        state_spinner.setAdapter(myAdapter);

        //Realizar el filtrado desde la creacion
        OrdersDao ordersDao  = db.ordersDao();

        adapter =new OrdenesAdapter(ordersDao.getAllOrders(),customersDao.getAllCustomer());
        ordenesRecycler.setAdapter(adapter);
        ordenesRecycler.setLayoutManager(new LinearLayoutManager(this));

        //Declaracion del toolbar como un actionbar
        toolbar = findViewById(R.id.ordenes_toolbar);
        setSupportActionBar(toolbar);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Cliente", clients_spinner.getSelectedItemPosition());
        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        OrdersDao ordersDao  = db.ordersDao();
        CustomersDao customersDao  = db.customersDao();
        int o = 0;
        ArrayList<Integer> aux = new ArrayList<Integer>();
        for (StateVO obj :  myAdapter.listState) {
            if (obj.isSelected()) {
                aux.add(o);
            }
            o++;
        }
        int[] aux2=new int[aux.size()];
        for (int i=0;i<aux.size();i++){
            aux2[i]=aux.get(i);
        }
        outState.putIntArray("Opciones",aux2);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());

        clients_spinner.setSelection(savedInstanceState.getInt("Cliente"));
        int[] aux2=savedInstanceState.getIntArray("Opciones");
       // int o = 0;
       // for (StateVO obj :  myAdapter.listState) {
       //     if (obj.isSelected()) {
       //         aux.add(o);
       //     }
       //     o++;
       // }

        ArrayList<StateVO> listVOs = new ArrayList<>();
        ArrayAdapter<String> catStatus= new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        StatusDao statusDao = db.statusDao();
        catStatus.add("Selecciona los estatus:");
        catStatus.addAll(statusDao.getStatusList());
        for (int i = 0; i < catStatus.getCount(); i++) {
            StateVO stateVO = new StateVO();
            stateVO.setTitle(catStatus.getItem(i));
            stateVO.setSelected(false);
            for(int j=0;j<aux2.length;j++){
                if(aux2[j]==i){
                    stateVO.setSelected(true);
            }}


            listVOs.add(stateVO);
        }
        myAdapter = new MyAdapter(Ordenes.this, 0,
                listVOs);
        state_spinner.setAdapter(myAdapter);
        OrdersDao ordersDao  = db.ordersDao();
        CustomersDao customersDao  = db.customersDao();



        if(clients_spinner.getSelectedItemPosition()==0&&!final_check.isChecked()&&!inicio_check.isChecked()){

            adapter =new OrdenesAdapter(ordersDao.getAllOrdersByStatusId(aux2),customersDao.getAllCustomer());
            ordenesRecycler.setAdapter(adapter);
        }else if(!final_check.isChecked()&&!inicio_check.isChecked()){
            adapter =new OrdenesAdapter(ordersDao.getAllOrdersByStatusId(aux2,adapter.customers.get(clients_spinner.getSelectedItemPosition()-1).getId()),customersDao.getAllCustomer());
            ordenesRecycler.setAdapter(adapter);
        }
        else{
            if(!final_check.isChecked()){

            }else if(!inicio_check.isChecked()){

            }else{

            }
        }
        //Evita que se abra el edittext apenas abre el activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
    private void obtenerFecha(){
        if(!inicio_check.isChecked()){
            return;
        }
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                inicio_date_text.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }
    private void obtenerFecha2(){
        if(!final_check.isChecked()){
            return;
        }
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                final_date_text.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

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
                Bundle parametros = new Bundle();
                 parametros.putInt("ClienteId",0);
                newCustomer.putExtras(parametros);
                startActivity(newCustomer);
                finish();
                return true;
            case R.id.search_btn:
                AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
                OrdersDao ordersDao  = db.ordersDao();
                CustomersDao customersDao  = db.customersDao();
                int o = 0;
                ArrayList<Integer> aux = new ArrayList<Integer>();
                for (StateVO obj :  myAdapter.listState) {
                    if (obj.isSelected()) {
                        aux.add(o-1);
                    }
                    o++;
                }
                int[] aux2=new int[aux.size()];
                for (int i=0;i<aux.size();i++){
                    aux2[i]=aux.get(i);
                }


               if(clients_spinner.getSelectedItemPosition()==0&&!final_check.isChecked()&&!inicio_check.isChecked()){

                   adapter =new OrdenesAdapter(ordersDao.getAllOrdersByStatusId(aux2),customersDao.getAllCustomer());
                   ordenesRecycler.setAdapter(adapter);
               }else if(!final_check.isChecked()&&!inicio_check.isChecked()){
                   adapter =new OrdenesAdapter(ordersDao.getAllOrdersByStatusId(aux2,adapter.customers.get(clients_spinner.getSelectedItemPosition()-1).getId()),customersDao.getAllCustomer());
                   ordenesRecycler.setAdapter(adapter);
               }
               else{
                   if(!final_check.isChecked()){

                   }else if(!inicio_check.isChecked()){

                   }else{

                   }
               }
               ordenesRecycler.setAdapter(adapter);
                return true;
          default:
                return true;
        }
    }
    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        AlertDialog.Builder builder;
        AlertDialog alertDialog;
        switch (item.getItemId()) {
            case 0:
                adapter.ordensDetails(item.getGroupId());
                return true;
            case 1:

                return true;
            case 2:
                builder = new AlertDialog.Builder(this);
               builder.setMessage("Seguro que desea cancelar esta orden?")
                       .setTitle("Cancelar orden");
               builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       adapter.cancelOrden(item.getGroupId());
                       ordenesRecycler.setAdapter(adapter);

                   }
               });
               builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                   }
               });
               alertDialog = builder.create();
               alertDialog.show();
                return true;
            case 3:
                builder = new AlertDialog.Builder(this);
                builder.setMessage("Seguro que desea confirmar esta orden?")
                        .setTitle("Confirmar orden");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        adapter.confirmarOrden(item.getGroupId());
                        ordenesRecycler.setAdapter(adapter);
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
                return true;
            case 4:
                builder = new AlertDialog.Builder(this);
                builder.setMessage("Seguro que desea poner en tránsito esta orden?")
                        .setTitle("Tránsito de orden");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        adapter.transitoOrden(item.getGroupId());
                        ordenesRecycler.setAdapter(adapter);
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
                return true;
            case 5:
                builder = new AlertDialog.Builder(this);
                builder.setMessage("Seguro que terminar esta orden?")
                        .setTitle("Término de orden");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        adapter.finalizarOrden(item.getGroupId());
                        ordenesRecycler.setAdapter(adapter);
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
                return true;
            default:
                return true;
        }
    }

    public class StateVO {
        private String title;
        private boolean selected;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }
    public class MyAdapter extends ArrayAdapter<StateVO> {
        private Context mContext;
        private ArrayList<StateVO> listState;
        private MyAdapter myAdapter;
        private boolean isFromView = false;

        public MyAdapter(Context context, int resource, List<StateVO> objects) {
            super(context, resource, objects);
            this.mContext = context;
            this.listState = (ArrayList<StateVO>) objects;
            this.myAdapter = this;
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(final int position, View convertView,
                                  ViewGroup parent) {

            final ViewHolder holder;
            if (convertView == null) {
                LayoutInflater layoutInflator = LayoutInflater.from(mContext);
                convertView = layoutInflator.inflate(R.layout.spinner_item, null);
                holder = new ViewHolder();
                holder.mTextView = (TextView) convertView
                        .findViewById(R.id.text_spinner_item);
                holder.mCheckBox = (CheckBox) convertView
                        .findViewById(R.id.checkbox_spinner_item);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.mTextView.setText(listState.get(position).getTitle());

            // To check weather checked event fire from getview() or user input
            isFromView = true;
            holder.mCheckBox.setChecked(listState.get(position).isSelected());
            isFromView = false;

            if ((position == 0)) {
                holder.mCheckBox.setVisibility(View.INVISIBLE);
            } else {
                holder.mCheckBox.setVisibility(View.VISIBLE);
            }
            holder.mCheckBox.setTag(position);
            holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();

                    if (!isFromView) {
                        if(!isChecked){
                            listState.get(position).setSelected(false);

                        }else {
                            listState.get(position).setSelected(true);
                        }
                        }
                }
            });

            return convertView;
        }

        private class ViewHolder {
            private TextView mTextView;
            private CheckBox mCheckBox;
        }
    }
}

