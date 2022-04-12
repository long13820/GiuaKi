package com.example.giuaki.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giuaki.R;
import com.example.giuaki.database.DBHelper;

public class Edit_DetailTimekeeping extends AppCompatActivity {
    DBHelper dbHelper;
    private Button btn_save, btn_cancel;
    private String name, price, value, valueErr;
    private Intent intent;
    private TextView tv_name, tv_price, tv_hint;
    private EditText ed_value, ed_valueErr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_detail_timekeeping);
        dbHelper = new DBHelper(this);
        tv_name = findViewById(R.id.tv_nameproduct);
        tv_price = findViewById(R.id.tv_priceProduct);
        tv_hint = findViewById(R.id.hint);
        ed_value = findViewById(R.id.editValue);
        ed_valueErr = findViewById(R.id.editValueErr);
        btn_save = (Button) findViewById(R.id.save_button);
        btn_cancel = (Button) findViewById(R.id.cancel_button);
        intent = this.getIntent();

        if (intent != null) {
            name = intent.getStringExtra("name");
            price = intent.getStringExtra("price");
            value = intent.getStringExtra("value");
            valueErr = intent.getStringExtra("valueErr");
        }

        tv_name.setText(name);
        tv_price.setText(price);
        ed_value.setText(value);
        ed_valueErr.setText(valueErr);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Edit_DetailTimekeeping.this, DetailTimekeepingActivity.class);
                if(!ed_value.getText().toString().equals("") && Integer.parseInt(ed_value.getText().toString()) >= 0 &&
                        !ed_valueErr.getText().toString().equals("") && Integer.parseInt(ed_valueErr.getText().toString()) >= 0
                ){
                    dbHelper.updateItemDetailTimekeeping(ed_value.getText().toString(), ed_valueErr.getText().toString(), tv_name.getText().toString());
                }else{
                    tv_hint.setText("Vui Lòng Xem Lại SL Sản Phẩm hoặc SL Sản Phẩm Lỗi");
                }
                onBackPressed();
            }
        });
    }
}