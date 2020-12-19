package com.example.appadmindatvephim.Activity.Movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.example.appadmindatvephim.Adapter.MovieAdapter;
import com.example.appadmindatvephim.DTO.Movie;
import com.example.appadmindatvephim.R;
import com.example.appadmindatvephim.Util.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MovieActivity extends AppCompatActivity {

    MovieAdapter movieAdapter;
    ArrayList<Movie> movies;
    ListView lvmovie;
    FloatingActionButton fabadd;
    FloatingSearchView txtsearch;
    SwipeRefreshLayout refeshlayoutlv;
    View footerview;
    volatile boolean isLoading = false;
    volatile int vitriload = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        addControls();
        addEvents();
        loadDataMovie();



    }

    private void addEvents() {
        lvmovie.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
               //thêm điều kiện totalitemcount != 0 để chắc chắn gằng khi scroll  sẽ chạy event này

                if (totalItemCount != 0 && visibleItemCount + firstVisibleItem == totalItemCount && isLoading == false) {
                    new LazyLoad().execute();
                }
            }
        });
        lvmovie.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Movie movie = movies.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(MovieActivity.this);
                builder.setTitle("Thông báo !");
                builder.setMessage("Ban Co muon xoa " + movie.getTenmovie() + " khong !");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        deleteMovie(movie.getId());
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

                return true;
            }
        });
        lvmovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = movies.get(position);
                Intent i = new Intent(MovieActivity.this, UpdateMovieActivity.class);
                i.putExtra("MOVIE", movie);
                startActivity(i);
            }
        });

        fabadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MovieActivity.this, AddMovieActivity.class);
                startActivity(i);
            }
        });
        refeshlayoutlv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                vitriload = 0;
                movies.clear();
                movieAdapter.notifyDataSetChanged();
                loadDataMovie();

                refeshlayoutlv.setRefreshing(false);
            }
        });
        txtsearch.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                movies.clear();
                movieAdapter.notifyDataSetChanged();
                loadDataSearch(newQuery);
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void deleteMovie(final int idphim) {
        RequestQueue requestQueue = Volley.newRequestQueue(MovieActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Util.LINK_DELETEMOVIE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> maps = new HashMap<>();
                maps.put("idphim", String.valueOf(idphim));
                return maps;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void loadDataSearch(String tenphim) {
        RequestQueue requestQueue = Volley.newRequestQueue(MovieActivity.this);
        String url = String.format(Util.LINK_SEARCHMOVIE, tenphim);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Movie movie = new Movie();
                            movie.setId(jsonArray.getJSONObject(i).getInt("ID"));
                            movie.setTenmovie(jsonArray.getJSONObject(i).getString("TenPhim"));
                            movie.setHinh(jsonArray.getJSONObject(i).getString("Hinh"));
                            movie.setTrangthai(jsonArray.getJSONObject(i).getString("TrangThai"));
                            movie.setThoigian(jsonArray.getJSONObject(i).getInt("ThoiGian"));
                            movie.setTrailerid(jsonArray.getJSONObject(i).getString("Trailer"));
                            movies.add(movie);
                            movieAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("//////", error.getMessage());
            }
        });
        requestQueue.add(stringRequest);
    }

    private void loadDataMovie() {
        RequestQueue requestQueue = Volley.newRequestQueue(MovieActivity.this);
        String url = String.format(Util.LINK_LOADMOVIE, vitriload);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Movie movie = new Movie();
                            movie.setId(jsonArray.getJSONObject(i).getInt("ID"));
                            movie.setTenmovie(jsonArray.getJSONObject(i).getString("TenPhim"));
                            movie.setHinh(jsonArray.getJSONObject(i).getString("Hinh"));
                            movie.setTrangthai(jsonArray.getJSONObject(i).getString("TrangThai"));
                            movie.setThoigian(jsonArray.getJSONObject(i).getInt("ThoiGian"));
                            movie.setTrailerid(jsonArray.getJSONObject(i).getString("Trailer"));
                            movie.setMota(jsonArray.getJSONObject(i).getString("MoTa"));
                            movie.setNgaykhoichieu(jsonArray.getJSONObject(i).getString("NgayKhoiChieu"));
                            movies.add(movie);
                        }
                        movieAdapter.notifyDataSetChanged();

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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private void addControls() {
        footerview = getLayoutInflater().inflate(R.layout.footer_listview, null);
        txtsearch = findViewById(R.id.txtsearch);
        movies = new ArrayList<>();
        movieAdapter = new MovieAdapter(MovieActivity.this, movies);
        lvmovie = findViewById(R.id.lvmovie);
        lvmovie.setAdapter(movieAdapter);
        fabadd = findViewById(R.id.fabadd);
        refeshlayoutlv = findViewById(R.id.refeshlayoutlv);
    }

    public class LazyLoad extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lvmovie.addFooterView(footerview);
                }
            });
            vitriload += 5;
            isLoading = true;
            loadDataMovie();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            isLoading = false;
            lvmovie.removeFooterView(footerview);
            movieAdapter.notifyDataSetChanged();

        }
    }

}

