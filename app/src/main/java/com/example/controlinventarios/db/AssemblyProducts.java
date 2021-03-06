package com.example.controlinventarios.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.service.autofill.FillEventHistory;

//CREATE TABLE [assembly_products](
//        [id] INTEGER NOT NULL REFERENCES assemblies([id]),
//        [product_id] INTEGER NOT NULL REFERENCES products([id]),
//        [qty] INTEGER NOT NULL,
//        CHECK(qty >= 0),
//        UNIQUE([id], [product_id]));

@Entity(tableName = "assembly_products", indices = {@Index(value = {"id","product_id"},unique = true)})
public class AssemblyProducts {
    @PrimaryKey
    @ColumnInfo(name="aux")
    private int aux;
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

    public int getAux() { return aux; }

    public void setAux(int aux) { this.aux = aux; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getProduct_id() { return product_id; }

    public void setProduct_id(int product_id) { this.product_id = product_id; }

    public int getQty() { return qty; }

    public void setQty(int qty) { this.qty = qty; }

    public AssemblyProducts(int aux, int id, int product_id, int qty) {
        this.aux = aux;
        this.id = id;
        this.product_id = product_id;
        this.qty = qty;
    }
    @Ignore
    @ColumnInfo(name="product")
    private String product;

    public String getProduct() { return product; }

    public void setProduct(String cost) { this.product = product; }
    @Ignore
    @ColumnInfo(name="price")
    private int price;

    public int getPrice() { return price; }

    public void setPrice(int product_id) { this.price = price; }
   @Ignore
   public AssemblyProducts(int aux, int id, int product_id, int qty,String product,int price) {
       this.aux = aux;
       this.id = id;
       this.product_id = product_id;
       this.qty = qty;
       this.product=product;
       this.price=price;
   }
}

