package com.example.appadmindatvephim.DTO;

public class Room {
    private int id;
    private String tenphong;

    @Override
    public String toString() {
        return tenphong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenphong() {
        return tenphong;
    }

    public void setTenphong(String tenphong) {
        this.tenphong = tenphong;
    }

    public Room(int id, String tenphong) {
        this.id = id;
        this.tenphong = tenphong;
    }

    public Room() {
    }
}
