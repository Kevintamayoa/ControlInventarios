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

    @Query("SELECT B.id as id,B.category_id as category_id,b.description as description,B.price as price,A.qty as qty FROM assembly_products A INNER JOIN products B ON(A.product_id=B.id) WHERE A.id=:id ")
    public List<Products> getAllProductsByAssembly(int id);
    @Query("SELECT * FROM products p WHERE id= :id")
    public Products getProductById(int id);
}
