package com.example.giuaki.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giuaki.R;
import com.example.giuaki.adapter.ChamCongAdapter;
import com.example.giuaki.adapter.SpinnerCongNhanAdapter;
import com.example.giuaki.database.DBHelper;
import com.example.giuaki.listener.ChamCongListener;
import com.example.giuaki.model.ChamCong;
import com.example.giuaki.model.CongNhan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChamCongActivity extends AppCompatActivity {
    DBHelper helper;
    Spinner spinnerCongNhan;

    RecyclerView recycleView;
    ChamCongAdapter chamCongAdapter;
    FloatingActionButton floatingActionButton;
    EditText editTextSearchChamCong;

    ImageView btnUpdate;

    List<ChamCong> arrayChamCong = new ArrayList<>();
    CongNhan congNhan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cham_cong);

        helper = DBHelper.getInstance(this);

        ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);

        setControl();
        setEvent();

        ArrayList<CongNhan> arrayCongNhan = new ArrayList<CongNhan>();

        ArrayList<String> arrayTenCN = new ArrayList<String>();
        arrayTenCN.add("Tất cả");

        //Hien thi

        Cursor dataCongNhan = helper.GetData("SELECT * FROM CongNhan");

        CongNhan congNhan;
        while (dataCongNhan.moveToNext()) {
            congNhan = new CongNhan(dataCongNhan.getString(0), dataCongNhan.getString(1), dataCongNhan.getString(2), dataCongNhan.getString(3));
            arrayCongNhan.add(congNhan);
            arrayTenCN.add(congNhan.getTen());
        }

        //ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayTenCN);
        SpinnerCongNhanAdapter adapter = new SpinnerCongNhanAdapter(this, R.layout.custom_pinner, arrayTenCN);
        spinnerCongNhan.setAdapter(adapter);
    }

    private void setControl() {
        spinnerCongNhan = findViewById(R.id.spinner1);
        recycleView = findViewById(R.id.recycleView);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        editTextSearchChamCong=findViewById(R.id.editTextSearchChamCong);
        btnUpdate = findViewById(R.id.btnUpdate);
    }
    private void setEvent(){
        spinnerCongNhan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                actionGetData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogInsert();
            }
        });

        editTextSearchChamCong.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                actionGetData();
                return false;
            }
        });
    }

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        private Paint paint = new Paint();

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            Toast.makeText(ChamCongActivity.this, "on Move", Toast.LENGTH_SHORT).show();
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            int position = viewHolder.getAdapterPosition();
            Cursor data = helper.GetData("SELECT * FROM ChiTietChamCong WHERE MaCC='" + arrayChamCong.get(position).getMaCC() + "'");
            while (data.moveToNext()) {
                Toast.makeText(ChamCongActivity.this, "Chấm công có sản phẩm nên không thể xóa", Toast.LENGTH_SHORT).show();
                return;
            }

            //Remove swiped item from list and notify the RecyclerView

            helper.QueryData("DELETE FROM ChamCong WHERE MaCC='" + arrayChamCong.get(position).getMaCC() + "'");
            arrayChamCong.remove(position);
            chamCongAdapter.notifyDataSetChanged();
            Toast.makeText(ChamCongActivity.this, "Xóa thành công " + arrayChamCong.get(position).getMaCC(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            float translationX = dX;
            View itemView = viewHolder.itemView;
            float height = (float) itemView.getBottom() - (float) itemView.getTop();

            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && dX <= 0) // Swiping Left
            {
                translationX = -Math.min(-dX, height * 2);
                paint.setColor(Color.RED);
                RectF background = new RectF((float) itemView.getRight() + translationX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                c.drawRect(background, paint);

                paint.setColor(Color.WHITE);
                paint.setTextSize(50);
                paint.setTypeface(Typeface.DEFAULT_BOLD);
                paint.setTextAlign(Paint.Align.LEFT);
                Rect titleBounds = new Rect();
                String title = "Delete";
                paint.getTextBounds(title, 0, title.length(), titleBounds);

                double y = background.height() / 2 + titleBounds.height() / 2 - titleBounds.bottom;
                c.drawText(title, background.left + 80, (float) (background.top + y), paint);
                //viewHolder.ItemView.TranslationX = translationX;
            } else if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && dX > 0) // Swiping Right
            {
                translationX = Math.min(dX, height * 2);
                paint.setColor(Color.RED);

                RectF background = new RectF((float) itemView.getRight() + translationX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                c.drawRect(background, paint);

                paint.setColor(Color.WHITE);
                paint.setTextSize(50);
                paint.setTypeface(Typeface.DEFAULT_BOLD);
                paint.setTextAlign(Paint.Align.LEFT);
                Rect titleBounds = new Rect();
                String title = "Delete";
                paint.getTextBounds(title, 0, title.length(), titleBounds);

                double y = background.height() / 2 + background.height() / 2 - titleBounds.bottom;
                c.drawText(title, background.left + 50, (float) (background.top + y), paint);


            }
            super.onChildDraw(c, recyclerView, viewHolder, translationX, dY, actionState, isCurrentlyActive);
        }
    };

    public void actionGetData() {
        arrayChamCong.clear();
        String searchCC= editTextSearchChamCong.getText().toString();
        if (spinnerCongNhan.getSelectedItem().toString().equals("Tất cả")) {

            Cursor dataChamCong ;
            if(searchCC != ""){
                dataChamCong = helper.GetData("SELECT * FROM ChamCong WHERE MaCC LIKE '%"+searchCC+"%' OR NgayCC LIKE '%"+searchCC+"%' OR MaCN LIKE '%"+searchCC+"%'");
            }
            else{
                dataChamCong = helper.GetData("SELECT * FROM ChamCong");
            }

            congNhan = new CongNhan("Tất cả", "Tất cả", "Tất cả", "Tất cả");

            ChamCong chamCong;
            while (dataChamCong.moveToNext()){
                chamCong = new ChamCong(Integer.valueOf(dataChamCong.getString(0)), dataChamCong.getString(1),dataChamCong.getString(2));
                arrayChamCong.add(chamCong);
            }
        } else {
            Cursor dataCongNhan = helper.GetData("SELECT * FROM CongNhan WHERE TenCN='" + spinnerCongNhan.getSelectedItem().toString() + "'");
            String ma = "";
            Cursor dataChamCong;
            while (dataCongNhan.moveToNext()) {
                ma = dataCongNhan.getString(0);
                congNhan = new CongNhan(ma, dataCongNhan.getString(1),dataCongNhan.getString(2),dataCongNhan.getString(3));
            }
            if(searchCC!=""){
                dataChamCong = helper.GetData("SELECT * FROM ChamCong WHERE MaCN = '" + ma + "' AND MaCC LIKE '%"+searchCC+"%'");
            }else{
                dataChamCong = helper.GetData("SELECT * FROM ChamCong WHERE MaCN = '" + ma + "'");
            }

            ChamCong chamCong;
            while (dataChamCong.moveToNext()) {
                chamCong = new ChamCong(Integer.valueOf(dataChamCong.getString(0)), dataChamCong.getString(1), dataChamCong.getString(2));
                arrayChamCong.add(chamCong);
            }
        }

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recycleView);


        recycleView.setHasFixedSize(true);
        recycleView.setLayoutManager(new LinearLayoutManager(ChamCongActivity.this, LinearLayoutManager.VERTICAL, false));

        chamCongAdapter = new ChamCongAdapter(ChamCongActivity.this, arrayChamCong, congNhan);
        recycleView.setAdapter(chamCongAdapter);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();
    }


    public void dialogUpdate(int maCC, String ngayCC, String maCN) {

        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sua_cham_cong);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        //anh xa
        EditText editMaCC= (EditText) dialog.findViewById(R.id.sua_id_cc);
        EditText editNgayCC= (EditText) dialog.findViewById(R.id.sua_ngay_cc);
        Button btnHoanTat = (Button) dialog.findViewById(R.id.btn_sua_cham_cong);
        Button btnXoa = (Button) dialog.findViewById(R.id.btn_xoa_cham_cong);
        Button btnBack = (Button) dialog.findViewById(R.id.btn_huy_cham_cong);

        //them du lieu vao spinner
        Spinner spinnerMaCN = dialog.findViewById(R.id.sua_ma_cn);
        Cursor dataCongNhan = helper.GetData("SELECT * FROM CongNhan");
        ArrayList<String> arrayMaCongNhanTam = new ArrayList<String>();
        ArrayList<String> arrayTenCongNhanTam = new ArrayList<String>();
        CongNhan congNhanTam;
        while (dataCongNhan.moveToNext()) {
            congNhanTam = new CongNhan(dataCongNhan.getString(0), dataCongNhan.getString(1),dataCongNhan.getString(2),dataCongNhan.getString(3));
            arrayMaCongNhanTam.add(congNhanTam.getMaCN());
            arrayTenCongNhanTam.add(congNhanTam.getTen());
        }
        SpinnerCongNhanAdapter arrayAdapterTam=new SpinnerCongNhanAdapter(this,R.layout.custom_pinner,arrayTenCongNhanTam);
        spinnerMaCN.setAdapter(arrayAdapterTam);

        //set du lieu
        editMaCC.setText(maCC + "");
        editNgayCC.setText(ngayCC);
        spinnerMaCN.setSelection(arrayAdapterTam.getPosition(maCN));

        //bat su kien nut bam
        btnHoanTat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ngayCCMoi = String.valueOf(editNgayCC.getText());
                String maCNMoi = arrayMaCongNhanTam.get(spinnerMaCN.getSelectedItemPosition()).toString();
                if (TextUtils.isEmpty(ngayCCMoi) || TextUtils.isEmpty(maCNMoi)) {
                    Toast.makeText(ChamCongActivity.this, "Nội dung cần sửa chưa được nhập", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }
                helper.QueryData("UPDATE ChamCong SET NgayCC='" + ngayCCMoi + "',MaCN='" + maCNMoi + "' WHERE MaCC ='" + maCC + "'");
                dialog.dismiss();
                actionGetData();
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor data = helper.GetData("SELECT * FROM ChiTietChamCong WHERE MaCC ='" + maCC + "'");
                while (data.moveToNext()) {
                    Toast.makeText(ChamCongActivity.this, "Phiếu nhập có vật tư nên không thể xóa", Toast.LENGTH_SHORT).show();
                    return;
                }
                helper.QueryData("DELETE FROM ChamCong WHERE MaCC ='" + maCC + "'");
                dialog.dismiss();
                actionGetData();
            }
        });
        btnBack.setOnClickListener(view -> {
            dialog.cancel();
        });

        dialog.show();
    }



    public void dialogInsert() {
        Dialog dialog = new Dialog(this);
        dialog.setTitle("Thêm chấm công");
        dialog.setContentView(R.layout.dialog_them_cham_cong);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        EditText editMaCC = (EditText) dialog.findViewById(R.id.insert_id_cc);
        EditText editNgayCC = (EditText) dialog.findViewById(R.id.insert_ngay_cc);
        Button btnThem = (Button) dialog.findViewById(R.id.btn_them_cham_cong);
        Button btnHuy = (Button) dialog.findViewById(R.id.btn_thoat);

        //them du lieu vao spinner
        Spinner spinnerMaCN2 = dialog.findViewById(R.id.insert_ma_cn);
        Cursor dataCongNhan = helper.GetData("SELECT * FROM CongNhan");
        ArrayList<CongNhan> arrayTam = new ArrayList<CongNhan>();
        ArrayList<String> arrayTenCNTam = new ArrayList<String>();
        CongNhan congNhanTam;
        while (dataCongNhan.moveToNext()) {
            congNhanTam = new CongNhan(dataCongNhan.getString(0), dataCongNhan.getString(1), dataCongNhan.getString(2),dataCongNhan.getString(3));
            arrayTam.add(congNhanTam);
            arrayTenCNTam.add(congNhanTam.getTen());
        }
        SpinnerCongNhanAdapter arrayAdapterTam=new SpinnerCongNhanAdapter(this,R.layout.custom_pinner,arrayTenCNTam);
        spinnerMaCN2.setAdapter(arrayAdapterTam);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int maCCMoi = 0;
                String ngayCCMoi = String.valueOf(editNgayCC.getText());
                String maCNMoi = String.valueOf(arrayTam.get(spinnerMaCN2.getSelectedItemPosition()).getMaCN());
                if (TextUtils.isEmpty(String.valueOf(editMaCC.getText())) || TextUtils.isEmpty(ngayCCMoi) || TextUtils.isEmpty(maCNMoi)) {
                    Toast.makeText(ChamCongActivity.this, "Nội dung cần thêm chưa được nhập", Toast.LENGTH_SHORT).show();

                    return;
                }

                //kiem tra chu cai
                try {
                    maCCMoi = Integer.parseInt(String.valueOf(editMaCC.getText()));
                } catch (Exception e) {
                    Toast.makeText(ChamCongActivity.this, "Mã CC là một số nguyên", Toast.LENGTH_SHORT).show();
                    return;
                }

                //kiem tra trung
                Cursor dataChamCong= helper.GetData("SELECT * FROM ChamCong");
                ArrayList<ChamCong> arrayChamCong = new ArrayList<ChamCong>();
                ChamCong chamCongTam;
                while (dataChamCong.moveToNext()) {
                    chamCongTam = new ChamCong(dataChamCong.getInt(0), dataChamCong.getString(1), dataChamCong.getString(2));
                    arrayChamCong.add(chamCongTam);
                }

                for (int i = 0; i < arrayChamCong.size(); i++) {
                    if (maCCMoi == arrayChamCong.get(i).getMaCC()) {
                        Toast.makeText(ChamCongActivity.this, "Số chấm công bị trùng", Toast.LENGTH_SHORT).show();

                        return;
                    }
                }

                helper.QueryData("INSERT INTO ChamCong VALUES ('" + maCCMoi + "','" + ngayCCMoi + "', '" + maCNMoi + "')");
                dialog.dismiss();
                actionGetData();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        //dialog.show();
    }
    public void btnClick(View view) {
        onBackPressed();
    }
}