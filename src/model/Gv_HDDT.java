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
public class Gv_HDDT {
    private String msgv;
    private String msdt;
    private Float diemhddt;

    public Gv_HDDT(String msgv, String msdt, Float diemhddt) {
        this.msgv = msgv;
        this.msdt = msdt;
        this.diemhddt = diemhddt;
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
        return diemhddt;
    }

    public void setDiemhddt(Float diemhddt) {
        this.diemhddt = diemhddt;
    }

    @Override
    public String toString() {
        return "Gv_HDDT{" + "msgv=" + msgv + ", msdt=" + msdt + ", diemhddt=" + diemhddt + '}';
    }
    
}
