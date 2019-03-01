package cn.psw.youwenbida.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Comment  implements Serializable {

    /**
     *  序列化ID
     */
    private static final long serialVersionUID = -5809782578272943999L;

    private Integer cid;

    private String cplz;

    private String cpl;

    private Integer cdzsl;

    private String cpllx;

    private Integer cbpl;

    private Date cdate;

    private User user;

    private Boolean isdz = false;

    private List<Reply> replies;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCplz() {
        return cplz;
    }

    public void setCplz(String cplz) {
        this.cplz = cplz == null ? null : cplz.trim();
    }

    public String getCpl() {
        return cpl;
    }

    public void setCpl(String cpl) {
        this.cpl = cpl == null ? null : cpl.trim();
    }

    public Integer getCdzsl() {
        return cdzsl;
    }

    public void setCdzsl(Integer cdzsl) {
        this.cdzsl = cdzsl;
    }

    public String getCpllx() {
        return cpllx;
    }

    public void setCpllx(String cpllx) {
        this.cpllx = cpllx == null ? null : cpllx.trim();
    }

    public Integer getCbpl() {
        return cbpl;
    }

    public void setCbpl(Integer cbpl) {
        this.cbpl = cbpl ;
    }

    public Boolean getIsdz() {
        return isdz;
    }

    public void setIsdz(Boolean isdz) {
        this.isdz = isdz;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }
}