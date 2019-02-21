package cn.psw.youwenbida.provider.mapper;

import cn.psw.youwenbida.api.model.Problem;

import java.util.List;

public interface ProblemMapper {
    int deleteByPrimaryKey(String pid);

    int insert(Problem record);

    Problem selectByPrimaryKey(Integer pid);

    List<Problem> selectAll();

    int updateByPrimaryKey(Problem record);
}