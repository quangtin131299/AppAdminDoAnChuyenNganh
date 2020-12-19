package com.example.appadmindatvephim.Activity.Cinema;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arlib.floatingsearchview.FloatingSearchView;
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
    FloatingSearchView txtsearch;   
    View footerview;
    SwipeRefreshLayout refeshlayoutlv;
    volatile boolean isLoading= false;
    volatile int vitri = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema);
        addControls();
        addEvents();
        loadDataCinema();
    }

    private void addEvents() {
        refeshlayoutlv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                vitri = 0;
                cinemas.clear();
                cinemaAdapter.notifyDataSetChanged();
                loadDataCinema();
                refeshlayoutlv.setRefreshing(false);
            }
        });
        lvcinema.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(totalItemCount != 0 && firstVisibleItem + visibleItemCount == totalItemCount && isLoading == false){
                    new LazyLoad().execute();
                }
            }
        });
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
                Intent i = new Intent(CinemaActivity.this, UpdateCinemaActivity.class);
                i.putExtra("CINEMA", cinema);
                startActivity(i);
            }
        });
        txtsearch.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                cinemas.clear();
                cinemaAdapter.notifyDataSetChanged();
                searchCinema(newQuery);
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void searchCinema(String newQuery) {
        RequestQueue requestQueue = Volley.newRequestQueue(CinemaActivity.this);
        String url = String.format(Util.LINK_SEARCHCINEMA, newQuery);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response);
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    private void loadDataCinema() {
        RequestQueue requestQueue = Volley.newRequestQueue(CinemaActivity.this);
        String url = String.format(Util.LINK_LOADDATACINEMA, vitri);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
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
        refeshlayoutlv = findViewById(R.id.refeshlayoutlv);
        footerview = getLayoutInflater().inflate(R.layout.footer_listview, null);
        fabadd = findViewById(R.id.fabadd);
        cinemas = new ArrayList<>();
        cinemaAdapter = new CinemaAdapter(CinemaActivity.this, cinemas);
        lvcinema = findViewById(R.id.lvcinema);
        lvcinema.setAdapter(cinemaAdapter);
        txtsearch = findViewById(R.id.txtsearch);
    }

    public class LazyLoad extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lvcinema.addFooterView(footerview);
                }
            });
            vitri += 5;
            isLoading = true;
            loadDataCinema();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            isLoading = false;
            lvcinema.removeFooterView(footerview);
            cinemaAdapter.notifyDataSetChanged();
        }
    }



}