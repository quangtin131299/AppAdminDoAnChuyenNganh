package com.example.appadmindatvephim.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appadmindatvephim.DTO.Movie;
import com.example.appadmindatvephim.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {

    Context context;
    ArrayList<Movie> movies;

    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_movie, null);
            viewHolder = new ViewHolder();
            viewHolder.imgmovie = convertView.findViewById(R.id.imgmovie);
            viewHolder.txtmoviename = convertView.findViewById(R.id.txtmoviename);
            viewHolder.txtthoigian = convertView.findViewById(R.id.txtthoigian);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Movie movie = movies.get(position);
        viewHolder.txtthoigian.setText(movie.getThoigian() + " min");
        viewHolder.txtmoviename.setText(movie.getTenmovie());
        Picasso.get().load(movie.getHinh()).into(viewHolder.imgmovie);
        return convertView;
    }

    public class ViewHolder{
        TextView txtmoviename, txtthoigian;
        ImageView imgmovie;
    }
}
