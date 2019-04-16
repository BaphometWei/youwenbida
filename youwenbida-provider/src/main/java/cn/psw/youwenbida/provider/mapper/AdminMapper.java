package cn.psw.youwenbida.provider.mapper;

import cn.psw.youwenbida.api.model.Admin;

import java.util.List;

public interface AdminMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    Admin selectByPrimaryKey(Integer id);

    List<Admin> selectAll();

    int updateByPrimaryKey(Admin record);
    
    Admin admindl(Admin admin);
}