package cn.psw.youwenbida.api.model;

import java.io.Serializable;

public class Operation implements Serializable {

    /**
     *  序列化ID
     */
    private static final long serialVersionUID = -5809782578272943999L;

    private Integer oid;

    private String ooz;

    private Integer obo;

    private String olx;

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public String getOoz() {
        return ooz;
    }

    public void setOoz(String ooz) {
        this.ooz = ooz == null ? null : ooz.trim();
    }

    public Integer getObo() {
        return obo;
    }

    public void setObo(Integer obo) {
        this.obo = obo;
    }

    public String getOlx() {
        return olx;
    }

    public void setOlx(String olx) {
        this.olx = olx == null ? null : olx.trim();
    }
}