package com.example.giuaki;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giuaki.activity.HomeActivity;
import com.example.giuaki.database.DBHelper;

public class ResigterActivity extends AppCompatActivity {

    EditText username,password,repassword;
    TextView txtLogin;
    Button register;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_signup);
        changeStatusBarColor();

        username = findViewById(R.id.txtUsername);
        password = findViewById(R.id.txtPhone);
        register = findViewById(R.id.btnButton);
        txtLogin = findViewById(R.id.txtLogin1);
        DB= new DBHelper(this);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass)){
                    Toast.makeText(ResigterActivity.this,"All fields Required",Toast.LENGTH_LONG).show();
                }
                else{
                    if(pass.equals(repass)){
                        Boolean checkuser =DB.checkUsername(user);
                        if(checkuser == false){
                            Boolean insert = DB.checkUsername(user);
                            if(insert == true)
                            {
                                Toast.makeText(ResigterActivity.this, "Registered Succesfully",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(ResigterActivity.this,"Registration Failed",Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(ResigterActivity.this,"User already Exists",Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(ResigterActivity.this,"Passwords are not matching",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void changeStatusBarColor(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }
    public void onLoginClick(View view){
        startActivity(new Intent(this, LoginActivity.class));
        //overridePendingTransition(R.anim.slide);
    }

}
