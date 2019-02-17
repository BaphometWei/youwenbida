package cn.psw.youwenbida.api.model;

import java.io.Serializable;

public class User implements Serializable {

    /**
     *  序列化ID
     */
    private static final long serialVersionUID = -5809782578272943999L;

    private String id;

    private String name;

    private String password;

    private String gxqm;

    private String sex;

    private String location;

    private String industry;

    private String gs;

    private String education;

    private String email;

    private String introduction;

    private String img;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getGxqm() {
        return gxqm;
    }

    public void setGxqm(String gxqm) {
        this.gxqm = gxqm == null ? null : gxqm.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }

    public String getGs() {
        return gs;
    }

    public void setGs(String gs) {
        this.gs = gs == null ? null : gs.trim();
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }
}