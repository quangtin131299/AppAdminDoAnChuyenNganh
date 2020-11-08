package com.example.appadmindatvephim.DTO;

import android.accounts.Account;

import java.io.Serializable;

public class Customer implements Serializable {
    private int id;
    private String hoten;
    private String email;
    private String ngaysinh;
    private String sdt;



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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public Customer(int id, String hoten, String email, String ngaysinh, String sdt) {
        this.id = id;
        this.hoten = hoten;
        this.email = email;
        this.ngaysinh = ngaysinh;
        this.sdt = sdt;
    }

    public Customer() {
    }
}
