package cn.psw.youwenbida.provider.serviceImpl;

import cn.psw.youwenbida.api.model.Answer;
import cn.psw.youwenbida.api.service.AnswerService;
import cn.psw.youwenbida.api.utils.ResponseBo;
import cn.psw.youwenbida.provider.mapper.AnswerMapper;
import cn.psw.youwenbida.provider.mapper.OperationMapper;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(timeout = 5000,retries = 0)
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    AnswerMapper answerMapper;
    @Autowired
    OperationMapper operationMapper;

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
        Map<String, Object> map = new HashMap<>();
        map.put("pid",pid);
        return answerMapper.getProAns(map);
    }

    @Override
    public List<Answer> getProYzAns(Integer pid){
        Map<String, Object> map = new HashMap<>();
        map.put("pid",pid);
        return answerMapper.getProYzAns(map);
    }

    @Override
    public List<Answer> getTopicAns(String bq){
        Map<String, Object> map = new HashMap<>();
        map.put("bq",bq);
        return answerMapper.getTopicAnsByOpDate(map);
    }

    @Override
    public Answer searchProAns(String pid) {
        return answerMapper.selectByPrimaryKey(answerMapper.searchProAns(Integer.parseInt(pid)));
    }

    @Override
    public ResponseBo setScore(String aid){
        double p_z = 1.96;
        Map<String,Object> map = operationMapper.getAnsDzAndCai(aid);
        long  dz = (long) map.get("dzsl");
        long  cai = (long ) map.get("caisl");
        double total = dz+cai;
        if(total>0){
            double pos_rat = dz * 1 / total * 1;  // 正例比率
            double score = (pos_rat + (Math.pow(p_z,2) / (2. * total))
                    - ((p_z / (2 * total)) * Math.sqrt(4 * total * (1 - pos_rat) * pos_rat + Math.pow(p_z,2)))) /
                    (1 + Math.pow(p_z,2) / total);
            Answer answer = new Answer();
            answer.setAid(Integer.parseInt(aid));
            answer.setScore(score);
//            if(score>0.7112 && dz >50)
//                answer.setYzhd("t");
            if(answerMapper.updateByPrimaryKey(answer)==0)
                return ResponseBo.error();
        }
        return ResponseBo.ok();
    }

    @Override
    public ResponseBo updateAns(Answer answer) {
        if(answerMapper.updateByPrimaryKey(answer)!=0)
            return ResponseBo.ok();
        return ResponseBo.error();
    }

    @Override
    public ResponseBo insAns(Answer answer) {
        if(answerMapper.insert(answer)!=0)
            return ResponseBo.ok().put("msg","回答成功").put("aid",answer.getAid());
        return ResponseBo.error();
    }

    @Override
    public List<Answer> getTopicAnsByAnsScore(String bq) {
        Map<String, Object> map = new HashMap<>();
        map.put("bq",bq);
        return answerMapper.getTopicAnsByAnsScore(map);
    }

    @Override
    public List<Answer> getTopicAnsByAnsDate(String bq) {
        Map<String, Object> map = new HashMap<>();
        map.put("bq",bq);
        return answerMapper.getTopicAnsByAnsDate(map);
    }
}
