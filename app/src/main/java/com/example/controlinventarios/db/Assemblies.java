package com.example.controlinventarios.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

//CREATE TABLE [assemblies](
//        [id] INTEGER PRIMARY KEY,
//        [description] TEXT NOT NULL);

@Entity(tableName = "assemblies")
public class Assemblies {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
    @NonNull
    @ColumnInfo(name = "description")
    private String description;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Assemblies(int id, String description) {
        this.id = id;
        this.description = description;
    }

    private int num_products;

    public int getnum_products() { return num_products; }

    public void setnum_products(int num_products) { this.num_products = num_products; }

    private double cost;

    public double getcost() { return cost; }

    public void setcost(double cost) { this.cost = cost; }

    public Assemblies(int id, String description,int num_products,double cost) {
        this.id = id;
        this.description = description;
        this.cost=cost;
        this.num_products=num_products;
    }
}
