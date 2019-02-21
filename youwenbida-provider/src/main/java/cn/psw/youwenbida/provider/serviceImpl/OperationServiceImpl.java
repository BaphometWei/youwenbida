package cn.psw.youwenbida.provider.serviceImpl;

import cn.psw.youwenbida.api.model.Operation;
import cn.psw.youwenbida.api.service.OperationService;
import cn.psw.youwenbida.provider.mapper.OperationMapper;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class OperationServiceImpl implements OperationService {

    @Autowired
    OperationMapper operationMapper;

    @Override
    public Operation getOplx(Operation operation) {
        return operationMapper.selectByPrimaryKey(operation);
    }

    @Override
    public Operation getDz(String uid,Integer aid) {
        Operation operation = new Operation();
        operation.setOoz(uid);
        operation.setObo(aid);
        operation.setOlx("1");
        return operationMapper.selectByPrimaryKey(operation);
    }

    @Override
    public Operation getSc(String uid,Integer aid) {
        Operation operation = new Operation();
        operation.setOoz(uid);
        operation.setObo(aid);
        operation.setOlx("2");
        return operationMapper.selectByPrimaryKey(operation);
    }

    @Override
    public Integer getConutByDz(Integer aid) {
        Operation operation = new Operation();
        operation.setObo(aid);
        operation.setOlx("1");
        return operationMapper.selectCount(operation);
    }

    @Override
    public Integer getCountDzByCom(Integer cid){
        Operation operation = new Operation();
        operation.setObo(cid);
        operation.setOlx("4");
        return operationMapper.selectCount(operation);
    }
}
