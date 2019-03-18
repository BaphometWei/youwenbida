package cn.psw.youwenbida.provider.mapper;

import cn.psw.youwenbida.api.model.Problem;

import java.util.List;

public interface ProblemMapper {
    int deleteByPrimaryKey(Integer pid);

    int insert(Problem record);

    Problem selectByPrimaryKey(Integer pid);

    List<Problem> selectAll();

    List<Problem> selectListByPrimaryKey(Problem problem);

    int updateByPrimaryKey(Problem record);

    int selectCountByPrimaryKey(Problem problem);
}