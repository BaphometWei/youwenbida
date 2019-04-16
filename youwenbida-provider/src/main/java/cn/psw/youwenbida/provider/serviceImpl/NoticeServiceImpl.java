package cn.psw.youwenbida.provider.serviceImpl;

import cn.psw.youwenbida.api.model.Notice;
import cn.psw.youwenbida.api.service.NoticeService;
import cn.psw.youwenbida.api.utils.ResponseBo;
import cn.psw.youwenbida.provider.mapper.NoticeMapper;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(timeout = 5000, retries = 0)
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    NoticeMapper noticeMapper;

    @Override
    public void insertNotice(Notice notice) {
        noticeMapper.insert(notice);
    }

    @Override
    public ResponseBo getNoticeByNz(String nz) {
        noticeMapper.updateByNzGbDateForYd(nz);
        List<Date> dates = noticeMapper.selectByNzGbDate(nz);
        List<Map<String,Object>> notices = new ArrayList<>();
        DateFormat bf = new SimpleDateFormat("yyyy-MM-dd");
        for(Date date:dates){
            Map<String,Object> map = new HashMap<>();
            map.put("nz",nz);
            map.put("ndate",bf.format(date));
            map.put("notices",noticeMapper.selectByDate(map));
            notices.add(map);
        }
        return ResponseBo.ok().put("groups",notices);
    }

    @Override
    public int getWdNoticeCount(String nz) {
        return noticeMapper.selectCountByNz(nz);
    }
}
