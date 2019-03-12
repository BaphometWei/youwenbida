package cn.psw.youwenbida.api.model;

import java.io.Serializable;

public class Topic implements Serializable {

    /**
     *  序列化ID
     */
    private static final long serialVersionUID = -5809782578272943999L;

    private Integer tid;

    private String tname;

    private String tjj;

    private String timg;

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname == null ? null : tname.trim();
    }

    public String getTjj() {
        return tjj;
    }

    public void setTjj(String tjj) {
        this.tjj = tjj == null ? null : tjj.trim();
    }

    public String getTimg() {
        return timg;
    }

    public void setTimg(String timg) {
        this.timg = timg == null ? null : timg.trim();
    }
}