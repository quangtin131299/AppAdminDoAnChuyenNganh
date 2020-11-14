package com.example.appadmindatvephim.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appadmindatvephim.DTO.Cinema;
import com.example.appadmindatvephim.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CinemaAdapter extends BaseAdapter {
    Context context;
    ArrayList<Cinema> cinemas;

    public CinemaAdapter(Context context, ArrayList<Cinema> cinemas) {
        this.context = context;
        this.cinemas = cinemas;
    }

    @Override
    public int getCount() {
        return cinemas.size();
    }

    @Override
    public Object getItem(int position) {
        return cinemas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cinema,null);
            viewHolder = new ViewHolder();
            viewHolder.imgrap = convertView.findViewById(R.id.imgrap);
            viewHolder.txtdiachi = convertView.findViewById(R.id.txtdiachi);
            viewHolder.txtmoviename = convertView.findViewById(R.id.txtmoviename);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Cinema cinema = cinemas.get(position);
        if(cinema.getHinh().equals("") == false){
            Picasso.get().load(cinema.getHinh()).into(viewHolder.imgrap);
        }
        viewHolder.txtmoviename.setText(cinema.getTenRap());
        viewHolder.txtdiachi.setText(cinema.getDiaChi());
        return convertView;
    }

    public class ViewHolder{
        TextView txtmoviename , txtdiachi;
        ImageView imgrap;
    }
}
