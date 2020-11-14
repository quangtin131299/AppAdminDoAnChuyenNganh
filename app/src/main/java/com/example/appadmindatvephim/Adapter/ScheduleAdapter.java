package com.example.appadmindatvephim.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appadmindatvephim.DTO.Schedule;
import com.example.appadmindatvephim.R;

import java.util.ArrayList;

public class ScheduleAdapter extends BaseAdapter {

    Context context;
    ArrayList<Schedule> schedules;

    public ScheduleAdapter(Context context, ArrayList<Schedule> schedules) {
        this.context = context;
        this.schedules = schedules;
    }

    @Override
    public int getCount() {
        return schedules.size();
    }

    @Override
    public Object getItem(int position) {
        return schedules.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lichchieu, null);
            viewHolder = new ViewHolder();
            viewHolder.txtngaylichchieu = convertView.findViewById(R.id.txtngaylichchieu);
            viewHolder.txttenrap = convertView.findViewById(R.id.txttenrap);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Schedule schedule = schedules.get(position);
        viewHolder.txttenrap.setText(schedule.getTenrap());
        viewHolder.txtngaylichchieu.setText(schedule.getNgay());
        return convertView;
    }

    public class ViewHolder{
        TextView txtngaylichchieu,txttenrap;
    }
}
