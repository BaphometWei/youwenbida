package cn.psw.youwenbida.api.model;

import java.io.Serializable;
import java.util.Date;

public class Problem  implements Serializable {

    /**
     *  序列化ID
     */
    private static final long serialVersionUID = -5809782578272943999L;

    private String pid;

    private String ptitle;

    private String pms;

    private String ptcz;

    private Integer pgzzsl;

    private Integer phdsl;

    private Date ptcrq;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
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

    public Integer getPhdsl() {
        return phdsl;
    }

    public void setPhdsl(Integer phdsl) {
        this.phdsl = phdsl;
    }

    public Date getPtcrq() {
        return ptcrq;
    }

    public void setPtcrq(Date ptcrq) {
        this.ptcrq = ptcrq;
    }
}