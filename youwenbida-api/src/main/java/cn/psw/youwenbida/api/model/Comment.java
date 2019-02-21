package cn.psw.youwenbida.api.model;

import java.io.Serializable;

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

    private String cplzname;

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

    public String getCplzname() {
        return cplzname;
    }

    public void setCplzname(String cplzname) {
        this.cplzname = cplzname == null ? null : cplzname.trim();
    }


}