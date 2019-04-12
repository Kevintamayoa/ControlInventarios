package com.example.controlinventarios.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

//CREATE TABLE [product_categories](
//        [id] INTEGER PRIMARY KEY,
//        [description] TEXT NOT NULL);

@Entity(tableName = "product_categories")
public class ProductCategories {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "description")
    private String description;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public ProductCategories(int id, String description) {
        this.id = id;
        this.description = description;
    }
}
