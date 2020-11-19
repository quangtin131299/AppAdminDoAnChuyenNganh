package com.example.appadmindatvephim.Activity.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appadmindatvephim.Activity.Home.HomeActivity;
import com.example.appadmindatvephim.DTO.Admin;
import com.example.appadmindatvephim.R;
import com.example.appadmindatvephim.Util.Util;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText inputEmail, inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tk = inputEmail.getText().toString().trim();
                String pass = Util.getMd5(inputPassword.getText().toString().trim());
                processLogin(tk, pass);
            }
        });
    }

    private void processLogin(final String tk, final String pass) {
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Util.LINK_LOGINADIM, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(response);
                        Admin admin = new Admin();
                        admin.setId(jsonArray.getJSONObject(0).getInt("ID"));
                        admin.setHoten(jsonArray.getJSONObject(0).getString("HoTen"));
                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                        i.putExtra("ADMIN", admin);
                        startActivity(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Thông báo !");
                    builder.setMessage("Đăng nhập thất bại");
                    builder.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("account", tk);
                map.put("password", pass);
                return map;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private void addControls() {
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }
}