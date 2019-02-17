package cn.psw.youwenbida.api.model;

import java.io.Serializable;

public class Operation implements Serializable {

    /**
     *  序列化ID
     */
    private static final long serialVersionUID = -5809782578272943999L;

    private String oid;

    private String ooz;

    private String obo;

    private String olx;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid == null ? null : oid.trim();
    }

    public String getOoz() {
        return ooz;
    }

    public void setOoz(String ooz) {
        this.ooz = ooz == null ? null : ooz.trim();
    }

    public String getObo() {
        return obo;
    }

    public void setObo(String obo) {
        this.obo = obo == null ? null : obo.trim();
    }

    public String getOlx() {
        return olx;
    }

    public void setOlx(String olx) {
        this.olx = olx == null ? null : olx.trim();
    }
}