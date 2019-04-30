package com.example.controlinventarios.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

//CREATE TABLE [orders](
//        [id] INTEGER PRIMARY KEY,
//        [status_id] INTEGER NOT NULL REFERENCES order_status([id]),
//        [customer_id] INTEGER NOT NULL REFERENCES customers([id]),
//        [date] TEXT NOT NULL,
//        [change_log] TEXT DEFAULT NULL);

@Entity(tableName = "orders")
public class Orders {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "status_id")
    @ForeignKey(entity = OrderStatus.class,
            parentColumns = "id", childColumns = "status_id")
    private int status_id;
    @ColumnInfo(name = "customer_id")
    @ForeignKey(entity = Customers.class,
            parentColumns = "id", childColumns = "customer_id")
    private int customer_id;
    @NonNull
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "change_log")
    private String change_log;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getStatus_id() { return status_id; }

    public void setStatus_id(int status_id) { this.status_id = status_id; }

    public int getCustomer_id() { return customer_id; }

    public void setCustomer_id(int customer_id) { this.customer_id = customer_id; }

    @NonNull
    public String getDate() { return date; }

    public void setDate(@NonNull String date) { this.date = date; }

    public String getChange_log() {
       return change_log; }

    public void setChange_log(String change_log) { this.change_log = change_log; }

    public Orders(int id, int status_id, int customer_id, @NonNull String date, String change_log) {
        this.id = id;
        this.status_id = status_id;
        this.customer_id = customer_id;
        this.date = date;
        this.change_log = change_log;
    }
}
