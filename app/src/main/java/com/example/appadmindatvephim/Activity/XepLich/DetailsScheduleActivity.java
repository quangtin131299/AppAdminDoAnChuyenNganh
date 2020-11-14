package com.example.appadmindatvephim.Activity.XepLich;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appadmindatvephim.Adapter.DetailsScheduleAdapter;
import com.example.appadmindatvephim.DTO.DetailSchedule;
import com.example.appadmindatvephim.DTO.Schedule;
import com.example.appadmindatvephim.DTO.ShowTime;
import com.example.appadmindatvephim.R;
import com.example.appadmindatvephim.Util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DetailsScheduleActivity extends AppCompatActivity {

    ArrayList<DetailSchedule> detailSchedules;
    DetailsScheduleAdapter detailsScheduleAdapter;
    ListView lvchitietlichchieu;
    TextView txtngaylichchieu;
    ImageView imgback;
    Schedule schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_schedule);
        addControls();
        addEvents();
        getIntentData();
        loadDetailsSchedule();
    }

    private void getIntentData() {
        Intent i = getIntent();
        if(i.hasExtra("SCHEDULE")){
            schedule= (Schedule) i.getSerializableExtra("SCHEDULE");
            txtngaylichchieu.setText(schedule.getNgay());
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

    private void loadDetailsSchedule() {
        RequestQueue requestQueue = Volley.newRequestQueue(DetailsScheduleActivity.this);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat ouput = new SimpleDateFormat("yyyy-MM-dd");
        String url = "";
        try {
            Date temp = simpleDateFormat.parse(schedule.getNgay());
            url = String.format(Util.LINK_LOADDETAILSCHEDULE, schedule.getIdrap(),ouput.format(temp));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET,url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length() ; i++) {
                        DetailSchedule detailSchedule = new DetailSchedule();
                        detailSchedule.setHinhphim(jsonArray.getJSONObject(i).getString("Hinh"));
                        detailSchedule.setTenphim(jsonArray.getJSONObject(i).getString("TenPhim"));
                        detailSchedule.setIdphim(jsonArray.getJSONObject(i).getInt("ID"));
                        detailSchedule.setShowTimes(new ArrayList<ShowTime>());
                        JSONArray jsonArray1 = jsonArray.getJSONObject(i).getJSONArray("suatchieu");
                        for(int j = 0 ; j < jsonArray1.length(); j++){
                            JSONObject jsonObject = jsonArray1.getJSONObject(j);
                            ShowTime showTime = new ShowTime();
                            showTime.setId(jsonObject.getInt("idsuat"));
                            showTime.setGio(jsonObject.getString("gio"));
                            detailSchedule.getShowTimes().add(showTime);
                        }

                        detailSchedules.add(detailSchedule);

                    }
                    detailsScheduleAdapter.notifyDataSetChanged();
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

    private void addControls() {
        imgback = findViewById(R.id.imgback);
        txtngaylichchieu = findViewById(R.id.txtngaylichchieu);
        detailSchedules = new ArrayList<>();
        detailsScheduleAdapter = new DetailsScheduleAdapter(DetailsScheduleActivity.this, detailSchedules);
        lvchitietlichchieu = findViewById(R.id.lvchitietlichchieu);
        lvchitietlichchieu.setAdapter(detailsScheduleAdapter);

    }
}