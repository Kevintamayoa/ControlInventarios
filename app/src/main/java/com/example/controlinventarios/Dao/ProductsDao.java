package com.example.controlinventarios.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.example.controlinventarios.db.Products;

import java.util.List;

@Dao
public interface ProductsDao {
    @Query("SELECT * FROM products p WHERE LOWER(p.description) LIKE LOWER('%'||:text||'%') ORDER BY p.description")
    public List<Products> getAllProductsByDescription(String text);

    @Query("SELECT * FROM products p WHERE p.category_id = :category AND LOWER(p.description) LIKE LOWER('%'||:text||'%') ORDER BY p.description")
    public List<Products> getProductsByCategoryAndDescription(int category,String text);

    @Query("SELECT B.id as id,B.category_id as category_id,b.description as description,B.price as price,A.qty as qty FROM assembly_products A INNER JOIN products B ON(A.product_id=B.id) WHERE A.id=:id ")
    public List<Products> getAllProductsByAssembly(int id);

    @Query("SELECT * FROM products p WHERE id= :id")
    public Products getProductById(int id);

    @Query("SELECT p.id, p.category_id, p.description, p.price, (required_qty - qty) AS qty " +
            "FROM products p " +
            "INNER JOIN (SELECT p.id, SUM(oa.qty*ap.qty) AS required_qty " +
            "            FROM order_assemblies oa " +
            "            INNER JOIN orders o ON (oa.id = o.id) " +
            "            INNER JOIN assembly_products ap ON (oa.assembly_id = ap.id) " +
            "            INNER JOIN products p ON (ap.product_id = p.id) " +
            "            WHERE o.status_id = 0 OR o.status_id = 2 " +
            "            GROUP BY p.id " +
            "            ) AS tmp ON (p.id = tmp.id) " +
            "WHERE (required_qty - qty) > 0;")
    public List<Products> getMissingProducts();

    @Query("SELECT p.id, p.category_id, p.description, p.price, p.qty AS qty FROM orders o INNER JOIN customers c ON (o.customer_id==c.id) " +
            "INNER JOIN order_assemblies oa ON (o.id==oa.id) INNER JOIN assembly_products ap ON (oa.assembly_id==ap.id) " +
            "INNER JOIN products p ON (ap.product_id==p.id) WHERE o.status_id ==0 GROUP BY p.id ORDER BY p.id ASC")
    public List<Products> getAllProducts();

    @Query("SELECT p.id, p.category_id, p.description, p.price, SUM(oa.qty*ap.qty) AS qty " +
            "FROM orders o " +
            "INNER JOIN customers c ON (o.customer_id==c.id) " +
            "INNER JOIN order_assemblies oa ON (o.id==oa.id) " +
            "INNER JOIN assembly_products ap ON (oa.assembly_id==ap.id) " +
            "INNER JOIN products p ON (ap.product_id==p.id) " +
            "WHERE o.id == :id " +
            "GROUP BY p.id " +
            "ORDER BY p.id ASC")
    public List<Products> getAllProductsByOrder(int id);
}
