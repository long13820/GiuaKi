package com.example.giuaki;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.giuaki.activity.MenuActivity;
import com.example.giuaki.database.DBHelper;
import com.example.giuaki.model.Utils;

public class LoginActivity extends AppCompatActivity {

    DBHelper DB;
    EditText editUser, editPass;
    Button btnlogin, btndangki;
    android.widget.CheckBox CheckBox;
    ImageButton btncall, btnweb, btnfb;
    public Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DB = new DBHelper(this);
        Anhxa();
        ControlBtn();

    }
    private void Anhxa() {
        editUser = (EditText) findViewById(R.id.editUser);
        editPass = (EditText) findViewById(R.id.editPass);
        btnlogin = findViewById(R.id.btlg);
        btndangki = findViewById(R.id.btndk);
        CheckBox = findViewById(R.id.showpass);
        CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (b) {
                    editPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    editPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }
    private void ControlBtn(){
        btndangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(LoginActivity.this);
                dialog.setTitle("Hộp thoại đăng kí");
                dialog.setCancelable(false);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.activity_signup);
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                EditText edtk = (EditText)dialog.findViewById(R.id.editUser_SU);
                EditText edmk = (EditText)dialog.findViewById(R.id.editPass_SU);
                final EditText edmkagain = (EditText)dialog.findViewById(R.id.editPassAgain_SU);

                Button btndongy = (Button)dialog.findViewById(R.id.btndongy);
                Button btnback = (Button)dialog.findViewById(R.id.btnthoat);
                btnback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                btndongy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String user = edtk.getText().toString();
                        String pass = edmk.getText().toString();
                        String repass = edmkagain.getText().toString();
                        if(user.equals("")|| pass.equals("")|| repass.equals("")){
                            Toast.makeText(LoginActivity.this, "Vui lòng điền vào ô trống",Toast.LENGTH_LONG).show();

                        }
                        else{
                            if (pass.equals(repass)) {
                                Boolean checkuser = DB.checkUsername(user);
                                if(checkuser ==false){
                                    Boolean insert = DB.insertData(user,pass);

                                    if(insert == true){
                                        Toast.makeText(LoginActivity.this,"Đăng kí thành công",Toast.LENGTH_SHORT).show();
                                        DB.setUpdate(user,pass);
                                        dialog.cancel();
                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this,"Đăng kí thất bại",Toast.LENGTH_LONG).show();
                                    }
                                }
                                else{
                                    Toast.makeText(LoginActivity.this,"Tài khoản đã tồn tại",Toast.LENGTH_LONG).show();
                                }
                            }
                            else{
                                Toast.makeText(LoginActivity.this,"Mật khẩu không khớp",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editUser.getText().length()!= 0 && editPass.getText().length()!= 0){
                    if(DB.checkUsernamePassword(editUser.getText().toString(),editPass.getText().toString()) == true){
                        Utils.username = editUser.getText().toString().trim();
                        Utils.pass = editPass.getText().toString().trim();
                        Toast.makeText(LoginActivity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                        intent = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(LoginActivity.this,"Tài khoản hoặc mật khẩu sai",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this,"Vui lòng nhập tài khoản và mật khẩu",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
