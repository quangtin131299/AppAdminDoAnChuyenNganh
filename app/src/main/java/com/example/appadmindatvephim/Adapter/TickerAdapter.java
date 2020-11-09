package com.example.appadmindatvephim.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appadmindatvephim.DTO.Ticker;
import com.example.appadmindatvephim.R;

import java.util.ArrayList;

public class TickerAdapter extends BaseAdapter {
    Context context;
    ArrayList<Ticker> tickers;

    public TickerAdapter(Context context, ArrayList<Ticker> tickers) {
        this.context = context;
        this.tickers = tickers;
    }

    @Override
    public int getCount() {
        return tickers.size();
    }

    @Override
    public Object getItem(int position) {
        return tickers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_ticker, null);
            viewHolder = new ViewHolder();
            viewHolder.txtIDticker = convertView.findViewById(R.id.txtIDticker);
            viewHolder.txtphim = convertView.findViewById(R.id.txtphim);
            viewHolder.txtgiave = convertView.findViewById(R.id.txtgiave);
            viewHolder.txttennguoidat = convertView.findViewById(R.id.txttennguoidat);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Ticker ticker = tickers.get(position);
        viewHolder.txttennguoidat.setText(ticker.getTenkhachhang());
        viewHolder.txtIDticker.setText(ticker.getId() + "");
        viewHolder.txtgiave.setText("45.000VND");
        viewHolder.txtphim.setText(ticker.getTenphim());

        return convertView;
    }

    public class ViewHolder{
        TextView txtIDticker, txttennguoidat, txtgiave, txtphim;
    }
}
