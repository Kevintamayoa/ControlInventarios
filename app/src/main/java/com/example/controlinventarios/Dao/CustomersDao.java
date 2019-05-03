package com.example.controlinventarios.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.controlinventarios.db.Customers;

import java.lang.reflect.Array;
import java.util.List;

@Dao
public interface CustomersDao {

    @Insert
    public void insertCustomer(Customers customer);

    @Update
    public void updateCustomer(Customers customer);

    @Delete
    public void deleteCustomer(Customers customer);

    @Query("SELECT * FROM customers c WHERE LOWER(c.first_name) LIKE LOWER('%'||:name||'%') ORDER BY c.last_name, c.first_name")
    public List<Customers> getCustomersByFirstName(String name);

    @Query("SELECT * FROM customers c WHERE LOWER(c.last_name) LIKE LOWER('%'||:name||'%') ORDER BY c.last_name, c.first_name")
    public List<Customers> getCustomersByLastName(String name);

    @Query("SELECT * FROM customers c WHERE LOWER(c.address) LIKE LOWER('%'||:adress||'%') ORDER BY c.last_name, c.first_name")
    public List<Customers> getCustomerByAdress(String adress);

    @Query("SELECt * FROM customers c WHERE c.phone1 LIKE '%'||:tel||'%' OR c.phone2 LIKE '%'||:tel||'%' OR c.phone3 LIKE '%'||:tel||'%' ORDER BY c.last_name, c.first_name")
    public List<Customers> getCustomerByTelephone(String tel);

    @Query("SELECt * FROM customers c WHERE LOWER(c.e_mail) LIKE LOWER('%'||:tel||'%')  ORDER BY c.last_name, c.first_name")
    public List<Customers> getCustomerByEmail(String tel);

    @Query("SELECT * FROM customers c  ORDER BY c.last_name, c.first_name")
    public List<Customers> getAllCustomer();

    @Query("SELECT (last_name ||', '|| first_name) FROM customers c  ORDER BY c.last_name, c.first_name")
    public List<String> getAllCustomerCat();
    @Query("SELECT c.id FROM customers c  ORDER BY c.last_name, c.first_name")
    public int[] getAllCustomerCatId();

    @Query("SELECT * FROM customers WHERE id=:id")
    public Customers getCustomerById(int id);


}
