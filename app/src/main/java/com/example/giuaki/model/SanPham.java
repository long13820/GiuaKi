package com.example.giuaki.model;

public class SanPham {
    private String MaSP;
    private String TenSP;
    private String DonGia;

    public SanPham() {
    }

    public SanPham(String maSP, String tenSP, String donGia) {
        MaSP = maSP;
        TenSP = tenSP;
        DonGia = donGia;
    }

    public String getMaSP() {
        return MaSP;
    }

    public void setMaSP(String maSP) {
        MaSP = maSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public String getDonGia() {
        return DonGia;
    }

    public void setDonGia(String donGia) {
        DonGia = donGia;
    }
}
