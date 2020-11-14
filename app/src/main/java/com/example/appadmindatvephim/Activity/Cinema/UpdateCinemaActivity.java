package com.example.appadmindatvephim.Activity.Cinema;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appadmindatvephim.DTO.Cinema;
import com.example.appadmindatvephim.R;
import com.example.appadmindatvephim.Util.Util;

import java.util.HashMap;
import java.util.Map;

public class UpdateCinemaActivity extends AppCompatActivity {

    EditText edtid, edttenrap, edtdiachi, edthinh;
    Cinema cinema;
    Button btnupdate;
    ImageView imgback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        addControls();
        addEvents();
        getIntentData();
        updateUI();


    }

    private void addEvents() {
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idcinema = Integer.parseInt(edtid.getText().toString().trim());
                String tenrap = edttenrap.getText().toString().trim();
                String hinhrap = edthinh.getText().toString().trim();
                String diachi = edtdiachi.getText().toString().trim();
                updateCinema(idcinema,tenrap, hinhrap, diachi);
            }
        });
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updateCinema(final int idcinema, final String tenrap, final String hinhrap, final String diachi) {
        RequestQueue requestQueue = Volley.newRequestQueue(UpdateCinemaActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Util.LINK_UPDATECINEMA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response!=null && response.equals("") == false){
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateCinemaActivity.this);
                    builder.setTitle("Thong bao");
                    builder.setMessage("Cap nhat thanh cong !");
                    builder.show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateCinemaActivity.this);
                    builder.setTitle("Thong bao");
                    builder.setMessage("Cap nhat that bai !");
                    builder.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> maps = new HashMap<>();
                maps.put("idcinema", String.valueOf(idcinema));
                maps.put("tencinema", tenrap);
                maps.put("hinhcinema",hinhrap);
                maps.put("diachicinema",diachi);
                return maps;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void updateUI() {
        if (cinema != null) {
            edtid.setText(cinema.getiD() + "");
            edttenrap.setText(cinema.getTenRap());
            edtdiachi.setText(cinema.getDiaChi());
            edthinh.setText(cinema.getHinh());
        }
    }

    private void getIntentData() {
        Intent i = getIntent();
        if (i.hasExtra("CINEMA")) {
            cinema = (Cinema) i.getSerializableExtra("CINEMA");
        }
    }

    private void addControls() {
        edtid = findViewById(R.id.edtid);
        edttenrap = findViewById(R.id.edttenrap);
        edtdiachi = findViewById(R.id.edtdiachi);
        edthinh = findViewById(R.id.edthinh);
        btnupdate = findViewById(R.id.btnupdate);
        imgback = findViewById(R.id.imgback);
    }
}