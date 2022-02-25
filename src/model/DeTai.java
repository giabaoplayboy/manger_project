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
public class DeTai {
    private String msdt;
    private String tendt;

    public DeTai(String msdt, String tendt) {
        this.msdt = msdt;
        this.tendt = tendt;
    }

    public String getMsdt() {
        return msdt;
    }

    public void setMsdt(String msdt) {
        this.msdt = msdt;
    }

    public String getTendt() {
        return tendt;
    }

    public void setTendt(String tendt) {
        this.tendt = tendt;
    }

    @Override
    public String toString() {
        return "DeTai{" + "msdt=" + msdt + ", tendt=" + tendt + '}';
    }
    
}
