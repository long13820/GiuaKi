package com.example.giuaki.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.giuaki.LoginActivity;
import com.example.giuaki.PreferenceManager;
import com.example.giuaki.R;
import com.google.android.material.imageview.ShapeableImageView;


public class HomeFragment extends Fragment {

    private PreferenceManager preferenceManager;

    ImageButton btnSignOut;
    ShapeableImageView profileImage;
    CardView congNhanCard, chamCongCard, chiTietChamCongCard, sanPhamCard;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setControl(view);


        preferenceManager = new PreferenceManager(getContext());

        setEvent();


        return view;
    }

    private void setEvent() {
        btnSignOut.setOnClickListener(v -> signOut());

        congNhanCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CongNhanActivity.class);
                startActivity(intent);
            }
        });
        chamCongCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChamCongActivity.class);
                startActivity(intent);
            }
        });
        chiTietChamCongCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailTimekeepingActivity.class);
                startActivity(intent);
            }
        });
//        sanPhamCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), ListBaoCaoActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    private void setControl(View view) {
        btnSignOut = view.findViewById(R.id.btnSignOut);
        congNhanCard = view.findViewById(R.id.congNhanCard);
        chamCongCard = view.findViewById(R.id.chamCongCard);
        chiTietChamCongCard = view.findViewById(R.id.chiTietChamCongCard);
        sanPhamCard = view.findViewById(R.id.sanPhamCard);
    }

    private void signOut() {
        Toast.makeText(getContext(), "Đăng xuất...", Toast.LENGTH_LONG).show();
        preferenceManager.clear();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}