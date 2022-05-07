package com.example.giuaki.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.giuaki.LoginActivity;
import com.example.giuaki.PreferenceManager;
import com.example.giuaki.R;

public class MenuActivity extends AppCompatActivity {
    CardView cvWorker, cvTimekeeping, cvDetail, cvProduct;
    ImageButton signOut;

    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        FragmentTransaction ft;
        HomeFragment fragmentHome = new HomeFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment_content_main, fragmentHome).commit();
    }

}