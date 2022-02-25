/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author MSI
 */
public class HOIDONG {
    private String mshd;

    private String msgv;

    private String phong;

    private String tgbd;

    private String ngayhd;

    private String tinhtrang;

    public HOIDONG(String mshd, String msgv, String phong, String tgbd, String ngayhd, String tinhtrang) {
        this.mshd = mshd;
        this.msgv = msgv;
        this.phong = phong;
        this.tgbd = tgbd;
        this.ngayhd = ngayhd;
        this.tinhtrang = tinhtrang;
    }

    public String getMshd() {
        return mshd;
    }

    public void setMshd(String mshd) {
        this.mshd = mshd;
    }

    public String getMsgv() {
        return msgv;
    }

    public void setMsgv(String msgv) {
        this.msgv = msgv;
    }

    public String getPhong() {
        return phong;
    }

    public void setPhong(String phong) {
        this.phong = phong;
    }

    public String getTgbd() {
        return tgbd;
    }

    public void setTgbd(String tgbd) {
        this.tgbd = tgbd;
    }

    public String getNgayhd() {
        return ngayhd;
    }

    public void setNgayhd(String ngayhd) {
        this.ngayhd = ngayhd;
    }

    public String getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(String tinhtrang) {
        this.tinhtrang = tinhtrang;
    }

    @Override
    public String toString() {
        return "HOIDONG{" + "mshd=" + mshd + ", msgv=" + msgv + ", phong=" + phong + ", tgbd=" + tgbd + ", ngayhd=" + ngayhd + ", tinhtrang=" + tinhtrang + '}';
    }
    
}
