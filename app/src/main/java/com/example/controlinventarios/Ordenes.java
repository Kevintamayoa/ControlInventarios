package com.example.controlinventarios;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import java.text.DateFormatSymbols;

public class Ordenes extends AppCompatActivity {
    DatePicker inicio_date;
    DatePicker final_date;
    Spinner clients_spinner;
    Spinner state_spinner;
    CheckBox inicio_check;
    CheckBox final_check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenes);
    }
}
