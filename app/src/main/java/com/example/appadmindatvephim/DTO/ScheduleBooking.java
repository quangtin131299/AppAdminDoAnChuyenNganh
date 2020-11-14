package com.example.appadmindatvephim.DTO;

import java.io.Serializable;

public class ScheduleBooking implements Serializable {
    private int idrap;
    private String ngay;
    private String gio;
    private int idphong;
    private int idphim;

    public ScheduleBooking() {
        this.idrap = 0;
        this.ngay = "";
        this.gio = "";
        this.idphong = 0;
        this.idphim = 0;
    }

    public int getIdrap() {
        return idrap;
    }

    public void setIdrap(int idrap) {
        this.idrap = idrap;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getGio() {
        return gio;
    }

    public void setGio(String gio) {
        this.gio = gio;
    }

    public int getIdphong() {
        return idphong;
    }

    public void setIdphong(int idphong) {
        this.idphong = idphong;
    }

    public int getIdphim() {
        return idphim;
    }

    public void setIdphim(int idphim) {
        this.idphim = idphim;
    }

    public ScheduleBooking(int idrap, String ngay, String gio, int idphong, int idphim) {
        this.idrap = idrap;
        this.ngay = ngay;
        this.gio = gio;
        this.idphong = idphong;
        this.idphim = idphim;
    }
}
