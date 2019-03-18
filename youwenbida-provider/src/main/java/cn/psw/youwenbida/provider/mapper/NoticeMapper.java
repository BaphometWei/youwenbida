package cn.psw.youwenbida.provider.mapper;

import cn.psw.youwenbida.api.model.Notice;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface NoticeMapper {
    int deleteByPrimaryKey(Integer nid);

    int insert(Notice record);

    Notice selectByPrimaryKey(Integer nid);

    List<Notice> selectAll();

    int updateByPrimaryKey(Notice record);

    List<Date> selectByNzGbDate(String nz);

    List<Notice> selectByDate(Map<String,Object> map);

    int selectCountByNz(String nz);

    int updateByNzGbDateForYd(String nz);
}