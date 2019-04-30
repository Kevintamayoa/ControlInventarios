package com.example.controlinventarios;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

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
        optionsSpinner = findViewById(R.id.confirmation_spinner);
        confirmationRecycler = findViewById(R.id.confirmation_recyclerview);
        final ArrayAdapter<String> optFiltrado = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        optFiltrado.addAll("Procesar por cliente", "Procesar por fecha", "Procesar por monto de venta");
        optionsSpinner.setAdapter(optFiltrado);
        optionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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
