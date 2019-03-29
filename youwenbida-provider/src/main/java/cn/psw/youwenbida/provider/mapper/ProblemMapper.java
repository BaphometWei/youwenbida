package cn.psw.youwenbida.provider.mapper;

import cn.psw.youwenbida.api.model.Problem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProblemMapper {
    int deleteByPrimaryKey(Integer pid);

    int insert(Problem record);

    Problem selectByPrimaryKey(Integer pid);

    List<Problem> selectAll();

    List<Problem> selectListByPrimaryKey(Problem problem);

    int updateByPrimaryKey(Problem record);

    int selectCountByPrimaryKey(Problem problem);

    List<Map<String,Object>> getUserByDate(@Param(value="date") String date);

    Integer selectCountByUserAndDate(Map<String,Object> map);

    List<Map<String,Object>> getTwCountByMonth();
}