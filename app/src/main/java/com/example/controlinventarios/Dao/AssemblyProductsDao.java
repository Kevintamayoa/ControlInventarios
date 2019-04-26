package com.example.controlinventarios.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.controlinventarios.db.AssemblyProducts;

import java.util.List;

@Dao
public interface AssemblyProductsDao {
    @Insert
    public void InsertAssemblyProducts(AssemblyProducts assemblyproducts);

    @Update
    public void UpdateAssemblyProducts(AssemblyProducts assemblyproducts);

    @Delete
    public void DeleteAssemblyProducts(AssemblyProducts assemblyproducts);

    @Query("SELECT A.aux as aux,A.id as id,A.product_id as product_id,A.qty as qty,B.description as product,B.price as price FROM assembly_products A INNER JOIN products B ON(A.product_id=B.id) WHERE A.id=:id " )
    public List<AssemblyProducts> getAllAssemblyProductsByAssembly(int id);
}
