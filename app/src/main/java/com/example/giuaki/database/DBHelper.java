package com.example.giuaki.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.giuaki.model.CongNhan;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static Context context;
    public static String DB_NAME = "TimeKeeping.sqlite";
    public static int DB_VERSION = 1;
    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table users(username TEXT primary key, password TEXT) ");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS CongNhan(MaCN VARCHAR(5) PRIMARY KEY,HoCN VARCHAR(100),TenCN VARCHAR(100),PhanXuong VARCHAR(100))");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS ChamCong(MaCC VARCHAR(5) PRIMARY KEY," + " NgayCC DATE,MaCN VARCHAR(5),FOREIGN KEY(MaCN) REFERENCES ChamCong(MaCN))");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS ChiTietChamCong(MaCC VARCHAR(5) PRIMARY KEY,"+ "SoTP INTEGER,SoPP INTEGER, MaSP VARCHAR(5),FOREIGN KEY(MaSP) REFERENCES SanPham(MaSP))");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS SanPham(MaSP VARCHAR(5) PRIMARY KEY, TenSP VARCHAR,DonGia INTEGER)");

        //Them du lieu cong nhan

        sqLiteDatabase.execSQL("INSERT INTO users VALUES ('hung','123')");
        sqLiteDatabase.execSQL("INSERT INTO users VALUES ('dan','123')");
        sqLiteDatabase.execSQL("INSERT INTO users VALUES ('duc','123')");

        //Them du lieu cong nhan
        sqLiteDatabase.execSQL("INSERT INTO CongNhan VALUES ('CN1','Nguyen','Hung','Bình Chánh')");
        sqLiteDatabase.execSQL("INSERT INTO CongNhan VALUES ('CN2','Tran','Dan','Tân Phú')");
        sqLiteDatabase.execSQL("INSERT INTO CongNhan VALUES ('CN3','Nguyen','Duc','Thủ Đức')");

        //Them du lieu cham cong
        sqLiteDatabase.execSQL("INSERT INTO ChamCong VALUES ('1','20/06/2021', 'CN1')");
        sqLiteDatabase.execSQL("INSERT INTO ChamCong VALUES ('2','07/07/2021', 'CN1')");
        sqLiteDatabase.execSQL("INSERT INTO ChamCong VALUES ('3','02/01/2021', 'CN2')");
        sqLiteDatabase.execSQL("INSERT INTO ChamCong VALUES ('4','05/03/2021', 'CN2')");
        sqLiteDatabase.execSQL("INSERT INTO ChamCong VALUES ('5','25/05/2021', 'CN3')");

        //Them du lieu chi tiet phieu nhap
        sqLiteDatabase.execSQL("INSERT INTO ChiTietChamCong VALUES ('1',4, 1, 'SP1')");
        sqLiteDatabase.execSQL("INSERT INTO ChiTietChamCong VALUES ('2',2, 0, 'SP2')");
        sqLiteDatabase.execSQL("INSERT INTO ChiTietChamCong VALUES ('3',3, 1, 'SP3')");
        sqLiteDatabase.execSQL("INSERT INTO ChiTietChamCong VALUES ('4',2, 1, 'SP1')");
        sqLiteDatabase.execSQL("INSERT INTO ChiTietChamCong VALUES ('5',4, 1, 'SP2')");


        //Them du lieu San Pham
        sqLiteDatabase.execSQL("INSERT INTO SanPham VALUES ('SP1','Gạch ống', 10000)");
        sqLiteDatabase.execSQL("INSERT INTO SanPham VALUES ('SP2','Gạch thẻ', 100000)");
        sqLiteDatabase.execSQL("INSERT INTO SanPham VALUES ('SP3','Sắt tròn', 300000)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int i1) {
        sqLiteDatabase.execSQL("drop table if exists users");
    }
    public Boolean insertData(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("password", password);

        long result = db.insert("users", null, values);
        if(result == -1) return false;

        else return true;
    }
    public void setUpdate(String username,String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        myDB.execSQL("INSERT INTO user VALUES ('"+username+"','"+password+"')");

    }
    public Boolean checkUsername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=?", new String[] {username});

        if(cursor.getCount()>0){
            return true;
        }
        else return false;
    }
    public Boolean checkUsernamePassword(String username,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=? and password=?", new String[] {username,password});

        if(cursor.getCount()>0){
            return true;
        }
        else return false;
    }

    public List<CongNhan> getAllEmployees() {
        List<CongNhan>  congNhans = new ArrayList<>();
        String query = "SELECT * FROM CongNhan";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            CongNhan congNhan = new CongNhan(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            cursor.moveToNext();
            congNhans.add(congNhan);
        }
        return congNhans;
    }
    public void themCongNhan(CongNhan congNhan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaCN",congNhan.getMaCN());
        values.put("HoCN",congNhan.getHo());
        values.put("TenCN",congNhan.getTen());
        values.put("PhanXuong",congNhan.getPhan_xuong());
        db.insert("CongNhan", null, values);
        db.close();
    }
    public Boolean checkMaCN(String maCN){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from CongNhan where MaCN=?", new String[] {maCN});

        if(cursor.getCount()>0) return true;
        return false;
    }

    public int updateCongNhan(CongNhan congNhan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaCN",congNhan.getMaCN());
        values.put("HoCN",congNhan.getHo());
        values.put("TenCN",congNhan.getTen());
        values.put("PhanXuong",congNhan.getPhan_xuong());
        // updating row
        return db.update("congNhan", values,   "MaCN = ?",
                new String[] { String.valueOf(congNhan.getMaCN()) });
    }
}
