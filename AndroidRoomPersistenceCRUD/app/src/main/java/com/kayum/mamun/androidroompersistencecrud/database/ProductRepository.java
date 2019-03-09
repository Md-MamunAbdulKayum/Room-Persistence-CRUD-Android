package com.kayum.mamun.androidroompersistencecrud.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.kayum.mamun.androidroompersistencecrud.dao.ProductDAO;
import com.kayum.mamun.androidroompersistencecrud.entity.Product;

import java.util.List;

public class ProductRepository {
    private ProductDAO productDAO;
    private LiveData<List<Product>> mAllProducts;

   public ProductRepository(Application application) {
        ProductAppDatabase db = ProductAppDatabase.getDatabase(application);
        productDAO = db.productDAO();
       mAllProducts = productDAO.getAllProduct();
    }

    public LiveData<List<Product>> getAllProducts() {
        return mAllProducts;
    }


    public void insert (Product product) {
        new insertAsyncTask(productDAO).execute(product);
    }

    public void delete(Product product) {
        new DeleteProductAsyncTask(productDAO).execute(product);
    }

    public void update(Product product) {
        new UpdateProductAsyncTask(productDAO).execute(product);
    }

    private static class insertAsyncTask extends AsyncTask<Product, Void, Void> {

        private ProductDAO mAsyncTaskDao;

        insertAsyncTask(ProductDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Product... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class UpdateProductAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDAO productDAO;

        private UpdateProductAsyncTask(ProductDAO productDAO) {
            this.productDAO = productDAO;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDAO.update(products[0]);
            return null;
        }
    }

    private static class DeleteProductAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDAO productDao;

        private DeleteProductAsyncTask(ProductDAO productDAO) {
            this.productDao = productDAO;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.delete(products[0]);
            return null;
        }
    }
}
