package com.example.giuaki.model;

import java.io.Serializable;

public class CongNhan implements Serializable {
    private String MaCN;
    private String Ho;
    private String Ten;
    private String PhanXuong;

    public String getPhan_xuong() {
        return PhanXuong;
    }

    public void setPhan_xuong(String phan_xuong) {
        PhanXuong = phan_xuong;
    }

    public CongNhan(String maCN, String ho, String ten,String phan_xuong) {
        MaCN = maCN;
        Ho = ho;
        Ten = ten;
        PhanXuong = phan_xuong;
    }

    public String getMaCN() {
        return MaCN;
    }

    public void setMaCN(String maCN) {
        MaCN = maCN;
    }

    public String getHo() {
        return Ho;
    }

    public void setHo(String ho) {
        Ho = ho;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }
}
