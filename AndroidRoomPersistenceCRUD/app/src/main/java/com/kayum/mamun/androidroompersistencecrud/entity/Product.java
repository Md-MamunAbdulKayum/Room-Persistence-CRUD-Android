package com.kayum.mamun.androidroompersistencecrud.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Entity(tableName = "products")
public class Product {

    @PrimaryKey(autoGenerate = true)
    private int productId;

    @NonNull
    @ColumnInfo(name = "product_name")
    private String productName;

    @NonNull
    @ColumnInfo(name = "product_description")
    private String productDescription;

    @NonNull
    @ColumnInfo(name = "date_time")
    private long dateTime;

    @NonNull
    @ColumnInfo(name = "product_quantity")
    private int productQuantity;

    @NonNull
    @ColumnInfo(name = "isactive")
    private boolean isActive;

    public Product(@NonNull String productName, @NonNull String productDescription, long dateTime, int productQuantity, boolean isActive) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.dateTime = dateTime;
        this.productQuantity = productQuantity;
        this.isActive = isActive;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @NonNull
    public String getProductName() {
        return productName;
    }

    public void setProductName(@NonNull String productName) {
        this.productName = productName;
    }

    @NonNull
    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(@NonNull String productDescription) {
        this.productDescription = productDescription;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getDateTimeFormatted(Context context){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss",
                context.getResources().getConfiguration().locale);
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format(new Date(dateTime));
    }
}
