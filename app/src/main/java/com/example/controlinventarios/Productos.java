package com.example.controlinventarios;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

public class Productos extends AppCompatActivity {

    ListView itemslist;
    EditText buscartext;
    Spinner categoriaspinner;
    ImageButton buscarbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        itemslist=findViewById(R.id.productos_list);
        buscarbtn=findViewById(R.id.buscarproductos_btn);
        buscartext=findViewById(R.id.buscarproductos_text);
        categoriaspinner=findViewById(R.id.categoriaproductos_spinner);
    }
}
