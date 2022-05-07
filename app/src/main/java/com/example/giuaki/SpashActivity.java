package com.example.giuaki;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class SpashActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView textView;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        lottieAnimationView = findViewById(R.id.lottie);

        progressBar = findViewById(R.id.progress_bar);
        textView = findViewById(R.id.text_view);


        progressBar.setMax(100);

        progressAnimation();
    }

    public void progressAnimation() {
        ProgressBarAnimation amin = new ProgressBarAnimation(this, progressBar, textView, 0f, 10f);
        amin.setDuration(800);
        progressBar.setAnimation(amin);
    }
}