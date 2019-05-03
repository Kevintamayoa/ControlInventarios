package com.example.controlinventarios.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.controlinventarios.db.Assemblies;
import com.example.controlinventarios.db.Orders;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface OrdersDao {
    @Query("SELECT id as id,status_id as status_id,customer_id as customer_id,date as date,(case when change_log is null then ' ' else change_log end) as change_log FROM orders")
    public List<Orders> getAllOrders();

    @Query("SELECT id as id,status_id as status_id,customer_id as customer_id,date as date,(case when change_log is null then ' ' else change_log end) as change_log FROM orders WHERE status_id IN (:list)")
    public List<Orders> getAllOrdersByStatusId(int[] list);

    @Query("SELECT id as id,status_id as status_id,customer_id as customer_id,date as date,(case when change_log is null then ' ' else change_log end) as change_log FROM orders WHERE status_id IN (:list) AND customer_id=:customer_id")
    public List<Orders> getAllOrdersByStatusId(int[] list, int customer_id);

    @Query(" SELECT * FROM orders o WHERE status_id IN (:list) AND date(substr(o.date,7,4)||'-'||substr(o.date,4,2)||'-'||substr(o.date,1,2))>=date(:DateIn)")
    public List<Orders> getAllOrdersByDateIn(int[] list,String DateIn);
    @Query(" SELECT * FROM orders o WHERE status_id IN (:list) AND date(substr(o.date,7,4)||'-'||substr(o.date,4,2)||'-'||substr(o.date,1,2))<=date(:DateIn)")
    public List<Orders> getAllOrdersByDateEnd(int[] list,String DateIn);
    @Query(" SELECT * FROM orders o WHERE status_id IN (:list) AND date(substr(o.date,7,4)||'-'||substr(o.date,4,2)||'-'||substr(o.date,1,2))<=date(:DateOut) AND date(substr(o.date,7,4)||'-'||substr(o.date,4,2)||'-'||substr(o.date,1,2))>=date(:DateIn)")
    public List<Orders> getAllOrdersByDates(int[] list,String DateIn,String DateOut);
    @Query(" SELECT * FROM orders o WHERE status_id IN (:list) AND date(substr(o.date,7,4)||'-'||substr(o.date,4,2)||'-'||substr(o.date,1,2))>=date(:DateIn) AND customer_id=:customer_id")
    public List<Orders> getAllOrdersByDateInAndClient(int[] list,String DateIn, int customer_id);
    @Query(" SELECT * FROM orders o WHERE status_id IN (:list) AND date(substr(o.date,7,4)||'-'||substr(o.date,4,2)||'-'||substr(o.date,1,2))<=date(:DateIn) AND customer_id=:customer_id")
    public List<Orders> getAllOrdersByDateEndAndClient(int[] list,String DateIn, int customer_id);
    @Query(" SELECT * FROM orders o WHERE status_id IN (:list) AND date(substr(o.date,7,4)||'-'||substr(o.date,4,2)||'-'||substr(o.date,1,2))<=date(:DateOut) AND date(substr(o.date,7,4)||'-'||substr(o.date,4,2)||'-'||substr(o.date,1,2))>=date(:DateIn) AND customer_id=:customer_id")
    public List<Orders> getAllOrdersByDatesAndClient(int[] list,String DateIn,String DateOut, int customer_id);

    @Query("SELECT (CASE WHEN (SELECT SUM(A.qty) FROM order_assemblies A WHERE A.id= :id) IS NULL THEN 0 \n" +
            "ELSE (SELECT SUM(A.qty) FROM order_assemblies A WHERE A.id=:id) END) AS qty  ")
    public int getNumAssembliesByOrder(int id);

    @Query("SELECT (CASE WHEN (SELECT SUM(A.qty) FROM order_assemblies A WHERE A.id= :id) IS NULL THEN 0 " +
            " ELSE (SELECT SUM(A.qty*C.qty*D.price) FROM order_assemblies A INNER JOIN assembly_products C on(A.assembly_id=C.id) INNER JOIN products D on(C.product_id=D.id) WHERE A.id=:id) END) AS cost ")
    public double getPriceByOrder(int id);

    //No se toman en cuenta las ordenes canceladas
    @Query("SELECT * FROM orders o WHERE o.status_id!=1 ORDER BY o.id")
    public List<Orders> getAllCasheableOrders();

    @Update
    public void UpdateOrder(Orders order);
    @Insert
    public void InserOrder(Orders order);

    @Query("SELECT MAX(id) FROM orders")
    public int getMaxId();
}
