package com.example.appadmindatvephim.Activity.Ticker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
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
import com.example.appadmindatvephim.Adapter.TickerAdapter;
import com.example.appadmindatvephim.DTO.Ticker;
import com.example.appadmindatvephim.R;
import com.example.appadmindatvephim.Util.Util;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class TickerActivity extends AppCompatActivity {

    TickerAdapter tickerAdapter;
    ArrayList<Ticker> tickers;
    ListView lvticker;
    SwipeRefreshLayout refeshlayoutlv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticker);
        addControls();
        addEvents();
        loadDataTicker();

    }

    private void addEvents() {
        refeshlayoutlv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tickers.clear();
                tickerAdapter.notifyDataSetChanged();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        loadDataTicker();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refeshlayoutlv.setRefreshing(false);
                            }
                        });
                    }
                });
            }
        });
        lvticker.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ticker ticker = tickers.get(position);
                Intent i = new Intent(TickerActivity.this, DetailTickerActivity.class);
                i.putExtra("TICKER", ticker);
                startActivity(i);
            }
        });
    }

    private void addControls() {
        refeshlayoutlv = findViewById(R.id.refeshlayoutlv);
        tickers = new ArrayList<>();
        tickerAdapter = new TickerAdapter(TickerActivity.this, tickers);
        lvticker = findViewById(R.id.lvticker);
        lvticker.setAdapter(tickerAdapter);
    }

    private void loadDataTicker(){
        RequestQueue requestQueue = Volley.newRequestQueue(TickerActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Util.LINK_LOADVEDAT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length() ; i++) {
                            Ticker ticker = new Ticker();
                            ticker.setId(jsonArray.getJSONObject(i).getInt("ID"));
                            ticker.setNgaydat(jsonArray.getJSONObject(i).getString("NgayDat"));
                            ticker.setSuatchieu(jsonArray.getJSONObject(i).getString("Gio"));
                            ticker.setTenghe(jsonArray.getJSONObject(i).getString("TenGhe"));
                            ticker.setTenphim(jsonArray.getJSONObject(i).getString("TenPhim"));
                            ticker.setTenkhachhang(jsonArray.getJSONObject(i).getString("HoTen"));
                            ticker.setTenrap(jsonArray.getJSONObject(i).getString("TenRap"));
                            ticker.setTenphong(jsonArray.getJSONObject(i).getString("TenPhong"));
                            ticker.setTrangthai(jsonArray.getJSONObject(i).getString("Status"));
                            tickers.add(ticker);
                        }
                        tickerAdapter.notifyDataSetChanged();
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