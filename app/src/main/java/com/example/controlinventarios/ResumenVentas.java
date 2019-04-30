package com.example.controlinventarios;

import android.app.Dialog;
import android.content.Context;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.controlinventarios.Dao.CustomersDao;
import com.example.controlinventarios.Dao.ProductsDao;
import com.example.controlinventarios.db.AppDatabase;
import com.example.controlinventarios.db.Customers;
import com.example.controlinventarios.db.EarningsPerOrder;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class BriefingDetailsAdapter extends RecyclerView.Adapter<BriefingDetailsAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout cvDetailsLL;
        private CardView cvDetailsEarnings;
        private TextView txtName;
        private TextView txtDate;
        private TextView txtEarning;

        EarningsPerOrder earningsPerOrder;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            cvDetailsLL = itemView.findViewById(R.id.cvDetailsLinearLayout);
            cvDetailsEarnings = itemView.findViewById(R.id.briefing_details_cardview);
            txtName = itemView.findViewById(R.id.briefing_details_customer);
            txtDate = itemView.findViewById(R.id.briefing_details_date);
            txtEarning = itemView.findViewById(R.id.briefing_details_earnings);
        }

        public void bind(EarningsPerOrder earningsPerOrder) {
            this.earningsPerOrder = earningsPerOrder;
            Customers customer = AppDatabase.getAppDatabase(itemView.getContext()).customersDao().getCustomerById(earningsPerOrder.getCustomer_id());
            String aux1 = customer.getLast_name()+", "+customer.getFirst_name();
            txtName.setText(aux1);
            String aux2 = "Pedido hecho el dia:\n"+earningsPerOrder.getDate();
            txtDate.setText(aux2);
            NumberFormat formatoImporte = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
            String aux3 = "Ganancias: "+formatoImporte.format(earningsPerOrder.getEarnings()/100);
            txtEarning.setText(aux3);
        }
    }

    List<EarningsPerOrder> earningsPerOrders;

    public BriefingDetailsAdapter(List<EarningsPerOrder> earningsPerOrders) {
        this.earningsPerOrders = earningsPerOrders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.briefing_details_recycler, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        final Context context = viewGroup.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.bind(earningsPerOrders.get(i));
    }

    @Override
    public int getItemCount() {
        return earningsPerOrders.size();
    }
}

class BriefingAdapter extends RecyclerView.Adapter<BriefingAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout cvLL;
        private CardView cv;
        private TextView txtYear;
        private TextView txtMonth;
        private TextView txtEarning;

        private MonthlyEarnings monthlyEarnings;
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
                "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            cvLL = itemView.findViewById(R.id.cvLinearLayout);
            cv = itemView.findViewById(R.id.briefing_details_cardview);
            txtYear = itemView.findViewById(R.id.briefing_year);
            txtMonth = itemView.findViewById(R.id.briefing_month);
            txtEarning = itemView.findViewById(R.id.briefing_earning);
        }

        public void bind(MonthlyEarnings monthlyEarnings) {
            this.monthlyEarnings = monthlyEarnings;
            txtYear.setText(monthlyEarnings.getYear());
            txtMonth.setText(meses[Integer.parseInt(monthlyEarnings.getMonth())-1]);
            NumberFormat formatoImporte = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
            String aux1 = "Ganancias mensuales: " + formatoImporte.format(monthlyEarnings.getEarnings() / 100);
            txtEarning.setText(aux1);
        }
    }
    Context context;
    List<MonthlyEarnings> monthlyEarnings;
    List<EarningsPerOrder> earningsPerOrders;
    Dialog sellsDialog;

    public BriefingAdapter(List<MonthlyEarnings> monthlyEarnings, List<EarningsPerOrder> earningsPerOrders) {
        this.monthlyEarnings = monthlyEarnings;
        this.earningsPerOrders = earningsPerOrders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_monthly_briefing, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        context = viewGroup.getContext();

        sellsDialog = new Dialog(context);
        sellsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sellsDialog.setContentView(R.layout.detail_briefing_layout);

        viewHolder.cvLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<EarningsPerOrder> auxEarningsPerOrders;
                RecyclerView auxRecyclerView = sellsDialog.findViewById(R.id.detail_briefing_recycler);
                auxEarningsPerOrders = getAuxEarningsPerOrders(earningsPerOrders,monthlyEarnings.get(viewHolder.getAdapterPosition()).getMonth(),
                        monthlyEarnings.get(viewHolder.getAdapterPosition()).getYear());
                auxRecyclerView.setLayoutManager(new LinearLayoutManager(sellsDialog.getContext()));
                auxRecyclerView.setAdapter(null);
                auxRecyclerView.setAdapter(new BriefingDetailsAdapter(auxEarningsPerOrders));
                sellsDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                sellsDialog.show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.bind(monthlyEarnings.get(i));
    }

    @Override
    public int getItemCount() {
        return monthlyEarnings.size();
    }

    public List<EarningsPerOrder> getAuxEarningsPerOrders(List<EarningsPerOrder> earningsPerOrderLis, String month, String year){
        List<EarningsPerOrder> auxEarningsPerOrders1 = new ArrayList<>();
        for(EarningsPerOrder earningsPerOrder : earningsPerOrderLis){
            if(earningsPerOrder.getDate().split("-")[1].equals(month) &&
                    earningsPerOrder.getDate().split("-")[2].equals(year)){
                auxEarningsPerOrders1.add(earningsPerOrder);
            }
        }
        return auxEarningsPerOrders1;
    }
}

public class ResumenVentas extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView mesesDelAnio;
    List<EarningsPerOrder> earningsPerOrders;
    List<MonthlyEarnings> monthlyEarnings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_ventas);
        toolbar = findViewById(R.id.sold_briefing);
        earningsPerOrders = AppDatabase.getAppDatabase(this).earningsPerOrderDao().getEarningsPerOrder();
        monthlyEarnings = new ArrayList<>();
        int counter = 0;
        for (EarningsPerOrder earningsPerOrder : earningsPerOrders) {
            int count = 0;
            for (MonthlyEarnings monthlyEarning : monthlyEarnings) {
                if (monthlyEarning.getMonth().equals(earningsPerOrder.getDate().split("-")[1]) &&
                        monthlyEarning.getYear().equals(earningsPerOrder.getDate().split("-")[2])) {
                    count++;
                }
            }
            if (count <= 0) {
                monthlyEarnings.add(new MonthlyEarnings("" + counter, earningsPerOrder.getDate().split("-")[1],
                        earningsPerOrder.getDate().split("-")[2], 0));
                counter++;
            }
        }
        for (MonthlyEarnings monthlyEarning : monthlyEarnings) {
            int earnings = 0;
            for (EarningsPerOrder earningsPerOrder : earningsPerOrders) {
                if (monthlyEarning.getMonth().equals(earningsPerOrder.getDate().split("-")[1]) &&
                        monthlyEarning.getYear().equals(earningsPerOrder.getDate().split("-")[2])) {
                    earnings = earnings + earningsPerOrder.getEarnings();
                }
            }
            monthlyEarning.setEarnings(earnings);
        }
        mesesDelAnio = findViewById(R.id.sells_recyclerview);
        mesesDelAnio.setLayoutManager(new LinearLayoutManager(this));
        mesesDelAnio.setAdapter(new BriefingAdapter(monthlyEarnings,earningsPerOrders));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

class MonthlyEarnings {
    private String day;
    private String month;
    private String year;
    private int earnings;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getEarnings() {
        return earnings;
    }

    public void setEarnings(int earnings) {
        this.earnings = earnings;
    }

    public MonthlyEarnings(String day, String month, String year, int earnings) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.earnings = earnings;
    }
}
