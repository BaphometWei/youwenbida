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

    @Reference(timeout = 5000,retries = 0)
    ProblemService problemService;
    @Reference(timeout = 5000,retries = 0)
    CommentService commentService;
    @Reference(timeout = 5000,retries = 0)
    OperationService operationService;
    @Reference(timeout = 5000,retries = 0)
    IdentityService identityService;
    @Reference(timeout = 5000,retries = 0)
    ReplyService replyService;
    @Reference(timeout = 5000,retries = 0)
    AnswerService answerService;
    @Reference(timeout = 5000,retries = 0)
    NoticeService noticeService;

    @RequestMapping("/problem")
    public String problem(){
        return "/pages/problem.html";
    }

    @RequestMapping("/pro")
    public String toPro(){
        return "/pages/pro.html";
    }

    @RequestMapping("/getPro")
    @ResponseBody
    public Problem getPro(HttpServletRequest request, @RequestBody @RequestParam("id")String id){
        Problem problem = problemService.getPro(Integer.parseInt(id));
        problem.setUser(identityService.getUser(problem.getPtcz()));
        problem.setPplsl(commentService.getProComCount(Integer.parseInt(id)));
        HttpSession session = request.getSession();
        if((String)session.getAttribute("userid")!=null)
            if(operationService.getOp((String)session.getAttribute("userid"),problem.getPid().toString(),"3")!=null)
                problem.setIsgz(true);
        problem.setPgzzsl(operationService.getOpConut(problem.getPid().toString(),"3"));
        problem.setPhdsl(answerService.getCountAns(problem.getPid()));
        return problem;
    }

    @RequestMapping("/getProblem")
    @ResponseBody
    public ResponseBo getProblem(HttpServletRequest request, @RequestBody @RequestParam("id")String id){
        Problem problem = problemService.getPro(Integer.parseInt(id));
        if(problem != null) {
            problem.setPplsl(commentService.getProComCount(Integer.parseInt(id)));
            problem.setUser(identityService.getUser(problem.getPtcz()));
            HttpSession session = request.getSession();
            if ((String) session.getAttribute("userid") != null)
                if (operationService.getOp((String) session.getAttribute("userid"), problem.getPid().toString(), "3") != null)
                    problem.setIsgz(true);
            problem.setPgzzsl(operationService.getOpConut(problem.getPid().toString(), "3"));
            problem.setPhdsl(answerService.getCountAns(problem.getPid()));
        }
        return ResponseBo.ok().put("problem",problem);
    }

    @RequestMapping("/insertPro")
    @ResponseBody
    public ResponseBo inserPro(HttpServletRequest request, Problem problem){
        HttpSession session =request.getSession();
        problem.setPtcz((String)session.getAttribute("userid"));
        problem.setPtcrq(new Date());
        return problemService.insertPro(problem);
    }

    @RequestMapping("/updatePro")
    @ResponseBody
    public ResponseBo updatePro(HttpServletRequest request, Problem problem){
        return problemService.updatePro(problem);
    }

    @RequestMapping("/deletePro")
    @ResponseBody
    public ResponseBo deletePro(HttpServletRequest request, @RequestBody @RequestParam("id")String id){
        return problemService.deletePro(id);
    }

    @RequestMapping("/gzPro")
    @ResponseBody
    public ResponseBo gzPro(HttpServletRequest request, @RequestBody @RequestParam("pid")String pid){
        HttpSession session = request.getSession();
        if((Integer)operationService.op((String)session.getAttribute("userid"),pid,"3").get("code")==0){
            Problem problem = problemService.getPro(Integer.parseInt(pid));
            Notice notice = new Notice();
            notice.setNlx("<i class='layui-icon layui-icon-friends'></i>");
            notice.setNnr("<a target='_blank' href='/zhuye?id="+(String)session.getAttribute("userid")+"'> "+(String)session.getAttribute("username")+" </a>关注了你提出的 <a target='_blank' href='/pro?proid="+problem.getPid()+"'>"+problem.getPtitle()+"</a> 问题");
            notice.setNz(problem.getPtcz());
            notice.setNdate(new Date());
            noticeService.insertNotice(notice);
            return ResponseBo.ok();
        }
        return ResponseBo.error();
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
        User user = new User();
        Answer answer = new Answer();
        for(Comment comment:comments){
            answer = answerService.getAns(comment.getCbpl());
            user = new User();
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
            List<Reply> replies = replyService.getRe(comment.getCid());
            for(Reply reply:replies){
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
            comment.setReplies(replies);
        }
        return ResponseBo.ok().put("coms",comments).put("userid",uid);
    }

    @RequestMapping("/insProCom")
    @ResponseBody
    public ResponseBo insProCom(HttpServletRequest request,Comment comment){
        HttpSession session = request.getSession();
        comment.setCplz((String)session.getAttribute("userid"));
        ResponseBo rb = commentService.insertProCom(comment);
        if((Integer)rb.get("code")==0){
            comment = new Comment();
            comment.setCid((Integer)rb.get("cid"));
            comment = commentService.getCom(comment,null);
            Problem problem = problemService.getPro(comment.getCbpl());
            Notice notice = new Notice();
            notice.setNlx("<i class='layui-icon layui-icon-dialogue'></i>");
            notice.setNnr("<a target='_blank' href='/zhuye?id=" + (String) session.getAttribute("userid") + "'> " + (String) session.getAttribute("username") + " </a>评论了你提出的 <a target='_blank' href='/pro?proid=" + problem.getPid()  +"&pllx=1&cid="+ comment.getCid() + "'>" + problem.getPtitle() + "</a> 问题");
            notice.setNz(problem.getPtcz());
            notice.setNdate(new Date());
            noticeService.insertNotice(notice);
        }
        return rb.put("user",identityService.getUser((String)session.getAttribute("userid")));
    }

    @RequestMapping("/getProAnswer")
    @ResponseBody
    public ResponseBo getProAnswer(HttpServletRequest request,@RequestBody @RequestParam("pid")String pid){
        List<Answer> answers = answerService.getProAns(Integer.parseInt(pid));
        List<Answer> yzanswers = answerService.getProYzAns(Integer.parseInt(pid));
        HttpSession session = request.getSession();
        String uid = (String)session.getAttribute("userid");
        for(Answer answer:answers){
            User user = new User();
            if(answer.getNm().equals("t")) {
                if(uid!=null&&answer.getAhd().equals(uid)) {
                    user.setName("匿名用户(我)");
                    user.setId("###");
                    user.setImg("/img/niming.jpg");
                }else{
                    user.setName("匿名用户");
                    user.setId("###");
                    user.setImg("/img/niming.jpg");
                }
            }
            if(answer.getNm().equals("f")) {
                user = identityService.getUser(answer.getAhdz());
            }
            if(user.getGxqm()==null)
                user.setGxqm("");
            answer.setUser(user);
            answer.setProblem(problemService.getPro(answer.getAhdwt()));
            if(uid!=null){
                if(operationService.getOp(uid,answer.getAid().toString(),"1")!=null)
                    answer.setDz(true);
                if(operationService.getOp(uid,answer.getAid().toString(),"2")!=null)
                    answer.setSc(true);
                if(operationService.getOp(uid, answer.getAid().toString(), "6") != null)
                    answer.setFd(true);
            }
            answer.setAplsl(commentService.getAnsComCount(answer.getAid()));
            answer.setAztsl(60);
            answer.setScore(0.8548);
//            answer.setAztsl(operationService.getOpConut(answer.getAid().toString(),"1"));
//            answer.setScore(answer.getScore());
        }
        for(Answer answer:yzanswers){
            User user = new User();
            if(answer.getNm().equals("t")) {
                if(uid!=null&&answer.getAhd().equals(uid)) {
                    user.setName("匿名用户(我)");
                    user.setId("###");
                    user.setImg("/img/niming.jpg");
                }else{
                    user.setName("匿名用户");
                    user.setId("###");
                    user.setImg("/img/niming.jpg");
                }
            }
            if(answer.getNm().equals("f")) {
                user = identityService.getUser(answer.getAhdz());
            }
            if(user.getGxqm()==null)
                user.setGxqm("");
            answer.setUser(user);
            answer.setProblem(problemService.getPro(answer.getAhdwt()));
            if(uid!=null){
                if(operationService.getOp(uid,answer.getAid().toString(),"1")!=null)
                    answer.setDz(true);
                if(operationService.getOp(uid,answer.getAid().toString(),"2")!=null)
                    answer.setSc(true);
                if(operationService.getOp(uid, answer.getAid().toString(), "6") != null)
                    answer.setFd(true);
            }
            answer.setAplsl(commentService.getAnsComCount(answer.getAid()));
            answer.setAztsl(operationService.getOpConut(answer.getAid().toString(),"1"));
        }
//        Iterator<Answer> it = answers.iterator();
//        while(it.hasNext()){
//            Answer x = it.next();
//            if(x.getYzhd().equals("t")){
//                yzanswers.add(x);
//                it.remove();
//            }
//        }
        return ResponseBo.ok().put("ans",answers).put("yzans",yzanswers).put("userid",uid);
    }

    @RequestMapping("/getProblemAnswer")
    @ResponseBody
    public ResponseBo getProblemAnswer(HttpServletRequest request,@RequestBody @RequestParam("pid")String pid){
        List<Answer> answers = answerService.getProAns(Integer.parseInt(pid));
        List<Answer> yzanswers = answerService.getProYzAns(Integer.parseInt(pid));
        HttpSession session = request.getSession();
        String uid = (String)session.getAttribute("userid");
        for(Answer answer:answers){
            User user = new User();
            if(answer.getNm().equals("t")) {
                if(uid!=null&&answer.getAhd().equals(uid)) {
                    user.setName("匿名用户(我)");
                    user.setId("###");
                    user.setImg("/img/niming.jpg");
                }else{
                    user.setName("匿名用户");
                    user.setId("###");
                    user.setImg("/img/niming.jpg");
                }
            }
            if(answer.getNm().equals("f")) {
                user = identityService.getUser(answer.getAhdz());
            }
            if(user.getGxqm()==null)
                user.setGxqm("");
            answer.setUser(user);
            answer.setProblem(problemService.getPro(answer.getAhdwt()));
            if(uid!=null){
                if(operationService.getOp(uid,answer.getAid().toString(),"1")!=null)
                    answer.setDz(true);
                if(operationService.getOp(uid,answer.getAid().toString(),"2")!=null)
                    answer.setSc(true);
                if(operationService.getOp(uid, answer.getAid().toString(), "6") != null)
                    answer.setFd(true);
            }
            answer.setAplsl(commentService.getAnsComCount(answer.getAid()));
            answer.setAztsl(60);
            answer.setScore(0.8548);
//            answer.setAztsl(operationService.getOpConut(answer.getAid().toString(),"1"));
//            answer.setScore(answer.getScore());
        }
        for(Answer answer:yzanswers){
            User user = new User();
            if(answer.getNm().equals("t")) {
                if(uid!=null&&answer.getAhd().equals(uid)) {
                    user.setName("匿名用户(我)");
                    user.setId("###");
                    user.setImg("/img/niming.jpg");
                }else{
                    user.setName("匿名用户");
                    user.setId("###");
                    user.setImg("/img/niming.jpg");
                }
            }
            if(answer.getNm().equals("f")) {
                user = identityService.getUser(answer.getAhdz());
            }
            if(user.getGxqm()==null)
                user.setGxqm("");
            answer.setUser(user);
            answer.setProblem(problemService.getPro(answer.getAhdwt()));
            if(uid!=null){
                if(operationService.getOp(uid,answer.getAid().toString(),"1")!=null)
                    answer.setDz(true);
                if(operationService.getOp(uid,answer.getAid().toString(),"2")!=null)
                    answer.setSc(true);
                if(operationService.getOp(uid, answer.getAid().toString(), "6") != null)
                    answer.setFd(true);
            }
            answer.setAplsl(commentService.getAnsComCount(answer.getAid()));
            answer.setAztsl(operationService.getOpConut(answer.getAid().toString(),"1"));
        }
//        Iterator<Answer> it = answers.iterator();
//        while(it.hasNext()){
//            Answer x = it.next();
//            if(x.getYzhd().equals("t")){
//                yzanswers.add(x);
//                it.remove();
//            }
//        }
        return ResponseBo.ok().put("ans",answers).put("yzans",yzanswers).put("userid",uid);
    }

}
