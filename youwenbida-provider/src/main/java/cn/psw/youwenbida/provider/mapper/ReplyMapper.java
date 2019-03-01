package cn.psw.youwenbida.provider.mapper;

import cn.psw.youwenbida.api.model.Reply;

import java.util.List;

public interface ReplyMapper {
    int deleteByPrimaryKey(Integer rid);

    int insert(Reply record);

    List<Reply> selectByPrimaryKey(Reply reply);

    List<Reply> selectAll();

    int updateByPrimaryKey(Reply record);
}