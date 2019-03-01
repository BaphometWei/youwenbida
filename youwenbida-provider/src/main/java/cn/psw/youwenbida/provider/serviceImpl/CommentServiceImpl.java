package cn.psw.youwenbida.provider.serviceImpl;

import cn.psw.youwenbida.api.model.Comment;
import cn.psw.youwenbida.api.service.CommentService;
import cn.psw.youwenbida.api.utils.ResponseBo;
import cn.psw.youwenbida.provider.mapper.CommentMapper;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Override
    public Integer getAnsComCount(Integer aid) {
        Comment comment = new Comment();
        comment.setCbpl(aid);
        comment.setCpllx("2");
        return commentMapper.selectCount(comment);
    }

    @Override
    public Integer getProComCount(Integer pid) {
        Comment comment = new Comment();
        comment.setCbpl(pid);
        comment.setCpllx("1");
        return commentMapper.selectCount(comment);
    }

    @Override
    public List<Comment> getAnsCom(Integer aid){
        Comment comment = new Comment();
        comment.setCbpl(aid);
        comment.setCpllx("2");
        return commentMapper.selectList(comment);
    }

    @Override
    public List<Comment> getProCom(Integer pid){
        Comment comment = new Comment();
        comment.setCbpl(pid);
        comment.setCpllx("1");
        return commentMapper.selectList(comment);
    }

    @Override
    public ResponseBo insertAnsCom(Comment comment) {
        comment.setCpllx("2");
        if(commentMapper.insert(comment)!=0)
            return ResponseBo.ok().put("cid",comment.getCid());
        else
            return ResponseBo.error();
    }

    @Override
    public ResponseBo insertProCom(Comment comment) {
        comment.setCpllx("1");
        if(commentMapper.insert(comment)!=0)
            return ResponseBo.ok().put("cid",comment.getCid());
        else
            return ResponseBo.error();
    }

    @Override
    public Comment getCom(Comment comment,String clx){
        comment.setCpllx(clx);
        return commentMapper.selectByPrimaryKey(comment);
    }

}
