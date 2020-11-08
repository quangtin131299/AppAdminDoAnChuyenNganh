package com.example.appadmindatvephim.Activity.Movie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appadmindatvephim.DTO.Movie;
import com.example.appadmindatvephim.R;
import com.example.appadmindatvephim.Util.Util;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UpdateMovieActivity extends AppCompatActivity {

    EditText edtidmovie, edtnamemovie, edtimgphim, edtthoigian, edttrailer, edtmota, edtngaykhoichieu;
    RadioGroup radiogroup;
    Button btnupdate;
    ImageView imgmovie, imgback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_movie);
        addControls();
        addEvents();
        getIntentData();
    }

    private void addEvents() {
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idmovie = Integer.parseInt(edtidmovie.getText().toString().trim());
                String tenphim = edtnamemovie.getText().toString().trim();
                String hinhphim = edtimgphim.getText().toString().trim();
                String trangthai = "";
                if (radiogroup.getCheckedRadioButtonId() == R.id.rdodachieu) {
                    trangthai = "Đã chiếu";
                } else if (radiogroup.getCheckedRadioButtonId() == R.id.rdosapchieu) {
                    trangthai = "Sắp chiếu";
                } else {
                    trangthai = "Đang chiếu";
                }
                String mota = edtmota.getText().toString().trim();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
                String ngaykhoichieu;
                try {
                    Date temp = simpleDateFormat.parse(edtngaykhoichieu.getText().toString().trim());
                    ngaykhoichieu = output.format(temp);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                int thoigian = Integer.parseInt(edtthoigian.getText().toString().trim());
                String trailer = edttrailer.getText().toString().trim();

                updateMovie(idmovie, tenphim, hinhphim, trangthai, thoigian, trailer);
            }
        });
    }

    private void updateMovie(final int idmovie, final String tenphim, final String hinhphim, final String trangthai, final int thoigian, final String trailer) {
        RequestQueue requestQueue = Volley.newRequestQueue(UpdateMovieActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Util.LINK_UPDATEMOVIE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    Toast.makeText(UpdateMovieActivity.this, response, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("//////", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> maps = new HashMap<>();
                maps.put("maphim", String.valueOf(idmovie));
                maps.put("tenphim", tenphim);
                maps.put("hinhphim", hinhphim);
                maps.put("trangthai", trangthai);
                maps.put("thoigian", String.valueOf(thoigian));
                maps.put("idtrailer", trailer);
                return maps;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(2000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private void getIntentData() {
        Intent i = getIntent();
        if (i.hasExtra("MOVIE")) {
            Movie movie = (Movie) i.getSerializableExtra("MOVIE");
            edtidmovie.setText(movie.getId() + "");
            edtnamemovie.setText(movie.getTenmovie());
            edtimgphim.setText(movie.getHinh());
            edttrailer.setText(movie.getTrailerid());
            edtthoigian.setText(movie.getThoigian() + "");
            edtmota.setText(movie.getMota());
            edtngaykhoichieu.setText(movie.getNgaykhoichieu());

            if (movie.getTrangthai().equalsIgnoreCase("Đang chiếu")) {
                radiogroup.check(R.id.rdodangchieu);
            } else if (movie.getTrangthai().equalsIgnoreCase("Sắp chiếu")) {
                radiogroup.check(R.id.rdosapchieu);
            } else {
                radiogroup.check(R.id.rdodachieu);
            }
            Picasso.get().load(movie.getHinh()).into(imgmovie);
        }
    }

    private void addControls() {
        imgmovie = findViewById(R.id.imgmovie);
        radiogroup = findViewById(R.id.radiogroup);
        btnupdate = findViewById(R.id.btnupdate);
        edtidmovie = findViewById(R.id.edtidmovie);
        edtnamemovie = findViewById(R.id.edtnamemovie);
        edtimgphim = findViewById(R.id.edtimgphim);
        edtthoigian = findViewById(R.id.edtthoigian);
        edttrailer = findViewById(R.id.edttrailer);
        edtmota = findViewById(R.id.edtmota);
        edtngaykhoichieu = findViewById(R.id.edtngaykhoichieu);
        imgback = findViewById(R.id.imgback);
    }


}