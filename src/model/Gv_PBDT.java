/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.*;

/**
 *
 * @author MSI
 */
public class Gv_PBDT {
    private String msgv;
    private String msdt;
    private Float diempbdt;

    public Gv_PBDT(String msgv, String msdt, Float diemhddt) {
        this.msgv = msgv;
        this.msdt = msdt;
        this.diempbdt = diemhddt;
    }

    public String getMsgv() {
        return msgv;
    }

    public void setMsgv(String msgv) {
        this.msgv = msgv;
    }

    public String getMsdt() {
        return msdt;
    }

    public void setMsdt(String msdt) {
        this.msdt = msdt;
    }

    public Float getDiemhddt() {
        return diempbdt;
    }

    public void setDiemhddt(Float diemhddt) {
        this.diempbdt = diemhddt;
    }

    @Override
    public String toString() {
        return "Gv_HDDT{" + "msgv=" + msgv + ", msdt=" + msdt + ", diemhddt=" + diempbdt + '}';
    }
    
}
