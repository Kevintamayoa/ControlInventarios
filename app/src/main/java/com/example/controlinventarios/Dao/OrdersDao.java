package com.example.controlinventarios.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.example.controlinventarios.db.EarningsPerOrder;
import com.example.controlinventarios.db.Orders;

import java.util.List;

@Dao
public interface OrdersDao {
    @Query("SELECT * FROM orders")
    public List<Orders> getAllOrders();

    @Query("SELECT (CASE WHEN (SELECT SUM(A.qty) FROM order_assemblies A WHERE A.id= :id) IS NULL THEN 0 \n" +
            "ELSE (SELECT SUM(A.qty) FROM order_assemblies A WHERE A.id=:id) END) AS qty  ")
    public int getNumAssembliesByOrder(int id);

    @Query("SELECT (CASE WHEN (SELECT SUM(A.qty) FROM order_assemblies A WHERE A.id= :id) IS NULL THEN 0 " +
            " ELSE (SELECT SUM(A.qty*C.qty*D.price) FROM order_assemblies A INNER JOIN assembly_products C on(A.assembly_id=C.id) INNER JOIN products D on(C.product_id=D.id) WHERE A.id=:id) END) AS cost ")
    public double getPriceByOrder(int id);

    //No se toman en cuenta las ordenes canceladas
    @Query("SELECT * FROM orders o WHERE o.status_id!=1 ORDER BY o.id")
    public List<Orders> getAllCasheableOrders();

    @Query("SELECT o.id, o.status_id, o.customer_id, o.date, SUM(oa.qty*ap.qty*p.price) AS earnings FROM orders o " +
            "INNER JOIN order_assemblies oa ON (o.id==oa.id) INNER JOIN assembly_products ap ON (oa.assembly_id==ap.id)  " +
            "INNER JOIN products p ON (ap.product_id==p.id) WHERE o.status_id !=1 GROUP BY o.id ORDER BY o.id")
    public List<EarningsPerOrder> getEarningsPerOrder();
}
