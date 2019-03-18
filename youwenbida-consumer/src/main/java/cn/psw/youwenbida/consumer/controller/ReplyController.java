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

@Controller
public class ReplyController {

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

    @RequestMapping("/insComRe")
    @ResponseBody
    public ResponseBo insComRe(HttpServletRequest request,@RequestBody @RequestParam("rr")String rr,@RequestParam("rbz")String rbz,@RequestParam("alt")String alt,@RequestParam("lx")String lx){
        HttpSession session = request.getSession();
        Reply reply = new Reply();
        reply.setRr(rr);
        reply.setRbc(Integer.parseInt(alt));
        Comment comment = new Comment();
        comment.setCid(Integer.parseInt(alt));
        comment = commentService.getCom(comment,null);
        User user = new User();
        if(lx.equals("01")){
            user = identityService.getUser(comment.getCplz());
        }
        if(lx.equals("02")){
            user = identityService.getUser(replyService.getReByRid(Integer.parseInt(rbz)).getRz());
        }
        reply.setRbz(user.getId());
        Notice notice = new Notice();
        notice.setNlx("<i class='layui-icon layui-icon-chat'></i>");
        if(comment.getCpllx().equals("1")) {
            Problem problem = problemService.getPro(comment.getCbpl());
            notice.setNnr("<a target='_blank' href='/zhuye?id=" + (String) session.getAttribute("userid") + "'> " + (String) session.getAttribute("username") + " </a>回复了你在 <a target='_blank' href='/pro?proid=" + problem.getPid() + "&pllx=1&cid="+ comment.getCid() +"&rid="+reply.getRid()+ "'>" + problem.getPtitle() + "</a> 问题中的评论");
        }else{
            Answer answer = answerService.getAns(comment.getCbpl());
            answer.setProblem(problemService.getPro(answer.getAhdwt()));
            if(answer.getNm().equals("f")) {
                answer.setUser(identityService.getUser(answer.getAhdz()));
                notice.setNnr("<a target='_blank' href='/zhuye?id=" + (String) session.getAttribute("userid") + "'> " + (String) session.getAttribute("username") + " </a>回复了你在 <a target='_blank' href='/pro?proid=" + answer.getProblem().getPid() + "&aid=" + answer.getAid() + "&pllx=2&cid=" + comment.getCid() + "&rid=" + reply.getRid() + "'>" + answer.getProblem().getPtitle() + "</a> 问题中 <a target='_blank' href='/zhuye?id=" + answer.getUser().getId() + "'> " + answer.getUser().getName() + "</a> 回答下的评论");
            }
            if(answer.equals("t")){
                notice.setNnr("<a target='_blank' href='/zhuye?id=" + (String) session.getAttribute("userid") + "'> " + (String) session.getAttribute("username") + " </a>回复了你在 <a target='_blank' href='/pro?proid=" + answer.getProblem().getPid() + "&aid=" + answer.getAid() + "&pllx=2&cid=" + comment.getCid() + "&rid=" + reply.getRid() + "'>" + answer.getProblem().getPtitle() + "</a> 问题中 <a target='_blank' href='###'> 匿名用户</a> 回答下的评论");
            }
        }
        notice.setNz(comment.getCplz());
        notice.setNdate(new Date());
        noticeService.insertNotice(notice);
        ResponseBo rb =  replyService.insComRe(reply,(String)session.getAttribute("userid"));
        return rb;
    }

    @RequestMapping("/deleteRe")
    @ResponseBody
    public ResponseBo deleteRe(HttpServletRequest request,@RequestBody @RequestParam("rid")String rid){
        return replyService.deleteRe(rid);
    }
}
