package cn.psw.youwenbida.consumer.controller;

import cn.psw.youwenbida.api.model.Answer;
import cn.psw.youwenbida.api.model.Comment;
import cn.psw.youwenbida.api.model.Reply;
import cn.psw.youwenbida.api.model.User;
import cn.psw.youwenbida.api.service.CommentService;
import cn.psw.youwenbida.api.service.IdentityService;
import cn.psw.youwenbida.api.service.OperationService;
import cn.psw.youwenbida.api.service.ReplyService;
import cn.psw.youwenbida.api.utils.ResponseBo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Reference
    ReplyService replyService;

    @RequestMapping("/getAnsCom")
    @ResponseBody
    public List<Comment> getAnsCom(HttpServletRequest request,Answer answer){
        List<Comment> comments = commentService.getAnsCom(answer.getAid());
        HttpSession session = request.getSession();
        String uid = (String)session.getAttribute("userid");
        for(Comment comment:comments){
            User user = identityService.getUser(comment.getCplz());
            comment.setUser(user);
            comment.setCdzsl(operationService.getOpConut(comment.getCid().toString(),"4"));
            if(uid!=null){
                if(operationService.getOp(uid,comment.getCid().toString(),"4")!=null)
                    comment.setIsdz(true);
            }
        }
//        Map<String,Object> map = new HashMap<>();
//        map.put("size",comments.size());
//        map.put("comments",comments);
        return comments;
    }

    @RequestMapping("/getCom")
    @ResponseBody
    public Comment getCom(HttpServletRequest request,Comment comment){
        comment = commentService.getCom(comment,"2");
        HttpSession session = request.getSession();
        comment.setUser(identityService.getUser(comment.getCplz()));
        comment.setReplies(replyService.getRe(comment.getCid()));
        for(Reply reply:comment.getReplies()){
            reply.setRzuser(identityService.getUser(reply.getRz()));
            reply.setRbzuser(identityService.getUser(reply.getRbz()));
        }
        return comment;
    }

    @RequestMapping("insertAnsCom")
    @ResponseBody
    public ResponseBo insertAnsCom(HttpServletRequest request,Comment comment){
        HttpSession session = request.getSession();
        comment.setCplz((String)session.getAttribute("userid"));
        return commentService.insertAnsCom(comment).put("user",identityService.getUser((String)session.getAttribute("userid")));
    }

    @RequestMapping("/dzComment")
    @ResponseBody
    public ResponseBo dzComment(HttpServletRequest request,@RequestBody @RequestParam("cid")String cid){
        HttpSession session = request.getSession();
        return operationService.op((String)session.getAttribute("userid"),cid,"4");
    }

    @RequestMapping("/deleteDzComment")
    @ResponseBody
    public ResponseBo deleteDzComment(HttpServletRequest request,@RequestBody @RequestParam("cid")String cid){
        HttpSession session = request.getSession();
        return operationService.deleteop((String)session.getAttribute("userid"),cid,"4");
    }

}
