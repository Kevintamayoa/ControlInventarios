package com.example.controlinventarios.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "earnings_per_order")
public class EarningsPerOrder{
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "status_id")
    private int status_id;
    @ColumnInfo(name = "customer_id")
    private int customer_id;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "earnings")
    private int earnings;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getStatus_id() { return status_id; }

    public void setStatus_id(int status_id) { this.status_id = status_id; }

    public int getCustomer_id() { return customer_id; }

    public void setCustomer_id(int customer_id) { this.customer_id = customer_id; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public int getEarnings() { return earnings; }

    public void setEarnings(int earnings) { this.earnings = earnings; }

    public EarningsPerOrder(int id, int status_id, int customer_id, String date, int earnings) {
        this.id = id;
        this.status_id = status_id;
        this.customer_id = customer_id;
        this.date = date;
        this.earnings = earnings;
    }
}
