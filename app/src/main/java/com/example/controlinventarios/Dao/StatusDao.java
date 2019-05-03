package com.example.controlinventarios.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.example.controlinventarios.db.OrderStatus;

import java.util.List;

@Dao
public interface StatusDao {
    @Query("SELECT * FROM order_status WHERE id==:id")
    public OrderStatus getOrderStatusById(int id);

    @Query("SELECT description FROM order_status ORDER BY id")
    public List<String> getStatusList();

}
