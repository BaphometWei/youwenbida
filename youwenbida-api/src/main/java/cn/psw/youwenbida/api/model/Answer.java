package cn.psw.youwenbida.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    private Double score;

    private String yzhd;

    private Boolean dz = false;

    private Boolean sc = false;

    private Boolean fd = false;

    private Problem problem;

    private User user;

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

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
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

    public List<Comment> comments;

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Boolean getFd() {
        return fd;
    }

    public void setFd(Boolean fd) {
        this.fd = fd;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getYzhd() {
        return yzhd;
    }

    public void setYzhd(String yzhd) {
        this.yzhd = yzhd;
    }
}