package com.example.giuaki.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import com.example.giuaki.R;

public class SpinnerCongNhanAdapter extends ArrayAdapter<String> {

    Context context;
    int resource;
    ArrayList<String> list;

    public SpinnerCongNhanAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> list) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(resource,null);
        TextView textView=convertView.findViewById(R.id.txt_spinner_content);
        textView.setText(list.get(position));
        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(resource,null);
        TextView textView=convertView.findViewById(R.id.txt_spinner_content);
        textView.setText(list.get(position));
        return convertView;
    }
}
