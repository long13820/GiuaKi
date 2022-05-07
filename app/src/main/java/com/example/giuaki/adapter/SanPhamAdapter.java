package com.example.giuaki.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.giuaki.R;
import com.example.giuaki.model.SanPham;

import java.util.List;

public class SanPhamAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<SanPham> dsSanPham;

    public SanPhamAdapter(Context context,int layout) {
        this.context = context;
        this.layout = layout;
    }

    public void setData(List<SanPham> dsSanPham){
        this.dsSanPham = dsSanPham;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(dsSanPham!=null){
            return dsSanPham.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if(dsSanPham!=null){
            return dsSanPham.get(i);
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
        TextView maSP,tenSP,don_gia;
        SanPham sanPham=null;
        if(dsSanPham!=null){
            sanPham = dsSanPham.get(i);
        }
        maSP = view.findViewById(R.id.id_sp);
        tenSP = view.findViewById(R.id.ten_sp);
        don_gia = view.findViewById(R.id.don_gia);
        if(sanPham!=null){
            maSP.setText(sanPham.getMaSP());
            tenSP.setText(sanPham.getTenSP());
            don_gia.setText(sanPham.getDonGia());
        }
        return view;
    }
}
