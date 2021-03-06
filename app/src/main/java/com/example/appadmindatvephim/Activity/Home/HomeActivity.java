package com.example.appadmindatvephim.Activity.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.appadmindatvephim.Activity.Cinema.CinemaActivity;
import com.example.appadmindatvephim.Activity.Customer.CustomerActivity;
import com.example.appadmindatvephim.Activity.Movie.MovieActivity;
import com.example.appadmindatvephim.Activity.Ticker.TickerActivity;
import com.example.appadmindatvephim.Activity.XepLich.ScheduleActivity;
import com.example.appadmindatvephim.DTO.Admin;
import com.example.appadmindatvephim.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {

    CardView cardmovie, cardcustomer, cardcinema,cardticker,cardschedule;
    TextView txttime, txtdate, txttendn;
    SimpleDateFormat timeformat, dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addControls();
        getIntentData();
        addEvents();
        setupDateTime();


    }

    private void getIntentData() {
        Intent i = getIntent();
        if(i.hasExtra("ADMIN")){
            Admin admin = (Admin) i.getSerializableExtra("ADMIN");
            if(admin != null){
                txttendn.setText(admin.getHoten());
            }

        }
    }

    private void addEvents() {
        cardschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ScheduleActivity.class);
                startActivity(i);
            }
        });
        cardticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, TickerActivity.class);
                startActivity(i);
            }
        });
        cardcinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, CinemaActivity.class);
                startActivity(i);
            }
        });
        cardcustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, CustomerActivity.class);
                startActivity(i);
            }
        });
        cardmovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, MovieActivity.class);
                startActivity(i);
            }
        });
    }

    private void addControls() {
        txttendn = findViewById(R.id.txttendn);
        cardticker = findViewById(R.id.cardticker);
        cardcustomer = findViewById(R.id.cardcustomer);
        cardschedule = findViewById(R.id.cardschedule);
        cardmovie = findViewById(R.id.cardmovie);
        timeformat = new SimpleDateFormat("HH:mm:ss");
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        txtdate = findViewById(R.id.txtdate);
        txttime = findViewById(R.id.txttime);
        cardcinema = findViewById(R.id.cardcinema);
    }

    private void setupDateTime() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtdate.setText(dateFormat.format(Calendar.getInstance().getTime()));
                        txttime.setText(timeformat.format(Calendar.getInstance().getTime()));
                    }
                });

            }
        };

        timer.schedule(timerTask, 0, 1000);

    }
}