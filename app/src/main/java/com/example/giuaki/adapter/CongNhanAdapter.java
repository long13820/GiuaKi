package com.example.giuaki.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.giuaki.R;
import com.example.giuaki.model.CongNhan;

import java.util.List;

public class CongNhanAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<CongNhan> congNhans;

    public CongNhanAdapter(Context context,int layout) {
        this.context = context;
        this.layout = layout;
    }

    public void setData(List<CongNhan> congNhans){
        this.congNhans = congNhans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(congNhans!=null){
            return congNhans.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if(congNhans!=null){
            return congNhans.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        TextView maCN,ten,phan_xuong;
        CongNhan congNhan=null;
        if(congNhans!=null){
            congNhan = congNhans.get(i);
        }
        maCN = view.findViewById(R.id.id_cn);
        ten = view.findViewById(R.id.ten_cn);
        phan_xuong = view.findViewById(R.id.phan_xuong);
        if(congNhan!=null){
            maCN.setText(congNhan.getMaCN());
            ten.setText(congNhan.getHo()+" "+congNhan.getTen());
            phan_xuong.setText(congNhan.getPhan_xuong());
        }
        return view;
    }
}
