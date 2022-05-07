package com.example.giuaki.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.giuaki.R;
import com.example.giuaki.adapter.SanPhamAdapter;
import com.example.giuaki.database.DBHelper;
import com.example.giuaki.model.SanPham;

import java.util.List;

public class SanPhamActivity extends AppCompatActivity {
    private List<SanPham> dsSanPham;
    private DBHelper dbHelper;
    private SanPhamAdapter adapter;
    private ListView listView;
    private Button btnInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        dbHelper = new DBHelper(this);
        listView = findViewById(R.id.lv_san_pham);
        dsSanPham = dbHelper.getAllProducts();
        adapter = new SanPhamAdapter(this,R.layout.item_san_pham);
        adapter.setData(dsSanPham);
        listView.setAdapter(adapter);
        btnInsert = findViewById(R.id.insert_san_pham);
        btnInsert.setOnClickListener(view -> {
            insertSanPham();
        });
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            updateSanPham(dsSanPham.get(i));
        });
    }
    void insertSanPham(){
        Dialog dialog = new Dialog(this);
        dialog.setTitle("Thêm Sản phẩm");
        dialog.setContentView(R.layout.activity_them_san_pham);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        EditText maSP = (EditText)dialog.findViewById(R.id.insert_id_sp);
        EditText tenSP = (EditText)dialog.findViewById(R.id.insert_ten_sp);
        EditText donGia = (EditText)dialog.findViewById(R.id.insert_don_gia);

        Button btnThem = (Button)dialog.findViewById(R.id.btn_them_san_pham);
        Button btnback = (Button)dialog.findViewById(R.id.btn_thoat);

        btnThem.setOnClickListener(view -> {
            if(maSP.getText().toString().trim().equals("")
                    || tenSP.getText().toString().trim().equals("")
                    || donGia.getText().toString().trim().equals("")){
                Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
            }else if(!maSP.getText().toString().trim().equals("")
                    && !tenSP.getText().toString().trim().equals("")
                    && !donGia.getText().toString().trim().equals("")){
                if(dbHelper.checkMaCN(maSP.getText().toString().trim()) ){
                    Toast.makeText(this, "Mã sản phẩm đã tồn tại", Toast.LENGTH_SHORT).show();
                }else{
                    SanPham sanPham = new SanPham(
                            maSP.getText().toString().trim()
                            ,tenSP.getText().toString().trim()
                            ,donGia.getText().toString().trim());
                    dbHelper.themSanPham(sanPham);
                    dsSanPham = dbHelper.getAllProducts();
                    adapter.setData(dsSanPham);
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
    public void updateSanPham(SanPham sanPham){
        Dialog dialog = new Dialog(this);
        dialog.setTitle("Update sản phẩm");
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_update_san_pham);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        EditText maSP = (EditText)dialog.findViewById(R.id.update_id_sp);
        EditText tenSP = (EditText)dialog.findViewById(R.id.update_ten_sp);
        EditText donGia = (EditText)dialog.findViewById(R.id.update_don_gia);

        Button btnThem = (Button)dialog.findViewById(R.id.btn_update_san_pham);
        Button btnXoa = (Button)dialog.findViewById(R.id.btn_xoa_san_pham);
        Button btnback = (Button)dialog.findViewById(R.id.btn_thoat_sp);

        //set value
        maSP.setText(sanPham.getMaSP());
        tenSP.setText(sanPham.getTenSP());
        donGia.setText(sanPham.getDonGia());

        btnThem.setOnClickListener(view -> {
            if(maSP.getText().toString().trim().equals("")
                    || tenSP.getText().toString().trim().equals("")
                    || donGia.getText().toString().trim().equals("")){
                Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
            }else if(!maSP.getText().toString().trim().equals("")
                    && !tenSP.getText().toString().trim().equals("")
                    && !donGia.getText().toString().trim().equals("")){
                if(!sanPham.getMaSP().equals(maSP.getText().toString().trim())
                        && dbHelper.checkMaCN(maSP.getText().toString().trim()) ){
                    Toast.makeText(this, "Mã sản phẩm đã tồn tại", Toast.LENGTH_SHORT).show();
                }else{
                    sanPham.setMaSP(maSP.getText().toString().trim());
                    sanPham.setTenSP(tenSP.getText().toString().trim());
                    sanPham.setDonGia(donGia.getText().toString().trim());
                    dbHelper.updateSanPham(sanPham);
                    dsSanPham = dbHelper.getAllProducts();
                    adapter.setData(dsSanPham);
                    dialog.cancel();
                }
            }else{
                Toast.makeText(this, "Update thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        btnXoa.setOnClickListener(view -> {
            if(dbHelper.xoaSanPham(maSP.getText().toString().trim())){
                dsSanPham = dbHelper.getAllProducts();
                adapter.setData(dsSanPham);
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
