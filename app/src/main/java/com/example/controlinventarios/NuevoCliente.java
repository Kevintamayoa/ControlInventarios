package com.example.controlinventarios;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
    final int NEW_CUSTOMER = 0;
    final int EDIT_CUSTOMER = 1;
    final String PURPOSE = "PURPOSE";
    Customers editCustomer;
    Customers newCustomer;
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
        saveCustomer = findViewById(R.id.save_customer);
        //Evita que se abra el edittext apenas abre el activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //Creacion de la coneccion al dao
        CustomersDao customerDao = AppDatabase.getAppDatabase(getApplicationContext()).customersDao();
        //Implementacion de manera que la entrada de enter cierre el teclado
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
        //Recuperando informacion transmitida por el activity anterior
        Bundle customerInfo = this.getIntent().getExtras();
        //Creacion de nuevo usuario
        if (customerInfo.getInt(PURPOSE) == NEW_CUSTOMER) {
            Toast.makeText(this, "New Customer", Toast.LENGTH_SHORT).show();
            saveCustomer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editFirstName.getText().toString().trim().equals("") || editLastName.getText().toString().trim().equals("") ||
                            editAdress.getText().toString().trim().equals("") || editPhone1.getText().toString().trim().equals("") ||
                            (chckPhone2.isChecked() && editPhone2.getText().toString().trim().equals("")) ||
                            (chckPhone3.isChecked() && editPhone3.getText().toString().trim().equals("")) ||
                            (chckEmail.isChecked() && editEmail.getText().toString().trim().equals(""))) {
                        editFirstName.setText(editFirstName.getText().toString().trim());
                        editLastName.setText(editLastName.getText().toString().trim());
                        editAdress.setText(editAdress.getText().toString().trim());
                        editPhone1.setText(editPhone1.getText().toString().trim());
                        editPhone2.setText(editPhone2.getText().toString().trim());
                        editPhone3.setText(editPhone3.getText().toString().trim());
                        editEmail.setText(editEmail.getText().toString().trim());
                        AlertDialog.Builder builder = new AlertDialog.Builder(NuevoCliente.this);
                        builder.setMessage("Te falta meter informacion.\nInformacion obligatoria: Nombre, Apellido, Direccion, Telefono 1.\nIgual es posible que te falte informacion opcional que hayas activado:\nTelefono 2, Telefono 3, Email")
                                .setTitle("Informacion faltante");
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        String firstName = editFirstName.getText().toString().trim();
                        String lastName = editLastName.getText().toString().trim();
                        String adress = editAdress.getText().toString().trim();
                        String phone1 = editPhone1.getText().toString().trim();
                        String phone2 = null;
                        String phone3 = null;
                        String email = null;
                        if(chckPhone2.isChecked()){
                            phone2 = editPhone2.getText().toString().trim();
                        }
                        if(chckPhone3.isChecked()){
                            phone3 = editPhone3.getText().toString().trim();
                        }
                        if(chckEmail.isChecked()){
                            email = editEmail.getText().toString().trim();
                        }
                        int id = AppDatabase.getAppDatabase(NuevoCliente.this).customersDao().getCustomersByFirstName("").size();
                        newCustomer = new Customers(id, firstName, lastName, adress, phone1, phone2, phone3, email);
                        AppDatabase.getAppDatabase(NuevoCliente.this).customersDao().insertCustomer(newCustomer);
                        Toast.makeText(NuevoCliente.this, "Cliente creado exitosamente!!!", Toast.LENGTH_SHORT).show();
                        Intent customersScreen = new Intent(NuevoCliente.this, Clientes.class);
                        startActivity(customersScreen);
                        finish();
                    }
                }
            });
        }
        //Editar usuario existente
        else if (customerInfo.getInt(PURPOSE) == EDIT_CUSTOMER) {
            //Recuperacion de la informacion del usuario
            switch (customerInfo.getInt(SELECTED_FILTER)) {
                case 0:
                    editCustomer = customerDao.getCustomersByFirstName(customerInfo.getString(TEXT_FILTERED)).get(customerInfo.getInt(POSITION));
                    break;
                case 1:
                    editCustomer = customerDao.getCustomersByLastName(customerInfo.getString(TEXT_FILTERED)).get(customerInfo.getInt(POSITION));
                    break;
                case 2:
                    editCustomer = customerDao.getCustomerByAdress(customerInfo.getString(TEXT_FILTERED)).get(customerInfo.getInt(POSITION));
                    break;
                case 3:
                    editCustomer = customerDao.getCustomerByTelephone(customerInfo.getString(TEXT_FILTERED)).get(customerInfo.getInt(POSITION));
                    break;
                case 4:
                    editCustomer = customerDao.getCustomerByEmail(customerInfo.getString(TEXT_FILTERED)).get(customerInfo.getInt(POSITION));
                    break;
                default:
                    break;
            }
            //Llenado de la forma
            editFirstName.setText(editCustomer.getFirst_name());
            editLastName.setText(editCustomer.getLast_name());
            editAdress.setText(editCustomer.getAddress());
            editPhone1.setText(editCustomer.getPhone1());
            if (editCustomer.getPhone2() != null) {
                chckPhone2.setChecked(true);
            }
            editPhone2.setText(editCustomer.getPhone2());
            if (editCustomer.getPhone3() != null) {
                chckPhone3.setChecked(true);
            }
            editPhone3.setText(editCustomer.getPhone3());
            if (editCustomer.getE_mail() != null) {
                chckEmail.setChecked(true);
            }
            editEmail.setText(editCustomer.getE_mail());
            saveCustomer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editFirstName.getText().toString().trim().equals("") || editLastName.getText().toString().trim().equals("") ||
                            editAdress.getText().toString().trim().equals("") || editPhone1.getText().toString().trim().equals("") ||
                            (chckPhone2.isChecked() && editPhone2.getText().toString().trim().equals("")) ||
                            (chckPhone3.isChecked() && editPhone3.getText().toString().trim().equals("")) ||
                            (chckEmail.isChecked() && editEmail.getText().toString().trim().equals(""))) {
                        editFirstName.setText(editFirstName.getText().toString().trim());
                        editLastName.setText(editLastName.getText().toString().trim());
                        editAdress.setText(editAdress.getText().toString().trim());
                        editPhone1.setText(editPhone1.getText().toString().trim());
                        editPhone2.setText(editPhone2.getText().toString().trim());
                        editPhone3.setText(editPhone3.getText().toString().trim());
                        editEmail.setText(editEmail.getText().toString().trim());
                        AlertDialog.Builder builder = new AlertDialog.Builder(NuevoCliente.this);
                        builder.setMessage("Te falta meter informacion.\nInformacion obligatoria: Nombre, Apellido, Direccion, Telefono 1.\nIgual es posible que te falte informacion opcional que hayas activado:\nTelefono 2, Telefono 3, Email")
                                .setTitle("Informacion faltante");
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        editCustomer.setFirst_name(editFirstName.getText().toString().trim());
                        editCustomer.setLast_name(editLastName.getText().toString().trim());
                        editCustomer.setAddress(editAdress.getText().toString().trim());
                        editCustomer.setPhone1(editPhone1.getText().toString().trim());
                        if(chckPhone2.isChecked()){
                            editCustomer.setPhone2(editPhone2.getText().toString().trim());
                        }
                        if(chckPhone3.isChecked()){
                            editCustomer.setPhone3(editPhone3.getText().toString().trim());
                        }
                        if(chckEmail.isChecked()){
                            editCustomer.setE_mail(editEmail.getText().toString().trim());
                        }
                        AppDatabase.getAppDatabase(NuevoCliente.this).customersDao().updateCustomer(editCustomer);
                        Toast.makeText(NuevoCliente.this, "Cliente creado exitosamente!!!", Toast.LENGTH_SHORT).show();
                        Intent customersScreen = new Intent(NuevoCliente.this, Clientes.class);
                        startActivity(customersScreen);
                        finish();
                    }
                }
            });
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Seguro que desea salir de esta ventana?, los datos ingresados se perderan.")
                .setTitle("Saliendo Sin Guardar");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent customersScreen = new Intent(NuevoCliente.this, Clientes.class);
                startActivity(customersScreen);
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
