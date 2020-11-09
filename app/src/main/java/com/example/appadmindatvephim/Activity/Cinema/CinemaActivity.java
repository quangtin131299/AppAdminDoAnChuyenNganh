package com.example.appadmindatvephim.Activity.Cinema;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appadmindatvephim.Adapter.CinemaAdapter;
import com.example.appadmindatvephim.DTO.Cinema;
import com.example.appadmindatvephim.R;
import com.example.appadmindatvephim.Util.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class CinemaActivity extends AppCompatActivity {

    ArrayList<Cinema> cinemas;
    CinemaAdapter cinemaAdapter;
    ListView lvcinema;
    FloatingActionButton fabadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema);
        addControls();
        addEvents();
        loadDataCinema();
    }

    private void addEvents() {
        fabadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CinemaActivity.this, AddCinemaActivity.class);
                startActivity(i);
            }
        });
        lvcinema.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cinema cinema = cinemas.get(position);
                Intent i = new Intent(CinemaActivity.this, UpdateActivity.class);
                i.putExtra("CINEMA", cinema);
                startActivity(i);
            }
        });
    }

    private void loadDataCinema() {
        RequestQueue requestQueue = Volley.newRequestQueue(CinemaActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Util.LINK_LOADDATACINEMA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Cinema cinema = new Cinema();
                            cinema.setiD(jsonArray.getJSONObject(i).getInt("ID"));
                            cinema.setTenRap(jsonArray.getJSONObject(i).getString("TenRap"));
                            cinema.setHinh(jsonArray.getJSONObject(i).getString("Hinh"));
                            cinema.setDiaChi(jsonArray.getJSONObject(i).getString("DiaChi"));
                            cinemas.add(cinema);
                        }
                        cinemaAdapter.notifyDataSetChanged();
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

    private void addControls() {
        fabadd = findViewById(R.id.fabadd);
        cinemas = new ArrayList<>();
        cinemaAdapter = new CinemaAdapter(CinemaActivity.this, cinemas);
        lvcinema = findViewById(R.id.lvcinema);
        lvcinema.setAdapter(cinemaAdapter);

    }
}