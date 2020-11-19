package com.example.appadmindatvephim.DTO;

import java.io.Serializable;

public class Admin implements Serializable {
    private int id;
    private String hoten;

    public Admin() {
    }

    public Admin(int id, String hoten) {
        this.id = id;
        this.hoten = hoten;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }
}
