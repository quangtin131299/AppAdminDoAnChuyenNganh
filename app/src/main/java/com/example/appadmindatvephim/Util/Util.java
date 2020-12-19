package com.example.appadmindatvephim.Util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {
    //Login
    public static final String LINK_LOGINADIM = "https://serverappdatve.herokuapp.com/loginadmin";
    //Movie
    public static final String LINK_LOADMOVIE = "https://serverappdatve.herokuapp.com/loadphimadmin?vitri=%d";
    public static final String LINK_UPDATEMOVIE = "https://serverappdatve.herokuapp.com/updatemovie";
    public static final String LINK_SEARCHMOVIE = "https://serverappdatve.herokuapp.com/timkiemmovieadmin?tenphim=%s";
    public static final String LINK_ADDMOVIE = "https://serverappdatve.herokuapp.com/insertnewmovieadmin";
    public static final String LINK_DELETEMOVIE = "https://serverappdatve.herokuapp.com/deletemovieadmin";
    //Type
    public static final String LINK_LOADTHELOAI = "https://serverappdatve.herokuapp.com/loadloaiphimadmin";
    //Customer
    public static final String LINK_LOADDATACUSTOMER = "https://serverappdatve.herokuapp.com/loadcustomeradmin?vitri=%d";
    public static final String LINK_SEARCHCUSTOMER = "https://serverappdatve.herokuapp.com/timkiemcustomer?tencustomer=%s";
    //Cinema
    public static final String LINK_LOADDATACINEMA = "https://serverappdatve.herokuapp.com/loadcinemaadmin?vitri=%d";
    public static final String LINK_ADDCINEMA = "https://serverappdatve.herokuapp.com/insertnewcinemaadmin";
    public static final String LINK_UPDATECINEMA = "https://serverappdatve.herokuapp.com/updatcinemaadmin";
    public static final String LINK_SEARCHCINEMA = "https://serverappdatve.herokuapp.com/timkiemcinemaadmin?tencinema=%s";
    //Ticker Booking
    public static final String LINK_LOADVEDAT = "https://serverappdatve.herokuapp.com/loadtickeradmin?vitri=%d";
    //Schedule
    public static final String LINK_SEARCHSCHEDULE = "https://serverappdatve.herokuapp.com/searchscheduleadmin?ngay=%s";
    public static final String LINK_LOADSCHEDULE = "https://serverappdatve.herokuapp.com/loadlichchieuadmin?vitri=%d";
    public static final String LINK_LOADDETAILSCHEDULE = "https://serverappdatve.herokuapp.com/loadchitietlichchieuadmin?idrap=%d&ngay=%s";
    public static final String LINK_XEPLICH = "https://serverappdatve.herokuapp.com/xeplich";
    public static final String LINK_LOADMOVIESCHEDULE = "https://serverappdatve.herokuapp.com/loadphimscheduleadmin";
    public static final String LINK_LOADPHONGSCHEDULE = "https://serverappdatve.herokuapp.com/loadphongscheduleadmin";
    public static final String LINK_LOADCINEMASCHEDULE = "https://serverappdatve.herokuapp.com/loadcinemascheduleadmin";
    //Room
    public static final String LINK_LOADROOM = "https://serverappdatve.herokuapp.com/loadphongadmin";
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