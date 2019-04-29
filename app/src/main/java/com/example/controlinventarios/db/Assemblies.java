package com.example.controlinventarios.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


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

   public Assemblies(int id, @NonNull String description) {
       this.id = id;
       this.description = description;
   }

    @Ignore
  @ColumnInfo(name = "num_products")
  private int num_products;

    public int getNum_products() { return num_products; }

    public void setNum_products(int num_products) { this.num_products = num_products; }

    @Ignore
    @ColumnInfo(name = "cost")
    private double cost;

    public double getCost() { return cost; }

    public void setCost(double cost) { this.cost = cost; }

    public Assemblies(int id,@NonNull String description,int num_products,double cost) {
        this.id = id;
        this.description = description;
        this.cost=cost;
        this.num_products=num_products;
    }




}
