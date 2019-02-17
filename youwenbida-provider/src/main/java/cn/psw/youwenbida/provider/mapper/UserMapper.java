package cn.psw.youwenbida.provider.mapper;

import cn.psw.youwenbida.api.model.User;
import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    User selectByPrimaryKey(String id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    User validateByColumn(User user);
}