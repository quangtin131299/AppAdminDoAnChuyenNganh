package com.example.appadmindatvephim.Activity.Ticker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appadmindatvephim.DTO.Ticker;
import com.example.appadmindatvephim.R;

public class DetailTickerActivity extends AppCompatActivity {

    TextView txtidticker, txttengnguoidat, txttenphong,txttenphim, txtsuatchieu, txttenghe, txttenrap,txttrangthai;
    ImageView imgback;
    Ticker ticker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ticker);
        addControls();
        getIntentData();
        addEvents();
        updateUI();
    }

    private void updateUI() {
        if(ticker != null){
            txtidticker.setText(ticker.getId() + "");
            txttengnguoidat.setText(ticker.getTenkhachhang());
            txttenphong.setText(ticker.getTenphong());
            txttenphim.setText(ticker.getTenphim());
            txtsuatchieu.setText(ticker.getSuatchieu());
            txttenghe.setText(ticker.getTenghe());
            txttenrap.setText(ticker.getTenrap());
            txttrangthai.setText(ticker.getTrangthai());

        }
    }

    private void getIntentData() {
        Intent i = getIntent();
        if(i.hasExtra("TICKER")){
            ticker = (Ticker) i.getSerializableExtra("TICKER");
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
        txtidticker = findViewById(R.id.txtidticker);
        txttengnguoidat = findViewById(R.id.txttengnguoidat);
        txttenphong = findViewById(R.id.txttenphong);
        txttenphim = findViewById(R.id.txttenphim);
        txtsuatchieu = findViewById(R.id.txtsuatchieu);
        txttenghe = findViewById(R.id.txttenghe);
        txttenrap = findViewById(R.id.txttenrap);
        txttrangthai = findViewById(R.id.txttrangthai);
        imgback = findViewById(R.id.imgback);



    }
}