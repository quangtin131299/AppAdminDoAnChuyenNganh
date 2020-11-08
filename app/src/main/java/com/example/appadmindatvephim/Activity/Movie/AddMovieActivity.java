package com.example.appadmindatvephim.Activity.Movie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appadmindatvephim.DTO.Movie;
import com.example.appadmindatvephim.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddMovieActivity extends AppCompatActivity {

    EditText edtnamemovie, edtimgphim, edtthoigian, edttrailer,edtngaykhoichieu, edtmota;
    Button btnnext;
    Movie movie = new Movie();
    RadioGroup radiogroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);
        addControls();
        addEvents();
    }


    private void addEvents() {
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movie.setTenmovie(edtnamemovie.getText().toString().trim());
                movie.setHinh(edtimgphim.getText().toString().trim());
                String trangthai = "";
                if(radiogroup.getCheckedRadioButtonId() == R.id.rdosapchieu){
                    trangthai = "Sắp chiếu";
                }else if(radiogroup.getCheckedRadioButtonId() == R.id.rdodachieu){
                    trangthai = "Đã chiếu";

                }else{
                    trangthai = "Đang chiếu";
                }
                movie.setThoigian(Integer.parseInt(edtthoigian.getText().toString().trim()));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat ouput = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date datetemp = simpleDateFormat.parse(edtngaykhoichieu.getText().toString().trim());
                    movie.setNgaykhoichieu(ouput.format(datetemp));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                movie.setTrailerid(edttrailer.getText().toString().trim());
                movie.setMota(edtmota.getText().toString().trim());
                Intent i = new Intent(AddMovieActivity.this, DetailsMovieActivity.class);
                i.putExtra("MOVIE", movie);
                startActivity(i);
            }
        });
    }

    private void addControls() {
        btnnext = findViewById(R.id.btnnext);
        edtnamemovie = findViewById(R.id.edtnamemovie);
        edtimgphim = findViewById(R.id.edtimgphim);
        edtthoigian = findViewById(R.id.edtthoigian);
        edttrailer = findViewById(R.id.edttrailer);
        radiogroup = findViewById(R.id.radiogroup);
        edtngaykhoichieu = findViewById(R.id.edtngaykhoichieu);
        edtmota = findViewById(R.id.edtmota);
    }
}