package com.example.giuaki.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.giuaki.R;

public class MenuActivity extends AppCompatActivity {
    CardView cvWorker, cvTimekeeping, cvDetail, cvProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        addControls();
        addEvents();
    }
    private void addControls(){
        cvWorker = findViewById(R.id.cvWorker);
        cvTimekeeping = findViewById(R.id.cvTimekeeping);
        cvDetail = findViewById(R.id.cvDetail);
        cvProduct = findViewById(R.id.cvProduct);
    }
    private void addEvents() {
//        cvWorker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity2.this, ManageWorker.class);
//                startActivity(intent);
//            }
//        });
        cvTimekeeping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ChamCongActivity.class);
                startActivity(intent);
            }
        });
        cvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // chỗ này chỉ set tạm để demo cái send mail khi đổi mk
                //nào làm cái chi tiết user thêm cái button change mk thì set lại
                Intent intent = new Intent(MenuActivity.this, ChangePassActivity.class);
                startActivity(intent);
            }
        });
//        cvProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity2.this, ManageProduct.class);
//                startActivity(intent);
//            }
//        });
        cvWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, CongNhanActivity.class);
                startActivity(intent);
            }
        });
    }
}