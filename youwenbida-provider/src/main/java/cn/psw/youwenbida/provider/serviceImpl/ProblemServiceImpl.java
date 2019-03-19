package cn.psw.youwenbida.provider.serviceImpl;

import cn.psw.youwenbida.api.model.Problem;
import cn.psw.youwenbida.api.service.ProblemService;
import cn.psw.youwenbida.api.utils.ResponseBo;
import cn.psw.youwenbida.provider.mapper.ProblemMapper;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    ProblemMapper problemMapper;

    @Override
    public Problem getPro(Integer id) {
        return problemMapper.selectByPrimaryKey(id);
    }

    @Override
    public ResponseBo insertPro(Problem problem){
        if(problemMapper.insert(problem)!=0)
            return ResponseBo.ok().put("msg","提问成功");
        return ResponseBo.error();
    }

    @Override
    public List<Problem> getProList(String uid){
        Problem problem = new Problem();
        problem.setPtcz(uid);
        return problemMapper.selectListByPrimaryKey(problem);
    }

    @Override
    public Integer getCountUserPro(String uid){
        Problem problem = new Problem();
        problem.setPtcz(uid);
        return problemMapper.selectCountByPrimaryKey(problem);
    }

    @Override
    public ResponseBo updatePro(Problem problem){
        if(problemMapper.updateByPrimaryKey(problem)!=0)
            return ResponseBo.ok();
        return ResponseBo.error();
    }

    @Override
    public ResponseBo deletePro(String pid){
        if(problemMapper.deleteByPrimaryKey(Integer.parseInt(pid))!=0)
            return ResponseBo.ok();
        return ResponseBo.error();
    }

    @Override
    public List<Map<String,Object>> getUserByDate(String date){
        return problemMapper.getUserByDate(date);
    }

    @Override
    public Integer getCountUserPro(String uid, String date) {
        Map<String,Object> map = new HashMap<>();
        map.put("uid",uid);
        map.put("date",date);
        return problemMapper.selectCountByUserAndDate(map);
    }
}
