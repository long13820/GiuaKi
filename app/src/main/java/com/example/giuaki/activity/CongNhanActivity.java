package com.example.giuaki.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giuaki.R;
import com.example.giuaki.adapter.CongNhanAdapter;
import com.example.giuaki.database.DBHelper;
import com.example.giuaki.model.CongNhan;

import java.util.List;

public class CongNhanActivity extends AppCompatActivity {
    private List<CongNhan> congNhans;
    private DBHelper dbHelper;
    private CongNhanAdapter adapter;
    private ListView listView;
    private Button btnInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cong_nhan);
        dbHelper = new DBHelper(this);
        listView = findViewById(R.id.lv_cong_nhan);
        congNhans = dbHelper.getAllEmployees();
        adapter = new CongNhanAdapter(this,R.layout.item_con_nhan);
        adapter.setData(congNhans);
        listView.setAdapter(adapter);
        btnInsert = findViewById(R.id.insert_cong_nhan);
        btnInsert.setOnClickListener(view -> {
            insertCongNhan();
        });
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            updateCongNhan(congNhans.get(i));
        });
    }
    void insertCongNhan(){
        Dialog dialog = new Dialog(this);
        dialog.setTitle("Thêm công nhân");
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.them_cong_nhan);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        EditText maCN = (EditText)dialog.findViewById(R.id.insert_id_cn);
        EditText ho = (EditText)dialog.findViewById(R.id.insert_ho_cn);
        EditText ten = (EditText)dialog.findViewById(R.id.insert_ten_cn);
        EditText phanXuong = (EditText)dialog.findViewById(R.id.insert_phan_xuong);

        Button btnThem = (Button)dialog.findViewById(R.id.btn_them_cong_nhan);
        Button btnback = (Button)dialog.findViewById(R.id.btn_thoat);

        btnThem.setOnClickListener(view -> {
            if(maCN.getText().toString().trim().equals("")
                    || ho.getText().toString().trim().equals("")
                    || ten.getText().toString().trim().equals("")
                    || phanXuong.getText().toString().trim().equals("")){
                Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
            }else if(!maCN.getText().toString().trim().equals("")
                    && !ho.getText().toString().trim().equals("")
                    && !ten.getText().toString().trim().equals("")
                    && !phanXuong.getText().toString().trim().equals("")){
                if(dbHelper.checkMaCN(maCN.getText().toString().trim()) ){
                    Toast.makeText(this, "Mã công nhân đã tồn tại", Toast.LENGTH_SHORT).show();
                }else{
                    CongNhan congNhan = new CongNhan(
                            maCN.getText().toString().trim()
                            ,ho.getText().toString().trim()
                            ,ten.getText().toString().trim()
                            ,phanXuong.getText().toString().trim());
                    dbHelper.themCongNhan(congNhan);
                    congNhans = dbHelper.getAllEmployees();
                    adapter.setData(congNhans);
                    dialog.cancel();
                }
            }else{
                Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        btnback.setOnClickListener(view -> {
            dialog.cancel();
        });

    }
    public void updateCongNhan(CongNhan congNhan){
        Dialog dialog = new Dialog(this);
        dialog.setTitle("Update công nhân");
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.update_cong_nhan);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        EditText maCN = (EditText)dialog.findViewById(R.id.update_id_cn);
        EditText ho = (EditText)dialog.findViewById(R.id.update_ho_cn);
        EditText ten = (EditText)dialog.findViewById(R.id.update_ten_cn);
        EditText phanXuong = (EditText)dialog.findViewById(R.id.update_phan_xuong);

        Button btnThem = (Button)dialog.findViewById(R.id.btn_update_cong_nhan);
        Button btnXoa = (Button)dialog.findViewById(R.id.btn_xoa_cong_nhan);
        Button btnback = (Button)dialog.findViewById(R.id.btn_update_thoat);

        //set value
        maCN.setText(congNhan.getMaCN());
        ho.setText(congNhan.getHo());
        ten.setText(congNhan.getTen());
        phanXuong.setText(congNhan.getPhan_xuong());

        btnThem.setOnClickListener(view -> {
            if(maCN.getText().toString().trim().equals("")
                    || ho.getText().toString().trim().equals("")
                    || ten.getText().toString().trim().equals("")
                    || phanXuong.getText().toString().trim().equals("")){
                Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
            }else if(!maCN.getText().toString().trim().equals("")
                    && !ho.getText().toString().trim().equals("")
                    && !ten.getText().toString().trim().equals("")
                    && !phanXuong.getText().toString().trim().equals("")){
                if(!congNhan.getMaCN().equals(maCN.getText().toString().trim())
                        && dbHelper.checkMaCN(maCN.getText().toString().trim()) ){
                    Toast.makeText(this, "Mã công nhân đã tồn tại", Toast.LENGTH_SHORT).show();
                }else{
                    congNhan.setMaCN(maCN.getText().toString().trim());
                    congNhan.setHo(ho.getText().toString().trim());
                    congNhan.setTen(ten.getText().toString().trim());
                    congNhan.setPhan_xuong(phanXuong.getText().toString().trim());
                    dbHelper.updateCongNhan(congNhan);
                    congNhans = dbHelper.getAllEmployees();
                    adapter.setData(congNhans);
                    dialog.cancel();
                }
            }else{
                Toast.makeText(this, "Update thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        btnXoa.setOnClickListener(view -> {
            if(dbHelper.deleteCongNhan(maCN.getText().toString().trim())){
                congNhans = dbHelper.getAllEmployees();
                adapter.setData(congNhans);
                dialog.cancel();
                Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Xóa Thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        btnback.setOnClickListener(view -> {
            dialog.cancel();
        });
    }
}