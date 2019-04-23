package com.example.controlinventarios.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

//CREATE TABLE [assembly_products](
//        [id] INTEGER NOT NULL REFERENCES assemblies([id]),
//        [product_id] INTEGER NOT NULL REFERENCES products([id]),
//        [qty] INTEGER NOT NULL,
//        CHECK(qty >= 0),
//        UNIQUE([id], [product_id]));

@Entity(tableName = "assembly_products")    // , indices = {@Index(value = "id",unique = true)})
public class AssemblyProducts {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @ForeignKey(entity = Assemblies.class,
            parentColumns = "id", childColumns = "id")
    private int id;

    @ColumnInfo(name = "product_id")
    @ForeignKey(entity = Products.class,
            parentColumns = "id", childColumns = "product_id")
    private int product_id;
    @ColumnInfo(name = "qty")
    private int qty;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getProduct_id() { return product_id; }

    public void setProduct_id(int product_id) { this.product_id = product_id; }

    public int getQty() { return qty; }

    public void setQty(int qty) { this.qty = qty; }

    public AssemblyProducts(int id, int product_id, int qty) {
        this.id = id;
        this.product_id = product_id;
        this.qty = qty;
    }
}
