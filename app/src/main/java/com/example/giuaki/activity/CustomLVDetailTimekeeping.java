package com.example.giuaki.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.giuaki.R;
import com.example.giuaki.model.DetailTimekeeping;

import java.util.ArrayList;

public class CustomLVDetailTimekeeping extends ArrayAdapter<DetailTimekeeping> {
    private Context context;
    private int resource;
    private ArrayList<DetailTimekeeping> arrDetail;

    public CustomLVDetailTimekeeping(Context context, int resource, ArrayList<DetailTimekeeping> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arrDetail = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_detail_timekeeping, parent, false);

        TextView tv_name = (TextView) convertView.findViewById(R.id.name);
        TextView tv_price = (TextView) convertView.findViewById(R.id.price);
        TextView tv_value = (TextView) convertView.findViewById(R.id.value);
        TextView tv_err = (TextView) convertView.findViewById(R.id.err);
        TextView tv_sum = (TextView) convertView.findViewById(R.id.sum);

        DetailTimekeeping detailTimekeeping = arrDetail.get(position);

        tv_name.setText(detailTimekeeping.getNameProduct());
        tv_price.setText(detailTimekeeping.getPrice());
        tv_value.setText(detailTimekeeping.getValue());
        tv_err.setText(detailTimekeeping.getProductErr());
        tv_sum.setText(String.valueOf(detailTimekeeping.getSum()));
        return  convertView;
    }
}
