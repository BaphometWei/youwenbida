package cn.psw.youwenbida.consumer.controller;

import cn.psw.youwenbida.api.model.Answer;
import cn.psw.youwenbida.api.model.User;
import cn.psw.youwenbida.api.service.*;
import cn.psw.youwenbida.api.utils.ResponseBo;
import cn.psw.youwenbida.consumer.utils.Lucence;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class SearchController {

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

    @RequestMapping("/toSearch")
    public String toSearch(){
        return "/pages/search.html";
    }

    @RequestMapping("/searchPro")
    @ResponseBody
    public ResponseBo searchPro(HttpServletRequest request, @RequestBody @RequestParam("q")String q) throws Exception{
        List<Map<String,Object>> list = Lucence.showSearchResults(q,"pro");
        List<Answer> answers = new ArrayList<>();
        HttpSession session = request.getSession();
        String uid = (String)session.getAttribute("userid");
        if(list!=null) {
            for (Map<String, Object> map : list) {
                Answer answer = answerService.searchProAns((String) map.get("id"));
                if (answer != null) {
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
                    answer.getProblem().setPtitle((String)map.get("text"));
                    answers.add(answer);
                }
            }
        }
        return ResponseBo.ok().put("ans",answers);
    }

    @RequestMapping("/searchAns")
    @ResponseBody
    public ResponseBo searchAns(HttpServletRequest request, @RequestBody @RequestParam("q")String q) throws Exception{
        List<Map<String,Object>> list = Lucence.showSearchResults(q,"ans");
        List<Answer> answers = new ArrayList<>();
        HttpSession session = request.getSession();
        String uid = (String)session.getAttribute("userid");
        if(list!=null) {
            for (Map<String, Object> map : list) {
                Answer answer = answerService.getAns(Integer.parseInt((String) map.get("id")));
                if (answer != null) {
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
                    answer.setAhd((String)map.get("text"));
                    answers.add(answer);
                }
            }
        }
        return ResponseBo.ok().put("ans",answers);
    }

    @RequestMapping("/searchUser")
    @ResponseBody
    public ResponseBo searchUser(HttpServletRequest request, @RequestBody @RequestParam("q")String q) throws Exception{
        List<Map<String,Object>> list = Lucence.showSearchResults(q,"user");
        List<User> users = new ArrayList<>();
        HttpSession session = request.getSession();
        String uid = (String)session.getAttribute("userid");
        if(list!=null) {
            for (Map<String, Object> map : list) {
                User user = identityService.getUser((String)map.get("id"));
                user.setName((String)map.get("text"));
                user.setTwsl(problemService.getCountUserPro(user.getId()));
                user.setHdsl(answerService.getCountUserAns(user.getId()));
                user.setGzzsl(operationService.getOpConut(user.getId(),"5"));
                if(uid!=null)
                    if (operationService.getOp(uid, user.getId(), "5") != null)
                        user.setIsgz(true);
                users.add(user);
            }
        }
        return ResponseBo.ok().put("users",users);
    }
}
