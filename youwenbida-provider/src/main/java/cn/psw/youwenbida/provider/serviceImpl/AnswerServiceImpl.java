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
        return answerMapper.selectCountByPrimaryKey(pid);
    }
}
