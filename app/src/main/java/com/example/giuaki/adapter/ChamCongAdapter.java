package com.example.giuaki.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.giuaki.model.ChamCong;

import java.util.List;

public class ChamCongAdapter extends ArrayAdapter<ChamCong> {
    public ChamCongAdapter(@NonNull Context context, int resource, List<ChamCong> objects, String loai) {
        super(context, resource, objects);



    }


}
