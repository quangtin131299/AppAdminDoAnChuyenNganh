package com.example.appadmindatvephim.Activity.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appadmindatvephim.DTO.Customer;
import com.example.appadmindatvephim.R;

public class DetailCustomerActivity extends AppCompatActivity {

    TextView txtid, txtnamecustomer, txtemail, txtngaysinh, txtsdt;
    ImageView imgback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_customer);
        addControls();
        updateUI();
        addEvents();
    }

    private void updateUI() {
        Intent i = getIntent();
        if (i.hasExtra("CUSTOMER")) {
            Customer customer = (Customer) i.getSerializableExtra("CUSTOMER");
            txtid.setText(customer.getId() + "");
            txtnamecustomer.setText(customer.getHoten());
            txtemail.setText(customer.getEmail());
            txtngaysinh.setText(customer.getNgaysinh());
            txtsdt.setText(customer.getSdt());

        }
    }

    private void addEvents() {
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addControls() {
        txtsdt = findViewById(R.id.txtsdt);
        txtngaysinh = findViewById(R.id.txtngaysinh);
        txtemail = findViewById(R.id.txtemail);
        txtnamecustomer = findViewById(R.id.txtnamecustomer);
        txtid = findViewById(R.id.txtid);
        imgback = findViewById(R.id.imgback);

    }
}