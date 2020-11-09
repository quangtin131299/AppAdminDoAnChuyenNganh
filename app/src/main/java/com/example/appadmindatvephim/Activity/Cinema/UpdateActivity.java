package com.example.appadmindatvephim.Activity.Cinema;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.appadmindatvephim.DTO.Cinema;
import com.example.appadmindatvephim.R;

public class UpdateActivity extends AppCompatActivity {

    EditText edtid,edttenrap, edtdiachi,edthinh;
    Cinema cinema;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        addControls();
        getIntentData();
        updateUI();
    }

    private void updateUI() {
        if(cinema != null){
            edtid.setText(cinema.getiD() + "");
            edttenrap.setText(cinema.getTenRap());
            edtdiachi.setText(cinema.getDiaChi());
            edthinh.setText(cinema.getHinh());
        }
    }

    private void getIntentData() {
        Intent i = getIntent();
        if(i.hasExtra("CINEMA")){
            cinema = (Cinema) i.getSerializableExtra("CINEMA");
        }
    }

    private void addControls() {
        edtid = findViewById(R.id.edtid);
        edttenrap = findViewById(R.id.edttenrap);
        edtdiachi = findViewById(R.id.edtdiachi);
        edthinh = findViewById(R.id.edthinh);
    }
}