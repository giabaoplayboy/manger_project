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
public class HocHam {
    private String mshh;
    private String tenhh;

    public HocHam(String mshh, String tenhh) {
        this.mshh = mshh;
        this.tenhh = tenhh;
    }

    public String getMshh() {
        return mshh;
    }

    public void setMshh(String mshh) {
        this.mshh = mshh;
    }

    public String getTenhh() {
        return tenhh;
    }

    public void setTenhh(String tenhh) {
        this.tenhh = tenhh;
    }

    @Override
    public String toString() {
        return "HocHam{" + "mshh=" + mshh + ", tenhh=" + tenhh + '}';
    }
    
}
