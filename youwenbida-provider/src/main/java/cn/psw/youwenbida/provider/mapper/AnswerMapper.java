package cn.psw.youwenbida.provider.mapper;

import cn.psw.youwenbida.api.model.Answer;

import java.util.List;

public interface AnswerMapper {
    int deleteByPrimaryKey(Integer aid);

    int insert(Answer record);

    Answer selectByPrimaryKey(Integer aid);

    List<Answer> selectAll();

    int updateByPrimaryKey(Answer record);

    List<Answer> selectListByPrimaryKey(Answer answer);

    Integer selectCountByPrimaryKey(Answer answer);

    Integer searchProAns(Integer pid);
}