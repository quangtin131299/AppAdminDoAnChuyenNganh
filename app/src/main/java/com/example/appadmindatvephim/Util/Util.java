package com.example.appadmindatvephim.Util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {
    //Login
    public static final String LINK_LOGINADIM = "http://192.168.0.122:3000/loginadmin";
    //Movie
    public static final String LINK_LOADMOVIE = "http://192.168.0.122:3000/loadphimadmin?soluong=5&vitri=%d";
    public static final String LINK_UPDATEMOVIE = "http://192.168.0.122:3000/updatemovie";
    public static final String LINK_SEARCHMOVIE = "http://192.168.0.122:3000/timkiemmovieadmin?tenphim=%s";
    public static final String LINK_ADDMOVIE = "http://192.168.0.122:3000/insertnewmovieadmin";
    public static final String LINK_DELETEMOVIE = "http://192.168.0.122:3000/deletemovieadmin";
    //Type
    public static final String LINK_LOADTHELOAI = "http://192.168.0.122:3000/loadloaiphimadmin";
    //Customer
    public static final String LINK_LOADDATACUSTOMER = "http://192.168.0.122:3000/loadcustomeradmin";
    public static final String LINK_SEARCHCUSTOMER = "http://192.168.0.122:3000/timkiemcustomer?tencustomer=%s";
    //Cinema
    public static final String LINK_LOADDATACINEMA = "http://192.168.0.122:3000/loadcinemaadmin";
    public static final String LINK_ADDCINEMA = "http://192.168.0.122:3000/insertnewcinemaadmin";
    public static final String LINK_UPDATECINEMA = "http://192.168.0.122:3000/updatcinemaadmin";
    public static final String LINK_SEARCHCINEMA = "http://192.168.0.122:3000/timkiemcinemaadmin?tencinema=%s";
    //Ticker Booking
    public static final String LINK_LOADVEDAT = "http://192.168.0.122:3000/loadtickeradmin";
    //Schedule
    public static final String LINK_SEARCHSCHEDULE = "http://192.168.0.122:3000/searchscheduleadmin?ngay=%s";
    public static final String LINK_LOADSCHEDULE = "http://192.168.0.122:3000/loadlichchieuadmin";
    public static final String LINK_LOADDETAILSCHEDULE = "http://192.168.0.122:3000/loadchitietlichchieuadmin?idrap=%d&ngay=%s";
    public static final String LINK_XEPLICH = "http://192.168.0.122:3000/xeplich";
    //Room
    public static final String LINK_LOADROOM = "http://192.168.1.6:3000/loadphongadmin";

    public static String getMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            return "";

        }
    }

}