package com.example.appadmindatvephim.DTO;

public class ShowTime {
    private int id;
    private String gio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGio() {
        return gio;
    }

    public void setGio(String gio) {
        this.gio = gio;
    }

    public ShowTime() {
    }

    public ShowTime(int id, String gio) {
        this.id = id;
        this.gio = gio;
    }
}
