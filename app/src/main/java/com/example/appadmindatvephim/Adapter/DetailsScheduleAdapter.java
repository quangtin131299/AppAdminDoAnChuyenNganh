package com.example.appadmindatvephim.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appadmindatvephim.DTO.DetailSchedule;
import com.example.appadmindatvephim.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class DetailsScheduleAdapter extends BaseAdapter {

    Context context;
    ArrayList<DetailSchedule> detailSchedules;

    public DetailsScheduleAdapter(Context context, ArrayList<DetailSchedule> detailSchedules) {
        this.context = context;
        this.detailSchedules = detailSchedules;
    }

    @Override
    public int getCount() {
        return detailSchedules.size();
    }

    @Override
    public Object getItem(int position) {
        return detailSchedules.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHoleder viewHoleder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_movie_detailsschedule, null);
            viewHoleder = new ViewHoleder();
            viewHoleder.imgphim = convertView.findViewById(R.id.imgmovie);
            viewHoleder.rvtime = convertView.findViewById(R.id.rvtime);
            viewHoleder.txttenphim = convertView.findViewById(R.id.txttenphim);
            convertView.setTag(viewHoleder);
        }else{
            viewHoleder = (ViewHoleder) convertView.getTag();
        }
        DetailSchedule detailSchedule = detailSchedules.get(position);
        viewHoleder.txttenphim.setText(detailSchedule.getTenphim());
        if(detailSchedule.getHinhphim().equals("") == false){
            Picasso.get().load(detailSchedule.getHinhphim()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    viewHoleder.imgphim.setBackground(new BitmapDrawable(context.getResources(), bitmap));
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
        }
        TimeAdapter timeAdapter = new TimeAdapter(context, detailSchedule.getShowTimes());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        viewHoleder.rvtime.setLayoutManager(linearLayoutManager);
        viewHoleder.rvtime.setAdapter(timeAdapter);

        return convertView;
    }

    public class ViewHoleder{
        RecyclerView rvtime;
        ImageView imgphim;
        TextView txttenphim;
    }
}
