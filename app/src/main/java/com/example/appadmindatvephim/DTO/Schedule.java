package com.example.appadmindatvephim.DTO;

import java.io.Serializable;

public class Schedule implements Serializable {
    private int id;
    private String ngay;
    private String tenrap;
    private int idrap;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getTenrap() {
        return tenrap;
    }

    public void setTenrap(String tenrap) {
        this.tenrap = tenrap;
    }

    public int getIdrap() {
        return idrap;
    }

    public void setIdrap(int idrap) {
        this.idrap = idrap;
    }

    public Schedule(int id, String ngay, String tenrap, int idrap) {
        this.id = id;
        this.ngay = ngay;
        this.tenrap = tenrap;
        this.idrap = idrap;
    }

    public Schedule() {
    }
}
