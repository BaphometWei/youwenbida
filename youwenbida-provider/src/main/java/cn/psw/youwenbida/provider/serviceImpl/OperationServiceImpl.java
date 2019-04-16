package cn.psw.youwenbida.provider.serviceImpl;

import cn.psw.youwenbida.api.model.Operation;
import cn.psw.youwenbida.api.service.OperationService;
import cn.psw.youwenbida.api.utils.ResponseBo;
import cn.psw.youwenbida.provider.mapper.OperationMapper;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service(timeout = 5000, retries = 0)
public class OperationServiceImpl implements OperationService {

    @Autowired
    OperationMapper operationMapper;

    @Override
    public Operation getOplx(Operation operation) {
        return operationMapper.selectByPrimaryKey(operation);
    }

    @Override
    public Operation getOp(String uid,String id,String op) {
        Operation operation = new Operation();
        operation.setOoz(uid);
        operation.setObo(id);
        operation.setOlx(op);
        return operationMapper.selectByPrimaryKey(operation);
    }

    @Override
    public List<Operation> getOpList(String uid, String id, String op) {
        Operation operation = new Operation();
        operation.setOoz(uid);
        operation.setObo(id);
        operation.setOlx(op);
        return operationMapper.selectList(operation);
    }

    @Override
    public List<Operation> getUserOp(String uid, String id, String op) {
        Operation operation = new Operation();
        operation.setOoz(uid);
        operation.setObo(id);
        operation.setOlx(op);
        return operationMapper.getUserOp(operation);
    }

    @Override
    public Integer getOpConut(String id,String lx) {
        Operation operation = new Operation();
        operation.setObo(id);
        operation.setOlx(lx);
        return operationMapper.selectCount(operation);
    }

    @Override
    public ResponseBo op(String uid,String id,String op) {
        Operation operation = new Operation();
        operation.setOlx(op);
        operation.setObo(id);
        operation.setOoz(uid);
        if(operationMapper.insert(operation)!=0)
            return ResponseBo.ok();
        else
            return ResponseBo.error();
    }

    @Override
    public ResponseBo deleteop(String uid,String id,String op) {
        Operation operation = new Operation();
        operation.setOlx(op);
        operation.setObo(id);
        operation.setOoz(uid);
        if(operationMapper.deleteByPrimaryKey(operation)!=0)
            return ResponseBo.ok();
        else
            return ResponseBo.error();
    }

    @Override
    public List<Map<String,Object>> getProGzByDate(String date){
        return operationMapper.getProGzByDate(date);
    }

    @Override
    public List<Map<String,Object>> getHdZanByDate(String date){
        return operationMapper.getHdZanByDate(date);
    }

    @Override
    public List<Map<String,Object>> getHdCaiByDate(String date){
        return operationMapper.getHdCaiByDate(date);
    }

    @Override
    public List<Map<String,Object>> getHdScByDate(String date){
        return operationMapper.getHdScByDate(date);
    }
}
