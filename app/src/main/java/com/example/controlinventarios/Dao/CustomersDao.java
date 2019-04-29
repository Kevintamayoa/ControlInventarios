package com.example.controlinventarios.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.controlinventarios.db.Customers;

import java.util.List;

@Dao
public interface CustomersDao {

    @Insert
    public void insertCustomer(Customers customer);

    @Update
    public void updateCustomer(Customers customer);

    @Delete
    public void deleteCustomer(Customers customer);

    @Query("SELECT * FROM customers c WHERE c.first_name LIKE '%'||:name||'%' ORDER BY c.last_name, c.first_name")
    public List<Customers> getCustomersByFirstName(String name);

    @Query("SELECT * FROM customers c WHERE c.last_name LIKE '%'||:name||'%' ORDER BY c.last_name, c.first_name")
    public List<Customers> getCustomersByLastName(String name);

    @Query("SELECT * FROM customers c WHERE c.address LIKE '%'||:adress||'%' ORDER BY c.last_name, c.first_name")
    public List<Customers> getCustomerByAdress(String adress);

    @Query("SELECt * FROM customers c WHERE c.phone1 LIKE '%'||:tel||'%' OR c.phone2 LIKE '%'||:tel||'%' OR c.phone3 LIKE '%'||:tel||'%' ORDER BY c.last_name, c.first_name")
    public List<Customers> getCustomerByTelephone(String tel);

    @Query("SELECT * FROM customers c WHERE c.e_mail LIKE '%'||:email||'%' ORDER BY c.last_name, c.first_name")
    public List<Customers> getCustomerByEmail(String email);
}
