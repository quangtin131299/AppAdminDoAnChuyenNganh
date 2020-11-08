package com.example.appadmindatvephim.DTO;

import java.io.Serializable;

public class Cinema implements Serializable {
    private int id;
    private String tenRap;
    private String hinh;
    private String diaChi;

    public int getiD() {
        return id;
    }

    public void setiD(int id) {
        this.id = id;
    }

    public String getTenRap() {
        return tenRap;
    }

    public void setTenRap(String tenRap) {
        this.tenRap = tenRap;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Cinema() {
    }

    public Cinema(int id, String tenRap, String hinh, String diaChi) {
        this.id = id;
        this.tenRap = tenRap;
        this.hinh = hinh;
        this.diaChi = diaChi;
    }
}
