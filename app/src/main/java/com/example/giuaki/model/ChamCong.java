package com.example.giuaki.model;

import java.io.Serializable;
import java.util.Date;

public class ChamCong implements Serializable {
    public int MaCC;
    public String ngayCC;
    public String MaCN;

    public ChamCong(int maCC, String ngayCC, String maCN) {
        MaCC = maCC;
        this.ngayCC = ngayCC;
        MaCN = maCN;
    }

    public int getMaCC() {
        return MaCC;
    }

    public void setMaCC(int maCC) {
        MaCC = maCC;
    }

    public String getNgayCC() {
        return ngayCC;
    }

    public void setNgayCC(String ngayCC) {
        this.ngayCC = ngayCC;
    }

    public String getMaCN() {
        return MaCN;
    }

    public void setMaCN(String maCN) {
        MaCN = maCN;
    }

    @Override
    public String toString() {
        return "ChamCong{" +
                "MaCC='" + MaCC + '\'' +
                ", ngayCC=" + ngayCC +
                ", MaCN='" + MaCN + '\'' +
                '}';
    }
}
