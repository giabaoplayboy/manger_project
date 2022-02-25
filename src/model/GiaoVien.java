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
public class GiaoVien {
    private String msgv;
    private String mshh;
    private String tengv;
    private String diachi_gv;
    private String sodt_gv;
    private String namhh ;

    public GiaoVien(String msgv, String mshh, String tengv, String diachi_gv, String sodt_gv, String namhh) {
        this.msgv = msgv;
        this.mshh = mshh;
        this.tengv = tengv;
        this.diachi_gv = diachi_gv;
        this.sodt_gv = sodt_gv;
        this.namhh = namhh;
    }

    public GiaoVien(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getMsgv() {
        return msgv;
    }

    public void setMsgv(String msgv) {
        this.msgv = msgv;
    }

    public String getMshh() {
        return mshh;
    }

    public void setMshh(String mshh) {
        this.mshh = mshh;
    }

    public String getTengv() {
        return tengv;
    }

    public void setTengv(String tengv) {
        this.tengv = tengv;
    }

    public String getDiachi_gv() {
        return diachi_gv;
    }

    public void setDiachi_gv(String diachi_gv) {
        this.diachi_gv = diachi_gv;
    }

    public String getSodt_gv() {
        return sodt_gv;
    }

    public void setSodt_gv(String sodt_gv) {
        this.sodt_gv = sodt_gv;
    }

    public String getNamhh() {
        return namhh;
    }

    public void setNamhh(String namhh) {
        this.namhh = namhh;
    }  

    @Override
    public String toString() {
        return "GiaoVien{" + "msgv=" + msgv + ", mshh=" + mshh + ", tengv=" + tengv + ", diachi_gv=" + diachi_gv + ", sodt_gv=" + sodt_gv + ", namhh=" + namhh + '}';
    }
    
}
