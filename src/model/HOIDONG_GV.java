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
public class HOIDONG_GV {
    private String mshd;
    private String msgv;

    public HOIDONG_GV(String mshd, String msgv) {
        this.mshd = mshd;
        this.msgv = msgv;
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

    @Override
    public String toString() {
        return "HOIDONG_GV{" + "mshd=" + mshd + ", msgv=" + msgv + '}';
    }
    
}
