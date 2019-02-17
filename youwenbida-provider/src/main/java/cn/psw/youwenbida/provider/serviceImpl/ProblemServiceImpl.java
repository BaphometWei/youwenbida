package cn.psw.youwenbida.provider.serviceImpl;

import cn.psw.youwenbida.api.model.Problem;
import cn.psw.youwenbida.api.service.ProblemService;
import cn.psw.youwenbida.provider.mapper.ProblemMapper;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    ProblemMapper problemMapper;

    @Override
    public Problem getPro(String id) {
        System.out.println("6666");
        return problemMapper.selectByPrimaryKey(id);
    }
}
