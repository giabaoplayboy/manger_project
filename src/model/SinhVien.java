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
public class SinhVien {
    private String mssv;
    private String tensv;
    private String lop;
    private String sodt; 
    private String diachi;

    public SinhVien(String mssv, String tensv, String lop, String sodt, String diachi) {
        this.mssv = mssv;
        this.tensv = tensv;
        this.lop = lop;
        this.sodt = sodt;
        this.diachi = diachi;
    }

    public SinhVien(String string, String string0, float aFloat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getTensv() {
        return tensv;
    }

    public void setTensv(String tensv) {
        this.tensv = tensv;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getSodt() {
        return sodt;
    }

    public void setSodt(String sodt) {
        this.sodt = sodt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    @Override
    public String toString() {
        return "SinhVien{" + "mssv=" + mssv + ", tensv=" + tensv + ", lop=" + lop + ", sodt=" + sodt + ", diachi=" + diachi + '}';
    }
    
      
}
