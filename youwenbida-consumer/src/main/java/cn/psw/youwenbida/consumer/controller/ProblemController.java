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
public class ProblemController {

    @Reference
    ProblemService problemService;
    @Reference
    CommentService commentService;
    @Reference
    OperationService operationService;
    @Reference
    IdentityService identityService;
    @Reference
    ReplyService replyService;
    @Reference
    AnswerService answerService;

    @RequestMapping("/problem")
    public String problem(){
        return "/pages/problem.html";
    }

    @RequestMapping("/toPro")
    public String toPro(String id){
        return "redirect:/problem?proid="+id;
    }

    @RequestMapping("/getPro")
    @ResponseBody
    public Problem getPro(HttpServletRequest request, @RequestBody @RequestParam("id")String id){
        Problem problem = problemService.getPro(Integer.parseInt(id));
        problem.setPplsl(commentService.getProComCount(Integer.parseInt(id)));
        HttpSession session = request.getSession();
        if((String)session.getAttribute("userid")!=null)
            if(operationService.getOp((String)session.getAttribute("userid"),problem.getPid().toString(),"3")!=null)
                problem.setIsgz(true);
        problem.setPgzzsl(operationService.getOpConut(problem.getPid().toString(),"3"));
        problem.setPhdsl(answerService.getCountAns(problem.getPid()));
        return problem;
    }

    @RequestMapping("/insertPro")
    @ResponseBody
    public ResponseBo inserPro(HttpServletRequest request, Problem problem){
        HttpSession session =request.getSession();
        problem.setPtcz((String)session.getAttribute("userid"));
        problem.setPtcrq(new Date());
        return problemService.insertPro(problem);
    }

    @RequestMapping("/gzPro")
    @ResponseBody
    public ResponseBo gzPro(HttpServletRequest request, @RequestBody @RequestParam("pid")String pid){
        HttpSession session = request.getSession();
        return  operationService.op((String)session.getAttribute("userid"),pid,"3");
    }

    @RequestMapping("/deleteGzPro")
    @ResponseBody
    public ResponseBo deleteGzPro(HttpServletRequest request, @RequestBody @RequestParam("pid")String pid){
        HttpSession session = request.getSession();
        return  operationService.deleteop((String)session.getAttribute("userid"),pid,"3");
    }

    @RequestMapping("/getProCom")
    @ResponseBody
    public ResponseBo getProCom(HttpServletRequest request, @RequestBody @RequestParam("pid")String pid){
        List<Comment> comments = commentService.getProCom(Integer.parseInt(pid));
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
            List<Reply> replies = replyService.getRe(comment.getCid());
            for(Reply reply:replies){
                reply.setRzuser(identityService.getUser(reply.getRz()));
                reply.setRbzuser(identityService.getUser(reply.getRbz()));
            }
            comment.setReplies(replies);
        }
        return ResponseBo.ok().put("coms",comments);
    }

    @RequestMapping("/insProCom")
    @ResponseBody
    public ResponseBo insProCom(HttpServletRequest request,Comment comment){
        HttpSession session = request.getSession();
        comment.setCplz((String)session.getAttribute("userid"));
        return commentService.insertProCom(comment).put("user",identityService.getUser((String)session.getAttribute("userid"))).put("msg","评论成功！");
    }

    @RequestMapping("/getProAnswer")
    @ResponseBody
    public List<Answer> getHotAnswer(HttpServletRequest request,@RequestBody @RequestParam("pid")String pid){
        List<Answer> answers = answerService.getProAns(Integer.parseInt(pid));
        HttpSession session = request.getSession();
        String uid = (String)session.getAttribute("userid");
        for(Answer answer:answers){
            User user = identityService.getUser(answer.getAhdz());
            if(user.getGxqm()==null)
                user.setGxqm("");
            answer.setUser(user);
            answer.setProblem(problemService.getPro(answer.getAhdwt()));
            if(uid!=null){
                if(operationService.getOp(uid,answer.getAid().toString(),"1")!=null)
                    answer.setDz(true);
                if(operationService.getOp(uid,answer.getAid().toString(),"2")!=null)
                    answer.setSc(true);
            }
            answer.setAplsl(commentService.getAnsComCount(answer.getAid()));
            answer.setAztsl(operationService.getOpConut(answer.getAid().toString(),"1"));
        }
        return answers;
    }

}
