package com.example.appadmindatvephim.DTO;

import java.io.Serializable;

public class Ticker implements Serializable {
    private int id;
    private String ngaydat;
    private String tenrap;
    private String tenghe;
    private String tenphim;
    private String tenkhachhang;
    private String tenphong;
    private String trangthai;
    private String suatchieu;

    public String getSuatchieu() {
        return suatchieu;
    }

    public void setSuatchieu(String suatchieu) {
        this.suatchieu = suatchieu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNgaydat() {
        return ngaydat;
    }

    public void setNgaydat(String ngaydat) {
        this.ngaydat = ngaydat;
    }

    public String getTenrap() {
        return tenrap;
    }

    public void setTenrap(String tenrap) {
        this.tenrap = tenrap;
    }

    public String getTenghe() {
        return tenghe;
    }

    public void setTenghe(String tenghe) {
        this.tenghe = tenghe;
    }

    public String getTenphim() {
        return tenphim;
    }

    public void setTenphim(String tenphim) {
        this.tenphim = tenphim;
    }

    public String getTenkhachhang() {
        return tenkhachhang;
    }

    public void setTenkhachhang(String tenkhachhang) {
        this.tenkhachhang = tenkhachhang;
    }

    public String getTenphong() {
        return tenphong;
    }

    public void setTenphong(String tenphong) {
        this.tenphong = tenphong;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public Ticker() {
    }

    public Ticker(int id, String ngaydat, String tenrap, String tenghe, String tenphim, String tenkhachhang, String tenphong, String trangthai) {
        this.id = id;
        this.ngaydat = ngaydat;
        this.tenrap = tenrap;
        this.tenghe = tenghe;
        this.tenphim = tenphim;
        this.tenkhachhang = tenkhachhang;
        this.tenphong = tenphong;
        this.trangthai = trangthai;
    }
}
