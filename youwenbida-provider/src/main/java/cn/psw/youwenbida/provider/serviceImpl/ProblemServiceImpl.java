package cn.psw.youwenbida.provider.serviceImpl;

import cn.psw.youwenbida.api.model.Problem;
import cn.psw.youwenbida.api.service.ProblemService;
import cn.psw.youwenbida.api.utils.ResponseBo;
import cn.psw.youwenbida.provider.mapper.ProblemMapper;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

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
            return ResponseBo.ok();
        return ResponseBo.error();
    }
}
