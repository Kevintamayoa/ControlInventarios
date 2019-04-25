package com.example.controlinventarios;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.controlinventarios.Dao.AssemblyProductsDao;
import com.example.controlinventarios.Dao.ProductCategoriesDao;
import com.example.controlinventarios.Dao.ProductsDao;
import com.example.controlinventarios.db.AppDatabase;
import com.example.controlinventarios.db.Assemblies;
import com.example.controlinventarios.db.AssemblyProducts;
import com.example.controlinventarios.db.Products;

import java.text.Format;
import java.util.List;

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
            String aux1 = "Precio: $" +  assembly.getcost()/100;
            String aux2 = "Productos: " + assembly.getnum_products();
            txtDescription.setText(assembly.getDescription());
            txtPrice.setText(aux1);
            txtQuantity.setText(aux2);
        }
    }

    private List<Assemblies> assemblies;
    Dialog assembliesDialog;

    public AssambliesAdapter( List<Assemblies> assemblies) {
        this.assemblies = assemblies;
    }
    ProductsDao pDao;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i, final AppDatabase db) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycle_assemblies, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        final Context context = viewGroup.getContext();
        assembliesDialog = new Dialog(context);
        assembliesDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        assembliesDialog.setContentView(R.layout.aux_showproduct);
        viewHolder.itemProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView productosrecycler = assembliesDialog.findViewById(R.id.productos_recycleview);
                final AssemblyProductsDao productsDao = db.assemblyProductsDao();
                productosrecycler.setAdapter(new ProductsAdapter(pDao, productsDao.getAllAssemblyProductsByAssembly(i)));
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ensambles);
    }
}
