package com.example.appadmindatvephim.DTO;

import java.io.Serializable;

public class Movie implements Serializable {
    private int id;
    private String tenmovie;
    private String hinh;
    private String trangthai;
    private int thoigian;
    private String trailerid;
    private String mota;
    private String ngaykhoichieu;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenmovie() {
        return tenmovie;
    }

    public void setTenmovie(String tenmovie) {
        this.tenmovie = tenmovie;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public int getThoigian() {
        return thoigian;
    }

    public void setThoigian(int thoigian) {
        this.thoigian = thoigian;
    }

    public String getTrailerid() {
        return trailerid;
    }

    public void setTrailerid(String trailerid) {
        this.trailerid = trailerid;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getNgaykhoichieu() {
        return ngaykhoichieu;
    }

    public void setNgaykhoichieu(String ngaykhoichieu) {
        this.ngaykhoichieu = ngaykhoichieu;
    }

    public Movie() {
        this.id = 0;
        this.tenmovie = "";
        this.hinh = "";
        this.trangthai = "";
        this.thoigian = 0;
        this.trailerid = "";
        this.mota = "";
        this.ngaykhoichieu = "";
    }

    public Movie(int id, String tenmovie, String hinh, String trangthai, int thoigian, String trailerid, String mota, String ngaykhoichieu) {
        this.id = id;
        this.tenmovie = tenmovie;
        this.hinh = hinh;
        this.trangthai = trangthai;
        this.thoigian = thoigian;
        this.trailerid = trailerid;
        this.mota = mota;
        this.ngaykhoichieu = ngaykhoichieu;
    }
}
