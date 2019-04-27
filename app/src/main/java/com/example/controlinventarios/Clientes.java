package com.example.controlinventarios;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class Clientes extends AppCompatActivity {

    RecyclerView customerRecycler;
    EditText buscartext;
    Spinner busquedaspinner;

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
        Toolbar toolbar = findViewById(R.id.customer_toolbar);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.customer_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_customer_btn:
                Intent config = new Intent(Clientes.this, NuevoCliente.class);
                startActivity(config);
                finish();
                return true;
            case R.id.search_btn:
                return true;
            default:
                return true;
        }
    }
}
