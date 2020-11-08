package com.example.appadmindatvephim.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appadmindatvephim.DTO.Customer;
import com.example.appadmindatvephim.R;

import java.util.ArrayList;

public class CustomerAdapter extends BaseAdapter {

    Context context;
    ArrayList<Customer> customers;

    public CustomerAdapter(Context context, ArrayList<Customer> customers) {
        this.context = context;
        this.customers = customers;
    }

    @Override
    public int getCount() {
        return customers.size();
    }

    @Override
    public Object getItem(int position) {
        return customers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_customer, null);
            viewHolder = new ViewHolder();
            viewHolder.txttencustomer = convertView.findViewById(R.id.txttencustomer);
            viewHolder.txtuserid = convertView.findViewById(R.id.txtuserid);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Customer customer = customers.get(position);
        viewHolder.txttencustomer.setText(customer.getHoten());
        viewHolder.txtuserid.setText(customer.getId() + "");
        return convertView;
    }


    public class ViewHolder {
        TextView txttencustomer, txtuserid;

    }
}
