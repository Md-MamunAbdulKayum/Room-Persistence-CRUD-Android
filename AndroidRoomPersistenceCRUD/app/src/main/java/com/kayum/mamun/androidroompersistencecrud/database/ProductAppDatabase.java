package com.kayum.mamun.androidroompersistencecrud.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.kayum.mamun.androidroompersistencecrud.dao.ProductDAO;
import com.kayum.mamun.androidroompersistencecrud.entity.Product;

@Database(entities = {Product.class}, version = 1)
public abstract  class ProductAppDatabase extends RoomDatabase {

    public abstract ProductDAO productDAO();

    private static volatile ProductAppDatabase INSTANCE;

    static ProductAppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ProductAppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ProductAppDatabase.class, "product_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
