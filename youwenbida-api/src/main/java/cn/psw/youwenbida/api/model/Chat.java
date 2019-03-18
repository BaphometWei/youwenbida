package cn.psw.youwenbida.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class Chat implements Serializable {

    /**
     *  序列化ID
     */
    private static final long serialVersionUID = -5809782578272943999L;

    private Integer chid;

    private String cz;

    private String cbz;

    private String ctext;

    private Date chdate;

    private String ydbs;

    public Integer getChid() {
        return chid;
    }

    public void setChid(Integer chid) {
        this.chid = chid;
    }

    public String getCz() {
        return cz;
    }

    public void setCz(String cz) {
        this.cz = cz == null ? null : cz.trim();
    }

    public String getCbz() {
        return cbz;
    }

    public void setCbz(String cbz) {
        this.cbz = cbz == null ? null : cbz.trim();
    }

    public String getCtext() {
        return ctext;
    }

    public void setCtext(String ctext) {
        this.ctext = ctext == null ? null : ctext.trim();
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    public Date getChdate() {
        return chdate;
    }

    public void setChdate(Date chdate) {
        this.chdate = chdate;
    }

    public String getYdbs() {
        return ydbs;
    }

    public void setYdbs(String ydbs) {
        this.ydbs = ydbs == null ? null : ydbs.trim();
    }
}