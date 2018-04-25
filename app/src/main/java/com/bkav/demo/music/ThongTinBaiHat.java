package com.bkav.demo.music;

/**
 * Created by vst on 10/04/2018.
 */

public class ThongTinBaiHat {

    private String TenBaiHat;
    private String Theloai;
    private int Sothutu;
    private int HinhAlbum;
    private String Thoigian;

    public ThongTinBaiHat(String tenBaiHat, String theloai, int sothutu, int hinhAlbum, String thoigian) {
        TenBaiHat = tenBaiHat;
        Theloai = theloai;
        Sothutu = sothutu;
        HinhAlbum = hinhAlbum;
        Thoigian = thoigian;
    }

    public String getTenBaiHat() {
        return TenBaiHat;
    }

    public void setTenBaiHat(String tenBaiHat) {
        TenBaiHat = tenBaiHat;
    }

    public String getTheloai() {
        return Theloai;
    }

    public void setTheloai(String theloai) {
        Theloai = theloai;
    }

    public int getSothutu() {
        return Sothutu;
    }

    public void setSothutu(int sothutu) {
        Sothutu = sothutu;
    }

    public int getHinhAlbum() {
        return HinhAlbum;
    }

    public void setHinhAlbum(int hinhAlbum) {
        HinhAlbum = hinhAlbum;
    }

    public String getThoigian() {
        return Thoigian;
    }

    public void setThoigian(String thoigian) {
        Thoigian = thoigian;
    }
}
