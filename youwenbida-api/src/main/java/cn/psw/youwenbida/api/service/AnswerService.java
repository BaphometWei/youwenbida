package cn.psw.youwenbida.api.service;

import cn.psw.youwenbida.api.model.Answer;

import java.util.List;

public interface AnswerService {

    public List<Answer> getHotAnswer();

    public Integer getCountAns(Integer pid);

}
