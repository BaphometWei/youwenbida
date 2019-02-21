package cn.psw.youwenbida.api.service;

import cn.psw.youwenbida.api.model.Comment;
import cn.psw.youwenbida.api.utils.ResponseBo;

import java.util.List;

public interface CommentService {

    public Integer getAnsComCount(Integer aid);

    public Integer getProComCount(Integer pid);

    public List<Comment> getAnsCom(Integer aid);

    public ResponseBo insertAnsCom(Comment comment);
}
