package com.example.appadmindatvephim.Activity.XepLich;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.example.appadmindatvephim.Adapter.ScheduleAdapter;
import com.example.appadmindatvephim.DTO.Schedule;
import com.example.appadmindatvephim.R;
import com.example.appadmindatvephim.Util.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ScheduleActivity extends AppCompatActivity {

    ArrayList<Schedule> schedules;
    ScheduleAdapter scheduleAdapter;
    ListView lvschedule;
    FloatingSearchView txtsearch;
    FloatingActionButton fabadd;
    SwipeRefreshLayout swipeRefreshLayout;
    View footerview;
    volatile int vitri = 0;
    volatile boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        addControls();
        addEvents();
        loadSchedule();
    }

    private void loadSchedule() {
        RequestQueue requestQueue = Volley.newRequestQueue(ScheduleActivity.this);
        String url = String.format(Util.LINK_LOADSCHEDULE, vitri);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length() ; i++) {
                            Schedule schedule = new Schedule();
                            schedule.setId(jsonArray.getJSONObject(i).getInt("ID"));
                            schedule.setNgay(jsonArray.getJSONObject(i).getString("Ngay"));
                            schedule.setIdrap(jsonArray.getJSONObject(i).getInt("IDRapPhim"));
                            schedule.setTenrap(jsonArray.getJSONObject(i).getString("TenRap"));
                            schedules.add(schedule);
                        }
                        scheduleAdapter.notifyDataSetChanged();
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

    private void addEvents() {
        lvschedule.setOnScrollListener(new AbsListView.OnScrollListener() {
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
        lvschedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Schedule schedule = schedules.get(position);
                Intent i = new Intent(ScheduleActivity.this, DetailsScheduleActivity.class);
                i.putExtra("SCHEDULE",schedule);
                startActivity(i);
            }
        });
        fabadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ScheduleActivity.this, AddScheduleActivity.class);
                startActivity(i);
            }
        });
        txtsearch.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {
                schedules.clear();
                scheduleAdapter.notifyDataSetChanged();
                processDate();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                schedules.clear();
                scheduleAdapter.notifyDataSetChanged();
                loadSchedule();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void processDate() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(ScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                txtsearch.setSearchText(simpleDateFormat.format(calendar.getTime()));
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                searchSchedule(simpleDateFormat.format(calendar.getTime()));
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void searchSchedule(String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(ScheduleActivity.this);
        String url = String.format(Util.LINK_SEARCHSCHEDULE, data);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length() ; i++) {
                        Schedule schedule = new Schedule();
                        schedule.setId(jsonArray.getJSONObject(i).getInt("ID"));
                        schedule.setNgay(jsonArray.getJSONObject(i).getString("Ngay"));
                        schedule.setIdrap(jsonArray.getJSONObject(i).getInt("IDRapPhim"));
                        schedule.setTenrap(jsonArray.getJSONObject(i).getString("TenRap"));
                        schedules.add(schedule);
                    }
                    scheduleAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
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
        schedules = new ArrayList<>();
        scheduleAdapter = new ScheduleAdapter(ScheduleActivity.this, schedules);
        lvschedule = findViewById(R.id.lvschedule);
        lvschedule.setAdapter(scheduleAdapter);
        txtsearch = findViewById(R.id.txtsearch);
        fabadd = findViewById(R.id.fabadd);
        swipeRefreshLayout = findViewById(R.id.refeshlayoutlv);

    }

    public class LazyLoad extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lvschedule.addFooterView(footerview);
                }
            });
            vitri += 5;
            isLoading = true;
            loadSchedule();
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
            lvschedule.removeFooterView(footerview);
            scheduleAdapter.notifyDataSetChanged();
        }
    }
}