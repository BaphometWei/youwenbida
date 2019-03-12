package cn.psw.youwenbida.provider.mapper;

import cn.psw.youwenbida.api.model.Topic;

import java.util.List;

public interface TopicMapper {
    int deleteByPrimaryKey(Integer tid);

    int insert(Topic record);

    Topic selectByPrimaryKey(Integer tid);

    List<Topic> selectAll();

    int updateByPrimaryKey(Topic record);
}