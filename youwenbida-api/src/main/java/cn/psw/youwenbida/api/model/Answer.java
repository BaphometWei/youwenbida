package cn.psw.youwenbida.api.model;

import java.io.Serializable;
import java.util.Date;

public class Answer implements Serializable {

    /**
     *  序列化ID
     */
    private static final long serialVersionUID = -5809782578272943999L;

    private String aid;

    private String ahdz;

    private String ahd;

    private Integer aztsl;

    private Integer aplsl;

    private Date ahdrq;

    private String ahdwt;

    private Boolean dz;

    private Boolean sc;

    private String title;

    private String ahdzname;

    private String ahdzgxqm;

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid == null ? null : aid.trim();
    }

    public String getAhdz() {
        return ahdz;
    }

    public void setAhdz(String ahdz) {
        this.ahdz = ahdz == null ? null : ahdz.trim();
    }

    public String getAhd() {
        return ahd;
    }

    public void setAhd(String ahd) {
        this.ahd = ahd == null ? null : ahd.trim();
    }

    public Integer getAztsl() {
        return aztsl;
    }

    public void setAztsl(Integer aztsl) {
        this.aztsl = aztsl;
    }

    public Integer getAplsl() {
        return aplsl;
    }

    public void setAplsl(Integer aplsl) {
        this.aplsl = aplsl;
    }

    public Date getAhdrq() {
        return ahdrq;
    }

    public void setAhdrq(Date ahdrq) {
        this.ahdrq = ahdrq;
    }

    public String getAhdwt() {
        return ahdwt;
    }

    public void setAhdwt(String ahdwt) {
        this.ahdwt = ahdwt == null ? null : ahdwt.trim();
    }

    public Boolean getDz() {
        return dz;
    }

    public void setDz(Boolean dz) {
        this.dz = dz;
    }

    public Boolean getSc() {
        return sc;
    }

    public void setSc(Boolean sc) {
        this.sc = sc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAhdzname() {
        return ahdzname;
    }

    public void setAhdzname(String ahdzname) {
        this.ahdzname = ahdzname;
    }

    public String getAhdzgxqm() {
        return ahdzgxqm;
    }

    public void setAhdzgxqm(String ahdzgxqm) {
        this.ahdzgxqm = ahdzgxqm;
    }
}