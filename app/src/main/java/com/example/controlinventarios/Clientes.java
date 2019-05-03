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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.controlinventarios.Dao.CustomersDao;
import com.example.controlinventarios.Dao.ProductCategoriesDao;
import com.example.controlinventarios.Dao.StatusDao;
import com.example.controlinventarios.db.AppDatabase;
import com.example.controlinventarios.db.Customers;
import com.example.controlinventarios.db.Products;

import java.util.ArrayList;
import java.util.List;

class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout customerItem;
        private TextView txtName;
        private TextView txtPhone1;
        private TextView txtPhone2;
        private TextView txtPhone3;
        private TextView txtEmail;
        private TextView txtAdress;

        private Customers customer;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            customerItem = itemView.findViewById(R.id.customer_item);
            txtName = itemView.findViewById(R.id.customer_name);
            txtPhone1 = itemView.findViewById(R.id.customer_phone1);
            txtPhone2 = itemView.findViewById(R.id.customer_phone2);
            txtPhone3 = itemView.findViewById(R.id.customer_phone3);
            txtEmail = itemView.findViewById(R.id.customer_email);
            txtAdress = itemView.findViewById(R.id.customer_adress);
        }

        public void bind(Customers customer) {
            this.customer = customer;
            String name = customer.getLast_name() + ", " + customer.getFirst_name();
            txtName.setText(name);
            if (customer.getPhone1() != null) {
                txtPhone1.setText(customer.getPhone1());
            }
            if (customer.getE_mail() != null) {
                txtEmail.setText(customer.getE_mail());
            }
            txtPhone2.setHeight(0);
            txtPhone3.setHeight(0);
            txtAdress.setHeight(0);
        }
    }

    Context context;
    List<Customers> customers;
    Dialog detailsDialog;

    public CustomerAdapter(List<Customers> customers) {
        this.customers = customers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycle_customers, viewGroup, false);
        context = viewGroup.getContext();
        ((Activity) viewGroup.getContext()).registerForContextMenu(view);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.customerItem.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(viewHolder.getAdapterPosition(), 0, 0, "Detalles");
                menu.add(viewHolder.getAdapterPosition(), 1, 1, "Editar");
                menu.add(viewHolder.getAdapterPosition(), 2, 2, "Eliminar");
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(customers.get(i));
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public void customerDetails(int position) {
        detailsDialog = new Dialog(context);
        detailsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        detailsDialog.setContentView(R.layout.recycle_customers);
        TextView txtName = detailsDialog.findViewById(R.id.customer_name);
        TextView txtPhone1 = detailsDialog.findViewById(R.id.customer_phone1);
        TextView txtPhone2 = detailsDialog.findViewById(R.id.customer_phone2);
        TextView txtPhone3 = detailsDialog.findViewById(R.id.customer_phone3);
        TextView txtEmail = detailsDialog.findViewById(R.id.customer_email);
        TextView txtAdress = detailsDialog.findViewById(R.id.customer_adress);
        String name = customers.get(position).getLast_name() + ", " + customers.get(position).getFirst_name();
        txtName.setText(name);
        if (customers.get(position).getPhone1() != null) {
            txtPhone1.setText(customers.get(position).getPhone1());
            if (customers.get(position).getPhone2() != null) {
                txtPhone2.setText(customers.get(position).getPhone2());
                if (customers.get(position).getPhone3() != null) {
                    txtPhone3.setText(customers.get(position).getPhone3());
                } else {
                    txtPhone3.setHeight(0);
                }
            } else {
                txtPhone2.setHeight(0);
                txtPhone3.setHeight(0);
            }
        } else {
            txtPhone2.setHeight(0);
            txtPhone3.setHeight(0);
        }
        if (customers.get(position).getE_mail() != null) {
            txtEmail.setText(customers.get(position).getE_mail());
        }
        txtAdress.setText(customers.get(position).getAddress());
        detailsDialog.show();
    }

    public void deleteCustomer(final int position) {
        AppDatabase.getAppDatabase(context).customersDao().deleteCustomer(customers.get(position));
    }
}

public class Clientes extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView customerRecycler;
    EditText buscartext;
    Spinner busquedaspinner;
    private CustomerAdapter adapter;


    //El customer Adapter que es el problema del query tan solo necesita obtener una lista de clientes de acuerdo a lo que se busque
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);
        buscartext = findViewById(R.id.buscar_text);
        busquedaspinner = findViewById(R.id.customer_spinner);
        customerRecycler = findViewById(R.id.customer_recyclerview);
        //Evita que se abra el edittext apenas abre el activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //Declaracion del toolbar como un actionbar
        toolbar = findViewById(R.id.customer_toolbar);
        setSupportActionBar(toolbar);
        //hace que la entrada de enter en el teclado sea un return
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
        //Llenado de las categorias de filtrado
        ArrayAdapter<String> catBusqueda = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        catBusqueda.addAll(/*"Selecciona los filtros:", */"Nombre", "Apellido", "Dirección", "Telefono", "E-mail");
        /*
        ArrayList<StateVO> listVOs = new ArrayList<>();

        for (int i = 0; i < catBusqueda.getCount(); i++) {
            StateVO stateVO = new StateVO();
            stateVO.setTitle(catBusqueda.getItem(i));
            if(i==0){
                stateVO.setSelected(false);
            }else{
                stateVO.setSelected(true);
            }
            listVOs.add(stateVO);
        }
        myAdapter = new MyAdapter(this, 0, listVOs);*/
        busquedaspinner.setAdapter(catBusqueda);
        busquedaspinner.setSelection(0);
        //Realizar el filtrado desde la creacion
        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        CustomersDao customersDao = db.customersDao();
        switch (busquedaspinner.getSelectedItemPosition()) {
            case 0:
                adapter = new CustomerAdapter(customersDao.getCustomersByFirstName(buscartext.getText().toString()));
                customerRecycler.setAdapter(adapter);
                break;
            case 1:
                adapter = new CustomerAdapter(customersDao.getCustomersByLastName(buscartext.getText().toString()));
                customerRecycler.setAdapter(adapter);
                break;
            case 2:
                adapter = new CustomerAdapter(customersDao.getCustomerByAdress(buscartext.getText().toString()));
                customerRecycler.setAdapter(adapter);
                break;
            case 3:
                adapter = new CustomerAdapter(customersDao.getCustomerByTelephone(buscartext.getText().toString()));
                customerRecycler.setAdapter(adapter);
                break;
            case 4:
                adapter = new CustomerAdapter(customersDao.getCustomerByEmail(buscartext.getText().toString()));
                customerRecycler.setAdapter(adapter);
                break;
            default:
                break;
        }
        customerRecycler.setLayoutManager(new LinearLayoutManager(this));
        customerRecycler.setAdapter(adapter);
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
                Intent newCustomer = new Intent(Clientes.this, NuevoCliente.class);
                newCustomer.putExtra("PURPOSE", 0);
                startActivity(newCustomer);
                finish();
                return true;
            case R.id.search_btn:
                AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
                CustomersDao customersDao = db.customersDao();
                switch (busquedaspinner.getSelectedItemPosition()) {
                    case 0:
                        adapter = new CustomerAdapter(customersDao.getCustomersByFirstName(buscartext.getText().toString()));
                        customerRecycler.setAdapter(adapter);
                        return true;
                    case 1:
                        adapter = new CustomerAdapter(customersDao.getCustomersByLastName(buscartext.getText().toString()));
                        customerRecycler.setAdapter(adapter);
                        return true;
                    case 2:
                        adapter = new CustomerAdapter(customersDao.getCustomerByAdress(buscartext.getText().toString()));
                        customerRecycler.setAdapter(adapter);
                        return true;
                    case 3:
                        adapter = new CustomerAdapter(customersDao.getCustomerByTelephone(buscartext.getText().toString()));
                        customerRecycler.setAdapter(adapter);
                        return true;
                    case 4:
                        adapter = new CustomerAdapter(customersDao.getCustomerByEmail(buscartext.getText().toString()));
                        customerRecycler.setAdapter(adapter);
                        return true;
                    default:
                        return true;
                }
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
                Intent editCustomer = new Intent(Clientes.this, NuevoCliente.class);
                editCustomer.putExtra("POSITION", item.getGroupId());
                editCustomer.putExtra("SELECTED_FILTER", busquedaspinner.getSelectedItemPosition());
                editCustomer.putExtra("TEXT_FILTERED", buscartext.getText().toString());
                editCustomer.putExtra("PURPOSE", 1);
                startActivity(editCustomer);
                finish();
                return true;
            case 2:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Seguro que desea eliminar este usuario?")
                        .setTitle("Eliminar Usuario");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        adapter.deleteCustomer(item.getGroupId());
                        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
                        CustomersDao customersDao = db.customersDao();
                        switch (busquedaspinner.getSelectedItemPosition()) {
                            case 0:
                                adapter = new CustomerAdapter(customersDao.getCustomersByFirstName(buscartext.getText().toString()));
                                customerRecycler.setAdapter(adapter);
                                break;
                            case 1:
                                adapter = new CustomerAdapter(customersDao.getCustomersByLastName(buscartext.getText().toString()));
                                customerRecycler.setAdapter(adapter);
                                break;
                            case 2:
                                adapter = new CustomerAdapter(customersDao.getCustomerByAdress(buscartext.getText().toString()));
                                customerRecycler.setAdapter(adapter);
                                break;
                            case 3:
                                adapter = new CustomerAdapter(customersDao.getCustomerByTelephone(buscartext.getText().toString()));
                                customerRecycler.setAdapter(adapter);
                                break;
                            case 4:
                                adapter = new CustomerAdapter(customersDao.getCustomerByEmail(buscartext.getText().toString()));
                                customerRecycler.setAdapter(adapter);
                                break;
                            default:
                                break;
                        }
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
    /*
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
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(final int position, View convertView, ViewGroup parent) {
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
                        if (!isChecked) {
                            listState.get(position).setSelected(false);

                        } else {
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
    */
}
