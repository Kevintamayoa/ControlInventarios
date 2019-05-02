package com.example.controlinventarios;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.controlinventarios.Dao.EarningPerOrderDao;
import com.example.controlinventarios.Dao.ProductsDao;
import com.example.controlinventarios.db.AppDatabase;
import com.example.controlinventarios.db.EarningsPerOrder;
import com.example.controlinventarios.db.Products;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class SimulationCustomerAdapter extends RecyclerView.Adapter<SimulationCustomerAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout cvSimulation;
        private CardView cv;
        private TextView txtName;
        private TextView txtDate;
        private TextView txtOrderId;
        private TextView txtEarnings;

        private EarningsPerOrder earningsPerOrder;
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
                "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            cvSimulation = itemView.findViewById(R.id.recycleritem_customer_simulation);
            cv = itemView.findViewById(R.id.simulation_customer_cardview);
            txtName = itemView.findViewById(R.id.simulation_customer_name);
            txtDate = itemView.findViewById(R.id.simulation_customer_date);
            txtOrderId = itemView.findViewById(R.id.simulation_customer_orderid);
            txtEarnings = itemView.findViewById(R.id.simulation_customer_earnings);
        }

        public void bind(EarningsPerOrder earningsPerOrder) {
            this.earningsPerOrder = earningsPerOrder;
            String aux1 =
                    AppDatabase.getAppDatabase(itemView.getContext()).customersDao().
                            getCustomerById(earningsPerOrder.getCustomer_id()).getLast_name()
                            + ", " + AppDatabase.getAppDatabase(itemView.getContext()).customersDao().
                            getCustomerById(earningsPerOrder.getCustomer_id()).getFirst_name();

            txtName.setText(aux1);
            String aux2 = "Pedido hecho el dia: " + earningsPerOrder.getDate();
            txtDate.setText(aux2);
            String aux3 = "ID de pedido: " + earningsPerOrder.getId();
            txtOrderId.setText(aux3);
            NumberFormat formatoImporte = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
            String aux4 = "Monto de venta: " + formatoImporte.format(earningsPerOrder.getEarnings() / 100);
            txtEarnings.setText(aux4);
        }
    }


    int count=0;
    List<Products> products;
    List<EarningsPerOrder> earningsPerOrders;
    Context context;
    ProductsDao pDao = AppDatabase.getAppDatabase(context).productsDao();
    List<Products> orderProducts;
    List<String> smProducts = new ArrayList<>();

    public SimulationCustomerAdapter(List<EarningsPerOrder> earningsPerOrders, List<Products> products) {
        this.products = products;
        this.earningsPerOrders = earningsPerOrders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycleritem_simulation_customer, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        context = viewGroup.getContext();
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.bind(earningsPerOrders.get(i));
        orderProducts = new ArrayList<>();
        orderProducts = pDao.getAllProductsByOrder(earningsPerOrders.get(i).getId());
        StringBuffer missProducts = new StringBuffer("");
        for (Products auxproducts2 : orderProducts) {
            for (Products auxproducts : products) {
                if (auxproducts2.getId() == auxproducts.getId()) {
                    if (auxproducts.getQty() - auxproducts2.getQty() < 0) {
                        count++;
                        String tmp = auxproducts.getDescription()+"ç";
                        missProducts.append(tmp);
                    }
                    auxproducts.setQty(auxproducts.getQty() - auxproducts2.getQty());
                }
            }
        }
        String aux =""+count;
        Toast.makeText(context,aux , Toast.LENGTH_SHORT).show();
        smProducts.add(missProducts.toString());
        if (count == 0) {
            count=0;
            viewHolder.cv.setCardBackgroundColor(Color.GREEN);
        }
        if (count>=orderProducts.size()) {
            count=0;
            viewHolder.cv.setCardBackgroundColor(Color.RED);
        }
        if (count<orderProducts.size() &&count>0){
            count=0;
            viewHolder.cv.setCardBackgroundColor(Color.YELLOW);
        }
        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Productos faltantes", Toast.LENGTH_LONG).show();
                String[] mProdcuts = smProducts.get(viewHolder.getAdapterPosition()).split("ç");
                for(String string : mProdcuts){
                    if(!string.equals("")){
                        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return earningsPerOrders.size();
    }
}

public class SimulacionDeConfirmaciones extends AppCompatActivity {

    Toolbar toolbar;
    Spinner optionsSpinner;
    RecyclerView confirmationRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulacion_de_confirmaciones);
        toolbar = findViewById(R.id.simulation_toolbar);
        setSupportActionBar(toolbar);
        //RecyclerView
        confirmationRecycler = findViewById(R.id.confirmation_recyclerview);
        confirmationRecycler.setLayoutManager(new LinearLayoutManager(this));
        //Spinner
        optionsSpinner = findViewById(R.id.confirmation_spinner);
        final ArrayAdapter<String> optFiltrado = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        optFiltrado.addAll("Procesar por cliente", "Procesar por fecha", "Procesar por monto de venta");
        optionsSpinner.setAdapter(optFiltrado);
        optionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        confirmationRecycler.setAdapter(new SimulationCustomerAdapter(AppDatabase.getAppDatabase(getApplicationContext())
                                .earningsPerOrderDao().getEarningsPerPendingOrderOrderedByName(),
                                AppDatabase.getAppDatabase(getApplicationContext()).productsDao().getAllProducts()));
                        break;
                    case 1:
                        confirmationRecycler.setAdapter(new SimulationCustomerAdapter(AppDatabase.getAppDatabase(getApplicationContext())
                                .earningsPerOrderDao().getEarningsPerPendingOrderOrderedByDate(),
                                AppDatabase.getAppDatabase(getApplicationContext()).productsDao().getAllProducts()));
                        break;
                    case 2:
                        confirmationRecycler.setAdapter(new SimulationCustomerAdapter(AppDatabase.getAppDatabase(getApplicationContext())
                                .earningsPerOrderDao().getEarningsPerPendingOrderOrderedByEarnings(),
                                AppDatabase.getAppDatabase(getApplicationContext()).productsDao().getAllProducts()));
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
