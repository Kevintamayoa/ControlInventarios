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

    @Query("SELECT * FROM assembly_products ORDER BY id")
    public List<AssemblyProducts> getAllAssemblyProductsByAssembly(int id);
}
