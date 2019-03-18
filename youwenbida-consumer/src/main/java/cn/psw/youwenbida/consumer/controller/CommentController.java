package cn.psw.youwenbida.consumer.controller;

import cn.psw.youwenbida.api.model.*;
import cn.psw.youwenbida.api.service.*;
import cn.psw.youwenbida.api.utils.ResponseBo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class CommentController {

    @Reference(timeout = 5000,retries = 0)
    CommentService commentService;
    @Reference(timeout = 5000,retries = 0)
    IdentityService identityService;
    @Reference(timeout = 5000,retries = 0)
    OperationService operationService;
    @Reference(timeout = 5000,retries = 0)
    ReplyService replyService;
    @Reference(timeout = 5000,retries = 0)
    NoticeService noticeService;
    @Reference(timeout = 5000,retries = 0)
    ProblemService problemService;
    @Reference(timeout = 5000,retries = 0)
    AnswerService answerService;

    @RequestMapping("/getAnsCom")
    @ResponseBody
    public ResponseBo getAnsCom(HttpServletRequest request,Answer answer){
        answer = answerService.getAns(answer.getAid());
        List<Comment> comments = commentService.getAnsCom(answer.getAid());
        HttpSession session = request.getSession();
        String uid = (String)session.getAttribute("userid");
        for(Comment comment:comments){
            User user = new User();
            if(answer.getNm().equals("t")){
                if(comment.getCplz().equals(answer.getAhdz())){
                    if(uid!=null&&comment.getCplz().equals(uid)) {
                        user.setName("匿名用户(我)");
                        user.setId("###");
                        user.setImg("/img/niming.jpg");
                    }else{
                        user.setName("匿名用户(作者)");
                        user.setId("###");
                        user.setImg("/img/niming.jpg");
                    }
                }else{
                    user = identityService.getUser(comment.getCplz());
                }
            }else{
                user = identityService.getUser(comment.getCplz());
            }
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
        return ResponseBo.ok().put("commets",comments).put("userid",uid);
    }

    @RequestMapping("/getCom")
    @ResponseBody
    public ResponseBo getCom(HttpServletRequest request,Comment comment){
        comment = commentService.getCom(comment,"2");
        HttpSession session = request.getSession();
        String uid = (String)session.getAttribute("userid");
        Answer answer = answerService.getAns(comment.getCbpl());
        User user = new User();
        if(answer.getNm().equals("t")){
            if(comment.getCplz().equals(answer.getAhdz())){
                if(uid!=null&&comment.getCplz().equals(uid)) {
                    user.setName("匿名用户(我)");
                    user.setId("###");
                    user.setImg("/img/niming.jpg");
                }else{
                    user.setName("匿名用户(作者)");
                    user.setId("###");
                    user.setImg("/img/niming.jpg");
                }
            }else{
                user = identityService.getUser(comment.getCplz());
            }
        }else{
            user = identityService.getUser(comment.getCplz());
        }
        comment.setUser(user);
        comment.setReplies(replyService.getRe(comment.getCid()));
        for(Reply reply:comment.getReplies()){
            user = new User();
            if(answer.getNm().equals("t")){
                if(reply.getRz().equals(answer.getAhdz())){
                    if(uid!=null&&reply.getRz().equals(uid)) {
                        user.setName("匿名用户(我)");
                        user.setId("###");
                        user.setImg("/img/niming.jpg");
                    }else{
                        user.setName("匿名用户(作者)");
                        user.setId("###");
                        user.setImg("/img/niming.jpg");
                    }
                }else{
                    user = identityService.getUser(reply.getRz());
                }
            }else{
                user = identityService.getUser(reply.getRz());
            }
            reply.setRzuser(user);
            user = new User();
            if(answer.getNm().equals("t")){
                if(reply.getRbz().equals(answer.getAhdz())){
                    if(uid!=null&&reply.getRbz().equals(uid)) {
                        user.setName("匿名用户(我)");
                        user.setId("###");
                        user.setImg("/img/niming.jpg");
                    }else{
                        user.setName("匿名用户(作者)");
                        user.setId("###");
                        user.setImg("/img/niming.jpg");
                    }
                }else{
                    user = identityService.getUser(reply.getRbz());
                }
            }else{
                user = identityService.getUser(reply.getRbz());
            }
            reply.setRbzuser(user);
        }
        return ResponseBo.ok().put("comment",comment).put("userid",(String)session.getAttribute("userid"));
    }

    @RequestMapping("/getProblemCom")
    @ResponseBody
    public ResponseBo getProblemCom(HttpServletRequest request,@RequestBody @RequestParam("cid")String cid, @RequestParam("pllx")String pllx){
        Comment comment = new Comment();
        comment.setCid(Integer.parseInt(cid));
        comment = commentService.getCom(comment,pllx);
        HttpSession session = request.getSession();
        if(comment!=null){
            String uid = (String)session.getAttribute("userid");
            Answer answer = answerService.getAns(comment.getCbpl());
            User user = new User();
            if(answer.getNm().equals("t")){
                if(comment.getCplz().equals(answer.getAhdz())){
                    if(uid!=null&&comment.getCplz().equals(uid)) {
                        user.setName("匿名用户(我)");
                        user.setId("###");
                        user.setImg("/img/niming.jpg");
                    }else{
                        user.setName("匿名用户(作者)");
                        user.setId("###");
                        user.setImg("/img/niming.jpg");
                    }
                }else{
                    user = identityService.getUser(comment.getCplz());
                }
            }else{
                user = identityService.getUser(comment.getCplz());
            }
            comment.setUser(user);
            comment.setReplies(replyService.getRe(comment.getCid()));
            for(Reply reply:comment.getReplies()){
                user = new User();
                if(answer.getNm().equals("t")){
                    if(reply.getRz().equals(answer.getAhdz())){
                        if(uid!=null&&reply.getRz().equals(uid)) {
                            user.setName("匿名用户(我)");
                            user.setId("###");
                            user.setImg("/img/niming.jpg");
                        }else{
                            user.setName("匿名用户(作者)");
                            user.setId("###");
                            user.setImg("/img/niming.jpg");
                        }
                    }else{
                        user = identityService.getUser(reply.getRz());
                    }
                }else{
                    user = identityService.getUser(reply.getRz());
                }
                reply.setRzuser(user);
                user = new User();
                if(answer.getNm().equals("t")){
                    if(reply.getRbz().equals(answer.getAhdz())){
                        if(uid!=null&&reply.getRbz().equals(uid)) {
                            user.setName("匿名用户(我)");
                            user.setId("###");
                            user.setImg("/img/niming.jpg");
                        }else{
                            user.setName("匿名用户(作者)");
                            user.setId("###");
                            user.setImg("/img/niming.jpg");
                        }
                    }else{
                        user = identityService.getUser(reply.getRbz());
                    }
                }else{
                    user = identityService.getUser(reply.getRbz());
                }
                reply.setRbzuser(user);
            }
        }
        return ResponseBo.ok().put("comment",comment).put("userid",(String)session.getAttribute("userid"));
    }

    @RequestMapping("/insertAnsCom")
    @ResponseBody
    public ResponseBo insertAnsCom(HttpServletRequest request,Comment comment){
        HttpSession session = request.getSession();
        String uid = (String)session.getAttribute("userid");
        comment.setCplz((String)session.getAttribute("userid"));
        ResponseBo rb = commentService.insertAnsCom(comment);
        User user = new User();
        if((Integer)rb.get("code")==0){
            comment = new Comment();
            comment.setCid((Integer)rb.get("cid"));
            comment = commentService.getCom(comment,null);
            Answer answer = answerService.getAns(comment.getCbpl());
            answer.setProblem(problemService.getPro(answer.getAhdwt()));
            Notice notice = new Notice();
            notice.setNlx("<i class='layui-icon layui-icon-dialogue'></i>");
            notice.setNnr("<a target='_blank' href='/zhuye?id=" + (String) session.getAttribute("userid") + "'> " + (String) session.getAttribute("username") + " </a>评论了你在 <a target='_blank' href='/pro?proid=" + answer.getProblem().getPid() + "&aid="+ answer.getAid() +"&pllx=2&cid="+ comment.getCid() + "'>" + answer.getProblem().getPtitle() + "</a> 问题中的回答");
            notice.setNz(answer.getAhdz());
            notice.setNdate(new Date());
            noticeService.insertNotice(notice);
            if(answer.getNm().equals("t")){
                if(comment.getCplz().equals(answer.getAhdz())){
                    if(uid!=null&&comment.getCplz().equals(uid)) {
                        user.setName("匿名用户(我)");
                        user.setId("###");
                        user.setImg("/img/niming.jpg");
                    }else{
                        user.setName("匿名用户(作者)");
                        user.setId("###");
                        user.setImg("/img/niming.jpg");
                    }
                }else{
                    user = identityService.getUser(comment.getCplz());
                }
            }else{
                user = identityService.getUser(comment.getCplz());
            }
        }
        return rb.put("user",user);
    }

    @RequestMapping("/dzComment")
    @ResponseBody
    public ResponseBo dzComment(HttpServletRequest request,@RequestBody @RequestParam("cid")String cid){
        HttpSession session = request.getSession();
        if((Integer)operationService.op((String)session.getAttribute("userid"),cid,"4").get("code")==0){
            Comment comment = new Comment();
            comment.setCid(Integer.parseInt(cid));
            comment = commentService.getCom(comment,null);
            Notice notice = new Notice();
            notice.setNlx("<i class='layui-icon layui-icon-praise'></i>");
            Answer answer = answerService.getAns(comment.getCbpl());
            answer.setProblem(problemService.getPro(answer.getAhdwt()));
            if(answer.getNm().equals("t")){
                notice.setNnr("<a target='_blank' href='/zhuye?id=" + (String) session.getAttribute("userid") + "'> " + (String) session.getAttribute("username") + " </a>赞了你在 <a target='_blank' href='/pro?proid=" + answer.getProblem().getPid() + "&aid=" + answer.getAid() + "&pllx=2&cid=" + comment.getCid() + "'>" + answer.getProblem().getPtitle() + "</a> 问题中 匿名用户 回答下的评论");
            }else {
                answer.setUser(identityService.getUser(answer.getAhdz()));
                notice.setNnr("<a target='_blank' href='/zhuye?id=" + (String) session.getAttribute("userid") + "'> " + (String) session.getAttribute("username") + " </a>赞了你在 <a target='_blank' href='/pro?proid=" + answer.getProblem().getPid() + "&aid=" + answer.getAid() + "&pllx=2&cid=" + comment.getCid() + "'>" + answer.getProblem().getPtitle() + "</a> 问题中 <a target='_blank' href='/zhuye?id=" + answer.getUser().getId() + "'> " + answer.getUser().getName() + "</a> 回答下的评论");
            }
            notice.setNz(comment.getCplz());
            notice.setNdate(new Date());
            noticeService.insertNotice(notice);
            return ResponseBo.ok();
        }
        return ResponseBo.error();
    }

    @RequestMapping("/deleteDzComment")
    @ResponseBody
    public ResponseBo deleteDzComment(HttpServletRequest request,@RequestBody @RequestParam("cid")String cid){
        HttpSession session = request.getSession();
        return operationService.deleteop((String)session.getAttribute("userid"),cid,"4");
    }

    @RequestMapping("/deleteCom")
    @ResponseBody
    public ResponseBo deleteCom(HttpServletRequest request,@RequestBody @RequestParam("cid")String cid){
        return commentService.deleteCom(cid);
    }

}
