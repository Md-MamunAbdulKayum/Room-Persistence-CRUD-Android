package com.kayum.mamun.androidroompersistencecrud.adapter;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kayum.mamun.androidroompersistencecrud.MyApplication;
import com.kayum.mamun.androidroompersistencecrud.R;
import com.kayum.mamun.androidroompersistencecrud.database.ProductRepository;
import com.kayum.mamun.androidroompersistencecrud.entity.Product;
import com.kayum.mamun.androidroompersistencecrud.viewmodel.ProductViewModel;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {
private ProductViewModel productViewModel;

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private final TextView productNameView, productDescriptionView, productCreationDateView, productQuantityView, productAvailableView;
        private final Button editButton, deleteButton;
        private ProductViewHolder(final View itemView) {
            super(itemView);
            productNameView = itemView.findViewById(R.id.textView_product_name);
            productDescriptionView = itemView.findViewById(R.id.textView_product_description);
            productCreationDateView = itemView.findViewById(R.id.textView_creation_date);
            productAvailableView = itemView.findViewById(R.id.textView_available);
            productQuantityView = itemView.findViewById(R.id.textView_quantity);

            editButton = itemView.findViewById(R.id.button_edit);
            deleteButton = itemView.findViewById(R.id.button_delete);
            //productViewModel = ViewModelProviders.of().get(ProductViewModel.class);


        }
    }

    private final LayoutInflater mInflater;
    private List<Product> mProducts; // Cached copy of words

    public ProductListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        if (mProducts != null) {
            Product current = mProducts.get(position);
            holder.productNameView.setText("Name: "+current.getProductName());
            holder.productDescriptionView.setText("Description: "+current.getProductDescription());
            holder.productCreationDateView.setText("Date Created: "+current.getDateTimeFormatted(holder.productCreationDateView.getContext()));
            holder.productQuantityView.setText("Quantity: "+current.getProductQuantity());
            if(current.isActive())
            holder.productAvailableView.setText("Available: Yes");
            else
                holder.productAvailableView.setText("Available: No");

        } else {
            // Covers the case of data not being ready yet.
            holder.productNameView.setText("No Product");
        }

    }

    public void  setmProducts(List<Product> products){
        mProducts = products;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mProducts != null)
            return mProducts.size();
        else return 0;
    }

    public Product getProductAt(int position) {
        return mProducts.get(position);
    }
}
