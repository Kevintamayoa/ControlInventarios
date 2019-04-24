package com.example.controlinventarios.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.example.controlinventarios.db.ProductCategories;

import java.util.List;

@Dao
public interface ProductCategoriesDao {
    @Query("SELECT pc.description FROM product_categories pc ORDER BY id")
    public List<String> getProductCategories();
}
