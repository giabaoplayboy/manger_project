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
public class SV_DETAI {
    private String MSSV;
    private String MSDT;

    public SV_DETAI(String MSSV, String MSDT) {
        this.MSSV = MSSV;
        this.MSDT = MSDT;
    }

    public String getMSSV() {
        return MSSV;
    }

    public void setMSSV(String MSSV) {
        this.MSSV = MSSV;
    }

    public String getMSDT() {
        return MSDT;
    }

    public void setMSDT(String MSDT) {
        this.MSDT = MSDT;
    }

    @Override
    public String toString() {
        return "SV_DETAI{" + "MSSV=" + MSSV + ", MSDT=" + MSDT + '}';
    }
    
    
}
