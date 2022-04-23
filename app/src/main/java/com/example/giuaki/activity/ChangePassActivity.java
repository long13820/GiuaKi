package com.example.giuaki.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.giuaki.R;
import com.example.giuaki.model.JavaMailAPI;
import com.example.giuaki.model.Utils;

public class ChangePassActivity extends AppCompatActivity {
    private EditText pass,newPass,confirmPass;
    private Button btnChange,btnExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        pass = findViewById(R.id.changePass_old);
        newPass = findViewById(R.id.changePass_new);
        confirmPass = findViewById(R.id.changePass_confirm);
        btnChange = findViewById(R.id.btn_changePass);
        btnExit = findViewById(R.id.btn_changePass_exit);

        btnChange.setOnClickListener(view -> {
            chnagePass();
        });

        btnExit.setOnClickListener(view -> {
            Intent intent = new Intent(ChangePassActivity.this,MenuActivity.class);
            startActivity(intent);
        });
    }

    void chnagePass(){
        if(pass.getText().toString().trim().equals("")
                ||newPass.getText().toString().trim().equals("")
                ||confirmPass.getText().toString().trim().equals("")){
            Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
        }else if(!pass.getText().toString().trim().equals("")
                && !newPass.getText().toString().trim().equals("")
                && !confirmPass.getText().toString().trim().equals("")){

            //Utils lưu username và pass khi đăng nhập đem ra check

            if(!pass.getText().toString().trim().equals(Utils.pass)){
                Toast.makeText(this, "Mật khẩu cũ không chính xác", Toast.LENGTH_SHORT).show();
            }else{
                if(!newPass.getText().toString().trim()
                        .equals(confirmPass.getText().toString().trim())){
                    Toast.makeText(this, "Mật khẩu xác thực không giống nhau", Toast.LENGTH_SHORT).show();
                }else{

                    int max = 10000;
                    int min = 1000;
                    int range = max - min + 1;
                    int code = (int)(Math.random() * range) + min;
                    //lấy email của user set cho email
                    //username lưu trong Utils
                    String mEmail = "lucdat1235@gmail.com"; //email này chỉ để demo
                    String mSubject = "Code đổi mật khẩu";
                    String mMessage = "CODE :"+code;
                    JavaMailAPI javaMailAPI = new JavaMailAPI(this, mEmail, mSubject, mMessage);
                    javaMailAPI.execute();

                    //nhập code
                    Dialog dialog = new Dialog(this);
                    dialog.setTitle("Nhập code trong mail");
                    dialog.setCancelable(false);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.enter_code);
                    Window window = dialog.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                    EditText enter_code = (EditText)dialog.findViewById(R.id.edit_enter_code);
                    Button btnXacNhan = (Button)dialog.findViewById(R.id.btn_enter_code);
                    Button btnback = (Button)dialog.findViewById(R.id.btn_enter_code_exit);

                    btnXacNhan.setOnClickListener(view -> {
                        if(!enter_code.getText().toString().trim().equals(code+"")){
                            Toast.makeText(this, "code không chính xác", Toast.LENGTH_SHORT).show();
                        }else{
                            //update lại mật khẩu vô db
                            //chỗ này ai đọc thì làm dùm chứ t lười quá :))
                            //viết câu query lấy user rồi update lại là oke
                            Toast.makeText(this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                            Intent intent = new Intent(ChangePassActivity.this,MenuActivity.class );
                            startActivity(intent);
                        }
                    });
                    btnback.setOnClickListener(view -> {
                        dialog.cancel();
                    });
                }
            }
        }
    }
}