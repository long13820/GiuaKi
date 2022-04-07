package com.example.giuaki.model;

public class CongNhan {
    private String maCN;
    private String ho;
    private String ten;
    private String phan_xuong;

    public String getPhan_xuong() {
        return phan_xuong;
    }

    public void setPhan_xuong(String phan_xuong) {
        this.phan_xuong = phan_xuong;
    }

    public CongNhan(String maCN, String ho, String ten,String phan_xuong) {
        this.maCN = maCN;
        this.ho = ho;
        this.ten = ten;
        this.phan_xuong = phan_xuong;
    }

    public String getMaCN() {
        return maCN;
    }

    public void setMaCN(String maCN) {
        this.maCN = maCN;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}
