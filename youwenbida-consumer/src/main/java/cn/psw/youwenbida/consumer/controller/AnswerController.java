package cn.psw.youwenbida.consumer.controller;

import cn.psw.youwenbida.api.model.Answer;
import cn.psw.youwenbida.api.model.User;
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
import java.util.List;

@Controller
public class AnswerController {

    @Reference
    AnswerService answerService;
    @Reference
    OperationService operationService;
    @Reference
    ProblemService problemService;
    @Reference
    IdentityService identityService;
    @Reference
    CommentService commentService;


    @RequestMapping("/getHotAnswer")
    @ResponseBody
    public List<Answer> getHotAnswer(HttpServletRequest request){
        List<Answer> answers = answerService.getHotAnswer();
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

    @RequestMapping("/dzAnswer")
    @ResponseBody
    public ResponseBo dzAnswer(HttpServletRequest request,@RequestBody @RequestParam("aid")String aid){
        HttpSession session = request.getSession();
        return operationService.op((String)session.getAttribute("userid"),aid,"1");
    }

    @RequestMapping("/deleteDzAnswer")
    @ResponseBody
    public ResponseBo deleteDzAnswer(HttpServletRequest request,@RequestBody @RequestParam("aid")String aid){
        HttpSession session = request.getSession();
        return operationService.deleteop((String)session.getAttribute("userid"),aid,"1");
    }

    @RequestMapping("/scAnswer")
    @ResponseBody
    public ResponseBo scAnswer(HttpServletRequest request,@RequestBody @RequestParam("aid")String aid){
        HttpSession session = request.getSession();
        return operationService.op((String)session.getAttribute("userid"),aid,"2");
    }

    @RequestMapping("/deleteScAnswer")
    @ResponseBody
    public ResponseBo deleteScAnswer(HttpServletRequest request,@RequestBody @RequestParam("aid")String aid){
        HttpSession session = request.getSession();
        return operationService.deleteop((String)session.getAttribute("userid"),aid,"2");
    }

}
