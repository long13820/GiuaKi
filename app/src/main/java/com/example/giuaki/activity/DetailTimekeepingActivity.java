package com.example.giuaki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giuaki.R;
import com.example.giuaki.database.DBHelper;
import com.example.giuaki.model.DetailTimekeeping;

import java.util.ArrayList;

public class DetailTimekeepingActivity extends AppCompatActivity {
    private TextView tv_sumProduct, tv_sumProductErr, tv_sumPrice, tv_codeCN;
    private Button btnReload, btn_AddItem;
    private ImageView btnBack;
    private DBHelper dbHelper;
    private ListView lvDetailTK;
    private ArrayList<DetailTimekeeping> arrDetailTK = new ArrayList<>();
    private int sumProduct, sumProductErr, sumPrice;

    @Override
    protected void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_detail_timekeeping);
        dbHelper = new DBHelper(this);
        btnBack = (ImageView) findViewById(R.id.btnBackDetail);
        lvDetailTK = (ListView) findViewById(R.id.lv_detail_timekeeping);
        tv_sumPrice = (TextView) findViewById(R.id.sum);
        tv_sumProduct = (TextView) findViewById(R.id.sum_product);
        tv_sumProductErr = (TextView) findViewById(R.id.sum_productErr);
        tv_codeCN = (TextView) findViewById(R.id.codeCN);
        btn_AddItem = findViewById(R.id.btn_addItemDetail);
        btnReload = findViewById(R.id.btn_reloadDetail);
        String maCC = String.valueOf(getIntent().getStringExtra("maCC"));
        tv_codeCN.setText(maCC);
        System.out.println("MaCC : " + maCC);
        loadListDetail(maCC);

        lvDetailTK.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DetailTimekeepingActivity.this, Edit_DetailTimekeeping.class);
                intent.putExtra("name", arrDetailTK.get(i).getNameProduct());
                intent.putExtra("price", arrDetailTK.get(i).getPrice());
                intent.putExtra("value", arrDetailTK.get(i).getValue());
                intent.putExtra("valueErr", arrDetailTK.get(i).getProductErr());
                intent.putExtra("MaCC", maCC);
                startActivity(intent);
            }
        });

        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadListDetail(maCC);
            }
        });

        btn_AddItem.setOnClickListener(view -> {
            Intent intent = new Intent(DetailTimekeepingActivity.this, AddItemTimekeeping.class);
            intent.putExtra("maCC", maCC);
            startActivity(intent);
        });

        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(DetailTimekeepingActivity.this, ChamCongActivity.class);
            startActivity(intent);
            //onBackPressed();
        });
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
    }

    private void loadListDetail(String maCC){
        arrDetailTK = dbHelper.getAllDetailTimekeeping(maCC);
        sumPrice = sumProduct = sumProductErr = 0;
        for (int i = 0; i < arrDetailTK.size(); i++) {
            sumPrice = sumPrice + arrDetailTK.get(i).getSum();
            sumProduct += Integer.valueOf(arrDetailTK.get(i).getValue());
            sumProductErr += Integer.valueOf(arrDetailTK.get(i).getProductErr());
        }

        tv_sumPrice.setText(String.valueOf(sumPrice));
        tv_sumProduct.setText(String.valueOf(sumProduct));
        tv_sumProductErr.setText(String.valueOf(sumProductErr));

        CustomLVDetailTimekeeping customLVDetailTimekeeping = new CustomLVDetailTimekeeping(this, R.layout.item_detail_timekeeping, arrDetailTK);
        lvDetailTK.setAdapter(customLVDetailTimekeeping);
    }
}
