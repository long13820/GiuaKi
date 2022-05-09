package com.example.giuaki.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.giuaki.R;
import com.example.giuaki.database.DBHelper;
import com.example.giuaki.model.DetailTimekeeping;

import java.util.ArrayList;

public class AddItemTimekeeping extends AppCompatActivity {
    DBHelper dbHelper;
    private Spinner spinner;
    private TextView tv_TenSP, tv_Loi;
    private EditText value, valueErr,maCC;
    private Button btn_Add, btn_Exit;
    private ArrayList<String> arrTenSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_timekeeping);
        dbHelper = new DBHelper(this);
        spinner = findViewById(R.id.spinner);
        tv_TenSP = findViewById(R.id.tv_TenSP);
        tv_Loi = findViewById(R.id.tv_Loi);
        value = findViewById(R.id.insert_value);
        valueErr = findViewById(R.id.insert_valueErr);
        btn_Add = findViewById(R.id.btn_them_item_timekeeping);
        btn_Exit = findViewById(R.id.btn_thoat);
        arrTenSP = dbHelper.getAllSanPham();
        maCC = findViewById(R.id.tv_ma_cc);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrTenSP);

        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tv_TenSP.setText(dbHelper.getMaSP(arrTenSP.get(i)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_Add.setOnClickListener(view -> {
            if(!value.getText().toString().equals("") && !valueErr.getText().toString().equals("")){
                DetailTimekeeping Add = new DetailTimekeeping(spinner.getSelectedItem().toString(), "0", value.getText().toString(), valueErr.getText().toString(), 0);
                dbHelper.themChiTietChamCong(Add,maCC.getText().toString().trim());

                Intent intent = new Intent(AddItemTimekeeping.this, ChamCongActivity.class);
                startActivity(intent);
            }else{
                tv_Loi.setText("Vui Lòng Xem Lại Số Lượng Sản Phẩm và Phế Phẩm");
            }
        });
    }
}