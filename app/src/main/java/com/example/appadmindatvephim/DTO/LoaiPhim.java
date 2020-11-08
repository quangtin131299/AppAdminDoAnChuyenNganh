package com.example.appadmindatvephim.DTO;

public class LoaiPhim {
    private int id;
    private String tenloai;

    public LoaiPhim(int id, String tenloai) {
        this.id = id;
        this.tenloai = tenloai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    public LoaiPhim() {
    }

    @Override
    public String toString() {
        return this.tenloai;
    }
}
