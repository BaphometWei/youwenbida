package cn.psw.youwenbida.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class Problem implements Serializable {

    /**
     *  序列化ID
     */
    private static final long serialVersionUID = -5809782578272943999L;

    private Integer pid;

    private String ptitle;

    private String pms;

    private String ptcz;

    private Integer pgzzsl = 0;

    private Integer pllsl = 0;

    private Date ptcrq;

    private Integer pplsl = 0;

    private Integer phdsl = 0;

    private Boolean isgz = false;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPtitle() {
        return ptitle;
    }

    public void setPtitle(String ptitle) {
        this.ptitle = ptitle == null ? null : ptitle.trim();
    }

    public String getPms() {
        return pms;
    }

    public void setPms(String pms) {
        this.pms = pms == null ? null : pms.trim();
    }

    public String getPtcz() {
        return ptcz;
    }

    public void setPtcz(String ptcz) {
        this.ptcz = ptcz == null ? null : ptcz.trim();
    }

    public Integer getPgzzsl() {
        return pgzzsl;
    }

    public void setPgzzsl(Integer pgzzsl) {
        this.pgzzsl = pgzzsl;
    }

    public Integer getPllsl() {
        return pllsl;
    }

    public void setPllsl(Integer pllsl) {
        this.pllsl = pllsl;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getPtcrq() {
        return ptcrq;
    }

    public void setPtcrq(Date ptcrq) {
        this.ptcrq = ptcrq;
    }

    public Integer getPplsl() {
        return pplsl;
    }

    public void setPplsl(Integer pplsl) {
        this.pplsl = pplsl;
    }

    public Integer getPhdsl() {
        return phdsl;
    }

    public void setPhdsl(Integer phdsl) {
        this.phdsl = phdsl;
    }

    public Boolean getIsgz() {
        return isgz;
    }

    public void setIsgz(Boolean isgz) {
        this.isgz = isgz;
    }
}