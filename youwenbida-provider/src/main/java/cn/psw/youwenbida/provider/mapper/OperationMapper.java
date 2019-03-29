package cn.psw.youwenbida.provider.mapper;

import cn.psw.youwenbida.api.model.Operation;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    List<Map<String,Object>> getProGzByDate(@Param(value="date") String date);

    List<Map<String,Object>> getHdZanByDate(@Param(value="date") String date);

    List<Map<String,Object>> getHdCaiByDate(@Param(value="date") String date);

    List<Map<String,Object>> getHdScByDate(@Param(value="date") String date);
}