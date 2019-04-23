package com.example.controlinventarios.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

//CREATE TABLE [order_assemblies](
//        [id] INTEGER NOT NULL REFERENCES orders([id]),
//        [assembly_id] INTEGER NOT NULL REFERENCES assemblies([id]),
//        [qty] INTEGER NOT NULL,
//        UNIQUE([id], [assembly_id]));

@Entity(tableName = "order_assemblies")
public class OrderAssemblies {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @ForeignKey(entity = Orders.class,
            parentColumns = "id", childColumns = "id")
    private int id;
    @ColumnInfo(name = "assembly_id")
    @ForeignKey(entity = Assemblies.class,
            parentColumns = "id", childColumns = "assembly_id")
    private int assembly_id;
    @ColumnInfo(name = "qty")
    private int qty;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getAssembly_id() { return assembly_id; }

    public void setAssembly_id(int assembly_id) { this.assembly_id = assembly_id; }

    public int getQty() { return qty; }

    public void setQty(int qty) { this.qty = qty; }

    public OrderAssemblies(int id, int assembly_id, int qty) {
        this.id = id;
        this.assembly_id = assembly_id;
        this.qty = qty;
    }
}
