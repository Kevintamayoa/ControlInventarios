package com.example.controlinventarios;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.facebook.stetho.Stetho;

public class MainActivity extends AppCompatActivity {
  ImageButton productos_btn;
  ImageButton ensambles_btn;
  ImageButton reportes_btn;
  ImageButton clientes_btn;
  ImageButton ordenes_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Stetho.initializeWithDefaults(this);

        productos_btn=findViewById(R.id.productos_button);
        productos_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent config = new Intent(MainActivity.this, Productos.class);
                startActivity(config);
            }});
        ensambles_btn=findViewById(R.id.ensambles_button);
        ensambles_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent config = new Intent(MainActivity.this, Ensambles.class);
                startActivity(config);
            }});
        reportes_btn=findViewById(R.id.reportes_button);
        reportes_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent config = new Intent(MainActivity.this, Reportes.class);
                startActivity(config);
            }});
        ordenes_btn=findViewById(R.id.ordenes_button);
        ordenes_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent config = new Intent(MainActivity.this, Ordenes.class);
                startActivity(config);
            }});
        clientes_btn=findViewById(R.id.clientes_button);
        clientes_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent config = new Intent(MainActivity.this, Clientes.class);
                startActivity(config);
            }});
    }
}
