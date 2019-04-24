package com.example.controlinventarios.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.example.controlinventarios.db.Products;

import java.util.List;

@Dao
public interface ProductsDao {
    @Query("SELECT * FROM products p WHERE p.description LIKE '%'||:text||'%' ORDER BY p.description")
    public List<Products> getAllProductsByDescription(String text);

    @Query("SELECT * FROM products p WHERE p.category_id = :category AND p.description LIKE '%'||:text||'%' ORDER BY p.description")
    public List<Products> getProductsByCategoryAndDescription(int category,String text);
}
