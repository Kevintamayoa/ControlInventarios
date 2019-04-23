package com.example.controlinventarios;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Clientes extends AppCompatActivity {
    ImageButton AgregarClientebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);
        AgregarClientebtn =findViewById(R.id.agregarcliente_btn);
        AgregarClientebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent config = new Intent(Clientes.this, NuevoCliente.class);
                startActivity(config);
            }});
    }
}
