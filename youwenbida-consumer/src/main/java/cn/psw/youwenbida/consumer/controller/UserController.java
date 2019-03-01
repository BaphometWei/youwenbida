package cn.psw.youwenbida.consumer.controller;

import cn.psw.youwenbida.api.model.Answer;
import cn.psw.youwenbida.api.model.Operation;
import cn.psw.youwenbida.api.model.Problem;
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
public class UserController {

    @Reference
    IdentityService identityService;
    @Reference
    OperationService operationService;
    @Reference
    AnswerService answerService;
    @Reference
    ProblemService problemService;
    @Reference
    CommentService commentService;


    @RequestMapping("/getUser")
    @ResponseBody
    public ResponseBo getUser(HttpServletRequest request, @RequestBody @RequestParam("uid") String uid) {
        User user = identityService.getUser(uid);
        HttpSession session = request.getSession();
        String br = "false", show1 = "false", show2 = "false";
        if (user.getId().equals((String) session.getAttribute("userid")))
            br = "true";
        else if ((String) session.getAttribute("userid") != null) {
            if (operationService.getOp((String) session.getAttribute("userid"), uid, "5") != null)
                user.setIsgz(true);
        }
        if (user.getIndustry() != null || user.getGs() != null) {
            show1 = "true";
        }
        if (user.getEducation() != null || user.getLocation() != null || user.getIntroduction() != null || user.getIndustry() != null || user.getGs() != null) {
            show2 = "true";
        }
        return ResponseBo.ok().put("user", user).put("br", br).put("show1", show1).put("show2", show2);
    }

    @RequestMapping("/gz")
    @ResponseBody
    public ResponseBo gz(HttpServletRequest request, @RequestBody @RequestParam("uid") String uid) {
        HttpSession session = request.getSession();
        return operationService.op((String) session.getAttribute("userid"), uid, "5");
    }

    @RequestMapping("/deleteGz")
    @ResponseBody
    public ResponseBo deleteGz(HttpServletRequest request, @RequestBody @RequestParam("uid") String uid) {
        HttpSession session = request.getSession();
        return operationService.deleteop((String) session.getAttribute("userid"), uid, "5");
    }

    @RequestMapping("/getUserOp")
    @ResponseBody
    public ResponseBo getUserOp(HttpServletRequest request, @RequestBody @RequestParam("uid") String uid) {
        HttpSession session = request.getSession();
        List<Operation> operations = operationService.getUserOp(uid, null, "('1','2','3')");
        uid = (String) session.getAttribute("userid");
        for (Operation operation : operations) {
            if (operation.getOlx().equals("1") || operation.getOlx().equals("2")) {
                Answer answer = answerService.getAns(Integer.parseInt(operation.getObo()));
                User user = identityService.getUser(answer.getAhdz());
                if (user.getGxqm() == null)
                    user.setGxqm("");
                answer.setUser(user);
                answer.setProblem(problemService.getPro(answer.getAhdwt()));
                if (uid != null) {
                    if (operationService.getOp(uid, answer.getAid().toString(), "1") != null)
                        answer.setDz(true);
                    if (operationService.getOp(uid, answer.getAid().toString(), "2") != null)
                        answer.setSc(true);
                }
                answer.setAplsl(commentService.getAnsComCount(answer.getAid()));
                answer.setAztsl(operationService.getOpConut(answer.getAid().toString(),"1"));
                operation.setAnswer(answer);
            }
            if (operation.getOlx().equals("3")) {
                operation.setProblem(problemService.getPro(Integer.parseInt(operation.getObo())));
            }
        }
        return ResponseBo.ok().put("ops", operations);
    }

    @RequestMapping("/getUserAns")
    @ResponseBody
    public ResponseBo getUserAns(HttpServletRequest request, @RequestBody @RequestParam("uid") String uid) {
        List<Answer> answers = answerService.getUserAns(uid);
        HttpSession session = request.getSession();
        uid = (String) session.getAttribute("userid");
        for (Answer answer : answers) {
            User user = identityService.getUser(answer.getAhdz());
            if (user.getGxqm() == null)
                user.setGxqm("");
            answer.setUser(user);
            answer.setProblem(problemService.getPro(answer.getAhdwt()));
            if (uid != null) {
                if (operationService.getOp(uid, answer.getAid().toString(), "1") != null)
                    answer.setDz(true);
                if (operationService.getOp(uid, answer.getAid().toString(), "2") != null)
                    answer.setSc(true);
            }
            answer.setAplsl(commentService.getAnsComCount(answer.getAid()));
            answer.setAztsl(operationService.getOpConut(answer.getAid().toString(),"1"));
        }
        return ResponseBo.ok().put("ans", answers);
    }

    @RequestMapping("/getUserSc")
    @ResponseBody
    public ResponseBo getUserSc(HttpServletRequest request, @RequestBody @RequestParam("uid") String uid) {
        List<Operation> operations = operationService.getOpList(uid, null, "2");
        HttpSession session = request.getSession();
        uid = (String) session.getAttribute("userid");
        for (Operation operation : operations) {
            Answer answer = answerService.getAns(Integer.parseInt(operation.getObo()));
            User user = identityService.getUser(answer.getAhdz());
            if (user.getGxqm() == null)
                user.setGxqm("");
            answer.setUser(user);
            answer.setProblem(problemService.getPro(answer.getAhdwt()));
            if (uid != null) {
                if (operationService.getOp(uid, answer.getAid().toString(), "1") != null)
                    answer.setDz(true);
                if (operationService.getOp(uid, answer.getAid().toString(), "2") != null)
                    answer.setSc(true);
            }
            answer.setAplsl(commentService.getAnsComCount(answer.getAid()));
            answer.setAztsl(operationService.getOpConut(answer.getAid().toString(),"1"));
            operation.setAnswer(answer);
        }
        return ResponseBo.ok().put("ops", operations);
    }

    @RequestMapping("/getUserPro")
    @ResponseBody
    public ResponseBo getUserPro(HttpServletRequest request, @RequestBody @RequestParam("uid") String uid) {
            List<Problem> problems = problemService.getProList(uid);
            for(Problem problem:problems){
                problem.setPgzzsl(operationService.getOpConut(problem.getPid().toString(),"3"));
                problem.setPhdsl(answerService.getCountAns(problem.getPid()));
            }
            return ResponseBo.ok().put("pros",problems);

    }

}
