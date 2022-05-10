package com.example.giuaki.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giuaki.R;

import com.example.giuaki.activity.ChamCongActivity;
import com.example.giuaki.activity.DetailTimekeepingActivity;
import com.example.giuaki.listener.ChamCongListener;
import com.example.giuaki.model.ChamCong;
import com.example.giuaki.model.CongNhan;

import java.util.List;

public class ChamCongAdapter extends RecyclerView.Adapter<ChamCongViewHolder> {

    ChamCongActivity context;
    List<ChamCong> list;
    CongNhan congNhan;

    public ChamCongAdapter(ChamCongActivity context, List<ChamCong> list, CongNhan congNhan) {
        this.context = context;
        this.list = list;
        this.congNhan = congNhan;
    }

    @NonNull
    @Override
    public ChamCongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChamCongViewHolder(LayoutInflater.from(context).inflate(R.layout.list_cham_cong, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChamCongViewHolder holder, int position) {
        holder.txtMaCC.setText("Mã CC: " + list.get(position).MaCC);
        holder.txtMaCN.setText("Mã CN: " + list.get(position).MaCN);
        holder.txtNgayCC.setText("Ngày CC: " + list.get(position).ngayCC);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MaCC", String.valueOf(list.get(holder.getAdapterPosition()).MaCC));
                Intent intent = new Intent(context, DetailTimekeepingActivity.class);

                Bundle bundle = new Bundle();

                bundle.putString("id", list.get(position).getMaCC()+"");
                bundle.putSerializable("chamCong", list.get(position));
                bundle.putSerializable("congNhan", congNhan);

                intent.putExtra("maCC", String.valueOf(list.get(holder.getAdapterPosition()).MaCC));
                intent.putExtra("data", bundle);

                context.startActivity(intent);
            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                context.dialogUpdate(Integer.parseInt(String.valueOf(list.get(holder.getAdapterPosition()).MaCC)),
                        String.valueOf(list.get(holder.getAdapterPosition()).ngayCC),
                        String.valueOf(list.get(holder.getAdapterPosition()).MaCN));
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class ChamCongViewHolder extends RecyclerView.ViewHolder {

    TextView txtMaCC, txtMaCN, txtNgayCC;
    CardView cardView;



    public ChamCongViewHolder(@NonNull View itemView) {
        super(itemView);

        txtMaCC = itemView.findViewById(R.id.txtMaCC);
        txtMaCN = itemView.findViewById(R.id.txtMaCN);
        txtNgayCC = itemView.findViewById(R.id.txtNgayCC);
        cardView = itemView.findViewById(R.id.cardView);
    }
}
