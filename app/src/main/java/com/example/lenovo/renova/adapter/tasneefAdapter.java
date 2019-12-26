package com.example.lenovo.renova.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lenovo.renova.R;
import com.example.lenovo.renova.list.tasneefList;

import java.util.ArrayList;

public class tasneefAdapter extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<tasneefList> mylist;
    int resources;
    public tasneefAdapter(@NonNull Context context, int resource, @NonNull ArrayList mylist) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
        this.mylist=mylist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(resource,parent,false);
        TextView tasneef=(TextView)convertView.findViewById(R.id.tasneef );
        tasneef.setText(mylist.get(position).getName().toString());
        return convertView;
    }
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        return getView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return mylist.size();
    }
}


