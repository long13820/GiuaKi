package com.example.giuaki.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.giuaki.model.CongNhan;
import com.example.giuaki.model.DetailTimekeeping;
import com.example.giuaki.model.SanPham;
import com.example.giuaki.model.User;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static String DB_NAME = "TimeKeeping.sqlite";
    public static int DB_VERSION = 1;
   ;

    private static DBHelper sInstance;

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static synchronized DBHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DBHelper(context.getApplicationContext());
        }
        return sInstance;
    }
    //Truy van khong tra ket qua
    public void QueryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }


    //Truy van tra ket qua
    public Cursor GetData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS CongNhan(MaCN VARCHAR(5) PRIMARY KEY,HoCN VARCHAR(100),TenCN VARCHAR(100),PhanXuong VARCHAR(100))");
//        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS ChamCong(MaCC INTEGER PRIMARY KEY,NgayCC VARCHAR(100), MaCN VARCHAR(5))");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " +
                "User(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "FIRSTNAME VARCHAR(100)," +
                "LASTNAME VARCHAR(100)," +
                "EMAIL VARCHAR(100), " +
                "PASSWORD VARCHAR(100)," +
                "IMAGE VARCHAR(5000))");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " +
                "CongNhan" +
                "(MaCN VARCHAR(5) PRIMARY KEY" +
                ",HoCN VARCHAR(100)" +
                ",TenCN VARCHAR(100)" +
                ",PhanXuong VARCHAR(100))" +
                "");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS ChamCong" +
                "(MaCC INTEGER PRIMARY KEY" +
                ",NgayCC DATE" +
                ",MaCN VARCHAR(5)" +
                ", FOREIGN KEY (MaCN)" +
                " REFERENCES CongNhan (MaCN)" +
                ")");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS ChiTietChamCong" +
                "(MaCC" +
                " REFERENCES ChamCong(MaCC)" +
                ",SoTP INTEGER" +
                ",SoPP INTEGER" +
                ",MaSP VARCHAR(5)" +
                ",FOREIGN KEY(MaSP)" +
                " REFERENCES SanPham(MaSP)" +
                ")");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS SanPham(MaSP VARCHAR(5) PRIMARY KEY, TenSP VARCHAR,DonGia INTEGER)");



        //Them du lieu cong nhan
        sqLiteDatabase.execSQL("INSERT INTO CongNhan VALUES ('CN1','Nguyen','Hung','Bình Chánh')");
        sqLiteDatabase.execSQL("INSERT INTO CongNhan VALUES ('CN2','Tran','Long','Tân Phú')");
        sqLiteDatabase.execSQL("INSERT INTO CongNhan VALUES ('CN3','Nguyen','Duc','Thủ Đức')");

        //Them du lieu cham cong
        sqLiteDatabase.execSQL("INSERT INTO ChamCong VALUES (1,'2022-04-17', 'CN1')");
        sqLiteDatabase.execSQL("INSERT INTO ChamCong VALUES (2,'2022-04-17', 'CN1')");
        sqLiteDatabase.execSQL("INSERT INTO ChamCong VALUES (3,'2022-04-17', 'CN2')");
        sqLiteDatabase.execSQL("INSERT INTO ChamCong VALUES (4,'2022-04-17', 'CN2')");
        sqLiteDatabase.execSQL("INSERT INTO ChamCong VALUES (5,'2022-04-17', 'CN3')");


        //user
        sqLiteDatabase.execSQL("INSERT INTO User(FIRSTNAME,LASTNAME, EMAIL, PASSWORD) VALUES ('admin','1','a','1')");

//        //Them du lieu chi tiet phieu nhap
//        sqLiteDatabase.execSQL("INSERT INTO ChiTietChamCong VALUES ('1',4, 1, 'SP1')");
//        sqLiteDatabase.execSQL("INSERT INTO ChiTietChamCong VALUES ('2',2, 0, 'SP2')");
//        sqLiteDatabase.execSQL("INSERT INTO ChiTietChamCong VALUES ('3',3, 1, 'SP3')");
//        sqLiteDatabase.execSQL("INSERT INTO ChiTietChamCong VALUES ('4',2, 1, 'SP1')");
//        sqLiteDatabase.execSQL("INSERT INTO ChiTietChamCong VALUES ('5',4, 1, 'SP2')");
//        sqLiteDatabase.execSQL("INSERT INTO ChiTietChamCong VALUES ('6',3, 3, 'SP3')");

        //Them du lieu San Pham
        sqLiteDatabase.execSQL("INSERT INTO SanPham VALUES ('SP1','Gạch ống', 10000)");
        sqLiteDatabase.execSQL("INSERT INTO SanPham VALUES ('SP2','Gạch thẻ', 100000)");
        sqLiteDatabase.execSQL("INSERT INTO SanPham VALUES ('SP3','Sắt tròn', 300000)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int i1) {

    }

    public int addUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = "WHERE EMAIL='" + user.getEmail() + "'";
        Cursor cursor = db.rawQuery("SELECT * FROM User " + selection, null);
        int count = cursor.getCount();
        if (count>1) {
            return 1;
        }

        db.execSQL("INSERT INTO User(FIRSTNAME,LASTNAME, EMAIL, PASSWORD, IMAGE) VALUES ('" + user.getFirstname()
                + "','" + user.getLastname()
                + "','" + user.getEmail()
                + "','" + user.getPassword()
                + "','" + user.getImageBitmap()
                + "')");
        return 0;
    }

    public User checkUserExist(String username, String password) {
        User user = null;
        SQLiteDatabase db = getReadableDatabase();

        String selection = "WHERE EMAIL='" + username + "' and PASSWORD = '" + password + "'";

        Cursor cursor = db.rawQuery("SELECT * FROM User " + selection, null);
        int count = cursor.getCount();
        System.out.println(count + "");
        while (cursor.moveToNext()) {
            user = new User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
            );
        }

        cursor.close();
        close();

        if (count > 0) {
            return user;
        } else {
            return null;
        }
    }



    public void setUpdate(String username, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        myDB.execSQL("INSERT INTO user VALUES ('" + username + "','" + password + "')");

    }


    public Boolean checkUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=?", new String[]{username});

        if (cursor.getCount() > 0) {
            return true;
        } else return false;
    }

    public Boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=? and password=?", new String[]{username, password});

        if (cursor.getCount() > 0) {
            return true;
        } else return false;
    }


    public Boolean insertData(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("password", password);

        long result = db.insert("users", null, values);
        if (result == -1) return false;

        else return true;
    }



    public List<CongNhan> getAllEmployees() {
        List<CongNhan> congNhans = new ArrayList<>();
        String query = "SELECT * FROM CongNhan";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            CongNhan congNhan = new CongNhan(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            cursor.moveToNext();
            congNhans.add(congNhan);
        }
        return congNhans;
    }

    public List<SanPham> getAllProducts() {
        List<SanPham> dsSanPham = new ArrayList<>();
        String query = "SELECT * FROM SanPham";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            SanPham sanPham = new SanPham(cursor.getString(0), cursor.getString(1), cursor.getString(2));
            cursor.moveToNext();
            dsSanPham.add(sanPham);
        }
        return dsSanPham;
    }
    public void themSanPham(SanPham sanPham) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaSP", sanPham.getMaSP());
        values.put("TenSP", sanPham.getTenSP());
        values.put("DonGia", sanPham.getDonGia());
        db.insert("SanPham", null, values);
        db.close();
    }

    public int updateSanPham(SanPham sanPham) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaSP", sanPham.getMaSP());
        values.put("TenSP", sanPham.getTenSP());
        values.put("DonGia", sanPham.getDonGia());
        // updating row
        return db.update("SanPham", values, "MaSP = ?",
                new String[]{String.valueOf(sanPham.getMaSP())});
    }

    public boolean xoaSanPham(String maSP) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("SanPham", "MaSP = ?", new String[]{maSP}) > 0;
    }

    public void themCongNhan(CongNhan congNhan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaCN", congNhan.getMaCN());
        values.put("HoCN", congNhan.getHo());
        values.put("TenCN", congNhan.getTen());
        values.put("PhanXuong", congNhan.getPhan_xuong());
        db.insert("CongNhan", null, values);
        db.close();
    }

    public void themChiTietChamCong(DetailTimekeeping value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // MaCC SoTP SoPP MaSP
        values.put("MaCC", "1");
        values.put("SoTP", value.getValue());
        values.put("SoPP", value.getProductErr());
        values.put("MaSP", getMaSP(value.getNameProduct()));
        db.insert("ChiTietChamCong", null, values);
        db.close();
    }

    public Boolean checkMaCN(String maCN) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from CongNhan where MaCN=?", new String[]{maCN});

        if (cursor.getCount() > 0) return true;
        return false;
    }

    public int updateCongNhan(CongNhan congNhan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaCN", congNhan.getMaCN());
        values.put("HoCN", congNhan.getHo());
        values.put("TenCN", congNhan.getTen());
        values.put("PhanXuong", congNhan.getPhan_xuong());
        // updating row
        return db.update("congNhan", values, "MaCN = ?",
                new String[]{String.valueOf(congNhan.getMaCN())});
    }



    public String getMaCN(String taiKhoan) {
        String maCN = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=?", new String[]{taiKhoan});

        while (cursor.moveToNext()) {
            maCN = cursor.getString(2);
        }
        return maCN;
    }

    public String getTenSanPham(String maSP) {
        String tenSP = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from SanPham where MaSP=?", new String[]{maSP});

        while (cursor.moveToNext()) {
            tenSP = cursor.getString(1);
        }
        return tenSP;
    }

    public String getGiaSanPham(String maSP) {
        String gia = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from SanPham where MaSP=?", new String[]{maSP});

        while (cursor.moveToNext()) {
            gia = cursor.getString(2);
        }
        return gia;
    }

    public String getMaSP(String tenSP) {
        String MaSP = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from SanPham where TenSP=?", new String[]{tenSP});

        while (cursor.moveToNext()) {
            MaSP = cursor.getString(0);
        }
        return MaSP;
    }

    public ArrayList<DetailTimekeeping> getAllDetailTimekeeping(String maCC) {
        ArrayList<DetailTimekeeping> detailTimekeepings = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ChiTietChamCong where maCC=?", new String[]{maCC});
        cursor.moveToFirst();
        //MaCC SoTP SoPP MaSP
        //name price value valueErr sum
        while (cursor.isAfterLast() == false) {
            DetailTimekeeping itemdetailTimekeeping = new DetailTimekeeping(getTenSanPham(cursor.getString(3)), getGiaSanPham(cursor.getString(3)), cursor.getString(1), cursor.getString(2), (Integer) (Integer.parseInt(getGiaSanPham(cursor.getString(3))) * Integer.parseInt(cursor.getString(1))));
            cursor.moveToNext();
            detailTimekeepings.add(itemdetailTimekeeping);
        }
        return detailTimekeepings;
    }

    public ArrayList<String> getAllSanPham() {
        ArrayList<String> ListTenSP = new ArrayList<>();
        //MaSP TenSP Gia
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SanPham ", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            String tenSP = cursor.getString(1);
            cursor.moveToNext();
            ListTenSP.add(tenSP);
        }
        return ListTenSP;
    }



    public int updateItemDetailTimekeeping(String SL, String SLErr, String name ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaCC", "1");
        values.put("SoTP", SL);
        values.put("SoPP", SLErr);
        values.put("MaSP", getMaSP(name));
        //ChiTietChamCong(MaCC VARCHAR(5) PRIMARY KEY," + "SoTP INTEGER,SoPP INTEGER, MaSP VARCHAR(5),FOREIGN KEY(MaSP) REFERENCES SanPham(MaSP))");
        // updating row
        return db.update("ChiTietChamCong", values, "MaCC = ? AND MaSP=?",
                new String[]{"1",getMaSP(name)});
    }

    public boolean deleteCongNhan(String maCN) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("congNhan", "MaCn = ?", new String[]{maCN}) > 0;
    }
}
