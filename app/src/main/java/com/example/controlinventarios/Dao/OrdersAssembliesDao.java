package com.example.controlinventarios.Dao;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.controlinventarios.db.OrderAssemblies;

@Dao
public interface OrdersAssembliesDao {
    @Insert
    public void InserOrder(OrderAssemblies order);
    @Query("SELECT MAX(id) FROM order_assemblies")
    public int getMaxId();
}
