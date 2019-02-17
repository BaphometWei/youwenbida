package cn.psw.youwenbida.provider.mapper;

import cn.psw.youwenbida.api.model.Operation;

import java.util.List;

public interface OperationMapper {
    int deleteByPrimaryKey(String oid);

    int insert(Operation record);

    Operation selectByPrimaryKey(Operation record);

    List<Operation> selectAll();

    int updateByPrimaryKey(Operation record);
}