package com.example.giuaki;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import android.widget.Toast;


import com.example.giuaki.activity.MenuActivity;
import com.example.giuaki.activity.RegisterActivity;
import com.example.giuaki.database.DBHelper;
import com.example.giuaki.model.User;


public class LoginActivity extends AppCompatActivity {

    DBHelper DB;
    EditText inputEmail, inputPassword;
    Button btnlogin, btndangki;
    android.widget.CheckBox CheckBox;
    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DB =  DBHelper.getInstance(this);
        setControl();
        setEvent();

        if (preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)) {
            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
    }
    private void setEvent() {
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check()) {
                    User user = DB.checkUserExist(inputEmail.getText().toString().trim(), inputPassword.getText().toString().trim());
                    if (user != null) {
//                        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
//                        preferenceManager.putString(Constants.KEY_USER_ID, user.getId() + "");
//                        preferenceManager.putString(Constants.KEY_NAME, user.getFirstname() + " " + user.getLastname());
//                        preferenceManager.putString(Constants.KEY_EMAIL, user.getEmail());
//                        preferenceManager.putString(Constants.KEY_IMAGE, user.getImageBitmap());

                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại!", Toast.LENGTH_LONG).show();

                    }
                }
            }
        });
        btndangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        CheckBox = findViewById(R.id.showpass);
        CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (b) {
                    inputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }
    private void setControl() {
        DB = DBHelper.getInstance(this);
        preferenceManager = new PreferenceManager(getApplicationContext());
        btnlogin = findViewById(R.id.btlg);
        btndangki = findViewById(R.id.btndk);
        inputEmail = findViewById(R.id.editEmail);
        inputPassword = findViewById(R.id.editPass);
    }
    private boolean check() {
        if (inputEmail.getText().toString().trim().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Enter email", Toast.LENGTH_LONG).show();
            return false;
        } else if (inputPassword.getText().toString().trim().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public void signUp(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
    public void forgotPassword(View view) {

        String email=inputEmail.getText().toString();
        if(email.isEmpty()){
            Toast.makeText(LoginActivity.this, "Enter email", Toast.LENGTH_LONG).show();
            return;
        }

        Cursor data= DB.GetData("SELECT * FROM User WHERE EMAIL='"+email+"'");

        while (data.moveToNext()) {
            Intent intent = new Intent(LoginActivity.this, OTPActivity.class);
            intent.putExtra("gmail",inputEmail.getText().toString());
            Log.d("gmail",inputEmail.getText().toString());
            startActivity(intent);
        }
        Toast.makeText(LoginActivity.this, "Email not exist", Toast.LENGTH_LONG).show();

    }
}
