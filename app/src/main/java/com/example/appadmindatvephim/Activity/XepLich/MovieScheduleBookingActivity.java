package com.example.appadmindatvephim.Activity.XepLich;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appadmindatvephim.Activity.Movie.MovieActivity;
import com.example.appadmindatvephim.DTO.Movie;
import com.example.appadmindatvephim.DTO.Room;
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
import java.util.HashMap;
import java.util.Map;

public class MovieScheduleBookingActivity extends AppCompatActivity {

    EditText edtnamemovie;
    Button btnchooicetime, btnsubmit;

    ArrayList<Movie> movies;
    ArrayList<Room> rooms;

    ArrayAdapter phimadapter;
    ArrayAdapter phongadapter;

    ListView lvphim;
    ListView lvphong;

    ScheduleBooking scheduleBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_schedule_booking);
        addControls();
        getIntentData();
        addEvents();
        loadDataPhong();
        loadDataMovie();
    }

    private void getIntentData() {
        Intent i = getIntent();
        if (i.hasExtra("DATABOOKING")) {
            scheduleBooking = (ScheduleBooking) i.getSerializableExtra("DATABOOKING");
        }
    }

    private void addEvents() {
        lvphong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Room room = rooms.get(position);
                if (room != null) {
                    scheduleBooking.setIdphong(room.getId());
                }
            }
        });
        lvphim.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = movies.get(position);
                if (movie != null) {
                    scheduleBooking.setIdphim(movie.getId());
                }
            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scheduleBooking != null) {
                    String ngay = scheduleBooking.getNgay();
                    String gio = scheduleBooking.getGio();
                    int idrap = scheduleBooking.getIdrap();
                    int idphong = scheduleBooking.getIdphong();
                    int idphim = scheduleBooking.getIdphim();
                    if (ngay.equals("") == false && gio.equals("") == false && idrap != 0 && idphong != 0 && idphim != 0) {
                        processScheduleBooking(ngay, gio, idrap, idphong, idphim);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MovieScheduleBookingActivity.this);
                        builder.setTitle("Thông Báo !");
                        builder.setMessage("Thông tin bị thiếu!");
                        builder.show();
                    }
                }

            }
        });
        btnchooicetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processTime();
            }
        });
    }

    private void processScheduleBooking(final String ngay, final String gio, final int idrap, final int idphong, final int idphim) {
        RequestQueue requestQueue = Volley.newRequestQueue(MovieScheduleBookingActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Util.LINK_XEPLICH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    Log.d("////", response);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MovieScheduleBookingActivity.this);
                    builder.setTitle("Thông Báo !");
                    builder.setMessage("Xếp lịch thành công !");
                    builder.show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("//////", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> maps = new HashMap<>();
                maps.put("idrapphim", String.valueOf(idrap));
                maps.put("ngay", ngay);
                maps.put("gio", gio);
                maps.put("idphong", String.valueOf(idphong));
                maps.put("idphim", String.valueOf(idphim));
                return maps;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));        requestQueue.add(stringRequest);

    }

    private void processTime() {
        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(MovieScheduleBookingActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (hourOfDay >= calendar.get(Calendar.HOUR_OF_DAY)) {
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                    edtnamemovie.setText(simpleDateFormat.format(calendar.getTime()));
                    scheduleBooking.setGio(formatTime(simpleDateFormat.format(calendar.getTime())));
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MovieScheduleBookingActivity.this);
                    builder.setTitle("Thông báo !");
                    builder.setMessage("Giờ không hợp");
                    builder.show();
                }
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    private void loadDataMovie() {
        RequestQueue requestQueue = Volley.newRequestQueue(MovieScheduleBookingActivity.this);
        String url = String.format(Util.LINK_LOADMOVIE, 0);
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
                        phimadapter.notifyDataSetChanged();
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

    private void loadDataPhong() {
        RequestQueue requestQueue = Volley.newRequestQueue(MovieScheduleBookingActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Util.LINK_LOADROOM, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Room room = new Room();
                            room.setId(jsonArray.getJSONObject(i).getInt("ID"));
                            room.setTenphong(jsonArray.getJSONObject(i).getString("TenPhong"));
                            rooms.add(room);
                        }
                        phongadapter.notifyDataSetChanged();
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
        btnsubmit = findViewById(R.id.btnsubmit);
        edtnamemovie = findViewById(R.id.edtnamemovie);
        btnchooicetime = findViewById(R.id.btnchooiceday);
        movies = new ArrayList<>();
        rooms = new ArrayList<>();
        phongadapter = new ArrayAdapter(MovieScheduleBookingActivity.this, android.R.layout.simple_list_item_single_choice, rooms);
        phimadapter = new ArrayAdapter(MovieScheduleBookingActivity.this, android.R.layout.simple_list_item_single_choice, movies);
        lvphim = findViewById(R.id.lvmovie);
        lvphim.setAdapter(phimadapter);
        lvphong = findViewById(R.id.lvphong);
        lvphong.setAdapter(phongadapter);
        lvphim.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        lvphong.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
    }

    private String formatTime(String time) {
        SimpleDateFormat inputtime = new SimpleDateFormat("HH:mm");
        SimpleDateFormat ouputtime = new SimpleDateFormat("HH:mm:ss");
        try {
            Date temp = inputtime.parse(time);
            return ouputtime.format(temp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


}