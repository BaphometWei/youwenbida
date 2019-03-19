package cn.psw.youwenbida.provider.mapper;

import cn.psw.youwenbida.api.model.Answer;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AnswerMapper {
    int deleteByPrimaryKey(Integer aid);

    int insert(Answer record);

    Answer selectByPrimaryKey(Integer aid);

    List<Answer> selectAll();

    int updateByPrimaryKey(Answer record);

    List<Answer> selectListByPrimaryKey(Answer answer);

    List<Answer> getProAns(Map<String, Object> map);

    List<Answer> getProYzAns(Map<String, Object> map);

    Integer selectCountByPrimaryKey(Answer answer);

    Integer searchProAns(Integer pid);

    List<Answer> getTopicAnsByOpDate(Map<String, Object> map);

    List<Answer> getTopicAnsByAnsDate(Map<String, Object> map);

    List<Answer> getTopicAnsByAnsScore(Map<String, Object> map);

    Integer selectCountByUserAndDate(Map<String,Object> map);

    List<Map<String,Object>> getUserByDate(@Param(value="date") String date);

}