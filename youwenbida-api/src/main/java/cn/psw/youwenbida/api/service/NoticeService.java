package cn.psw.youwenbida.api.service;

import cn.psw.youwenbida.api.model.Notice;
import cn.psw.youwenbida.api.utils.ResponseBo;

public interface NoticeService {

    void insertNotice(Notice notice);

    ResponseBo getNoticeByNz(String nz);

    int getWdNoticeCount(String nz);

}
