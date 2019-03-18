package cn.psw.youwenbida.provider.mapper;

import cn.psw.youwenbida.api.model.Chat;

import java.util.List;

public interface ChatMapper {
    int deleteByPrimaryKey(Integer chid);

    int insert(Chat record);

    Chat selectByPrimaryKey(Integer chid);

    List<Chat> selectAll();

    int updateByPrimaryKey(Chat record);

    List<Chat> getChat(Chat record);

    String getHaveChat(Chat chat);

    int insertHaveChat(Chat chat);

    int updateHaveChat(Chat chat);
}