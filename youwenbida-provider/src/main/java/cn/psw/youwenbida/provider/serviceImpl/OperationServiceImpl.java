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
}
