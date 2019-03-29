package cn.psw.youwenbida.api.service;

import cn.psw.youwenbida.api.model.Answer;
import cn.psw.youwenbida.api.utils.ResponseBo;

import java.util.List;
import java.util.Map;

public interface AnswerService {

    public List<Answer> getHotAnswer();

    public Integer getCountAns(Integer pid);

    public Answer getAns(Integer aid);

    public List<Answer> getUserAns(String uid);

    public List<Answer> getProAns(Integer pid);

    public List<Answer> getProYzAns(Integer pid);

    public Answer searchProAns(String pid);

    Integer getCountUserAns(String uid);

    Integer getCountUserAns(String uid, String date);

    ResponseBo setScore(String aid);

    ResponseBo updateAns(Answer answer);

    ResponseBo insAns(Answer answer);

    List<Answer> getTopicAns(String bq);

    List<Answer> getTopicAnsByAnsScore(String bq);

    List<Answer> getTopicAnsByAnsDate(String bq);

    ResponseBo deleteAns(String aid);

    List<Map<String,Object>> getUserByDate(String date);

    List<Map<String,Object>> getTwHdByDate(String date);

    List<Map<String,Object>> getHdCountByMonth();
}
