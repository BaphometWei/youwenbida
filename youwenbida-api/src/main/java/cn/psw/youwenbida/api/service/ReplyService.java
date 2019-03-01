package cn.psw.youwenbida.api.service;

import cn.psw.youwenbida.api.model.Reply;
import cn.psw.youwenbida.api.utils.ResponseBo;

import java.util.List;

public interface ReplyService {

    public ResponseBo insComRe(Reply reply, String uid);

    public List<Reply> getRe(Integer cid);
}
