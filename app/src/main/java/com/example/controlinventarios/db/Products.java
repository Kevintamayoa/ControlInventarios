package com.example.controlinventarios.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

//CREATE TABLE [products](
//        [id] INTEGER PRIMARY KEY,
//        [category_id] INTEGER NOT NULL REFERENCES product_categories([id]),
//        [description] TEXT NOT NULL,
//        [price] INTEGER NOT NULL,
//        [qty] INTEGER NOT NULL,
//        CHECK(price >= 0),
//        CHECK(qty >= 0));

@Entity(tableName = "products")
public class Products {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "category_id")
    @ForeignKey(entity = ProductCategories.class,
            parentColumns = "id", childColumns = "category_id")
    private int category_id;
    @NonNull
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "price")
    private int price;
    @ColumnInfo(name = "qty")
    private int qty;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getCategory_id() { return category_id; }

    public void setCategory_id(int category_id) { this.category_id = category_id; }

    @NonNull
    public String getDescription() { return description; }

    public void setDescription(@NonNull String description) { this.description = description; }

    public int getPrice() { return price; }

    public void setPrice(int price) { this.price = price; }

    public int getQty() { return qty; }

    public void setQty(int qty) { this.qty = qty; }

    public Products(int id, int category_id, @NonNull String description, int price, int qty) {
        this.id = id;
        this.category_id = category_id;
        this.description = description;
        this.price = price;
        this.qty = qty;
    }
}
