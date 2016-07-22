package com.theleafapps.shopnick.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.theleafapps.shopnick.R;
import com.theleafapps.shopnick.models.Customer;
import com.theleafapps.shopnick.tasks.GetCustomerByIdTask;

import java.util.concurrent.ExecutionException;

public class CustomerProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    ActionBar actionBar;

    TextView c_first_name_value,c_last_name_value;
    TextView c_address_value,c_zipcode_value,c_country_value;
    TextView c_mobile_value,c_wallet_amount_value;
    Button c_profile_shop_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        toolbar = (Toolbar) findViewById(R.id.toolbar_customer_profile);
        setSupportActionBar(toolbar);

        actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Your Profile");

        c_first_name_value      =   (TextView) findViewById(R.id.c_first_name_value);
        c_last_name_value       =   (TextView) findViewById(R.id.c_last_name_value);
        c_address_value         =   (TextView) findViewById(R.id.c_address_value);
        c_zipcode_value         =   (TextView) findViewById(R.id.c_zipcode_value);
        c_country_value         =   (TextView) findViewById(R.id.c_country_value);
        c_mobile_value          =   (TextView) findViewById(R.id.c_mobile_value);
        c_wallet_amount_value   =   (TextView) findViewById(R.id.c_wallet_amount_value);
        c_profile_shop_button   =   (Button) findViewById(R.id.c_profile_shop_button);

        SharedPreferences sharedPreferences = getSharedPreferences("Shopnick", Context.MODE_PRIVATE);
        String cid = sharedPreferences.getString("cid","");

        try {
            if (!TextUtils.isEmpty(cid)) {

                int id = Integer.parseInt(cid);

                GetCustomerByIdTask getCustomerByIdTask = new GetCustomerByIdTask(this, id);
                getCustomerByIdTask.execute().get();

                Customer customer = getCustomerByIdTask.customerRec;

                if(customer != null){

                    c_first_name_value.setText(customer.first_name);
                    c_last_name_value.setText(customer.last_name);
                    c_address_value.setText(customer.address);
                    c_zipcode_value.setText(customer.zipcode);
                    c_country_value.setText(customer.country);
                    c_mobile_value.setText(customer.mobile);
                    c_wallet_amount_value.setText(String.valueOf(customer.wallet_value));

                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        c_profile_shop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerProfileActivity.this,ShowcaseActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}
