package cn.psw.youwenbida.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class Notice implements Serializable {

    /**
     *  序列化ID
     */
    private static final long serialVersionUID = -5809782578272943999L;

    private Integer nid;

    private String nz;

    private String nlx;

    private String nnr;

    private Date ndate;

    private String ydbs;

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public String getNz() {
        return nz;
    }

    public void setNz(String nz) {
        this.nz = nz == null ? null : nz.trim();
    }

    public String getNlx() {
        return nlx;
    }

    public void setNlx(String nlx) {
        this.nlx = nlx == null ? null : nlx.trim();
    }

    public String getNnr() {
        return nnr;
    }

    public void setNnr(String nnr) {
        this.nnr = nnr == null ? null : nnr.trim();
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getNdate() {
        return ndate;
    }

    public void setNdate(Date ndate) {
        this.ndate = ndate;
    }

    public String getYdbs() {
        return ydbs;
    }

    public void setYdbs(String ydbs) {
        this.ydbs = ydbs;
    }
}