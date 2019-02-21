package cn.psw.youwenbida.consumer.controller;

import cn.psw.youwenbida.api.model.Answer;
import cn.psw.youwenbida.api.model.User;
import cn.psw.youwenbida.api.service.*;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
            answer.setAhdzname(user.getName());
            answer.setAhdzgxqm(user.getGxqm());
            answer.setTitle(problemService.getPro(answer.getAhdwt()).getPtitle());
            if(uid!=null){
                if(operationService.getDz(uid,answer.getAid())!=null)
                    answer.setDz(true);
                if(operationService.getSc(uid,answer.getAid())!=null)
                    answer.setSc(true);
            }
            answer.setAplsl(commentService.getAnsComCount(answer.getAid()));
            answer.setAztsl(operationService.getConutByDz(answer.getAid()));
        }
        return answers;
    }

}
