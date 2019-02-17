package cn.psw.youwenbida.provider.mapper;

import cn.psw.youwenbida.provider.Model.Answer;
import java.util.List;

public interface AnswerMapper {
    int deleteByPrimaryKey(String aid);

    int insert(Answer record);

    Answer selectByPrimaryKey(String aid);

    List<Answer> selectAll();

    int updateByPrimaryKey(Answer record);
}