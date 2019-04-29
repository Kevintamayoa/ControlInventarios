package com.example.controlinventarios.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.controlinventarios.db.Assemblies;
import com.example.controlinventarios.db.Assemblies2;

import java.util.List;

@Dao
public interface AssembliesDao {
    @Insert
    public void InsertAssemblies(Assemblies assembly);

    @Update
    public void UpdateAssemblies(Assemblies assembly);

    @Delete
    public void DeleteAssemblies(Assemblies assembly);

    @Query("SELECT A.id as id,A.description as description, " +
            "(CASE WHEN (SELECT SUM(qty) FROM assembly_products WHERE id=A.id) IS NULL THEN 0 ELSE (SELECT SUM(qty) FROM assembly_products WHERE id=A.id) END) AS num_products," +
            " (CASE WHEN (SELECT SUM(qty) FROM assembly_products WHERE id=A.id) IS NULL THEN 0 ELSE (SELECT SUM(B.qty*C.price) FROM assembly_products B INNER JOIN products C " +
            "on(B.product_id=C.id) WHERE B.id=A.id) END) AS cost FROM assemblies A WHERE A.description LIKE '%'||:text||'%' ORDER BY description ASC")
    public List<Assemblies2> getAllAssemblies(String text);
}
