package com.example.giuaki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import com.example.giuaki.R;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TimekeepingActivity extends AppCompatActivity {
    Button btnBack;
//    ArrayList<ChamCong> chamcong;
    ImageButton btnadd,btndelete,btnupdate;
    private Intent intent;
    ListView lv;
    //ChamCongAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timekeeping);
//        chamcong = new ArrayList<ChamCong>();
    }
}
