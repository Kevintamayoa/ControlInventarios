package com.example.controlinventarios.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.example.controlinventarios.db.EarningsPerOrder;

import java.util.List;

@Dao
public interface EarningPerOrderDao {
    @Query("SELECT o.id, o.status_id, o.customer_id, o.date, SUM(oa.qty*ap.qty*p.price) AS earnings FROM orders o " +
            "INNER JOIN order_assemblies oa ON (o.id==oa.id) INNER JOIN assembly_products ap ON (oa.assembly_id==ap.id)  " +
            "INNER JOIN products p ON (ap.product_id==p.id) WHERE o.status_id !=1 GROUP BY o.id ORDER BY o.id")
    public List<EarningsPerOrder> getEarningsPerOrder();

    @Query("SELECT o.id, o.status_id, o.customer_id, o.date, SUM(oa.qty*ap.qty*p.price) AS earnings FROM orders o " +
            "INNER JOIN customers c ON (o.customer_id==c.id) INNER JOIN order_assemblies oa ON (o.id==oa.id) " +
            "INNER JOIN assembly_products ap ON (oa.assembly_id==ap.id)  INNER JOIN products p ON (ap.product_id==p.id) " +
            "WHERE o.status_id ==0 GROUP BY o.id ORDER BY c.last_name, c.first_name")
    public List<EarningsPerOrder> getEarningsPerPendingOrderOrderedByName();

    @Query("SELECT o.id, o.status_id, o.customer_id, o.date, SUM(oa.qty*ap.qty*p.price) AS earnings FROM orders o " +
            "INNER JOIN customers c ON (o.customer_id==c.id) INNER JOIN order_assemblies oa ON (o.id==oa.id) " +
            "INNER JOIN assembly_products ap ON (oa.assembly_id==ap.id)  INNER JOIN products p ON (ap.product_id==p.id) " +
            "WHERE o.status_id ==0 GROUP BY o.id ORDER BY SUM(oa.qty*ap.qty*p.price) DESC")
    public List<EarningsPerOrder> getEarningsPerPendingOrderOrderedByEarnings();

    @Query("SELECT o.id, o.status_id, o.customer_id, o.date, SUM(oa.qty*ap.qty*p.price) AS earnings FROM orders o " +
            "INNER JOIN customers c ON (o.customer_id==c.id) INNER JOIN order_assemblies oa ON (o.id==oa.id) " +
            "INNER JOIN assembly_products ap ON (oa.assembly_id==ap.id)  INNER JOIN products p ON (ap.product_id==p.id) " +
            "WHERE o.status_id ==0 GROUP BY o.id ORDER BY o.id ASC")
    public List<EarningsPerOrder> getEarningsPerPendingOrderOrderedByDate();
}
