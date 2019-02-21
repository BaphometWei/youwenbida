package cn.psw.youwenbida.consumer.controller;

import cn.psw.youwenbida.api.model.Answer;
import cn.psw.youwenbida.api.model.Comment;
import cn.psw.youwenbida.api.model.User;
import cn.psw.youwenbida.api.service.CommentService;
import cn.psw.youwenbida.api.service.IdentityService;
import cn.psw.youwenbida.api.service.OperationService;
import cn.psw.youwenbida.api.utils.ResponseBo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CommentController {

    @Reference
    CommentService commentService;
    @Reference
    IdentityService identityService;
    @Reference
    OperationService operationService;

    @RequestMapping("/getAnsCom")
    @ResponseBody
    public List<Comment> getAnsCom(Answer answer){
        List<Comment> comments = commentService.getAnsCom(answer.getAid());
        for(Comment comment:comments){
            User user = identityService.getUser(comment.getCplz());
            comment.setCplzname(user.getName());
            comment.setCdzsl(operationService.getCountDzByCom(comment.getCid()));
        }
//        Map<String,Object> map = new HashMap<>();
//        map.put("size",comments.size());
//        map.put("comments",comments);
        return comments;
    }

    @RequestMapping("insertAnsCom")
    @ResponseBody
    public ResponseBo insertAnsCom(HttpServletRequest request,Comment comment){
        HttpSession session = request.getSession();
        comment.setCplz((String)session.getAttribute("userid"));
        return commentService.insertAnsCom(comment);
    }

}
