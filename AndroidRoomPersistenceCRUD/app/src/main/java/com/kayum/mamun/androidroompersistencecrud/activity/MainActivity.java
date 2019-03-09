package com.kayum.mamun.androidroompersistencecrud.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kayum.mamun.androidroompersistencecrud.R;
import com.kayum.mamun.androidroompersistencecrud.adapter.ProductListAdapter;
import com.kayum.mamun.androidroompersistencecrud.adapter.RecyclerItemClickListener;
import com.kayum.mamun.androidroompersistencecrud.entity.Product;
import com.kayum.mamun.androidroompersistencecrud.utils.ApplicationStrings;
import com.kayum.mamun.androidroompersistencecrud.viewmodel.ProductViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_WORD_ACTIVITY_REQUEST_CODE = 2;

    private ProductViewModel mProductViewModel;
    private Button editButton, deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ProductListAdapter adapter = new ProductListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        // Get a new or existing ViewModel from the ViewModelProvider.
        mProductViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mProductViewModel.getmAllProduct().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable final List<Product> products) {
                // Update the cached copy of the words in the adapter.
                adapter.setmProducts(products);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewProductActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, final int position) {
                        editButton = v.findViewById(R.id.button_edit);
                        //Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();
                        deleteButton = v.findViewById(R.id.button_delete);

                        deleteButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), "Delete button is called"+adapter.getProductAt(position).getProductName() , Toast.LENGTH_SHORT).show();

                                mProductViewModel.delete(adapter.getProductAt(position));
                            }
                        });

                        editButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), "Edit button is called"+adapter.getProductAt(position).getProductName() , Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, NewProductActivity.class);
                                intent.putExtra(ApplicationStrings.EXTRA_REPLY_PRODUCT_ID, adapter.getProductAt(position).getProductId());
                                intent.putExtra(ApplicationStrings.EXTRA_REPLY_PRODUCT_NAME, adapter.getProductAt(position).getProductName());
                                intent.putExtra(ApplicationStrings.EXTRA_REPLY_PRODUCT_DESCRIPTION, adapter.getProductAt(position).getProductDescription());
                                intent.putExtra(ApplicationStrings.EXTRA_REPLY_PRODUCT_QUANTITY,  Integer.toString(adapter.getProductAt(position).getProductQuantity()));
                                startActivityForResult(intent, EDIT_WORD_ACTIVITY_REQUEST_CODE);
                            }
                        });
                    }
                })
        );


    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String productName = data.getStringExtra(ApplicationStrings.EXTRA_REPLY_PRODUCT_NAME);
            String productDescription = data.getStringExtra(ApplicationStrings.EXTRA_REPLY_PRODUCT_DESCRIPTION);
            String productQuantity = data.getStringExtra(ApplicationStrings.EXTRA_REPLY_PRODUCT_QUANTITY);
            int quantity = Integer.parseInt(productQuantity);

            Product product = new Product(productName,productDescription, System.currentTimeMillis(),quantity,true);
            mProductViewModel.insert(product);
        } else if (requestCode == EDIT_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            int id = data.getIntExtra(ApplicationStrings.EXTRA_REPLY_PRODUCT_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String productName = data.getStringExtra(ApplicationStrings.EXTRA_REPLY_PRODUCT_NAME);
            String productDescription = data.getStringExtra(ApplicationStrings.EXTRA_REPLY_PRODUCT_DESCRIPTION);
            String productQuantity = data.getStringExtra(ApplicationStrings.EXTRA_REPLY_PRODUCT_QUANTITY);
            int quantity = Integer.parseInt(productQuantity);

            Product product = new Product(productName,productDescription, System.currentTimeMillis(),quantity,true);
            product.setProductId(id);
            mProductViewModel.update(product);

            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }



}
