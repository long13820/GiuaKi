package com.example.giuaki;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giuaki.activity.HomeActivity;
import com.example.giuaki.database.DBHelper;

public class LoginActivity extends AppCompatActivity {

    EditText username,password;
    Button signin;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        username = findViewById(R.id.txtUsername);
        password = findViewById(R.id.txtPhone);
        signin = findViewById(R.id.btnButton);
        DB= new DBHelper(this);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)){
                    Toast.makeText(LoginActivity.this, "All fields Required", Toast.LENGTH_LONG).show();
                }
                else{
                    Boolean checkuserpass = DB.checkUsernamePassword(user,pass);
                    if(checkuserpass == true){
                        Toast.makeText(LoginActivity.this,"Login Successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(LoginActivity.this,"Login Failed",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


    }
    public void onLoginClick(View view){
        startActivity(new Intent(this, ResigterActivity.class));
       
    }
}
