package cn.psw.youwenbida.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class Reply implements Serializable {

    /**
     *  序列化ID
     */
    private static final long serialVersionUID = -5809782578272943999L;

    private Integer rid;

    private String rz;

    private String rbz;

    private String rr;

    private Integer rbc;

    private Date rdate;

    private User rzuser;

    private User rbzuser;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getRz() {
        return rz;
    }

    public void setRz(String rz) {
        this.rz = rz == null ? null : rz.trim();
    }

    public String getRbz() {
        return rbz;
    }

    public void setRbz(String rbz) {
        this.rbz = rbz == null ? null : rbz.trim();
    }

    public String getRr() {
        return rr;
    }

    public void setRr(String rr) {
        this.rr = rr == null ? null : rr.trim();
    }

    public Integer getRbc() {
        return rbc;
    }

    public void setRbc(Integer rbc) {
        this.rbc = rbc;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getRdate() {
        return rdate;
    }

    public void setRdate(Date rdate) {
        this.rdate = rdate;
    }

    public User getRzuser() {
        return rzuser;
    }

    public void setRzuser(User rzuser) {
        this.rzuser = rzuser;
    }

    public User getRbzuser() {
        return rbzuser;
    }

    public void setRbzuser(User rbzuser) {
        this.rbzuser = rbzuser;
    }
}