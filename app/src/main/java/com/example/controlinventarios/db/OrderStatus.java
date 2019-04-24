package com.example.controlinventarios.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

//CREATE TABLE [order_status](
//        [id] INTEGER PRIMARY KEY,
//        [description] TEXT NOT NULL,
//        [editable] INTEGER NOT NULL,
//        [previous] TEXT NOT NULL,
//        [next] TEXT NOT NULL,
//        CHECK(editable = 0 OR editable = 1));

@Entity(tableName = "order_status")
public class OrderStatus {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "description")
    @NonNull
    private String description;
    @ColumnInfo(name = "editable") //editable solo puede ser false 0 o true 1
    private int editable;
    @ColumnInfo(name = "previous")
    @NonNull
    private String previous;
    @ColumnInfo(name = "next")
    @NonNull
    private String next;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    @NonNull
    public String getDescription() { return description; }

    public void setDescription(@NonNull String description) { this.description = description; }

    public int getEditable() { return editable; }

    public void setEditable(int editable) { this.editable = editable; }

    @NonNull
    public String getPrevious() { return previous; }

    public void setPrevious(@NonNull String previous) { this.previous = previous; }

    @NonNull
    public String getNext() { return next; }

    public void setNext(@NonNull String next) { this.next = next; }

    public OrderStatus(int id, @NonNull String description, int editable, @NonNull String previous, @NonNull String next) {
        this.id = id;
        this.description = description;
        this.editable = editable;
        this.previous = previous;
        this.next = next;
    }
}
