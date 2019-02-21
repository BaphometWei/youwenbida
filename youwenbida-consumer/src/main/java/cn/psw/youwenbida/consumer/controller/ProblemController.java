package cn.psw.youwenbida.consumer.controller;

import cn.psw.youwenbida.api.model.Problem;
import cn.psw.youwenbida.api.service.CommentService;
import cn.psw.youwenbida.api.service.ProblemService;
import cn.psw.youwenbida.api.utils.ResponseBo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class ProblemController {

    @Reference
    ProblemService problemService;
    @Reference
    CommentService commentService;

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
    public Problem getPro(String id){
        Problem problem = problemService.getPro(Integer.parseInt(id));
        problem.setPplsl(commentService.getProComCount(Integer.parseInt(id)));
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

}
