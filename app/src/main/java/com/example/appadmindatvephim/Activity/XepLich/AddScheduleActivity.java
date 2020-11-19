package com.example.appadmindatvephim.Activity.XepLich;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appadmindatvephim.Activity.Cinema.CinemaActivity;
import com.example.appadmindatvephim.DTO.Cinema;
import com.example.appadmindatvephim.DTO.ScheduleBooking;
import com.example.appadmindatvephim.R;
import com.example.appadmindatvephim.Util.Util;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddScheduleActivity extends AppCompatActivity {

    ArrayAdapter arrayAdapter;
    ArrayList<Cinema> cinemas;
    ListView lvcinema;
    Button btnchonngay, btnnext;
    EditText edtngay;
    ScheduleBooking scheduleBooking = new ScheduleBooking();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        addControls();
        addEvents();
        loadDataCinema();
    }

    private void loadDataCinema() {
        RequestQueue requestQueue = Volley.newRequestQueue(AddScheduleActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Util.LINK_LOADDATACINEMA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
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
                        arrayAdapter.notifyDataSetChanged();
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

    private void addEvents() {
        lvcinema.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cinema cinema = cinemas.get(position);
                if (cinema != null) {
                    scheduleBooking.setIdrap(cinema.getiD());
                }
            }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtngay.getText().toString().equals("") == false && scheduleBooking.getIdrap() != 0) {
                    Intent i = new Intent(AddScheduleActivity.this, MovieScheduleBookingActivity.class);
                    i.putExtra("DATABOOKING", scheduleBooking);
                    startActivity(i);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddScheduleActivity.this);
                    builder.setTitle("Thông báo !");
                    builder.setMessage("Bạn cần chọn đủ thông tin !");
                    builder.show();
                }

            }
        });
        btnchonngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processChooseDay();
            }
        });
    }

    private void processChooseDay() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (year >= calendar.get(Calendar.YEAR) && month >= calendar.get(Calendar.MONTH) && dayOfMonth >= calendar.get(Calendar.DAY_OF_MONTH)) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    edtngay.setText(simpleDateFormat.format(calendar.getTime()));
                    scheduleBooking.setNgay(formatDate(simpleDateFormat.format(calendar.getTime())));
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddScheduleActivity.this);
                    builder.setTitle("Thông Báo !");
                    builder.setMessage("Ngày tháng không hợp lệ !");
                    builder.show();
                }

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void addControls() {
        btnnext = findViewById(R.id.btnnext);
        cinemas = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(AddScheduleActivity.this, android.R.layout.simple_list_item_single_choice, cinemas);
        btnchonngay = findViewById(R.id.btnchonngay);
        edtngay = findViewById(R.id.edtngay);
        lvcinema = findViewById(R.id.lvcinema);
        lvcinema.setAdapter(arrayAdapter);
        lvcinema.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
    }

    public String formatDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat ouput = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date temp = simpleDateFormat.parse(date);
            return ouput.format(temp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";

    }
}