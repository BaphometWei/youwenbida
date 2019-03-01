package cn.psw.youwenbida.api.service;

import cn.psw.youwenbida.api.model.Answer;

import java.util.List;

public interface AnswerService {

    public List<Answer> getHotAnswer();

    public Integer getCountAns(Integer pid);

    public Answer getAns(Integer aid);

    public List<Answer> getUserAns(String uid);

    public List<Answer> getProAns(Integer pid);

    public Answer searchProAns(String pid);

    Integer getCountUserAns(String uid);

}
