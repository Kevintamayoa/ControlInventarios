package com.example.controlinventarios.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.controlinventarios.db.Assemblies;

import java.util.List;

@Dao
public interface AssembliesDao {
    @Insert
    public void InsertAssemblies(Assemblies assembly);

    @Update
    public void UpdateAssemblies(Assemblies assembly);

    @Delete
    public void DeleteAssemblies(Assemblies assembly);

    @Query("SELECT * FROM assemblies ORDER BY id")
    public List<Assemblies> getAllAssemblies();
}
