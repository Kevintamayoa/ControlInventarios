package com.example.controlinventarios.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

//CREATE TABLE [product_categories](
//        [id] INTEGER PRIMARY KEY,
//        [description] TEXT NOT NULL);

@Entity(tableName = "product_categories")
public class ProductCategories {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
    @NonNull
    @ColumnInfo(name = "description")
    private String description;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    @NonNull
    public String getDescription() { return description; }

    public void setDescription(@NonNull String description) { this.description = description; }

    public ProductCategories(int id, @NonNull String description) {
        this.id = id;
        this.description = description;
    }
}
