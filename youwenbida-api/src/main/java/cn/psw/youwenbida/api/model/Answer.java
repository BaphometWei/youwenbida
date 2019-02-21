package cn.psw.youwenbida.api.model;

import java.io.Serializable;
import java.util.Date;

public class Answer implements Serializable {

    /**
     *  序列化ID
     */
    private static final long serialVersionUID = -5809782578272943999L;

    private Integer aid;

    private String ahdz;

    private String ahd;

    private Integer aztsl;

    private Integer aplsl = 0;

    private Date ahdrq;

    private Integer ahdwt;

    private Boolean dz = false;

    private Boolean sc = false;

    private String title;

    private String ahdzname;

    private String ahdzgxqm;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid ;
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

    public Integer getAhdwt() {
        return ahdwt;
    }

    public void setAhdwt(Integer ahdwt) {
        this.ahdwt = ahdwt;
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