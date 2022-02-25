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
public class GV_HV_CN {
    private String msgv;
    private String mscn;
    private String mshv;
    private String nam;

    public GV_HV_CN(String msgv, String mscn, String mshv, String nam) {
        this.msgv = msgv;
        this.mscn = mscn;
        this.mshv = mshv;
        this.nam = nam;
    }

    public String getMsgv() {
        return msgv;
    }

    public void setMsgv(String msgv) {
        this.msgv = msgv;
    }

    public String getMscn() {
        return mscn;
    }

    public void setMscn(String mscn) {
        this.mscn = mscn;
    }

    public String getMshv() {
        return mshv;
    }

    public void setMshv(String mshv) {
        this.mshv = mshv;
    }

    public String getNam() {
        return nam;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }

    @Override
    public String toString() {
        return "GV_HV_CN{" + "msgv=" + msgv + ", mscn=" + mscn + ", mshv=" + mshv + ", nam=" + nam + '}';
    }
    
}
