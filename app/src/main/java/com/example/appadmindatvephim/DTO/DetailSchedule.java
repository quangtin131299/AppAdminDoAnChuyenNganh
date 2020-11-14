package com.example.appadmindatvephim.DTO;

import java.util.ArrayList;

public class DetailSchedule {
    private int idphim;
    private String tenphim;
    private String hinhphim;
    private ArrayList<ShowTime> showTimes;

    public DetailSchedule() {
    }

    public String getHinhphim() {
        return hinhphim;
    }

    public void setHinhphim(String hinhphim) {
        this.hinhphim = hinhphim;
    }

    public int getIdphim() {
        return idphim;
    }

    public void setIdphim(int idphim) {
        this.idphim = idphim;
    }

    public String getTenphim() {
        return tenphim;
    }

    public void setTenphim(String tenphim) {
        this.tenphim = tenphim;
    }

    public ArrayList<ShowTime> getShowTimes() {
        return showTimes;
    }

    public void setShowTimes(ArrayList<ShowTime> showTimes) {
        this.showTimes = showTimes;
    }

    public DetailSchedule(int idphim, String tenphim, String hinhphim, ArrayList<ShowTime> showTimes) {
        this.idphim = idphim;
        this.tenphim = tenphim;
        this.hinhphim = hinhphim;
        this.showTimes = showTimes;
    }
}
