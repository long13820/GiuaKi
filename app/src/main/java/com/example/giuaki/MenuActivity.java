package com.example.giuaki;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;

public class MenuActivity extends AppCompatActivity {
    CardView cvWorker, cvTimekeeping, cvDetail, cvProduct;
       
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
}