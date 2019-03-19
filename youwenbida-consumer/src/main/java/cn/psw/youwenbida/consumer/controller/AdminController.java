package cn.psw.youwenbida.consumer.controller;


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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    @Reference(timeout = 5000,retries = 0)
    AnswerService answerService;
    @Reference(timeout = 5000,retries = 0)
    OperationService operationService;
    @Reference(timeout = 5000,retries = 0)
    ProblemService problemService;
    @Reference(timeout = 5000,retries = 0)
    IdentityService identityService;
    @Reference(timeout = 5000,retries = 0)
    CommentService commentService;
    @Reference(timeout = 5000,retries = 0)
    TopicService topicService;
    @Reference(timeout = 5000,retries = 0)
    NoticeService noticeService;


    @RequestMapping("/admin-index")
    public String admsy(){
        return "/pages/admin/index.html";
    }

    @RequestMapping("/admin-getUserOp")
    @ResponseBody
    public ResponseBo admingetUserOp(@RequestBody @RequestParam("op")String op, @RequestParam("time")String time)throws ParseException{
        List<User> users = new ArrayList<>();
        List<Map<String,Object>> list = new ArrayList<>();
        String uid = null;
        if(op.equals("1")) {
            list = problemService.getUserByDate(time);
            uid = "ptcz";
        }
        else{
            list = answerService.getUserByDate(time);
            uid = "ahdz";
        }
        for(Map<String,Object> map:list){
            User user = identityService.getUser((String)map.get(uid));
            user.setTwsl(problemService.getCountUserPro(user.getId(),time));
            user.setHdsl(answerService.getCountUserAns(user.getId(),time));
            users.add(user);
        }
        return ResponseBo.ok().put("op",op).put("time",time).put("users",users);
    }

    @RequestMapping("/admin-searchUser")
    @ResponseBody
    public ResponseBo searchUser(HttpServletRequest request, @RequestBody @RequestParam("name")String name) throws Exception{
        List<Map<String,Object>> list = Lucence.showSearchResults(name,"user");
        List<User> users = new ArrayList<>();
        HttpSession session = request.getSession();
        String uid = (String)session.getAttribute("userid");
        if(list!=null) {
            for (Map<String, Object> map : list) {
                User user = identityService.getUser((String)map.get("id"));
                user.setName((String)map.get("text"));
                user.setTwsl(problemService.getCountUserPro(user.getId(),"10000"));
                user.setHdsl(answerService.getCountUserAns(user.getId(),"10000"));
                users.add(user);
            }
        }
        return ResponseBo.ok().put("users",users);
    }

}
