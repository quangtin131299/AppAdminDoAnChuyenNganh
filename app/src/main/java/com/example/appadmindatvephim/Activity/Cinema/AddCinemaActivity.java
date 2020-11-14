package com.example.appadmindatvephim.Activity.Cinema;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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
import com.example.appadmindatvephim.R;
import com.example.appadmindatvephim.Util.Util;

import java.util.HashMap;
import java.util.Map;

public class AddCinemaActivity extends AppCompatActivity {

    EditText edtnamemovie, edtimgcinema, edtdiachi;
    Button btnThemCinema;
    ImageView imgback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cinema);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnThemCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenrap = edtnamemovie.getText().toString().trim();
                String hinhrap = edtimgcinema.getText().toString().trim();
                String diachi = edtimgcinema.getText().toString().trim();
                insertCinema(tenrap, hinhrap, diachi);
            }
        });
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void insertCinema(final String tenrap, final String hinhrap, final String diachi) {
        RequestQueue requestQueue = Volley.newRequestQueue(AddCinemaActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Util.LINK_ADDCINEMA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null && response.equals("") == false) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddCinemaActivity.this);
                    builder.setMessage(response);
                    builder.setTitle("Thông Báo");
                    builder.show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddCinemaActivity.this);
                    builder.setMessage("Thêm thất bại !");
                    builder.setTitle("Thông Báo");
                    builder.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> maps = new HashMap<>();
                maps.put("tencinema", tenrap);
                maps.put("hinhcinema", hinhrap);
                maps.put("diachicinema", diachi);
                return maps;

            }
        };

        requestQueue.add(stringRequest);
    }

    private void addControls() {
        edtnamemovie = findViewById(R.id.edtnamemovie);
        edtimgcinema = findViewById(R.id.edtimgcinema);
        edtdiachi = findViewById(R.id.edtdiachi);
        btnThemCinema = findViewById(R.id.btnthemrapmoi);
        imgback = findViewById(R.id.imgback);
    }
}