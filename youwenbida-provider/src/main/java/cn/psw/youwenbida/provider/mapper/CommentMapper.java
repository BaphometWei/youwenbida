package cn.psw.youwenbida.provider.mapper;

import cn.psw.youwenbida.api.model.Comment;
import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer cid);

    int insert(Comment record);

    List<Comment> selectList(Comment comment);

    Comment selectByPrimaryKey(Comment comment);

    Integer selectCount(Comment comment);

    List<Comment> selectAll();

    int updateByPrimaryKey(Comment record);
}