package cn.psw.youwenbida.api.service;

import cn.psw.youwenbida.api.model.Comment;
import cn.psw.youwenbida.api.utils.ResponseBo;

import java.util.List;
import java.util.Map;

public interface CommentService {

    public Integer getAnsComCount(Integer aid);

    public Integer getProComCount(Integer pid);

    public List<Comment> getAnsCom(Integer aid);

    public ResponseBo insertAnsCom(Comment comment);

    public Comment getCom(Comment comment,String clx);

    public List<Comment> getProCom(Integer pid);

    public ResponseBo insertProCom(Comment comment);

    ResponseBo deleteCom(String cid);

    List<Map<String,Object>> getPlCountByMonth();
}
