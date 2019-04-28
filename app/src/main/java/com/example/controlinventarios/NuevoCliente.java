package com.example.controlinventarios;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.controlinventarios.Dao.CustomersDao;
import com.example.controlinventarios.db.AppDatabase;
import com.example.controlinventarios.db.Customers;

public class NuevoCliente extends AppCompatActivity {

    final String POSITION = "POSITION";
    final String SELECTED_FILTER = "SELECTED_FILTER";
    final String TEXT_FILTERED = "TEXT_FILTERED";
    Customers customer;
    EditText editFirstName;
    EditText editLastName;
    EditText editAdress;
    EditText editPhone1;
    EditText editPhone2;
    CheckBox chckPhone2;
    EditText editPhone3;
    CheckBox chckPhone3;
    EditText editEmail;
    CheckBox chckEmail;
    Button saveCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_cliente);
        editFirstName = findViewById(R.id.edit_customer_firstname);
        editLastName = findViewById(R.id.edit_customer_lastname);
        editAdress = findViewById(R.id.edit_customer_adress);
        editPhone1 = findViewById(R.id.edit_customer_phone1);
        editPhone2 = findViewById(R.id.edit_customer_phone2);
        chckPhone2 = findViewById(R.id.chck_edit_phone2);
        editPhone3 = findViewById(R.id.edit_customer_phone3);
        chckPhone3 = findViewById(R.id.chck_edit_phone3);
        editEmail = findViewById(R.id.edit_email);
        chckEmail = findViewById(R.id.chck_edit_email);
        saveCustomer =findViewById(R.id.save_customer);
        //Evita que se abra el edittext apenas abre el activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //Creacion de la coneccion al dao
        CustomersDao customerDao = AppDatabase.getAppDatabase(getApplicationContext()).customersDao();
        //Implementacion de manera que la entrada de enter sea un return true
        editFirstName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
        editLastName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
        editAdress.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
        editPhone1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
        editPhone2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
        editPhone3.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
        editEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
        //Recuperacion de la informacion del usuario
        Bundle customerInfo = this.getIntent().getExtras();
        switch (customerInfo.getInt(SELECTED_FILTER)){
            case 0:
                customer = customerDao.getCustomersByFirstName(customerInfo.getString(TEXT_FILTERED)).get(customerInfo.getInt(POSITION));
                break;
            case 1:
                customer = customerDao.getCustomersByLastName(customerInfo.getString(TEXT_FILTERED)).get(customerInfo.getInt(POSITION));
                break;
            case 2:
                customer = customerDao.getCustomerByAdress(customerInfo.getString(TEXT_FILTERED)).get(customerInfo.getInt(POSITION));
                break;
            case 3:
                customer = customerDao.getCustomerByTelephone(customerInfo.getString(TEXT_FILTERED)).get(customerInfo.getInt(POSITION));
                break;
            case 4:
                customer = customerDao.getCustomerByEmail(customerInfo.getString(TEXT_FILTERED)).get(customerInfo.getInt(POSITION));
                break;
            default:
                break;
        }
        //Llenado de la forma
        editFirstName.setText(customer.getFirst_name());
        editLastName.setText(customer.getLast_name());
        editAdress.setText(customer.getAddress());
        editPhone1.setText(customer.getPhone1());
        if(customer.getPhone2()!=null){
            chckPhone2.setChecked(true);
        }
        editPhone2.setText(customer.getPhone2());
        if(customer.getPhone3()!=null){
            chckPhone3.setChecked(true);
        }
        editPhone3.setText(customer.getPhone3());
        if(customer.getE_mail()!=null){
            chckEmail.setChecked(true);
        }
        editEmail.setText(customer.getE_mail());


        saveCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Seguro que desea salir de esta ventana?, los datos ingresados se perderan.")
                .setTitle("Saliendo Sin Guardar");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
