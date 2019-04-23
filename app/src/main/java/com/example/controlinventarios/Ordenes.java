package com.example.controlinventarios;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

public class Ordenes extends AppCompatActivity {
    ListView itemslist;
    EditText buscartext;
    Spinner categoriaspinner;
    ImageButton buscarbtn;
    ImageButton NuevoOrdenbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenes);
        NuevoOrdenbtn =findViewById(R.id.agregarorden_btn);
        NuevoOrdenbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent config = new Intent(Ordenes.this, NuevaOrden.class);
                startActivity(config);
            }});
        itemslist=findViewById(R.id.ordenes_list);
        buscarbtn=findViewById(R.id.buscarordenes_btn);
        buscartext=findViewById(R.id.buscarordenes_text);
        categoriaspinner=findViewById(R.id.categoriaordenes_spinner);
    }
}
