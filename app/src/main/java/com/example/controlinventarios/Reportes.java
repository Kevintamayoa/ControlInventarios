package com.example.controlinventarios;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Reportes extends AppCompatActivity {

    ImageButton Faltantesbtn;
    ImageButton Resumenbtn;
    ImageButton Confirmacionbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);
        Faltantesbtn=findViewById(R.id.faltantes_button);
        Faltantesbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent config = new Intent(Reportes.this, ProductosFaltantes.class);
                startActivity(config);
            }});
        Resumenbtn=findViewById(R.id.resumen_button);
        Resumenbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent config = new Intent(Reportes.this, ResumenVentas.class);
                startActivity(config);
            }});
        Confirmacionbtn=findViewById(R.id.confitmacion_button);
        Confirmacionbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent config = new Intent(Reportes.this, SimulacionDeConfirmaciones.class);
                startActivity(config);
            }});
    }
}
