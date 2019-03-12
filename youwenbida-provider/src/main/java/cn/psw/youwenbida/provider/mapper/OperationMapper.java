package cn.psw.youwenbida.provider.mapper;

import cn.psw.youwenbida.api.model.Operation;

import java.util.*;

public interface OperationMapper {
    int deleteByPrimaryKey(Operation record);

    int insert(Operation record);

    List<Operation> selectList(Operation operation);

    List<Operation> getUserOp(Operation operation);

    Operation selectByPrimaryKey(Operation record);

    Integer selectCount(Operation record);

    List<Operation> selectAll();

    int updateByPrimaryKey(Operation record);

    Map<String, Object> getAnsDzAndCai(String aid);
}