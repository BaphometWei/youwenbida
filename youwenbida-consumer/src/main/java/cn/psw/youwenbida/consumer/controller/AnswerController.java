package cn.psw.youwenbida.consumer.controller;

import cn.psw.youwenbida.api.model.Answer;
import cn.psw.youwenbida.api.model.Operation;
import cn.psw.youwenbida.api.model.User;
import cn.psw.youwenbida.api.service.AnswerService;
import cn.psw.youwenbida.api.service.IdentityService;
import cn.psw.youwenbida.api.service.OperationService;
import cn.psw.youwenbida.api.service.ProblemService;
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


    @RequestMapping("/getHotAnswer")
    @ResponseBody
    public List<Answer> getHotAnswer(HttpServletRequest request){
        List<Answer> answers = answerService.getHotAnswer();
        HttpSession session = request.getSession();
        String uid = (String)session.getAttribute("userid");
        Operation operation = new Operation();
        operation.setOlx("1");
        operation.setOoz(uid);
        for(Answer answer:answers){
            User user = identityService.getUser(answer.getAhdz());
            answer.setAhdzname(user.getName());
            answer.setAhdzgxqm(user.getGxqm());
            answer.setTitle(problemService.getPro(answer.getAhdwt()).getPtitle());
            answer.setDz(false);
            answer.setSc(false);
            if(uid!=null){
                operation.setObo(answer.getAid());
                if(operationService.getOplx(operation)!=null)
                    answer.setDz(true);
                operation.setOlx("2");
                if(operationService.getOplx(operation)!=null)
                    answer.setSc(true);
            }
        }
        return answers;
    }

}
