package com.kayum.mamun.androidroompersistencecrud.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kayum.mamun.androidroompersistencecrud.R;

public class NewProductActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY_PRODUCT_ID = "com.kayum.mamun.roomapp.EXTRA_ID";
    public static final String EXTRA_REPLY_PRODUCT_NAME = "com.kayum.mamun.roomapp.NAME";
    public static final String EXTRA_REPLY_PRODUCT_DESCRIPTION = "com.kayum.mamun.roomapp.DESCRIPTION";
    public static final String EXTRA_REPLY_PRODUCT_QUANTITY = "com.kayum.mamun.roomapp.QUANTITY";

    private EditText mEditProductNameView, mEditProductDescriptionView, mEditProductQuantityView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        mEditProductNameView = findViewById(R.id.edit_product_name);
        mEditProductDescriptionView = findViewById(R.id.edit_product_description);
        mEditProductQuantityView = findViewById(R.id.edit_product_quantity);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_REPLY_PRODUCT_ID)) {
            setTitle("Edit Product");
            mEditProductNameView.setText(intent.getStringExtra(EXTRA_REPLY_PRODUCT_NAME));
            mEditProductDescriptionView.setText(intent.getStringExtra(EXTRA_REPLY_PRODUCT_DESCRIPTION));
           // int quantity = intent.getIntExtra(EXTRA_REPLY_PRODUCT_QUANTITY,-1);
            //Log.v("Quantity", Integer.toString(quantity));
            mEditProductQuantityView.setText(intent.getStringExtra(EXTRA_REPLY_PRODUCT_QUANTITY));

        } else {
            setTitle("Add Product");
        }

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditProductNameView.getText()) || TextUtils.isEmpty(mEditProductDescriptionView.getText()) || TextUtils.isEmpty(mEditProductQuantityView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String productName = mEditProductNameView.getText().toString();
                    String productDescription = mEditProductDescriptionView.getText().toString();
                    String productQuantity = mEditProductQuantityView.getText().toString();

                    replyIntent.putExtra(EXTRA_REPLY_PRODUCT_NAME, productName);
                    replyIntent.putExtra(EXTRA_REPLY_PRODUCT_DESCRIPTION, productDescription);
                    replyIntent.putExtra(EXTRA_REPLY_PRODUCT_QUANTITY, productQuantity);

                    int id = getIntent().getIntExtra(EXTRA_REPLY_PRODUCT_ID, -1);
                    if (id != -1) {
                        replyIntent.putExtra(EXTRA_REPLY_PRODUCT_ID, id);
                    }

                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
