package com.example.appadmindatvephim.Activity.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.example.appadmindatvephim.Adapter.CustomerAdapter;
import com.example.appadmindatvephim.DTO.Customer;
import com.example.appadmindatvephim.R;
import com.example.appadmindatvephim.Util.Util;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity {

    CustomerAdapter customerAdapter;
    SwipeRefreshLayout refeshlayoutlv;
    ArrayList<Customer> customers;
    ListView lvcustomer;
    FloatingSearchView txtsearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        addControls();
        addEvents();
        loadDataUser();
    }

    private void addEvents() {
        refeshlayoutlv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                customers.clear();
                customerAdapter.notifyDataSetChanged();
                loadDataUser();
                refeshlayoutlv.setRefreshing(false);
            }
        });
        lvcustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Customer customer = customers.get(position);
                Intent i = new Intent(CustomerActivity.this, DetailCustomerActivity.class);
                i.putExtra("CUSTOMER", customer);
                startActivity(i);
            }
        });
        txtsearch.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                customers.clear();
                customerAdapter.notifyDataSetChanged();
                searchUser(newQuery);
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void loadDataUser() {
        RequestQueue requestQueue = Volley.newRequestQueue(CustomerActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Util.LINK_LOADDATACUSTOMER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Customer customer = new Customer();
                            customer.setId(jsonArray.getJSONObject(i).getInt("ID"));
                            customer.setHoten(jsonArray.getJSONObject(i).getString("HoTen"));
                            customer.setEmail(jsonArray.getJSONObject(i).getString("Email"));
                            customer.setNgaysinh(jsonArray.getJSONObject(i).getString("NgaySinh"));
                            customer.setSdt(jsonArray.getJSONObject(i).getString("SDT"));
                            customers.add(customer);
                        }
                        customerAdapter.notifyDataSetChanged();
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

    private void searchUser(String tencustomer) {
        RequestQueue requestQueue = Volley.newRequestQueue(CustomerActivity.this);
        String url = String.format(Util.LINK_SEARCHCUSTOMER, tencustomer);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Customer customer = new Customer();
                            customer.setId(jsonArray.getJSONObject(i).getInt("ID"));
                            customer.setHoten(jsonArray.getJSONObject(i).getString("HoTen"));
                            customer.setEmail(jsonArray.getJSONObject(i).getString("Email"));
                            customer.setNgaysinh(jsonArray.getJSONObject(i).getString("NgaySinh"));
                            customer.setSdt(jsonArray.getJSONObject(i).getString("SDT"));
                            customers.add(customer);
                        }
                        customerAdapter.notifyDataSetChanged();
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
        customers = new ArrayList<>();
        customerAdapter = new CustomerAdapter(CustomerActivity.this, customers);
        lvcustomer = findViewById(R.id.lvcustomer);
        lvcustomer.setAdapter(customerAdapter);
        txtsearch = findViewById(R.id.txtsearch);
    }
}