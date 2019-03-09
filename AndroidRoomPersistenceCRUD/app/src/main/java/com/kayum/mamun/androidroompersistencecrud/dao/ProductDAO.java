package com.kayum.mamun.androidroompersistencecrud.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.kayum.mamun.androidroompersistencecrud.entity.Product;

import java.util.List;

@Dao
public interface ProductDAO {
    @Insert
    void insert(Product product);

    @Update
    void update(Product product);

    @Delete
    void delete(Product product);

    @Query("DELETE FROM products")
    void deleteAll();

    @Query("SELECT * from products ORDER BY product_name ASC")
    LiveData<List<Product>> getAllProduct();
}
