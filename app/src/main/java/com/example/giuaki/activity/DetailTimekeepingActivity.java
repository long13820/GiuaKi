package com.example.giuaki.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giuaki.R;
import com.example.giuaki.database.DBHelper;
import com.example.giuaki.model.DetailTimekeeping;

import java.util.ArrayList;

public class DetailTimekeepingActivity extends AppCompatActivity {
    private TextView tv_sumProduct, tv_sumProductErr, tv_sumPrice, tv_codeCN;
    private DBHelper dbHelper;
    private ListView lvDetailTK;
    private ArrayList<DetailTimekeeping> arrDetailTK = new ArrayList<>();
    private int sumProduct, sumProductErr, sumPrice;

    @Override
    protected void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_detail_timekeeping);
        dbHelper = new DBHelper(this);
        lvDetailTK = (ListView) findViewById(R.id.lv_detail_timekeeping);
        tv_sumPrice = (TextView) findViewById(R.id.sum);
        tv_sumProduct = (TextView) findViewById(R.id.sum_product);
        tv_sumProductErr = (TextView) findViewById(R.id.sum_productErr);
        tv_codeCN = (TextView) findViewById(R.id.codeCN);
        arrDetailTK = dbHelper.getAllDetailTimekeeping("1");

        for (int i = 0; i < arrDetailTK.size(); i++) {
            sumPrice = sumPrice + arrDetailTK.get(i).getSum();
            sumProduct += Integer.valueOf(arrDetailTK.get(i).getValue()) ;
            sumProductErr += Integer.valueOf(arrDetailTK.get(i).getProductErr());
        }

        tv_sumPrice.setText(String.valueOf(sumPrice));
        tv_sumProduct.setText(String.valueOf(sumProduct));
        tv_sumProductErr.setText(String.valueOf(sumProductErr));

        CustomLVDetailTimekeeping customLVDetailTimekeeping = new CustomLVDetailTimekeeping(this, R.layout.item_detail_timekeeping, arrDetailTK);
        lvDetailTK.setAdapter(customLVDetailTimekeeping);
    }
}
