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
public class HOIDONG_DT {
    private String mshd;
    private String msgv;
    private String quyetdinh;

    public HOIDONG_DT(String mshd, String msgv, String quyetdinh) {
        this.mshd = mshd;
        this.msgv = msgv;
        this.quyetdinh = quyetdinh;
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

    public String getQuyetdinh() {
        return quyetdinh;
    }

    public void setQuyetdinh(String quyetdinh) {
        this.quyetdinh = quyetdinh;
    }

    @Override
    public String toString() {
        return "HOIDONG_DT{" + "mshd=" + mshd + ", msgv=" + msgv + ", quyetdinh=" + quyetdinh + '}';
    }
    
}
