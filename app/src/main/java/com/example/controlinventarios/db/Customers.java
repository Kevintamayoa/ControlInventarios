package com.example.controlinventarios.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

//CREATE TABLE [customers](
//        [id] INTEGER PRIMARY KEY,
//        [first_name] TEXT NOT NULL,
//        [last_name] TEXT NOT NULL,
//        [address] TEXT NOT NULL,
//        [phone1] TEXT,
//        [phone2] TEXT,
//        [phone3] TEXT,
//        [e_mail] TEXT);

@Entity(tableName = "customers")
public class Customers {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
    @NonNull
    @ColumnInfo(name = "first_name")
    private String first_name;
    @NonNull
    @ColumnInfo(name = "last_name")
    private String last_name;
    @NonNull
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(name = "phone1")
    private String phone1;
    @ColumnInfo(name = "phone2")
    private String phone2;
    @ColumnInfo(name = "phone3")
    private String phone3;
    @ColumnInfo(name = "e_mail")
    private String e_mail;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    @NonNull
    public String getFirst_name() { return first_name; }

    public void setFirst_name(@NonNull String first_name) { this.first_name = first_name; }

    @NonNull
    public String getLast_name() { return last_name; }

    public void setLast_name(@NonNull String last_name) { this.last_name = last_name; }

    @NonNull
    public String getAddress() { return address; }

    public void setAddress(@NonNull String address) { this.address = address; }

    public String getPhone1() { return phone1; }

    public void setPhone1(String phone1) { this.phone1 = phone1; }

    public String getPhone2() { return phone2; }

    public void setPhone2(String phone2) { this.phone2 = phone2; }

    public String getPhone3() { return phone3; }

    public void setPhone3(String phone3) { this.phone3 = phone3; }

    public String getE_mail() { return e_mail; }

    public void setE_mail(String e_mail) { this.e_mail = e_mail; }

    public Customers(int id, @NonNull String first_name, @NonNull String last_name, @NonNull String address, String phone1, String phone2, String phone3, String e_mail) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.phone3 = phone3;
        this.e_mail = e_mail;
    }
}
