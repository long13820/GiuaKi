package com.example.giuaki.model;

import java.io.Serializable;

public class Worker implements Serializable {
    private String maCN, ho, ten, phanxuong;

    public Worker() {
    }

    public Worker(String maCN, String ho, String ten, String phanxuong) {
        this.maCN = maCN;
        this.ho = ho;
        this.ten = ten;
        this.phanxuong = phanxuong;
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

    public String getPhanxuong() {
        return phanxuong;
    }

    public void setPhanxuong(String phanxuong) {
        this.phanxuong = phanxuong;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "maCN='" + maCN + '\'' +
                ", ho='" + ho + '\'' +
                ", ten='" + ten + '\'' +
                ", phanxuong='" + phanxuong + '\'' +
                '}';
    }
}
