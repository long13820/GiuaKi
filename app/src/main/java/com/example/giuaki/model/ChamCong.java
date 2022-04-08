package com.example.giuaki.model;

import java.util.Date;

public class ChamCong {
    private String maCC,maCN,tenCN;
    private Date ngayCC;

    public ChamCong() {
    }

    public ChamCong(String maCC, String maCN, String tenCN, Date ngayCC) {
        this.maCC = maCC;
        this.maCN = maCN;
        this.tenCN = tenCN;
        this.ngayCC = ngayCC;
    }

    public String getMaCC() {
        return maCC;
    }

    public void setMaCC(String maCC) {
        this.maCC = maCC;
    }

    public String getMaCN() {
        return maCN;
    }

    public void setMaCN(String maCN) {
        this.maCN = maCN;
    }

    public String getTenCN() {
        return tenCN;
    }

    public void setTenCN(String tenCN) {
        this.tenCN = tenCN;
    }

    public Date getNgayCC() {
        return ngayCC;
    }

    public void setNgayCC(Date ngayCC) {
        this.ngayCC = ngayCC;
    }

    @Override
    public String toString() {
        return "ChamCong{" +
                "maCC='" + maCC + '\'' +
                ", maCN='" + maCN + '\'' +
                ", tenCN='" + tenCN + '\'' +
                ", ngayCC=" + ngayCC +
                '}';
    }
}
