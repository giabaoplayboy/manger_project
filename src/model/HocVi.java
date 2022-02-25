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
public class HocVi {
    private String mshv;
    private String tenhv;

    public HocVi(String mshv, String tenhv) {
        this.mshv = mshv;
        this.tenhv = tenhv;
    }

    public String getMshv() {
        return mshv;
    }

    public void setMshv(String mshv) {
        this.mshv = mshv;
    }

    public String getTenhv() {
        return tenhv;
    }

    public void setTenhv(String tenhv) {
        this.tenhv = tenhv;
    }

    @Override
    public String toString() {
        return "HocVi{" + "mshv=" + mshv + ", tenhv=" + tenhv + '}';
    }
    
}
