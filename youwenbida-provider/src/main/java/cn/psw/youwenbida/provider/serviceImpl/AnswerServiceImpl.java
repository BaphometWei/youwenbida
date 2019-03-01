package cn.psw.youwenbida.provider.serviceImpl;

import cn.psw.youwenbida.api.model.Answer;
import cn.psw.youwenbida.api.service.AnswerService;
import cn.psw.youwenbida.provider.mapper.AnswerMapper;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    AnswerMapper answerMapper;

    @Override
    public List<Answer> getHotAnswer() {
        return answerMapper.selectAll();
    }

    @Override
    public Integer getCountAns(Integer pid) {
        Answer answer = new Answer();
        answer.setAhdwt(pid);
        return answerMapper.selectCountByPrimaryKey(answer);
    }

    @Override
    public Integer getCountUserAns(String uid) {
        Answer answer = new Answer();
        answer.setAhdz(uid);
        return answerMapper.selectCountByPrimaryKey(answer);
    }

    @Override
    public Answer getAns(Integer aid) {
        return answerMapper.selectByPrimaryKey(aid);
    }

    @Override
    public List<Answer> getUserAns(String uid){
        Answer answer = new Answer();
        answer.setAhdz(uid);
        return answerMapper.selectListByPrimaryKey(answer);
    }

    @Override
    public List<Answer> getProAns(Integer pid){
        Answer answer = new Answer();
        answer.setAhdwt(pid);
        return answerMapper.selectListByPrimaryKey(answer);
    }

    @Override
    public Answer searchProAns(String pid) {
        return answerMapper.selectByPrimaryKey(answerMapper.searchProAns(Integer.parseInt(pid)));
    }
}
