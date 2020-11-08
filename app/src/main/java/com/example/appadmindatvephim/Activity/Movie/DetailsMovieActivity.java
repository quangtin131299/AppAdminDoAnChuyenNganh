package com.example.appadmindatvephim.Activity.Movie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appadmindatvephim.DTO.LoaiPhim;
import com.example.appadmindatvephim.DTO.Movie;
import com.example.appadmindatvephim.R;
import com.example.appadmindatvephim.Util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailsMovieActivity extends AppCompatActivity {

    ArrayList<LoaiPhim> loaiPhims;
    ListView lvtheloai;
    ArrayAdapter theloaiadapter;
    Button btnthemphimmoi;
    Movie movie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);
        addControls();
        getIntentData();
        loadDataLoai();
        addEvents();

    }

    private void temp() {
       
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra("MOVIE")) {
            movie = (Movie) intent.getSerializableExtra("MOVIE");
        }
    }

    private void addEvents() {

        btnthemphimmoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<LoaiPhim> loaphimselects = new ArrayList<>();
                SparseBooleanArray sparseBooleanArray = lvtheloai.getCheckedItemPositions();
                for (int i = 0; i < sparseBooleanArray.size(); i++) {
                    int position = sparseBooleanArray.keyAt(i);
                    LoaiPhim loaiPhim = loaiPhims.get(position);
                    loaphimselects.add(loaiPhim);
                }
                addMovie(loaphimselects);

            }
        });


    }

    private void addMovie(final ArrayList<LoaiPhim> loaphimselects) {
        RequestQueue requestQueue = Volley.newRequestQueue(DetailsMovieActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Util.LINK_ADDMOVIE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> maps = new HashMap<>();
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < loaphimselects.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("id", loaphimselects.get(i).getId());
                        jsonObject.put("tenloai", loaphimselects.get(i).getTenloai());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(jsonObject);
                }

                maps.put("tenphim", movie.getTenmovie());
                maps.put("hinhphim", movie.getHinh());
                maps.put("trangthai", movie.getTrangthai());
                maps.put("thoigian", String.valueOf(movie.getThoigian()));
                maps.put("idtrailer", movie.getTrailerid());
                maps.put("ngaykhoichieu",movie.getNgaykhoichieu());
                maps.put("mota", movie.getMota());
                maps.put("arrloaiphim", jsonArray.toString());
                return maps;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }


    public void addControls() {
        loaiPhims = new ArrayList<>();
        theloaiadapter = new ArrayAdapter(DetailsMovieActivity.this, android.R.layout.simple_list_item_multiple_choice, loaiPhims);
        lvtheloai = findViewById(R.id.lvtheloai);
        lvtheloai.setAdapter(theloaiadapter);
        lvtheloai.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        btnthemphimmoi = findViewById(R.id.btnthemphimmoi);
    }


    public void loadDataLoai() {
        RequestQueue requestQueue = Volley.newRequestQueue(DetailsMovieActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Util.LINK_LOADTHELOAI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            LoaiPhim loaiPhim = new LoaiPhim();
                            loaiPhim.setId(jsonArray.getJSONObject(i).getInt("ID"));
                            loaiPhim.setTenloai(jsonArray.getJSONObject(i).getString("TenLoai"));
                            loaiPhims.add(loaiPhim);
                        }
                        theloaiadapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

}