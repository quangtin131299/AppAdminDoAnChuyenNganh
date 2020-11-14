package com.example.appadmindatvephim.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appadmindatvephim.DTO.ShowTime;
import com.example.appadmindatvephim.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {

    Context context;
    ArrayList<ShowTime> showTimes;

    public TimeAdapter(Context context, ArrayList<ShowTime> showTimes) {
        this.context = context;
        this.showTimes = showTimes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_time, null));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShowTime showTime = showTimes.get(position);
        SimpleDateFormat input = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("HH:mm");
        try {
            Date datetemp = input.parse(showTime.getGio());
            holder.txttime.setText(output.format(datetemp));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return showTimes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txttime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txttime = itemView.findViewById(R.id.txttime);
        }
    }
}
