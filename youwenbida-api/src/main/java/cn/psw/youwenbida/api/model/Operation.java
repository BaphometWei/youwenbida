package cn.psw.youwenbida.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class Operation implements Serializable {

    /**
     *  序列化ID
     */
    private static final long serialVersionUID = -5809782578272943999L;

    private Integer oid;

    private String ooz;

    private String obo;

    private String olx;

    private Date odate;

    private Problem problem;

    private Answer answer;

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public String getOoz() {
        return ooz;
    }

    public void setOoz(String ooz) {
        this.ooz = ooz == null ? null : ooz.trim();
    }

    public String getObo() {
        return obo;
    }

    public void setObo(String obo) {
        this.obo = obo;
    }

    public String getOlx() {
        return olx;
    }

    public void setOlx(String olx) {
        this.olx = olx == null ? null : olx.trim();
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getOdate() {
        return odate;
    }

    public void setOdate(Date odate) {
        this.odate = odate;
    }
}